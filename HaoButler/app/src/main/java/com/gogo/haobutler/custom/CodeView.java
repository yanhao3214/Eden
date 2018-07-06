package com.gogo.haobutler.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;

import com.gogo.haobutler.R;

/**
 * @author: 闫昊
 * @date: 2018/6/13 0013
 * @function: todo--->
 * 1.code_type
 * 2.frame_style
 * 3.enum命名
 * 4.字体过大溢出
 * 5.写博客
 */
public class CodeView extends View {
    private static final int COLOR_PARTS = 3;
    private Random mRandom = new Random();
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mPointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mFramePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    /**
     * 验证码内容
     */
    private char[] mCode;

    /**
     * 验证码长度
     */
    private int mLength = 4;

    /**
     * 验证码字体大小
     */
    private float mCodeSize = 66;

    /**
     * 随机的线条数
     */
    private int mLines = 33;

    /**
     * 随机的点数
     */
    private int mPoints = 188;

    /**
     * 是否包含随机线条
     */
    private boolean withLines = true;

    /**
     * 是否包含随机点
     */
    private boolean withPoints = true;

    /**
     * 是否包含边框
     */
    private boolean withFrame = true;

    /**
     * 边框线条宽度
     */
    private float frameWidth = 0.5f;

    /**
     * 边框线条颜色
     */
    private int frameColor = Color.GREEN;

    /**
     * 边框样式
     */
    private int frameStyle;

    public CodeView(Context context) {
        super(context);
        init();
    }

    public CodeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CodeView);
        mLength = a.getInt(R.styleable.CodeView_code_length, 4);
        mCodeSize = a.getFloat(R.styleable.CodeView_code_size, 66);
        mLines = a.getInt(R.styleable.CodeView_lines, 33);
        mPoints = a.getInt(R.styleable.CodeView_points, 188);
        withLines = a.getBoolean(R.styleable.CodeView_with_lines, true);
        withPoints = a.getBoolean(R.styleable.CodeView_with_points, true);
        withFrame = a.getBoolean(R.styleable.CodeView_with_frame, false);
        frameWidth = a.getFloat(R.styleable.CodeView_frame_width, 1);
        frameColor = a.getColor(R.styleable.CodeView_frame_color, Color.GREEN);
        frameStyle = a.getInt(R.styleable.CodeView_frame_style, 0);
        a.recycle();
        //点击刷新
        this.setOnClickListener(v -> postInvalidate());
        init();
    }

    public char[] getCode() {
        return mCode;
    }

    private void init() {
    }

    /**
     * 处理WrapContent情况：
     * 1.若宽高都为WrapContent，则设置固定尺寸120*60
     * 2.若仅一个WrapContent，则按宽高2:1的比例设置另一尺寸
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(120, 60);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(heightSize * 2, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, widthSize / 2);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int width = getWidth() - getPaddingLeft() - getPaddingRight();
        int height = getHeight() - getPaddingTop() - getPaddingBottom();
        drawText(width, height, canvas);
        if (withLines) {
            drawLines(width, height, canvas);
        }
        if (withPoints) {
            drawPoints(width, height, canvas);
        }
        if (withFrame) {
            drawFrame(width, height, canvas);
        }
    }

    /**
     * 生成随机验证码内容，并在适当位置绘制
     * 若mLength == RANDOM，则验证码的长度为4-6之间的随机数
     *
     * @param width
     * @param height
     * @param canvas
     */
    private void drawText(int width, int height, Canvas canvas) {
        mCode = getRandomCode(mLength);
        int n = mCode.length;
        for (int i = 0; i < n; i++) {
            randomTextStyle(mTextPaint);
            float x = (float) (width * (i + 0.2) / n + getPaddingLeft());
            float y = mRandom.nextFloat() * height * 3 / 5 + height / 3 + getPaddingTop();
            canvas.drawText(mCode[i] + "", x, y, mTextPaint);
        }
    }

    /**
     * 随机绘制线条
     *
     * @param width
     * @param height
     * @param canvas
     */
    private void drawLines(int width, int height, Canvas canvas) {
        for (int i = 0; i < mLines; i++) {
            mLinePaint.setColor(getRandomColor());
            mLinePaint.setStrokeWidth((float) 0.5 * (1 + mRandom.nextFloat()));
            float startX = mRandom.nextFloat() * width + getPaddingLeft();
            float startY = mRandom.nextFloat() * height + getPaddingTop();
            float endX = mRandom.nextFloat() * width + getPaddingLeft();
            float endY = mRandom.nextFloat() * height + getPaddingTop();
            canvas.drawLine(startX, startY, endX, endY, mLinePaint);
        }
    }

    /**
     * 随机绘制点
     *
     * @param width
     * @param height
     * @param canvas
     */
    private void drawPoints(int width, int height, Canvas canvas) {
        for (int i = 0; i < mPoints; i++) {
            mPointPaint.setColor(getRandomColor());
            float x = mRandom.nextFloat() * width + getPaddingLeft();
            float y = mRandom.nextFloat() * height + getPaddingTop();
            float r = mRandom.nextFloat() + (float) 0.5;
            canvas.drawCircle(x, y, r, mPointPaint);
        }
    }

    /**
     * 绘制边框
     *
     * @param width
     * @param height
     * @param canvas
     */
    private void drawFrame(int width, int height, Canvas canvas) {
        mFramePaint.setStyle(Paint.Style.STROKE);
        mFramePaint.setColor(frameColor);
        mFramePaint.setStrokeWidth(frameWidth);
        switch (frameStyle) {
            case 1:
                //画虚线框
                mFramePaint.setPathEffect(new DashPathEffect(new float[]{20, 10}, 0));
                break;
            case 2:
                //画双边框
                canvas.drawRect(5, 3, width - 5, height - 3, mFramePaint);
                break;
            case 3:
                //画双边虚线框
                mFramePaint.setPathEffect(new DashPathEffect(new float[]{20, 10}, 0));
                canvas.drawRect(5, 3, width - 5, height - 3, mFramePaint);
                break;
            default:
                break;
        }
        canvas.drawRect(0, 0, width, height, mFramePaint);
    }

    /**
     * 随机生成固定位数的字符数组，由数字和字母（包括大小写）组成
     *
     * @param length
     * @return
     */
    private char[] getRandomCode(int length) {
        char[] code = new char[length == 0 ? mRandom.nextInt(3) + 4 : length];
        int n = code.length;
        for (int i = 0; i < n; i++) {
            if (mRandom.nextBoolean()) {
                code[i] = (char) (mRandom.nextInt(10) + 48);
            } else {
                code[i] = (char) (mRandom.nextInt(26) + (mRandom.nextBoolean() ? 65 : 97));
            }
        }
        Log.d("yh", "code.length ==" + code.length);
        return code;
    }

    /**
     * 随机生成格式为"#******"的16进制颜色
     *
     * @return
     */
    private int getRandomColor() {
        StringBuilder color = new StringBuilder("#");
        for (int i = 0; i < COLOR_PARTS; i++) {
            String partialColor = Integer.toHexString(mRandom.nextInt(256)).toUpperCase();
            partialColor = partialColor.length() == 1 ? "0" + partialColor : partialColor;
            color.append(partialColor);
        }
        return Color.parseColor(color.toString());
    }

    /**
     * 随机设置字体样式：颜色、粗细、斜度
     *
     * @param paint
     */
    private void randomTextStyle(Paint paint) {
        paint.setFakeBoldText(mRandom.nextBoolean());
        float skewX = mRandom.nextBoolean() ? mRandom.nextFloat() : -mRandom.nextFloat();
        paint.setTextSkewX(skewX);
        paint.setTextSize(mCodeSize);
        //随机画笔颜色的另一种方法,其中透明度上限设为200，否则容易看不清楚
        paint.setARGB(200 + mRandom.nextInt(56), mRandom.nextInt(256),
                mRandom.nextInt(256), mRandom.nextInt(256));
    }
}
