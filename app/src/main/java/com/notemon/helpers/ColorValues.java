package com.notemon.helpers;

import android.content.Context;

import com.notemon.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by emil on 01.05.17.
 */

public class ColorValues {

    private static Map<String, Integer> colorList;
    private static Map<String, Integer> darkColorList;

    static {
        colorList = new HashMap<>();
        darkColorList = new HashMap<>();

        colorList.put("red", R.color.project_red);
        colorList.put("blue", R.color.project_blue);
        colorList.put("green", R.color.project_green);
        colorList.put("yellow", R.color.project_yellow);
        colorList.put("pink", R.color.project_pink);
        colorList.put("purple", R.color.project_purple);
        colorList.put("teal", R.color.project_teal);
        colorList.put("orange", R.color.project_orange);
        colorList.put("lime", R.color.project_lime);


        darkColorList.put("red", R.color.project_red_dark);
        darkColorList.put("blue", R.color.project_blue_dark);
        darkColorList.put("green", R.color.project_green_dark);
        darkColorList.put("yellow", R.color.project_yellow_dark);
        darkColorList.put("pink", R.color.project_pink_dark);
        darkColorList.put("purple", R.color.project_purple_dark);
        darkColorList.put("teal", R.color.project_teal_dark);
        darkColorList.put("orange", R.color.project_orange_dark);
        darkColorList.put("lime", R.color.project_lime_dark);
    }


    public static int getColorId(String colorName, Context context){
        return context.getResources().getColor(colorList.get(colorName));
    }

    public static int getDarkColorId(String colorName, Context context){
        return context.getResources().getColor(darkColorList.get(colorName));
    }
}
