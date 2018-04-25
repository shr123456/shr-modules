package com.kexin.dkt.core.common.util;

/**
 * Created by kexin on 2017/10/25.
 */
public class GetK {

    public static double getKByScore(long score){
        double k = 0.8;
        if(score >=90 && score<=92){
            k = 90 / score;
        }else if(score >=93 && score<=95){
            k = 90 / score * 0.95;
        }else if(score >=96 && score<=99){
            k = 90 / score * 0.9;
        }else if(score >=0 && score<=60){
            k = 1.5;
        }else if(score >=61 && score<79){
            k = 80 / score;
        }
        return k;
    }
}
