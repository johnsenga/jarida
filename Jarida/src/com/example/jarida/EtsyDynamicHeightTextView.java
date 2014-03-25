package com.example.jarida;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 *
 * A {@link android.widget.TextView} that maintains a consistent width to height aspect ratio.
 * In the real world this would likely extend ImageView.
 */
public class EtsyDynamicHeightTextView extends TextView {

    private double mHeightRatio;

    public EtsyDynamicHeightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EtsyDynamicHeightTextView(Context context) {
        super(context);
    }

    public void setHeightRatio(double ratio) {
        if (ratio != mHeightRatio) {
            mHeightRatio = ratio;
            requestLayout();
        }
    }

    public double getHeightRatio() {
        return mHeightRatio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mHeightRatio > 0.0) {
            // set the image views size
            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = (int) (width * mHeightRatio);
            setMeasuredDimension(width, height);
        }
        else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}
