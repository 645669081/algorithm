package com.atguigu.kmp;

/**
 * @Date 2020/4/6 14:17
 * @Auther 梁伟
 * @Description 字符串搜索--暴力匹配
 */
public class ViolenceMatch {
    public static void main(String[] args) {
        //测试暴力匹配算法
        String str1 = "硅硅谷 尚硅谷你尚硅 尚硅谷你尚硅谷你尚硅你好";
        String str2 = "尚硅谷你尚硅你";
        int index = violenceMatch(str1, str2);
        System.out.println("index=" + index);
    }

    // 暴力匹配算法实现
    /**
     * @auther 梁伟
     * @Description
     * @Date 2020/4/6 14:35
     * @Param [str1, str2] str1是整串，str2是子串
     * @return int
     **/
    public static int violenceMatch(String str1, String str2) {
        char[] s1 = str1.toCharArray();
        char[] s2 = str2.toCharArray();
        int i = 0; // i索引指向s1
        int j = 0; // j索引指向s2

        while (i < s1.length && j < s2.length) {
            //相同时两个指针同时移位
            if (s1[i] == s2[j]) {
                i++;
                j++;
            } else {
                //不同时子串的指针j置为0重头开始，整串的指针每次不同时向前移动一个
                i = i -j + 1;
                j = 0;
            }
        }
        //说明子串搜索到了末尾全部相同
        if (j == s2.length) {
            //整串指针位置减去子串的长度就可以算出起始匹配的位置
            return i - j;
        } else {
            return -1;
        }
    }
}
