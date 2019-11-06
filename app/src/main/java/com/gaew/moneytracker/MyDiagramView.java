package com.gaew.moneytracker;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class MyDiagramView extends View {

    private float mEpences;
    private float mIncome;

    private Paint expencePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint incomePaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public MyDiagramView(Context context) {
        super(context);
        init(null);
    }

    public void update(float expences, float income) {

        mEpences = expences;
        mIncome = income;
        invalidate();


    }

    private void init(@Nullable AttributeSet set) {

        if (set==null){

            return;
        }

        TypedArray ta = getContext().obtainStyledAttributes(set, R.styleable.MyDiagramView);
        mEpences = ta.getColor(R.styleable.MyDiagramView_diagram_color, Color.MAGENTA);
        expencePaint.setColor((int) mEpences);
        mIncome = ta.getColor(R.styleable.MyDiagramView_diagram_color,Color.GREEN);
        incomePaint.setColor((int) mIncome);
        ta.recycle();

 //       expencePaint.setColor(ContextCompat.getColor(getContext(), R.styleable.DiagramView_diagram_color,Color.RED);
//        incomePaint.setColor(ContextCompat.getColor(getContext(), R.color.apple_green));

    }

    public MyDiagramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public MyDiagramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public MyDiagramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        init(null);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        init(null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float total = mEpences + mIncome;

        float expensAngle = 360f * mEpences / total;
        float incomeAngle = 306f * mIncome / total;
        int space = 10;
        int size = Math.min(getWidth(), getHeight()) - space * 2;

        int xMargin = (getWidth() - size) / 2;
        int yMargin = (getHeight() - size) / 2;

        canvas.drawArc(xMargin - space, yMargin, getWidth() - xMargin - space,
                getHeight() - yMargin, 180 - expensAngle / 2, expensAngle, true, expencePaint);

        canvas.drawArc(xMargin + space, yMargin, getWidth() - xMargin + space,
                getHeight() - yMargin, 360 - incomeAngle / 2, incomeAngle, true, incomePaint);


    }
}
