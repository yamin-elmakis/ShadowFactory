package com.yamin.shadowlibrary;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.PaintDrawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.annotation.IntRange;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

/**
 * Created by Yamin on 18-Mar-17.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class Shadow {
    private static final String TAG = "Shadow";
    private static int DEFAULT_BLUR = 6;
    private static int DEFAULT_ALPHA = 5;
    @ColorRes
    private static int DEFAULT_BACK_COLOR = android.R.color.white;
    @ColorRes
    private static int DEFAULT_SHADOW_COLOR = android.R.color.background_light ;
    private int blur;
    private Context context;
    private int shadowLeft, shadowUp, shadowRight, shadowDown;
    private @ColorRes int resBackColor;
    private @ColorRes int resShadowColor;
    private int cornerRadius, alpha;

    private Shadow(Context context) {
        this.context = context;
        blur = DEFAULT_BLUR;
        alpha = DEFAULT_ALPHA;
        shadowLeft = 0;
        shadowUp = 0;
        shadowRight = 0;
        shadowDown = 0;
        cornerRadius = 0;
        resBackColor = DEFAULT_BACK_COLOR;
        resShadowColor = DEFAULT_SHADOW_COLOR;
    }

    public LayerDrawable set(View view) {
        LayerDrawable layerDrawable = create();
        PaintDrawable back = (PaintDrawable)layerDrawable.getDrawable(blur - 1);

        back.setPadding(view.getPaddingLeft() - shadowLeft * (blur - 1),
                view.getPaddingTop() - shadowUp * (blur - 1),
                view.getPaddingRight() - shadowRight* (blur - 1),
                view.getPaddingBottom() - shadowDown * (blur
                        - 1));

        layerDrawable.setDrawableByLayerId(blur - 1, back);
        if (Build.VERSION.SDK_INT > 15)
            view.setBackground(layerDrawable);
        else
            view.setBackgroundDrawable(layerDrawable);
        return layerDrawable;
    }

    public LayerDrawable create() {
        Drawable[] layers = new Drawable[blur];
        int deltaAlpha = this.alpha / blur;
        int alpha = deltaAlpha;
        PaintDrawable sd;
        for (int i = 0; i < blur - 1; i++) {
            sd = new PaintDrawable(ContextCompat.getColor(context, resShadowColor));
            alpha += deltaAlpha;

            sd.setAlpha(alpha);
            sd.setPadding(shadowLeft, shadowUp, shadowRight, shadowDown);
            if (cornerRadius > 0)
                sd.setCornerRadius(cornerRadius);
            layers[i] = sd;
        }

        sd = new PaintDrawable(ContextCompat.getColor(context, resBackColor));
        if (cornerRadius > 0)
            sd.setCornerRadius(cornerRadius);

        layers[blur - 1] = sd;
        return new LayerDrawable(layers);
    }

    public Shadow shadowAll(int shadow) {
        this.shadowLeft = shadow;
        this.shadowUp = shadow;
        this.shadowRight = shadow;
        this.shadowDown = shadow;
        return this;
    }

    public float getShadowDown() {
        return shadowDown;
    }

    public Shadow shadowDown(int shadowDown) {
        this.shadowDown = shadowDown;
        return this;
    }

    public int getShadowLeft() {
        return shadowLeft;
    }

    public Shadow shadowLeft(int shadowLeft) {
        this.shadowLeft = shadowLeft;
        return this;
    }

    public int getShadowRight() {
        return shadowRight;
    }

    public Shadow shadowRight(int shadowRight) {
        this.shadowRight = shadowRight;
        return this;
    }

    public int getShadowUp() {
        return shadowUp;
    }

    public Shadow shadowUp(int shadowUp) {
        this.shadowUp = shadowUp;
        return this;
    }

    public int getAlpha() {
        return alpha;
    }

    public Shadow alpha(@IntRange(from = 1, to = 255) int alpha) {
        if (alpha > 0 && alpha < 256)
            this.alpha = alpha;
        else
            Log.e(TAG, "alpha: "+alpha+"  IS NOT IN RANGE[1..255]");
        return this;
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public Shadow radius(int radius) {
        this.cornerRadius = radius;
        return this;
    }

    public int getBackgroundColor() {
        return resBackColor;
    }

    public Shadow backgroundColor(@ColorRes int backgroundColor) {
        this.resBackColor = backgroundColor;
        return this;
    }

    public int getshadowColor() {
        return resShadowColor;
    }

    public Shadow shadowColor(@ColorRes int shadowColor) {
        this.resShadowColor = shadowColor;
        return this;
    }

    public int getLayersSize() {
        return blur;
    }

    public Shadow blur(@IntRange(from=1) int blur) {
        if (blur > 1)
            this.blur = blur;
        else
            Log.e(TAG, "blur: "+blur+" IS NOT IN RANGE[2..)");
        return this;
    }

    public static void setDefaultAlpha(@IntRange(from=1, to = 255) int alpha) {
        DEFAULT_ALPHA = alpha;
    }

    public static void setDefaultBlur(@IntRange(from=1) int blur) {
        DEFAULT_BLUR = blur;
    }

    public static void setDefaultBackgroundColorRes(@ColorRes int backgroundColorRes) {
        DEFAULT_BACK_COLOR = backgroundColorRes;
    }

    public static void setDefaultShadowColorRes(@ColorRes int shadowColorRes) {
        DEFAULT_SHADOW_COLOR = shadowColorRes;
    }

    public static class Builder {

        public static ShadowSize init(Context context){
            return new BuilderImpl().context(context);
        }

        public interface ShadowSize {
            ShadowProperties shadowDown(int shadowDown);
            ShadowProperties shadowUp(int shadowUp);
            ShadowProperties shadowLeft(int shadowLeft);
            ShadowProperties shadowRight(int shadowRight);
            ShadowParameters shadowAll(int shadow);
        }

        public interface ShadowProperties extends ShadowSize, ShadowParam { }

        public interface ShadowParam {
            ShadowParameters alpha(@IntRange(from=1, to = 255) int alpha);
            ShadowParameters blur(@IntRange(from=1) int blur);
            ShadowParameters defaultParameters();
            ShadowParameters radius(int radius);
            ShadowColors defaultAll();
        }

        public interface ShadowParameters extends ShadowColor, ShadowParam { }

        public interface ShadowColor {
            ShadowColors backgroundColorRes(@ColorRes int backgroundColor);
            ShadowColors shadowColorRes(@ColorRes int shadowColor);
            ShadowColors defaultColors();
        }

        public interface ShadowColors extends ShadowColor, Build { }

        public interface Build {
            Shadow build();
        }

        private static class BuilderImpl implements ShadowProperties, ShadowParameters ,ShadowColors  {

            private Context context;
            private int blur, radius, alpha;
            private int left, right, up, down;
            private @ColorRes int backColor;
            private @ColorRes int shadowColor;

            public ShadowSize context(Context context) {
                this.context = context;
                return this;
            }

            @Override
            public ShadowProperties shadowDown(int shadowDown) {
                down = shadowDown;
                return this;
            }

            @Override
            public ShadowProperties shadowUp(int shadowUp) {
                up = shadowUp;
                return this;
            }

            @Override
            public ShadowProperties shadowLeft(int shadowLeft) {
                left = shadowLeft;
                return this;
            }

            @Override
            public ShadowProperties shadowRight(int shadowRight) {
                right = shadowRight;
                return this;
            }

            @Override
            public ShadowParameters shadowAll(int shadow) {
                shadowUp(shadow);
                shadowDown(shadow);
                shadowLeft(shadow);
                shadowRight(shadow);
                return this;
            }

            @Override
            public ShadowParameters radius(int radius) {
                this.radius = radius;
                return this;
            }

            @Override
            public ShadowColors defaultAll() {
                return this;
            }

            /**
             * Set the alpha level of the result drawable [0..255].
             * @param alpha the end alpha
             */
            @Override
            public ShadowParameters alpha(@IntRange(from=1, to = 255) int alpha) {
                this.alpha = alpha;
                return this;
            }

            @Override
            public ShadowParameters blur(@IntRange(from = 1) int blur) {
                this.blur = blur;
                return this;
            }

            @Override
            public ShadowParameters defaultParameters() {
                return this;
            }

            @Override
            public ShadowColors backgroundColorRes(@ColorRes int backgroundColor) {
                backColor = backgroundColor;
                return this;
            }

            @Override
            public ShadowColors shadowColorRes(@ColorRes int shadowColor) {
                this.shadowColor = shadowColor;
                return this;
            }

            @Override
            public ShadowColors defaultColors() {
                return this;
            }

            @Override
            public Shadow build() {
                Shadow shadow = new Shadow(context);
                if (left > 0)
                    shadow.shadowLeft(left);
                if (right > 0)
                    shadow.shadowRight(right);
                if (up > 0)
                    shadow.shadowUp(up);
                if (down > 0)
                    shadow.shadowDown(down);
                if (radius > 0)
                    shadow.radius(radius);
                if (blur > 0)
                    shadow.blur(blur);
                if (alpha > 0)
                    shadow.alpha(alpha);
                if (shadowColor > 0)
                    shadow.shadowColor(shadowColor);
                if (backColor > 0)
                    shadow.backgroundColor(backColor);
                return shadow;
            }
        }
    }
}