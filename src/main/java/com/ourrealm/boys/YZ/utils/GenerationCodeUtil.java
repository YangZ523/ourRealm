package com.ourrealm.boys.YZ.utils;

import java.util.Date;
import java.util.Random;

/**
 * Created by make it on 2015/9/11.
 */
public class GenerationCodeUtil {

    public static int expiredTime = 10;     //10分钟验证码过期

    /**
     * 创建指定数量的随机字符串
     *
     * @param numberFlag 是否是数字
     * @param length
     * @return
     */
    public static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        do {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        } while (bDone);

        return retStr;
    }

    /**
     * 随机生成五位验证码
     * @return
     */
    public static String GenerateNumber(){
        return GenerationCodeUtil.createRandom(true, 5);
    }

    /**
     * 生成订单号
     * @return
     */
    public static String generationFlowId(){
        String dataVarchar = DateUtil.format(new Date(),DateUtil.PATTERN_CLASSICAL_ALL);
        String code = createRandom(true, 6);
        return dataVarchar+code;
    }

    /**
     * 生成批处理批次号
     * @return
     */
    public static String generationPatchNumber(){
        String dataVarchar = DateUtil.format(new Date(),DateUtil.PATTERN_CLASSICAL_ALL);
        String code = createRandom(true, 2);
        return dataVarchar+code;
    }

    /**
     * 生产指定范围的随机数
     * 如 maxNum=3  minNum=1,那么从【1,2,3】里面随机一个数
     * @param minNum
     * @param maxNum
     * @return
     */
    public static Integer getRandom(Integer minNum,Integer maxNum){
        return new Random().nextInt(maxNum - minNum + 1) + minNum;
    }

    /**
     * 随机指定范围内N个不重复的数
     * 最简单最基本的方法
     * @param min 指定范围最小值
     * @param max 指定范围最大值
     * @param n 随机数个数
     */
    public static int[] randomArray(int min,int max,int n){
        if(max < 0){
            return new int[0];
        }

        if(n > min + max +1){
            n = min + max +1;
        }

        int len = max-min+1;

        if(max < min || n > len){
            return null;
        }

        //初始化给定范围的待选数组
        int[] source = new int[len];
        for (int i = min; i < min+len; i++){
            source[i-min] = i;
        }

        int[] result = new int[n];
        Random rd = new Random();
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            //待选数组0到(len-2)随机一个下标
            index = Math.abs(rd.nextInt() % len--);
            //将随机到的数放入结果集
            result[i] = source[index];
            //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
            source[index] = source[len];
        }
        return result;
    }

//    public static void main(String[] args) {
//        for (int i=0;i<10;i++) {
//            int[] rs = randomArray(0,0,4);
//            String ss = "";
//            for (int r : rs) {
//                ss+=r+",";
//            }
//            System.out.println(ss);
//        }
//    }
}
