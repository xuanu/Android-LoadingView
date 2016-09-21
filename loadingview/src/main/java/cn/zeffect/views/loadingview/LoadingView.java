package cn.zeffect.views.loadingview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 不停旋转的进度条
 * 用最小的边求长度
 * Created by xuan on 2016/9/20.
 */
public class LoadingView extends View {
    private Paint mProgressPaint;
    private RectF mRectf;
    private float mRadius = 20f;
    private float mStrokeWidth = 5f;
    private int mDefaultColor = 0xff1976D2;
    private int mProgress = 0;
    private int centerX, centerY;
    /**
     * 是否显示圆心
     **/
    private boolean showCenter = false;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public LoadingView(Context context, AttributeSet attrs, int defaulStyle) {
        super(context, attrs, defaulStyle);
        mStrokeWidth = dip2px(getContext(), mStrokeWidth);
        mRadius = dip2px(getContext(), mRadius);
        //
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
        try {
            mStrokeWidth = array.getDimension(R.styleable.LoadingView_border_width, mStrokeWidth);
            mDefaultColor = array.getColor(R.styleable.LoadingView_border_color, mDefaultColor);
        } finally {
            array.recycle();
        }
        //
        init();
    }

    private void init() {
        if (mStrokeWidth > mRadius) {
            mStrokeWidth = mRadius;
        }
        //
        mProgressPaint = new Paint();
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(mStrokeWidth);
        mProgressPaint.setColor(mDefaultColor);
        //
        mRectf = new RectF();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = (int) (getPaddingLeft() + mRadius * 2 + mStrokeWidth * 2 + getPaddingRight());
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = (int) (getPaddingTop() + mRadius * 2 + mStrokeWidth * 2 + getPaddingBottom());
        }
        mRadius = (width > height ? (height - getPaddingTop() - getPaddingBottom() - mStrokeWidth * 2) / 2 : (width - getPaddingLeft() - getPaddingRight() - mStrokeWidth * 2) / 2);
        centerX = width / 2;
        centerY = height / 2;
        setMeasuredDimension(width, height);
        initRectF();
    }

    /***
     * 初始化绘画区域
     */
    private void initRectF() {
        int viewSize = (int) (mRadius + mStrokeWidth);
        int halfViewSize = viewSize / 2;
        mRectf.set(centerX - halfViewSize, centerY - halfViewSize, centerX + halfViewSize, centerY + halfViewSize);
        //
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mProgress += 1;
        canvas.drawArc(mRectf,
                mProgress - 90, mProgress, showCenter, mProgressPaint);
        if (mProgress > 360) {
            mProgress = -360;
        }
        if (mustInvalidate) {
            invalidate();
        }
    }

    /**
     * 是否必须刷新
     **/
    private boolean mustInvalidate = true;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        mustInvalidate = true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mustInvalidate = false;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
