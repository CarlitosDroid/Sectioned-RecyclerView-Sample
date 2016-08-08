package com.example.carlitos.swipeitemrecycler.view.control;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;

import com.example.carlitos.swipeitemrecycler.R;


/**
 * Created by Ricardo Bravo on 19/05/16.
 *
 */

public class ImageProfilePercentage extends ImageView {

    private static int ANIMATION_DURATION = 1200;
    private static int ANIMATION_DELAY = 500;
    private float mMax = 100;
    private float mProgress = 0;
    private float mCurrentProgress = 0;
    private float mBackgroundRingSize = 40;
    private float mProgressRingSize = mBackgroundRingSize;
    private boolean mProgressRingOutline = false;
    private int mBackgroundRingColor = 0xAA83d0c9;
    private int mProgressRingColor =  0xff009688;
    private Paint.Cap mProgressRingCap = Paint.Cap.BUTT;
    private ObjectAnimator mAnimator;
    private Interpolator mDefaultInterpolator = new OvershootInterpolator();
    private int mViewHeight = 0;
    private int mViewWidth = 0;
    private int mPaddingTop;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    private Paint mProgressRingPaint;
    private Paint mBackgroundRingPaint;
    private RectF mRingBounds;
    private float mOffsetRingSize;
    private Paint mMaskPaint;
    private Bitmap mOriginalBitmap;
    private Canvas mCacheCanvas;

    public ImageProfilePercentage(Context context) {
        super(context);
    }

    public ImageProfilePercentage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0, 0);
    }

    public ImageProfilePercentage(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ImageProfilePercentage(Context context, @Nullable AttributeSet attrs, int defStyleAttr,
                                  int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs, defStyleAttr, defStyleRes);
    }

    private void init(@Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.ImageProfilePercentage, defStyleAttr, defStyleRes);

        setMax(a.getFloat(
                R.styleable.ImageProfilePercentage_max, mMax));
        setProgress(a.getFloat(
                R.styleable.ImageProfilePercentage_progress, mProgress));
        if (!a.hasValue(R.styleable.ImageProfilePercentage_backgroundSize)) {
            if (a.hasValue(R.styleable.ImageProfilePercentage_circleSize)) {
                setProgressRingSize(a.getDimension(
                        R.styleable.ImageProfilePercentage_circleSize, mProgressRingSize));
                setBackgroundRingSize(mProgressRingSize);
            }
        } else {
            setBackgroundRingSize(a.getDimension(
                    R.styleable.ImageProfilePercentage_backgroundSize, mBackgroundRingSize));
            setProgressRingSize(a.getDimension(
                    R.styleable.ImageProfilePercentage_circleSize, mProgressRingSize));
        }
        setProgressRingOutline(
                a.getBoolean(R.styleable.ImageProfilePercentage_progressOutline, false));
        setBackgroundRingColor(a.getColor(
                R.styleable.ImageProfilePercentage_backgroundCircle, mBackgroundRingColor));
        setProgressRingColor(a.getColor(
                R.styleable.ImageProfilePercentage_progressColor, mProgressRingColor));
        setProgressRingCap(a.getInt(
                R.styleable.ImageProfilePercentage_animation, Paint.Cap.BUTT.ordinal()));

        a.recycle();

        setupAnimator();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int size;
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int widthWithoutPadding = width - getPaddingLeft() - getPaddingRight();
        int heightWithoutPadding = height - getPaddingTop() - getPaddingBottom();

        if (widthWithoutPadding > heightWithoutPadding) {
            size = heightWithoutPadding;
        } else {
            size = widthWithoutPadding;
        }

        setMeasuredDimension(
                size + getPaddingLeft() + getPaddingRight(),
                size + getPaddingTop() + getPaddingBottom());
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mViewWidth = w;
        mViewHeight = h;

        if(isProgressRingOutline()) {
            setPadding(
                    Float.valueOf(mBackgroundRingSize + getPaddingLeft()).intValue(),
                    Float.valueOf(mBackgroundRingSize + getPaddingTop()).intValue(),
                    Float.valueOf(mBackgroundRingSize + getPaddingRight()).intValue(),
                    Float.valueOf(mBackgroundRingSize + getPaddingBottom()).intValue());
        }

        setupBounds();
        setupBackgroundRingPaint();
        setupProgressRingPaint();

        requestLayout();
        invalidate();
    }

    private void setupBounds() {

        int minValue = Math.min(mViewWidth, mViewHeight);
        int xOffset = mViewWidth - minValue;
        int yOffset = mViewHeight - minValue;
        int outline = 0;

        if(isProgressRingOutline()) {
            outline = Float.valueOf(-mBackgroundRingSize).intValue();
        }

        mPaddingTop = outline + this.getPaddingTop() + (yOffset / 2);
        mPaddingBottom = outline + this.getPaddingBottom() + (yOffset / 2);
        mPaddingLeft = outline + this.getPaddingLeft() + (xOffset / 2);
        mPaddingRight = outline + this.getPaddingRight() + (xOffset / 2);

        float biggerRingSize = mBackgroundRingSize > mProgressRingSize
                ? mBackgroundRingSize : mProgressRingSize;

        mOffsetRingSize = biggerRingSize / 2;

        int width = getWidth();
        int height = getHeight();

        mRingBounds = new RectF(
                mPaddingLeft + mOffsetRingSize,
                mPaddingTop + mOffsetRingSize,
                width - mPaddingRight - mOffsetRingSize,
                height - mPaddingBottom - mOffsetRingSize);
    }

    private void setupMask() {
        mOriginalBitmap = Bitmap.createBitmap(
                getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Shader shader = new BitmapShader(mOriginalBitmap,
                Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        mMaskPaint = new Paint();
        mMaskPaint.setAntiAlias(true);
        mMaskPaint.setShader(shader);
    }

    private void setupProgressRingPaint() {
        mProgressRingPaint = new Paint();
        mProgressRingPaint.setColor(mProgressRingColor);
        mProgressRingPaint.setAntiAlias(true);
        mProgressRingPaint.setStrokeCap(mProgressRingCap);
        mProgressRingPaint.setStyle(Paint.Style.STROKE);
        mProgressRingPaint.setStrokeWidth(mProgressRingSize);
    }

    private void setupBackgroundRingPaint() {
        mBackgroundRingPaint = new Paint();
        mBackgroundRingPaint.setColor(mBackgroundRingColor);
        mBackgroundRingPaint.setAntiAlias(true);
        mBackgroundRingPaint.setStyle(Paint.Style.STROKE);
        mBackgroundRingPaint.setStrokeWidth(mBackgroundRingSize);
    }

    private void setupAnimator() {
        mAnimator = ObjectAnimator.ofFloat(
                this, "progress", this.getProgress(), this.getProgress());
        mAnimator.setDuration(ANIMATION_DURATION);
        mAnimator.setInterpolator(mDefaultInterpolator);
        mAnimator.setStartDelay(ANIMATION_DELAY);
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setCurrentProgress((float) animation.getAnimatedValue());
                setProgress(getCurrentProgress());
            }
        });
    }

    @SuppressWarnings("unused")
    public void startAnimation() {
        float finalProgress = this.getProgress();
        this.setProgress(this.getCurrentProgress());
        mAnimator.setFloatValues(this.getCurrentProgress(), finalProgress);
        mAnimator.start();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {

        if(mMaskPaint == null) {
            setupMask();
        }

        if(mCacheCanvas == null) {
            mCacheCanvas = new Canvas(mOriginalBitmap);
        }

        super.onDraw(mCacheCanvas);

        canvas.drawCircle(
                mRingBounds.centerX(),
                mRingBounds.centerY(),
                (mRingBounds.width() / 2) - (mBackgroundRingSize / 2),
                mMaskPaint);

        if (mBackgroundRingSize > 0){
            canvas.drawArc(mRingBounds, 360, 360, false, mBackgroundRingPaint);
        }

        if(mProgressRingSize > 0) {
            canvas.drawArc(mRingBounds, -90, getSweepAngle(), false, mProgressRingPaint);
        }
    }

    private float getSweepAngle() {
        return (360f / mMax * mProgress);
    }

    public ObjectAnimator getAnimator() {
        return mAnimator;
    }

    public float getMax() {
        return mMax;
    }

    public void setMax(float max) {
        mMax = max;
    }

    public float getCurrentProgress() {
        return mCurrentProgress;
    }

    public void setCurrentProgress(float currentProgress) {
        mCurrentProgress = currentProgress;
    }

    public float getProgress() {
        return mProgress;
    }

    public void setProgress(float progress) {
        if (progress < 0)
            this.mProgress = 0;
        else if (progress > 100)
            this.mProgress = 100;
        else
            this.mProgress = progress;
        invalidate();
    }

    public float getProgressRingSize() {
        return mProgressRingSize;
    }

    public void setProgressRingSize(float progressRingSize) {
        mProgressRingSize = progressRingSize;
    }

    public float getBackgroundRingSize() {
        return mBackgroundRingSize;
    }

    public void setBackgroundRingSize(float backgroundRingSize) {
        mBackgroundRingSize = backgroundRingSize;
    }

    public boolean isProgressRingOutline() {
        return mProgressRingOutline;
    }

    public void setProgressRingOutline(boolean progressRingOutline) {
        mProgressRingOutline = progressRingOutline;
    }

    public int getBackgroundRingColor() {
        return mBackgroundRingColor;
    }

    public void setBackgroundRingColor(int backgroundRingColor) {
        mBackgroundRingColor = backgroundRingColor;
    }

    public int getProgressRingColor() {
        return mProgressRingColor;
    }

    public void setProgressRingColor(int progressRingColor) {
        mProgressRingColor = progressRingColor;
    }

    public Paint.Cap getProgressRingCap() {
        return mProgressRingCap;
    }

    public void setProgressRingCap(int progressRingCap) {
        mProgressRingCap = getCap(progressRingCap);
    }

    private Paint.Cap getCap(int id) {
        for (Paint.Cap value : Paint.Cap.values()) {
            if (id == value.ordinal()) {
                return value;
            }
        }
        return Paint.Cap.BUTT;
    }
}
