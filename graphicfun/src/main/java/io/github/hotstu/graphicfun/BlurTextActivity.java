package io.github.hotstu.graphicfun;

import android.graphics.BlurMaskFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

public class BlurTextActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blur_text);
        TextView tv = findViewById(R.id.text);
        tv.getPaint().setColor(Color.RED);
        //tv.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        tv.getPaint().setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.SOLID));
        tv.invalidate();
        RenderScript renderScript = RenderScript.create(this);
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
    }
}
