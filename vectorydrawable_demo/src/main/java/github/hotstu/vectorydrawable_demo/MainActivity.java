package github.hotstu.vectorydrawable_demo;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView image = findViewById(R.id.image);
        ImageView image2 = findViewById(R.id.image2);
        Drawable drawable = image.getDrawable();
        //image2.setImageResource(R.drawable.ic_skill);
        System.out.println(drawable.getClass().getCanonicalName());
        //Drawable drawable1 = ContextCompat.getDrawable(this, R.drawable.ic_skill); //not work
        //.out.println(drawable1.getClass().getCanonicalName());
        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(getResources(), R.drawable.ic_skill, getTheme());
        image2.setImageDrawable(vectorDrawableCompat);

        //AnimatedVectorDrawableCompat;
        AnimatedVectorDrawable s = null;
        ImageView image3 = findViewById(R.id.image3);
        Drawable drawable3 = image3.getDrawable();
        if (drawable3 instanceof Animatable) {
            ((Animatable) drawable3).start();
        }

    }
}
