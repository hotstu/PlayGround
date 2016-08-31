package io.github.hotstu.graphicfun;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import io.github.hotstu.graphicfun.particleText.PContext;

/**
 * Created by hotstuNg on 2016/8/29.
 */

public class ColorfullView extends View {
    public ColorfullView(Context context) {
        super(context);
    }

    public ColorfullView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ColorfullView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    int x, y, i, w = 512;

    void setup() {
        for (i = 0; ++i < w * w; ) set(i % w, i / w, -1 ^ r(1 << 24));
    }

    void draw() {
        for (i = 0; ++i < w; ) {
            x = y;
            y = r(w);
            set(x + r(3) - 1, y + r(3) - 1, get(x, y, r(99), r(99)));
        }
    }

    int r(int a) {
        return (int)(PContext.random(a));
    }
}
