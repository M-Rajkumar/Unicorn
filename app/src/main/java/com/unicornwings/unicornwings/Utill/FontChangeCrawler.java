package com.unicornwings.unicornwings.Utill;

/**
 * Created by hai on 6/13/2017.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.lang.reflect.Field;

public class FontChangeCrawler {
    private Typeface typeface;
    private Typeface btntypeface;

    public FontChangeCrawler(Typeface typeface) {
        this.typeface = typeface;
    }

    //public FontChangeCrawler(AssetManager assets, String assetsFontFileName)
    public FontChangeCrawler(Context mContect, int id)
    {
        //typeface = Typeface.createFromAsset(assets, assetsFontFileName);
        typeface = CommonUtil.getfont(mContect, id);
    }

    public void replaceFonts(ViewGroup viewTree) {
        View child;
        for (int i = 0; i < viewTree.getChildCount(); ++i) {
            child = viewTree.getChildAt(i);
            if (child instanceof ViewGroup) {
                // recursive call
                replaceFonts((ViewGroup) child);
            } else if (child instanceof TextView) {
                // base case
                ((TextView) child).setTypeface(typeface);
            } else if (child instanceof EditText) {
                ((EditText) child).setTypeface(typeface);
            } else if (child instanceof Button) {
                ((Button) child).setTypeface(typeface);
            }

        }
    }


    public void replaceFontsbtn(ViewGroup viewTree, Context cont, int idd, boolean tv, boolean ed, boolean btn) {
        View child;
        btntypeface = CommonUtil.getfont(cont, idd);
        for (int i = 0; i < viewTree.getChildCount(); ++i) {
            child = viewTree.getChildAt(i);
            if (child instanceof ViewGroup) {
                // recursive call
                replaceFonts((ViewGroup) child);
            } else if (child instanceof TextView) {
                // base case
                if (tv == true) {
                    ((TextView) child).setTypeface(btntypeface);
                }
            } else if (child instanceof EditText) {
                if (ed == true) {
                    ((EditText) child).setTypeface(btntypeface);
                }
            } else if (child instanceof Button) {
                if (btn == true) {
                    ((Button) child).setTypeface(btntypeface);
                }
            }
        }
    }

    public static void setDefaultFont(Context context,
                                      String staticTypefaceFieldName, String fontAssetName) {
        final Typeface regular = Typeface.createFromAsset(context.getAssets(),
                fontAssetName);
        replaceFontall(staticTypefaceFieldName, regular);
    }

    protected static void replaceFontall(String staticTypefaceFieldName,
                                         final Typeface newTypeface) {
        try {
            final Field staticField = Typeface.class
                    .getDeclaredField(staticTypefaceFieldName);
            staticField.setAccessible(true);
            staticField.set(null, newTypeface);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}