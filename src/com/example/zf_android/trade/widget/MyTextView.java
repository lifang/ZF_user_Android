package com.example.zf_android.trade.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Set the TextView and its top drawable to center vertical
 * <p/>
 * Created by Leo on 2015/2/9.
 */
public class MyTextView extends TextView {

    private Rect rect;

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        rect = new Rect();
        getPaint().getTextBounds(getText().toString(), 0, getText().length(), rect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Drawable[] drawables = getCompoundDrawables();
        if (drawables != null) {
            Drawable drawableTop = drawables[1];
            if (drawableTop != null) {
                int textHeight = rect.height();
                int drawablePadding = getCompoundDrawablePadding() + getPaddingTop() + getPaddingBottom();
                int drawableHeight = drawableTop.getIntrinsicHeight();
                float bodyHeight = textHeight + drawableHeight + drawablePadding;
                canvas.translate(0, (getHeight() - bodyHeight) / 2);
            }
        }
        super.onDraw(canvas);
    }
}