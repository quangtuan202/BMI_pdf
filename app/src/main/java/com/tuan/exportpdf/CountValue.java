package com.tuan.exportpdf;

public class CountValue {
    private static int count=1;
    public static void increase(){
        CountValue.count++;
    }
    public static void resetCount(){
        CountValue.count=0;
    }
    public static int getCount(){
        return CountValue.count;
    }

}
