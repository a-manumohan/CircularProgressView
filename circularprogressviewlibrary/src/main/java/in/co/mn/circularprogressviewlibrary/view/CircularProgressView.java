package in.co.mn.circularprogressviewlibrary.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import in.co.mn.circularprogressviewlibrary.R;

/**
 * Created by manuMohan on 15/04/02.
 */
public class CircularProgressView extends View {
    private Paint mStrokeCirclePaint;
    private Paint mProgressStrokePaint;
    private int mStrokeColor;
    private int mProgressStrokeColor;
    private float mStrokeWidth;
    private float mProgressStrokeWidth;

    private RectF mArcOval;
    private float mStrokeStartAngle;
    private float mStrokeSweepAngle;
    private float mProgressStrokeStartAngle;
    private float mProgressStrokeSweepAngle;

    private int mMaxProgress = 100;
    private int mCurrentProgress;

    private enum Direction {
        CLOCKWISE,
        ANTI_CLOCKWISE
    }

    private Direction mDirection;


    public CircularProgressView(Context context) {
        super(context);
        loadAttributes(null);
        init();
    }

    public CircularProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        loadAttributes(attrs);
        init();
    }

    public CircularProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        loadAttributes(attrs);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircularProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        loadAttributes(attrs);
        init();
    }


    private void init() {
        mStrokeCirclePaint = new Paint(0);
        mStrokeCirclePaint.setColor(mStrokeColor);
        mStrokeCirclePaint.setStyle(Paint.Style.STROKE);
        mStrokeCirclePaint.setStrokeWidth(mStrokeWidth);

        mProgressStrokePaint = new Paint(0);
        mProgressStrokePaint.setColor(mProgressStrokeColor);
        mProgressStrokePaint.setStyle(Paint.Style.STROKE);
        mProgressStrokePaint.setStrokeWidth(mProgressStrokeWidth);

        mCurrentProgress = 0;

        calculateAngles();
    }

    private void loadAttributes(AttributeSet attrs) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CircularProgressView, 0, 0);
        try {
            mStrokeWidth = a.getDimension(R.styleable.CircularProgressView_strokeWidth, 20.0f);
            mProgressStrokeWidth = a.getDimension(R.styleable.CircularProgressView_progressStrokeWidth, 20.0f);
            mStrokeColor = a.getColor(R.styleable.CircularProgressView_strokeColor, getContext().getResources().getColor(R.color.black));
            mProgressStrokeColor = a.getColor(R.styleable.CircularProgressView_progressStrokeColor, getContext().getResources().getColor(R.color.red));
            mProgressStrokeStartAngle = a.getInteger(R.styleable.CircularProgressView_startAngle, 0);
            mMaxProgress = a.getInteger(R.styleable.CircularProgressView_maxProgress, 100);
            mDirection = a.getInt(R.styleable.CircularProgressView_direction, 0) == 0 ? Direction.CLOCKWISE : Direction.ANTI_CLOCKWISE;
        } finally {
            a.recycle();
        }
    }

    private void calculateAngles() {
        if (mDirection == Direction.CLOCKWISE) {
            mProgressStrokeSweepAngle = 360.0f * ((float) mCurrentProgress / (float) mMaxProgress);
            mStrokeStartAngle = mProgressStrokeStartAngle + mProgressStrokeSweepAngle;
            mStrokeSweepAngle = 360 - mProgressStrokeSweepAngle;
        } else {
            mProgressStrokeSweepAngle = 360.0f * ((float) mCurrentProgress / (float) mMaxProgress) * -1;
            mStrokeStartAngle = (Math.abs(mProgressStrokeStartAngle) + Math.abs(mProgressStrokeSweepAngle)) * -1;
            mStrokeSweepAngle = (360 - Math.abs(mProgressStrokeSweepAngle)) * -1;
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int mCx = w / 2;
        int mCy = h / 2;

        int mRadius = w > h ? h / 2 : w / 2;
        mRadius -= mStrokeWidth > mProgressStrokeWidth ? mStrokeWidth : mProgressStrokeWidth;
        mArcOval = new RectF(mCx - mRadius, mCy - mRadius, mCx + mRadius, mCy + mRadius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawArc(mArcOval, mStrokeStartAngle, mStrokeSweepAngle, false, mStrokeCirclePaint);
        canvas.drawArc(mArcOval, mProgressStrokeStartAngle, mProgressStrokeSweepAngle, false, mProgressStrokePaint);
    }

    public void setMaxProgress(int maxProgress) {
        this.mMaxProgress = mMaxProgress;
    }

    public void setProgress(int progress) {
        mCurrentProgress = progress < mMaxProgress ? progress : mMaxProgress;
        calculateAngles();
        invalidate();
    }
}
