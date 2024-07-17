package com.uzx.util;

/**
 * NumberUtils
 * @author UZX
 * @date 2024-06-20
 */
public class NumberUtils {
    /**
     * 获取数字为2的几次幂
     * @param number 数字
     * @return {@link int }
     * @author UZX
     * @date 2024-06-20
     */
    public static int isTimesTwo(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("The Number Cannot Be Less Than Zero!!!");
        }
        if (!(number > 0 && (number & (number - 1)) == 0)) {
            return Integer.toBinaryString(number).length();
        }
        return Integer.toBinaryString(number).length() - 1;
    }
}
