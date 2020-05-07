package github.hotstu.demo.viewfactory;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(this);
        LayoutInflater layoutInflater = inflater.cloneInContext(this);
        layoutInflater.setFactory2(new LayoutInflater.Factory2() {
            @Nullable
            @Override
            public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
                if (name.indexOf("test") != -1) {
                    System.out.println(1);
                    TextView textView = new TextView(context);
                    textView.setText("test");
                    return textView;
                }
                return null;
            }

            @Nullable
            @Override
            public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
                if (name.indexOf("test") != -1) {
                    System.out.println(1);
                    TextView textView = new TextView(context);
                    textView.setText("test");
                    return textView;
                }
                return null;
            }
        });
//        inflater.setFilter(new LayoutInflater.Filter() {
//            @Override
//            public boolean onLoadClass(Class clazz) {
//                Log.d(TAG, "on Load:" + clazz.getCanonicalName());
//                return true;
//            }
//        });
//        setFactory2(inflater, new LayoutInflater.Factory2() {
//            @Nullable
//            @Override
//            public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
//                if (name.indexOf("test") != -1) {
//                    System.out.println(1);
//                    TextView textView = new TextView(context);
//                    textView.setText("test");
//                    return textView;
//                }
//                return null;
//            }
//
//            @Nullable
//            @Override
//            public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
//                if (name.indexOf("test") != -1) {
//                    System.out.println(2);
//                }
//                return null;
//            }
//        });
        setContentView(layoutInflater.inflate(R.layout.activity_main, null));
    }

    public static void setFactory2(
            @NonNull LayoutInflater inflater, @NonNull LayoutInflater.Factory2 factory) {
        final LayoutInflater.Factory existingFactory = inflater.getFactory();
        try {
            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(inflater, false);
            inflater.setFactory2(factory);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
