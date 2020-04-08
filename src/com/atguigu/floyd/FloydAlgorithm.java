package com.atguigu.floyd;

import java.util.Arrays;

/**
 * @Date 2020/4/9 6:40
 * @Auther 梁伟
 * @Description 弗洛伊德算法求所有点之间的最短路径，它将所有点作为中转节点得到的距离与原先两点间的距离比较，如果小就替换原来的。最终会得到所有点之间
 * 的最短路径
 */
public class FloydAlgorithm {
    public static void main(String[] args) {
        char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
        //创建邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;
        matrix[0] = new int[] { 0, 5, 7, N, N, N, 2 };
        matrix[1] = new int[] { 5, 0, N, 9, N, N, 3 };
        matrix[2] = new int[] { 7, N, 0, N, 8, N, N };
        matrix[3] = new int[] { N, 9, N, 0, N, 4, N };
        matrix[4] = new int[] { N, N, 8, N, 0, 5, 4 };
        matrix[5] = new int[] { N, N, N, 4, 5, 0, 6 };
        matrix[6] = new int[] { 2, 3, N, N, 4, 6, 0 };

        //创建 Graph 对象
        Graph graph = new Graph(vertex, matrix);
        graph.floyd();
        graph.show();
    }
}

// 创建图
class Graph {
    //存放顶点
    public char[] vertexs;
    //存放距离
    public int[][] dis;
    //存放前驱
    public int[][] pre;

    public Graph(char[] vertexs, int[][] dis) {
        this.vertexs = vertexs;
        this.dis = dis;
        this.pre = new int[vertexs.length][vertexs.length];
        //初始化每一个顶点的前驱为自己,注意存放的是前驱顶点的下标
        for (int i = 0; i < pre.length; i++) {
            Arrays.fill(pre[i], i);
        }
    }

    // 显示pre数组和dis数组
    public void show() {
        //为了显示便于阅读，我们优化一下输出
        char[] vertex = vertexs;
        for (int k = 0; k < dis.length; k++) {
            // 先将pre数组输出的一行
            for (int i = 0; i < dis.length; i++) {
                System.out.print(vertex[pre[k][i]] + " ");
            }
            System.out.println();
            // 输出dis数组的一行数据
            for (int i = 0; i < dis.length; i++) {
                System.out.print("("+vertex[k]+"到"+vertex[i]+"的最短路径是" + dis[k][i] + ") ");
            }
            System.out.println();
            System.out.println();
        }
    }

    public void floyd() {
        //遍历中转点
        for (int k = 0; k < vertexs.length; k++) {
            //遍历出发点
            for (int i = 0; i < vertexs.length; i++) {
                //遍历终点
                for (int j = 0; j < vertexs.length; j++) {
                    int len = dis[i][k] + dis[k][j];
                    //更新前驱表和距离表
                    if (len < dis[i][j]) {
                        dis[i][j] = len;
                        pre[i][j] = pre[k][j];
                    }
                }
            }
        }
    }
}
