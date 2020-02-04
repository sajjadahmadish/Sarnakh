package project.utils.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;


public class CanvasView extends AppCompatImageView {

    private Paint strokePaint;
    private Paint arcStrokePaint;
    private Paint fillPaint;
    private Paint textPaint;

    public CanvasView(Context context) {
        super(context);
        initialize();
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize();
    }

    private void initialize() {
        strokePaint = new Paint();
        strokePaint.setColor(Color.parseColor("#ffffff"));
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(2);
        strokePaint.setAntiAlias(true);

        fillPaint = new Paint();
        fillPaint.setColor(Color.parseColor("#888888"));
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setAntiAlias(true);

        arcStrokePaint = new Paint();
        arcStrokePaint.setColor(Color.parseColor("#ffffff"));
        arcStrokePaint.setStyle(Paint.Style.STROKE);
        arcStrokePaint.setStrokeWidth(10);
        arcStrokePaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setColor(Color.parseColor("#000000"));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setAntiAlias(true);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(20);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        int centerX = width / 2;
        int centerY = height / 2;

        canvas.drawCircle(centerX, centerY, 100, fillPaint);
        canvas.drawCircle(centerX, centerY, 100, strokePaint);

        int radius = 150;
        RectF rect = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(rect, 90, 270, false, arcStrokePaint);

        canvas.drawRect(rect, strokePaint);
        canvas.drawText("Hello Canvas", centerX, centerY, textPaint);
    }
}
