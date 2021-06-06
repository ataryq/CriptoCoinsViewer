package com.example.udimitestproject.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SparkLineBitmap extends View {

    private List<Float> mSparkLines;
    private Paint mPaint;

    public SparkLineBitmap(List<String> sparkLines, Context context, int color) {
        super(context);
        mSparkLines = convertStringArrayToNumbers(sparkLines);
        mSparkLines = normalizeSparkLines(mSparkLines);

        if(color != -1) {
            mPaint = new Paint();
            mPaint.setColor(color);
            mPaint.setStrokeWidth(1);
        }
    }

    public List<Float> getSparkLines() {
        return mSparkLines;
    }

    public Bitmap getBitmap() {
        Bitmap bitmapSource;
        bitmapSource = Bitmap.createBitmap(mSparkLines.size(),
                mSparkLines.size(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapSource);
        drawToCanvas(canvas);
        return bitmapSource;
    }

    public static List<Float> normalizeSparkLines(List<Float> sparkLines) {
        List<Float> normalizedSparkLines = new ArrayList<>();

        if(sparkLines.isEmpty())
            return normalizedSparkLines;

        float min = sparkLines.get(0);
        float max = sparkLines.get(0);

        for (Float num: sparkLines) {
            min = Math.min(num, min);
            max = Math.max(num, max);
        }

        float range = (max - min);

        for (Float num: sparkLines) {
            normalizedSparkLines.add((num - min) / range * sparkLines.size());
        }

        return normalizedSparkLines;
    }

    public static Float parseNumber(String num) {
        Float parsed = null;
        try {
            parsed = Float.parseFloat(num);
        }
        catch (Exception ex) {
            //ex.printStackTrace();
        }
        return parsed == null ? 0.0f : parsed;
    }

    public static List<Float> convertStringArrayToNumbers(List<String> sparkLines) {
        List<Float> sparkLinesNumber = new ArrayList<>();
        for (String num: sparkLines) {
            sparkLinesNumber.add(parseNumber(num));
        }

        return sparkLinesNumber;
    }

    public void drawToCanvas(Canvas canvas) {
        for (int i = 1; i < mSparkLines.size(); ++i) {
            canvas.drawLine(i - 1, mSparkLines.get(i - 1),
                    i, mSparkLines.get(i), mPaint);
        }
    }

}

