package com.example.udimitestproject.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.example.udimitestproject.R;
import com.example.udimitestproject.Tools;
import com.example.udimitestproject.coinsData.CoinsItem;
import com.example.udimitestproject.databinding.ListOfCoinsItemBinding;

import java.text.DecimalFormat;

public class CoinsListAdapterItem extends RecyclerView.ViewHolder {

    private ListOfCoinsItemBinding mBinding;
    private CoinsItem mCoinInfo;
    private int mOrder;

    public CoinsListAdapterItem(ListOfCoinsItemBinding binding) {
        super(binding.getRoot());
        mBinding = binding;
    }

    public void bind(CoinsItem coinInfo, int order) {
        mCoinInfo = coinInfo;
        mOrder = order;
        Context context = mBinding.getRoot().getContext();

        mBinding.name.setText(cut(mCoinInfo.getName(), 9));
        mBinding.setCoinItem(mCoinInfo);
        mBinding.order.setText(String.valueOf(mOrder + 1));
        mBinding.price.setText("$ " + parseAndCutPrice());
        mBinding.volume.setText(makeVolumeText());
        mBinding.dayChange.setText(makeDayChangeText());
        loadCoinIcon();
        mBinding.sparkline.setImageBitmap(createSparklineBitmap(context));
    }

    private String cut(String str, int  maxSymbols) {
        String cutString = str;
        if(str.length() > maxSymbols) {
            cutString = str.substring(0, maxSymbols);
            cutString += "...";
        }

        return cutString;
    }

    private String parseAndCutPrice() {
        return parseAndCutNumber(mCoinInfo.getPrice(), 3);
    }

    private Double cutNumber(double number, int maxSymbols) {
        int tenSymbols = (int) Math.log10(number);

        if(tenSymbols > maxSymbols)
            tenSymbols = maxSymbols;
        int numbersAfterDot = maxSymbols - tenSymbols;
        return Tools.round(number, numbersAfterDot);
    }

    private String convertDoubleToString(Double number) {
        if(number - number.intValue() == 0)
            return new Integer(number.intValue()).toString();
        else
            return number.toString();
    }

    private String parseAndCutNumber(String numberStr, int maxSymbols) {
        Double number = Tools.parseNumber(numberStr);
        Double cutNumber = cutNumber(number, maxSymbols);
        return convertDoubleToString(cutNumber);
    }

    private void loadCoinIcon() {
        SvgLoader.pluck()
                .with(MainActivity.instance)
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(mCoinInfo.getIconUrl(), mBinding.coinIcon);
    }

    private Bitmap createSparklineBitmap(Context context) {
        int colorSparkline = getChange() > 0 ? Color.GREEN : Color.RED;
        SparkLineBitmap canvas =
                new SparkLineBitmap(mCoinInfo.getSparkline(), context, colorSparkline);
        return canvas.getBitmap();
    }

    /*
        The result is opposite to visualization in SparkLine.
        Probably a bug on the server side.
     */
    private double getChange() {
        if(mCoinInfo == null)
            return 0.0;

        double change = parseAndRoundDouble(mCoinInfo.getChange());
        change = -change;
        return change;
    }

    private static Double parseAndRoundDouble(String number) {
        return Tools.round(Tools.parseNumber(number), 2);
    }

    private String makeVolumeText() {

        double volume = Double.parseDouble (mCoinInfo.getJsonMember24hVolume());
        String volumePostfix = "";

        if(volume > 10e12) {
            volumePostfix = "T";
            volume /= 1e12;
        }
        else if(volume > 1e9) {
            volumePostfix = "B";
            volume /= 1e9;
        }
        else if(volume > 1e6) {
            volumePostfix = "M";
            volume /= 1e6;
        }

        String cutVolume = convertDoubleToString(cutNumber(volume, 2));

        return "$ " + cutVolume + volumePostfix;
    }

    private String makeDayChangeText() {
        double change = getChange();

        String addPrefix = "";
        if(change > 0) {
            mBinding.dayChange.setTextColor(Color.GREEN);
            addPrefix = "+ ";
        }
        else {
            mBinding.dayChange.setTextColor(Color.RED);
        }

        return addPrefix + change + " %";
    }
}
