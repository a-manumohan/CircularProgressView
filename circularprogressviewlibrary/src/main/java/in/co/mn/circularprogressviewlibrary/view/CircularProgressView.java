package in.co.mn.circularprogressviewlibrary.view;

import android.annotation.TargetApi;
import android.content.Context;
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
    private float mStrokeWidth = 20.f;
    private float mProgressStrokeWidth = 25.0f;

    private int mRadius;
    private RectF mArcOval;
    private float mStrokeStartAngle;
    private float mStrokeSweepAngle;
    private float mProgressStrokeStartAngle;
    private float mProgressStrokeSweepAngle;

    private int mMaxProgress = 100;
    private int mCurrentProgress;


    public CircularProgressView(Context context) {
        super(context);
        init();
    }

    public CircularProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircularProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircularProgressView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        mStrokeCirclePaint = new Paint(0);
        mStrokeCirclePaint.setColor(getContext().getResources().getColor(R.color.black));
        mStrokeCirclePaint.setStyle(Paint.Style.STROKE);
        mStrokeCirclePaint.setStrokeWidth(mStrokeWidth);

        mProgressStrokePaint = new Paint(0);
        mProgressStrokePaint.setColor(getContext().getResources().getColor(android.R.color.holo_red_dark));
        mProgressStrokePaint.setStyle(Paint.Style.STROKE);
        mProgressStrokePaint.setStrokeWidth(mProgressStrokeWidth);

        mRadius = 200;
        mCurrentProgress = 35;

        calculateAngles();
    }

    private void calculateAngles(){
        mStrokeStartAngle = -90;
        mStrokeSweepAngle = 360.0f * ((float)mCurrentProgress/(float)mMaxProgress);

        mProgressStrokeStartAngle = mStrokeStartAngle+mStrokeSweepAngle;
        mProgressStrokeSweepAngle = 360 - mStrokeSweepAngle;


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int mCx = w / 2;
        int mCy = h / 2;

        mArcOval = new RectF(mCx -mRadius, mCy -mRadius, mCx +mRadius, mCy +mRadius);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        canvas.drawCircle(mCx, mCy, mRadius, mStrokeCirclePaint);
        canvas.drawArc(mArcOval,mStrokeStartAngle,mStrokeSweepAngle,false,mStrokeCirclePaint);
        canvas.drawArc(mArcOval,mProgressStrokeStartAngle,mProgressStrokeSweepAngle,false,mProgressStrokePaint);
    }

}
