package com.sevketbuyukdemir.colorseekbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class ColorSeekBar extends View {
    private final Context context;
    private OnColorSeekBarTouchListener onColorSeekBarTouchListener;

    /**
     * Set function for OnFourWayArrowTouchListener, call from activity after define colorSeekBar view.
     * @param onColorSeekBarTouchListener
     */
    public void setOnColorSeekBarTouchListener(OnColorSeekBarTouchListener onColorSeekBarTouchListener) {
        this.onColorSeekBarTouchListener = onColorSeekBarTouchListener;
    }

    /**
     * Listener interface for define required functions.
     */
    public interface OnColorSeekBarTouchListener {
        public abstract void onStartTrackingTouch(ColorSeekBar colorSeekBar, int WHICH_COLOR);
        public abstract void onMoveTrackingTouch(ColorSeekBar colorSeekBar, int WHICH_COLOR);
        public abstract void onStopTrackingTouch(ColorSeekBar colorSeekBar, int WHICH_COLOR);
    }

    private int[] main_colors;
    private final int[] main_colors_red = {255, 255, 51, 51, 51, 255, 255};
    private final int[] main_colors_green = {51, 255, 255, 255, 51, 51, 51};
    private final int[] main_colors_blue = {51, 51, 51, 255, 255, 255, 51};

    private Paint pointerPaint;
    private Path pointerPath;

    private void calculateMainColors() {
        this.main_colors = new int[7];
        for(int i = 0; i < 7; i++) {
            main_colors[i] = Color.rgb(main_colors_red[i], main_colors_green[i], main_colors_blue[i]);
        }
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        calculateMainColors();
        GradientDrawable gd = new GradientDrawable();
        gd.setColors(main_colors);
        gd.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        gd.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
        gd.setShape(GradientDrawable.RECTANGLE);
        gd.setStroke(3, Color.BLUE);
        gd.setSize(getWidth(), getHeight());
        setBackground(gd);

        pointerPath = new Path();

        pointerPaint = new Paint();
        pointerPaint.setAntiAlias(true);
        pointerPaint.setDither(true);
        pointerPaint.setStyle(Paint.Style.FILL);
        pointerPaint.setColor(Color.GRAY);
    }

    public ColorSeekBar(Context context) {
        super(context);
        this.context = context;
        init(context, null, 0);
    }

    public ColorSeekBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context, attrs, 0);
    }

    public ColorSeekBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        pointerPaint.setShader(null);
        canvas.drawPath(pointerPath, pointerPaint);
        pointerPath.reset();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float height = getHeight();

        float width = getWidth();
        float partition = width/6f;
        float difference = 204/partition;
        float pointer_width = difference*30;

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (onColorSeekBarTouchListener != null) {
                    pointerPath.moveTo(x, 0);
                    pointerPath.lineTo(x, height);
                    pointerPath.lineTo(x + pointer_width, height);
                    pointerPath.lineTo(x + pointer_width, 0);
                    onColorSeekBarTouchListener.onStartTrackingTouch(this, calculatePressedColor(event));
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (onColorSeekBarTouchListener != null) {
                    pointerPath.moveTo(x, 0);
                    pointerPath.lineTo(x, height);
                    pointerPath.lineTo(x + pointer_width, height);
                    pointerPath.lineTo(x + pointer_width, 0);
                    onColorSeekBarTouchListener.onMoveTrackingTouch(this, calculatePressedColor(event));
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (onColorSeekBarTouchListener != null) {
                    pointerPath.moveTo(x, 0);
                    pointerPath.lineTo(x, height);
                    pointerPath.lineTo(x + pointer_width, height);
                    pointerPath.lineTo(x + pointer_width, 0);
                    onColorSeekBarTouchListener.onStopTrackingTouch(this, calculatePressedColor(event));
                    invalidate();
                }
                break;
        }
        return true;
    }

    private int calculatePressedColor(MotionEvent event) {
        int color = Color.TRANSPARENT;
        float width = getWidth();
        float partition = width/6f;
        float difference = 204/partition;
        float current_value;
        int x = (int) event.getX();
        if(x < partition) {
            current_value = x * difference;
            color = Color.rgb(255, ((int)current_value), 51);
        } else if(x == (partition)) {
            color = Color.rgb(255, 255, 51);
        } else if(x < (partition*2)) {
            x = (int) (x - partition);
            current_value = x * difference;
            color = Color.rgb(((int)(255 - current_value)), 255, 51);
        } else if(x == (partition*2)) {
            color = Color.rgb(51, 255, 51);
        } else if(x < (partition*3)) {
            x = (int) (x - (partition*2));
            current_value = x * difference;
            color = Color.rgb(51, 255, ((int)(51 + current_value)));
        } else if(x == (partition*3)) {
            color = Color.rgb(51, 255, 255);
        } else if(x < (partition*4)) {
            x = (int) (x - (partition*3));
            current_value = x * difference;
            color = Color.rgb(51, ((int)(255 - current_value)), 255);
        } else if(x == (partition*4)) {
            color = Color.rgb(51, 51, 255);
        } else if(x < (partition*5)) {
            x = (int) (x - (partition*4));
            current_value = x * difference;
            color = Color.rgb(((int)(51 + current_value)), 51, 255);
        } else if(x == (partition*5)) {
            color = Color.rgb(255, 51, 255);
        } else if(x < (partition*6)) {
            x = (int) (x - (partition*5));
            current_value = x * difference;
            color = Color.rgb(255, 51, ((int)(255 - current_value)));
        } else if(x == (partition*6)) {
            color = Color.rgb(255, 51, 51);
        }
        return color;
    }

}
