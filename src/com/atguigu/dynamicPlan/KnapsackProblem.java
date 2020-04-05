package com.atguigu.dynamicPlan;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Date 2020/4/5 15:48
 * @Auther 梁伟
 * @Description 使用动态规划求解背包问题，求解该问题尽量不要使用递归，递归会对同一个值进行多次计算
 * 问题描述
 * 使用一个容量一定的背包，在若干件商品中，小偷如何偷取才能让偷取的价值是最多的
 *
 * B为一个最大价值计算的函数，需要输入当前拿的是第几件物品和背包剩余空间
 * B(k,w):以当前背包剩余空间为w去放入第k件物品，对于放入第k件有以下几种情况
 * 1.背包放不下该物品，则此时B(k,w)=B(k-1,w),即和上一次放入的最优解相同
 * 2.当背包能放下该物品，此时需要看拿或不拿哪个获得的收益更大
 *      1.拿：此时收益为B(k-1,w-w[k]) + v[k]。可以倒推理解为第k件物品的价值v[k]，但是此时剩余空间为w-w[k],然后以该空间去拿前边k-1件商品
 *      2.不拿：此时获得的收益和背包放不下相同，B(k-1,w)
 *
 *
 * 此类问题推倒，通过递归斐波那契数列求解时其实造成了值的重复运算，其复杂度为n^2。而动态规划这样的问题也是需要建立在前一次对问题求解的基础上
 * 进行下一次求解，所以我们不想用递归，使用一个二维数组来保存，避免重复的去计算一些已经算过的值。
 */
public class KnapsackProblem {
    public static void main(String[] args) {
        //物品数量，这两个变量要预留一个0的位置，即背包0容量和物品0数量这两种情况，每一个分别占据一行一列
        int N = 6;
//        int N = 4;
        //背包大小
        int W = 21;
//        int W = 5;

        //物品重量,之所以两个数组的最前边加了一个0是因为动态规划一般都是从0开始，即实际填写数据的位置是(1,1)这个坐标开始的。
        //即B数组中上边一行0，左边一行0
        int[] w = {0, 2, 3, 4, 5, 9};
//        int[] w = {0, 1, 4, 3};
        //物品价值，这两个数组是一一对一个关系，即相同的索引下的就是该物品的重量和价值
        int[] v = {0, 3, 4, 5, 8 ,10};
//        int[] v = {0, 1500, 3000, 2000};
        //最终要打印的结果数组,横行为物品，列为背包容量。此时说的是最大的背包容量和最大物品数量。包含两个0点
        int[][] B = new int[N][W];
        //创建一个记录最优解将哪些物品放入了背包
        int[][] path = new int[N][W];

        //将第0行和第0列初始化为0，即对应没有一件物品和一个没有容量的背包的情况
        //没有物品的情况
        for (int i = 0; i < w.length; i++) {
            B[0][i] = 0;
        }
        //没有容量的情况
        for (int i = 0; i < v.length; i++) {
            B[i][0] = 0;
        }
        //从坐标为(1,1)的位置开始填写B表，当前背包剩余容量为c,当前是第k件物品。遍历时以背包最大容量W和物品最大数量N为边界
        for (int c = 1; c < W; c++) {
            for (int k = 1; k < N; k++) {
                //背包当前容量放不下第k件物品，那么B(K,C)就等于它上一次拿的最大价值B(K-1,C)
                if (w[k] > c) {
                    B[k][c] = B[k-1][c];
                } else {
                    //此时通过最大价值比较来判断偷还是不偷第k件物品
                    //B(k-1,c-w[k]) + v[k]。当前最大价值=偷前k-1件的物品价值 + 第k件物品价值。
                    // 而且偷前k-1件的物品空间由于偷了第k件就只剩下了c-w[k]的空间，采用一种倒推的思想，先去拿第k件，然后再去拿前k-1件
                    int steal = B[k-1][c - w[k]] + v[k];
                    int notSteal = B[k-1][c];//B(k-1,c)
                    if (steal > notSteal) {
                        B[k][c] = steal;
                        //物品放入了背包，该位置标记为1，用于最终获取放入了哪些物品
                        path[k][c] = 1;
                    } else {
                        B[k][c] = notSteal;
                    }
                }
            }
        }
        print(B);
        System.out.println("当前最优解是：" + B[N-1][W-1]);
//        print(path);
        printPath(path, w);
    }

    public static void print(int[][] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + "\t");
            }
            System.out.println();
        }
    }

    /**
     * @auther 梁伟
     * @Description 打印最优解的放置物品的顺序
     * @Date 2020/4/5 20:26
     * @Param [path] 在放入背包时已经有了标记的路径数组
     * @return void
     **/
    public static void printPath(int[][] path, int[] w) {
        //列的最大索引
        int j = path[0].length -1;
        int i = path.length -1;
        //只有逆向遍历才能拿到最优解的第一个路径，不然无法确定最优解路径在哪
        while (i > 0 && j > 0) {
            //打印被标记路径
            if (path[i][j] == 1) {
                System.out.printf("第%d个商品放入到背包\n", i);
                //当前重量j减去最后一个物品重量，那么就能得到上一个物品加入过后的重量，这样就定位了j的位置。采用了倒推的方式
                j -= w[i];
            }
            //i减小到上一个物品再根据得到的j来确定上一个物品拿的是谁
            i--;
        }
    }
}
