package com.milkzz.myapplication;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Create by zuoqi@bhz.com.cn on 2019/11/12 9:59
 */
public class ZFinishImage1 extends View {

    private Paint paint;
    private Paint paint2;
    private Paint paint3;
    private Path pathOk;
    private Path pathOk2;
    private Path pathOk3;
    private PathMeasure pathMeasure;
    private boolean ifFirst;
    private float fraction;

    private int colorBG;        // 图形背景色，默认透明色
    private int colorCircle;    // 圆的颜色，默认蓝色
    private int colorMark;      // 对勾颜色，默认白色
    private int colorHookStoke; // 圆形框的颜色，默认蓝色
    private int hookWidth;      // 对勾宽度，默认会自己根据圆的大小来计算宽度
    private int circleR;        // 圆的半径
    private int durationTime;   // 动画时间
    private boolean startWhenInit; // 是否自动加载动画，true：UI显示时候自动加载，false，人工控制动画开始时间

    public void setColorBG(int colorBG) {
        this.colorBG = colorBG;
    }

    public void setColorCircle(int colorCircle) {
        this.colorCircle = colorCircle;
    }

    public void setColorMark(int colorMark) {
        this.colorMark = colorMark;
    }

    public void setCircleR(int circleR) {
        this.circleR = circleR;
    }

    public void setDurationTime(int durationTime) {
        this.durationTime = durationTime;
    }

    public void setHookWidth(int hookWidth) {
        this.hookWidth = hookWidth;
    }

    public void setStartWhenInit(boolean startWhenInit) {
        this.startWhenInit = startWhenInit;
    }

    public void setColorHookStoke(int colorHookStoke) {
        this.colorHookStoke = colorHookStoke;
    }

    public ZFinishImage1(Context context) {
        this(context, null);
    }

    public ZFinishImage1(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (attrs != null) {
            TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ZFinishImage1);
            this.circleR = array.getDimensionPixelOffset(R.styleable.ZFinishImage1_z1CircleR, dp2px(30, getContext()));
            this.colorCircle = array.getColor(R.styleable.ZFinishImage1_z1ColorCircle, Color.parseColor("#3C86FF"));
            this.colorMark = array.getColor(R.styleable.ZFinishImage1_z1ColorMark, Color.WHITE);
            this.colorBG = array.getColor(R.styleable.ZFinishImage1_z1ColorBG, Color.TRANSPARENT);
            this.durationTime = array.getInt(R.styleable.ZFinishImage1_z1AnimTime, 2000);
            this.startWhenInit = array.getBoolean(R.styleable.ZFinishImage1_z1StartWhenInit, true);
            this.hookWidth = array.getDimensionPixelOffset(R.styleable.ZFinishImage1_z1WidthHook, -1);
            this.colorHookStoke = array.getColor(R.styleable.ZFinishImage1_z1ColorCircleStoke,Color.parseColor("#3C86FF"));
            array.recycle();
        }
        init();
    }

    private void init() {
        float r = circleR;
        float strokeWidth = (hookWidth == -1) ? r / 5 : hookWidth;
        float privateX = circleR;
        float privateY = circleR;

        paint = new Paint();
        paint.setColor(colorHookStoke);
        paint.setStyle(Paint.Style.STROKE);

        paint2 = new Paint();
        paint2.setColor(colorMark);
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(strokeWidth);

        paint3 = new Paint();
        paint3.setStyle(Paint.Style.FILL);
        paint3.setColor(colorCircle);

        pathMeasure = new PathMeasure();

        Path pathCirCle = new Path();
        pathCirCle.addCircle(privateX, privateY, r, Path.Direction.CCW);
        pathCirCle.moveTo(privateX - r / 2 - strokeWidth, privateY);
        pathCirCle.lineTo(privateX - strokeWidth, privateY + r / 2);
        pathCirCle.lineTo(privateX + 2 * r / 3, privateY - 1 * r / 3);
        pathOk = new Path();
        pathOk2 = new Path();
        pathOk3 = new Path();
        pathMeasure.setPath(pathCirCle, false);

        ifFirst = true;

        if (startWhenInit) startAnim();
    }

    public void startAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 2.5f);
        valueAnimator.setDuration(durationTime);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(colorBG);

        float cr = 0;
        if (fraction <= 1) {
            float len = pathMeasure.getLength();
            float et = len * fraction;
            pathMeasure.getSegment(0, et, pathOk, true);
        } else if (fraction <= 1.5) {
            cr = circleR * (fraction - 1f)*2;
        } else {
            cr = circleR;
            if (ifFirst) {
                ifFirst = false;
                pathMeasure.nextContour();
            }
            float len = pathMeasure.getLength();
            float et = len * (fraction - 1.5f);
            pathMeasure.getSegment(0, et, pathOk2, true);
        }

        canvas.drawPath(pathOk, paint);
        canvas.drawCircle(circleR,circleR,cr, paint3);
        canvas.drawPath(pathOk2, paint2);
    }

    private int dp2px(int value, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value,
                context.getResources().getDisplayMetrics());
    }
}
