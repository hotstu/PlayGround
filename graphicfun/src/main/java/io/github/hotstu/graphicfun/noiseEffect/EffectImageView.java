package io.github.hotstu.graphicfun.noiseEffect;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hotstuNg on 2016/8/21.
 */
public class EffectImageView extends ImageView {
    private List<Effect> mEffects;

    public EffectImageView(Context context) {
        super(context);
        init();
    }

    public EffectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EffectImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mEffects = new ArrayList<>();
    }

    public void attachEffect(Effect effect) {
        if (!mEffects.contains(effect)) {
            mEffects.add(effect);
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < mEffects.size(); i++) {
            if (mEffects.get(i) != null) {
                mEffects.get(i).draw(canvas);
            }
        }

    }

    public void update() {
        for (int i = 0; i < mEffects.size(); i++) {
            if (mEffects.get(i) != null) {
                mEffects.get(i).update();
            }
        }
        ViewCompat.postInvalidateOnAnimation(this);
    }
}
