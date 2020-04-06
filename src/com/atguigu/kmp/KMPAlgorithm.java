package com.atguigu.kmp;

import java.util.Arrays;

/**
 * @Date 2020/4/6 15:58
 * @Auther 梁伟
 * @Description kmp算法查找子字符串在大字符串中出现的位置
 * 根据要匹配的字符串自己构建一个部分匹配表。要求将子串从1个字符到length-1个字符进行分割，每次增加一位。然后去找每个串的最长的相同前缀和后缀
 * 即正数和倒数相同的字符个数，得到的串是相同的。要最长的那个
 * 搜索子串：a b a b c
 *          -1
 * a        0
 * ab       0
 * aba      1
 * abab     2
 * ababc    0      最后这个不需要纳入prefixTable，并给前边增加一个-1

 *
 * 根据上边的推导过程得到--prefixTable
 * 编号               0    1   2   3   4
 * 子串               a    b   a   b   c
 * 公共前后缀长度     -1    0   0   1   2
 */
public class KMPAlgorithm {

    public static int count;

    public static void main(String[] args) {
//        char[] pattern = {'A', 'B', 'A', 'B', 'C', 'A', 'B', 'A', 'A'};
//        char[] text = "ABABABCABAABABABAB".toCharArray();


        char[] text = "BBC ABCDAB ABCDABCDABDE".toCharArray();
//        char[] pattern = "ABCDABD".toCharArray();
        char[] pattern = "ABC".toCharArray();

        kmpSearch(text, pattern);
//        int n = pattern.length;
//        int[] prefixTable = prefixTable(pattern, n);
//        int[] movePrefixTable = movePrefixTable(pattern, n);
//        System.out.println(Arrays.toString(prefixTable));
//        System.out.println(Arrays.toString(movePrefixTable));
    }

    /**
     * @auther 梁伟
     * @Description
     * @Date 2020/4/6 20:14
     * @param text 大字符串
     * @param pattern 小字符串
     * @return void
     **/
    public static int kmpSearch(char[] text, char[] pattern) {
        int n = pattern.length;
        int m = text.length;
        int[] movePrefixTable = movePrefixTable(pattern, n);
        System.out.println("当前的movePrefixTable是：" + Arrays.toString(movePrefixTable));
        //进行约定，大字符串指针i，长度m。小字符串指针j，长度n
        //text[i]   len(text)    = m
        //patten[j] len(pattern) = n

        int i = 0;
        int j = 0;
        while (i < m) {
            //当j匹配到最后一个字母的时候,并且最后匹配的一个也相同，那么该子串全部匹配完成，即找到了该子串的位置
            //此时只是第一次找到，有可能会找到多个该子串，所以需要继续往后找
            if (j == n-1 && pattern[j] == text[i]) {
                System.out.println("第"+ ++count +"次找到子串的位置" + (i - j));
                //继续往后匹配
                j = movePrefixTable[j];
            }
            if (text[i] == pattern[j]) {
                i++;
                j++;
            } else {
                //当匹配失败需要移动时去公共前后缀表中获取当前要移动的位置
                j = movePrefixTable[j];
                //当movePrefixTable[j]指向的是-1的时候即最前端的位置，此时要比较的位置就是-1，-1对齐到原来的j的位置，其实也就是j后移
                //此时i也要跟着后移
                if (j == -1) {
                    i++;
                    j++;
                }
            }
        }
        return -1;
    }

    /**
     * @auther 梁伟
     * @Description 对prefixTable的生成采用每一次子串的生成都借助前一次子串生成后的结果
     *              如aba的公共前后缀是1，那么下一次abab前后缀的计算就无需比较原来的那一位公共前后缀，
     *              只需要比较 aba中的第一个a的后一位b 和 abab中的最后一位，如果相同即最大公共前后缀在上一个的基础上加1。
     *
     * @Date 2020/4/6 18:16
     * @param pattern 要比较的字符串
     * @param n 整个公共前后缀表的长度
     * @return int[] 生成的公共前后缀的数组
     **/
    public static int[] prefixTable(char[] pattern, int n) {
        int[] prefix = new int[n];
        //一个字符没有前后缀当然是0
        prefix[0] = 0;
        //要比较的长度
        int len = 0;
        //当前检测的是第i个字母，从第一个开始比较，因为第一个是0不需要比较
        int i = 1;
        //只要指针在后缀表长度范围内，防止越界
        while (i < n) {
            //这个就是要根据前一次的结果来确定本次前后缀，在方法的注释中已经描述
            if (pattern[i] == pattern[len]) {
                //移动到下一位进行比较
                len++;
                //填写公共前后缀表为上次加1的长度
                prefix[i] = len;
                //后移计算下一个子串的公共前后缀
                i++;
            } else {
                //上一次的公共前缀的后一位和本次字符串的最后一位不相等，也就是i所指的位置
                //此时的len需要先减1获取斜着的位置的prefix中的值即frefix[len -1]以这个值作为新的len
                //增加判断放置len斜着往左走的时候索引越界
                if (len > 0) {
                    len = prefix[len -1];
                } else {
                    //当pattern[i] == pattern[len]不相等的时候并且退到了prefix的最左端没办法再退了
                    prefix[i] = len;//此处的len也可以换成0，因为此时的len就是0
                    i++;
                }
            }
        }
        return prefix;
    }


    /**
     * 对使用prefixTable方法生成的前缀表进行移位，最前的位置填充-1，形成一个空位置
     * @auther 梁伟
     * @Description 移动将原来的prefixTable第一位填充-1，最后一位舍弃
     * @Date 2020/4/6 20:05
     * @Param [pattern, n]
     * @return int[]
     **/
    public static int[] movePrefixTable(char[] pattern, int n) {
        int[] prefixTable = prefixTable(pattern, n);
        int[] movePrefixTable = new int[n];
        movePrefixTable[0] = -1;
        for (int i = 1; i < movePrefixTable.length; i++) {
            movePrefixTable[i] = prefixTable[i-1];
        }
        return movePrefixTable;
    }
}
