package cn.zwz.newviewdemo.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by zhuweizhong on 2018/4/12.
 */

public class MoveView extends View {

    private int lastX;
    private int lastY;
    Scroller scroller;

    public MoveView(Context context) {
        super(context);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scroller = new Scroller(context);
    }

    public MoveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (scroller.computeScrollOffset()){
            ((View) getParent()).scrollTo(scroller.getCurrX(),scroller.getCurrY());
            //通过不断的重绘不断的调用computeScroll方法
            invalidate();

        }
    }

    public void smoothScrollTo(int destX,int destY){
        int scrollX=getScrollX();
        int delta=destX-scrollX;
        //1000秒内滑向destX
        scroller.startScroll(scrollX,0,delta,0,2000);
        invalidate();
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int offesx = x - lastX;
                int offesy = y - lastY;

                //调用layout方法来重新放置它的位置
                //layout(getLeft() + offesx, getTop() + offesy, getRight() + offesx, getBottom() + offesy);

                //对left和right进行偏移
                //  offsetLeftAndRight(offesx);
                // 对top和bottom进行偏移
                // offsetTopAndBottom(offesy);
                //scollTo(x,y)表示移动到一个具体的坐标点
                ((View) getParent()).scrollBy(-offesx, -offesy);
                break;
        }

        return true;
    }
}
