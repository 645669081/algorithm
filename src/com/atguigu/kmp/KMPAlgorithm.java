package com.atguigu.kmp;

/**
 * @Date 2020/4/6 15:58
 * @Auther 梁伟
 * @Description kmp算法查找子字符串在大字符串中出现的位置
 * 根据要匹配的字符串自己构建一个部分匹配表。要求将子串从1个字符到length-1个字符进行分割，每次增加一位。然后去找每个串的最长的相同前缀和后缀
 * 即从前数和从后数相同的字符个数，得到的串是相同的。要最长的那个
 * 搜索子串：A    B   C   D   A   B   D
 * A        0
 * AB       0
 * ABC      0
 * ABCD     0
 * ABCDA    1
 * ABCDAB   2
 *
 * 根据上边的推导过程得到--部分匹配表
 * A    B   C   D   A   B
 * 0    0   0   0   1   2
 */
public class KMPAlgorithm {
    public static void main(String[] args) {

    }
}
