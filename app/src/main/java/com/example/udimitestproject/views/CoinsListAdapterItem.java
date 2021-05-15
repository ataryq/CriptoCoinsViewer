package com.example.udimitestproject.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;

import androidx.recyclerview.widget.RecyclerView;

import com.ahmadrosid.svgloader.SvgLoader;
import com.example.udimitestproject.R;
import com.example.udimitestproject.data.CoinsItem;
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
        mBinding.order.setText(String.valueOf(mOrder));
        mBinding.price.setText("$ " + parseAndRoundDouble(mCoinInfo.getPrice()));
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

    private void loadCoinIcon() {
        SvgLoader.pluck()
                .with(MainActivity.instance)
                .setPlaceHolder(R.mipmap.ic_launcher, R.mipmap.ic_launcher)
                .load(mCoinInfo.getIconUrl(), mBinding.coinIcon);
    }

    private Bitmap createSparklineBitmap(Context context) {
        int colorSparkline = getChange() > 0 ? Color.GREEN : Color.RED;
        SparkLineBitmap canvas = new SparkLineBitmap(
                context, mCoinInfo.getSparkline(), colorSparkline, new Float(getChange()));
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

    private static Double roundDouble(Double number) {
        if(number == null)
            return 0.0;

        DecimalFormat df = new DecimalFormat("#.##");

        Double parseDouble = null;
        try {
            parseDouble = Double.valueOf(df.format(number));
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return parseDouble == null ? 0.0 : parseDouble;
    }

    private static Double parseAndRoundDouble(String number) {
        if(number == null)
            return 0.0;

        Double parseDouble = null;
        try {
            parseDouble = Double.parseDouble(number);
        }
        catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return parseDouble == null ? 0.0 : roundDouble(parseDouble);
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

        return "$ " + roundDouble(volume) + volumePostfix;
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
