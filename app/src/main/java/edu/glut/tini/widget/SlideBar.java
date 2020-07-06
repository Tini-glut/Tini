package edu.glut.tini.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import edu.glut.tini.R;


public class SlideBar extends View {


    //字母高度
    private float letterHeight;
    private float textSize = 43.0f;
    private Paint paint = new Paint();
    private float baseHeight;
    //需要改变颜色的位置
    private int index;
    //字母改变监听
    private OnTouchLetterChangeListener onTouchLetterChangeListener;

    public void setOnTouchLetterChangeListener(OnTouchLetterChangeListener onTouchLetterChangeListener) {
        this.onTouchLetterChangeListener = onTouchLetterChangeListener;
    }

    private final String[] LETTERS = {"A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z"};

    public SlideBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        letterHeight = (float) h / LETTERS.length;
        float textHeight = fontMetrics.descent - fontMetrics.ascent;
        baseHeight = letterHeight / 2 + (textHeight / 2 - fontMetrics.descent);
    }

    //初始化画笔
    void initPaint() {
        paint.setColor(Color.GRAY);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(textSize);
        paint.setAntiAlias(true);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();

        float x = getWidth() / 2.0f;
        float y = baseHeight;
       /* for (String letter : LETTERS) {
            canvas.drawText(letter, x, y, paint);
            y += letterHeight;
        }*/

        for (int i = 0; i < LETTERS.length; i++) {
            initPaint();
            if (i==index){
                paint.setColor(R.color.colorPrimary);
            }
            canvas.drawText(LETTERS[i], x, y, paint);
            y+=letterHeight;
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                setBackgroundColor(Color.parseColor("#CCCCCC"));
                 index = getTouchIndex(event);
                String letter = LETTERS[index];
//                System.out.println(letter);
                if (onTouchLetterChangeListener != null) {
                    onTouchLetterChangeListener.onTouchLetterChange(letter);
                }
                break;
            case MotionEvent.ACTION_UP:
                setBackgroundColor(Color.parseColor("#FFFFFF"));
                if (onTouchLetterChangeListener != null) {
                    onTouchLetterChangeListener.onSlideFinish();
                }
                break;
        }
        return true;
    }

    private int getTouchIndex(MotionEvent event) {
        int index = (int) (event.getY() / getHeight() * LETTERS.length);
        if (index < 0) {
            index = 0;
        } else if (index >= LETTERS.length) {
            index = LETTERS.length - 1;
        }
        return index;
    }

    /**
     * 字母改变监听接口
     */
    public interface OnTouchLetterChangeListener {
        void onTouchLetterChange(String letter);

        void onSlideFinish();
    }

}
