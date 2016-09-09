package io.github.hotstu.navichooser;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;


/**
 *  
 * @Description: 
 * @Copyright: Copyright (c)2015
 * @Company: BOCO
 * @author: songweidong
 * @version: V1.0
 * @date 2015-12-30 上午11:48:37
 * 
 */
public class DrawableUtil {
	public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {  
	    final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
	    DrawableCompat.setTintList(wrappedDrawable, colors);
	    return wrappedDrawable;
	}
}
