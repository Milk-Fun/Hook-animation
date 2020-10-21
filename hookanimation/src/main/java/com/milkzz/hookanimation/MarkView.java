package cn.wsds.gamemaster.ui.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.subao.utils.MetricsUtils;

import cn.wsds.gamemaster.R;


/**
 * create by zuoqi@ on 2020/6/17 19:49
 * description: 打勾动画，宽和高最优设置为35dp
 * <p>
 * 亲自测试发现的最优图案坐标，暂且保存
 * path.moveTo(10, 50);
 * path.lineTo(30, 70);
 * path.lineTo(70,30);
 */
public class MarkView extends View {

    private Paint p;
    private Path path;
    private Path pathDst;
    private PathMeasure pm;
    private float fraction = 0;
    private ValueAnimator valueAnimator;
    private float paintStrokeWidth = MetricsUtils.dp2px(getContext(), 2);
    private int proWidth = 0;

    public MarkView(Context context) {
        super(context);
        init();
    }

    public MarkView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MarkView);
            this.paintStrokeWidth = typedArray.getDimension(
                    R.styleable.MarkView_mark_view_paint_stroke_width,
                    MetricsUtils.dp2px(getContext(), 2));
            typedArray.recycle();
        }
        init();
    }

    private void init() {
        this.setLayerType(LAYER_TYPE_SOFTWARE, null);
        p = new Paint();
        p.setStyle(Paint.Style.STROKE);
        p.setStrokeCap(Paint.Cap.ROUND);
        p.setColor(getResources().getColor(R.color.color_4A81FF));
        p.setStrokeWidth(paintStrokeWidth);
        p.setAntiAlias(true);
        pathDst = new Path();

        valueAnimator = ValueAnimator.ofFloat(0, 1);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                fraction = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (path == null) {
            int width = getWidth();
            int heigth = getHeight();

            // 保存上一次获取的宽度，防止之后出现ondraw时候，获取的宽度为0的情况
            if (width == 0) {
                if (proWidth == 0) {
                    proWidth = MetricsUtils.dp2px(getContext(), 35);
                }
                width = this.proWidth;
                heigth = this.proWidth;
            } else {
                this.proWidth = width;
            }

            path = new Path();
            path.moveTo(width / 4, heigth / 2);
            path.lineTo(9 * width / 20, 7 * heigth / 10);
            path.lineTo(17 * width / 20, 7 * heigth / 20);

            pm = new PathMeasure(path, false);
        }
        pm.getSegment(0, fraction * pm.getLength(), pathDst, true);
        canvas.drawPath(pathDst, p);
    }

    public void startDelay(long times) {
        this.setVisibility(VISIBLE);
        if (!valueAnimator.isRunning()) {
            valueAnimator.setStartDelay(times);
            valueAnimator.start();
        }
    }
}
