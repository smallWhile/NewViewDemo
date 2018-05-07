package cn.zwz.newviewdemo;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xw.repo.BubbleSeekBar;

import java.util.List;

import cn.zwz.newviewdemo.view.CurveChartColuli;
import cn.zwz.newviewdemo.view.MoveView;

public class MainActivity extends AppCompatActivity {

    MoveView moveView;
    BubbleSeekBar mBbubbleSeekBar;
    TextView numtext;
    private CurveChartColuli line_curvechart;
    //这里的x和y轴自己可以添加100000条，只要你想要多少就可以。去试试吧。
    float[] xValues = new float[]{1, 2, 3, 4, 5, 6, 7};
    float[] yValues = new float[]{66, 11, 44, 67, 44, 99, 12};
    private static CurveChartColuli.CurveChartBuilder chartBuilder;
    private String UserID;
    private List<Integer> arryList;
    private float MaxColilu;
    private float density;
    private int densityDPI;
    private int screenWidth;
    private int screenHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moveView = (MoveView) findViewById(R.id.move);

        mBbubbleSeekBar = (BubbleSeekBar) findViewById(R.id.bubbleseekbar);
        numtext = (TextView) findViewById(R.id.numjd);
        numtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTextFromClip(MainActivity.this);
            }
        });

        // moveView.smoothScrollTo(-400,0);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(moveView, "translationX", 0, 400);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(moveView, "rotationY", 0, 200);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(moveView, "rotation", 0, 500);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(1000);
        set.play(animator1).with(animator2).after(animator3);
        set.start();
        // ObjectAnimator.ofFloat(moveView, "rotationY", 0, 400).setDuration(1000).start();

        mBbubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(int progress, float progressFloat) {
                numtext.setText("当前进度：" + progress);

            }

            @Override
            public void getProgressOnActionUp(int progress, float progressFloat) {

            }

            @Override
            public void getProgressOnFinally(int progress, float progressFloat) {

            }
        });


        //初始化时候你们不要buidle.show()。没封装好调用会异常，你们有时间自己可以好好优化一下。
        line_curvechart = (CurveChartColuli) findViewById(R.id.line_curvechart_colu);
        chartBuilder = CurveChartColuli.CurveChartBuilder.createBuilder(line_curvechart);
        chartBuilder.setXYCoordinate((float) 1.06, (float) 8.5, 0, 1);//第一个参数决定了位置第一个起始位置显示的数字。
        chartBuilder.setFillDownColor(Color.parseColor("#8c8b8b"));
        chartBuilder.setXYValues(xValues, yValues);
        //这个方法里面为了屏幕适配没别的。你们可以不用管
        setDimager();
        initView();
    }

    private void setDimager() {
        DisplayMetrics dm = new DisplayMetrics();
        dm = getResources().getDisplayMetrics();
        density = dm.density; // 屏幕密度（像素比例：0.75/1.0/1.5/2.0）
        densityDPI = dm.densityDpi; // 屏幕密度（每寸像素：120/160/240/320）
        float xdpi = dm.xdpi;
        float ydpi = dm.ydpi;
        screenWidth = dm.widthPixels; // 屏幕宽（像素，如：480px）
        screenHeight = dm.heightPixels; // 屏幕高（像素，如：800px）
        line_curvechart.mHeight = (int) (screenHeight * 0.31);
        line_curvechart.mWidth = (int) (screenWidth * 0.83);
        line_curvechart.setFillDownLineColor(true);
    }

    private void initView() {
        chartBuilder.setXYValues(xValues, yValues);
    }

    public void getTextFromClip(Context context) {
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        //判断剪切版时候有内容
        if (!clipboardManager.hasPrimaryClip())
            return;
        ClipData clipData = clipboardManager.getPrimaryClip();
        //获取 ClipDescription
        ClipDescription clipDescription = clipboardManager.getPrimaryClipDescription();
        //获取 lable
        String lable = clipDescription.getLabel().toString();
        //获取 text
        String text = clipData.getItemAt(0).getText().toString();

        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}
