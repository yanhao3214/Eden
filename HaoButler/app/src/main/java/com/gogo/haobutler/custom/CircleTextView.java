package com.gogo.haobutler.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.gogo.haobutler.R;

/**
 * @author: 闫昊
 * @date: 2018/6/13 0013
 * @function:
 */
public class CircleTextView extends View {
    private String mText;
    private int mCircleColor;
    private int mTextColor;
    private float mTextSize;
    private Integer defSize;
    private Paint mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Rect mBound = new Rect();

    public CircleTextView(Context context) {
        super(context);
        init();
    }

    public CircleTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义的样式属性
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleTextView);
//        int count = a.getIndexCount();
//        for (int i = 0; i < count; i++) {
//            int attr = a.getIndex(i);
//            switch (attr) {
//                case R.styleable.CircleTextView_circle_text:
//                    mText = a.getString(attr);
//                    break;
//                case R.styleable.CircleTextView_circle_yh_color:
//                    mCircleColor = a.getColor(attr, Color.BLUE);
//                    break;
//                case R.styleable.CircleTextView_text_color:
//                    mTextColor = a.getColor(attr, Color.RED);
//                    break;
//                case R.styleable.CircleTextView_text_size:
//                    mTextSize = a.getFloat(attr, 16);
//                    break;
//                case R.styleable.CircleTextView_circle_defSize:
//                    defSize = a.getInt(attr, 100);
//                    break;
//                default:
//                    break;
//            }
//        }
        mText = a.getString(R.styleable.CircleTextView_circle_text);
        mCircleColor = a.getColor(R.styleable.CircleTextView_circle_yh_color, Color.BLACK);
        mTextColor = a.getColor(R.styleable.CircleTextView_text_color, Color.RED);
        mTextSize = a.getFloat(R.styleable.CircleTextView_text_size, 16);
        defSize = a.getInteger(R.styleable.CircleTextView_circle_defSize, 100);
        a.recycle();
        init();
    }

    private void init() {
        mCirclePaint.setColor(mCircleColor);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.getTextBounds(mText, 0, mText.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, 200);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(200, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, 200);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        int radius = Math.min(width, height) / 2;
        canvas.drawCircle(width / 2 + getPaddingLeft(), height / 2 + getPaddingTop(),
                radius, mCirclePaint);
        //在View中心绘制Text，绘制起点为文字左下角
        canvas.drawText(mText, width / 2 + getPaddingLeft() - mBound.width() / 2,
                height / 2 + getPaddingTop() + mBound.height() / 2, mTextPaint);
    }
}
