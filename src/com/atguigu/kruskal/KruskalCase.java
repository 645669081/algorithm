package com.atguigu.kruskal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * author:w_liangwei
 * date:2020/4/7
 * Description: 克鲁斯卡尔算法构建最小生成树
 * 1..将边按照权值从小到大的顺序加入到生成树
 * 2. 加入时判断该边是否会和原有生成树构成回路，构成则不添加继续找下一个次小的
 * 3. 由于n个顶点需要n-1条边构建最小生成树，所以添加边到达n-1时则最小生成树构建完成
 */
public class KruskalCase {
    public static void main(String[] args) {
        char[] vertexs = {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        int INF = Integer.MAX_VALUE;
        //克鲁斯卡尔算法的邻接矩阵
        int[][] matrix = {
                /*A*//*B*//*C*//*D*//*E*//*F*//*G*/
                /*A*/ {   0,  12, INF, INF, INF,  16,  14},
                /*B*/ {  12,   0,  10, INF, INF,   7, INF},
                /*C*/ { INF,  10,   0,   3,   5,   6, INF},
                /*D*/ { INF, INF,   3,   0,   4, INF, INF},
                /*E*/ { INF, INF,   5,   4,   0,   2,   8},
                /*F*/ {  16,   7,   6, INF,   2,   0,   9},
                /*G*/ {  14, INF, INF, INF,   8,   9,   0}};

        Graph graph = new Graph(matrix, vertexs);
        graph.showGraph();

        Edata[] edata = graph.getEdata();
        System.out.println(Arrays.toString(edata));
        graph.kruskal();
    }
}


class Graph {
    //保存边的关系，填写的是两个顶点之间的权值
    public int[][] edges;

    public int edgeNum;

    //使用 INF 表示两个顶点不能连通
    private static final int INF = Integer.MAX_VALUE;

    //保存顶点数据
    public char[] vertexs;

    public Graph(int[][] edges, char[] vertexs) {
        if (edges.length <= 0 && edges.length != vertexs.length) {
            throw new RuntimeException("边和顶点不对应");
        }
        this.edges = edges;
        this.vertexs = vertexs;
        //统计边的条数,二层循环使用i+1为起始点，这样可以只统计矩阵的一半，无向图可以去除重复边即只统计矩阵的左上角
        for(int i =0; i < vertexs.length; i++) {
            for(int j = i + 1; j < vertexs.length; j++) {
                if(this.edges[i][j] != INF) {
                    edgeNum++;
                }
            }
        }
    }

    //显示图的邻接矩阵
    public void showGraph() {
        System.out.println("邻接矩阵为: \n");
        for(int i = 0; i < vertexs.length; i++) {
            for(int j=0; j < vertexs.length; j++) {
                System.out.printf("%12d", edges[i][j]);
            }
            System.out.println();
        }
    }

    /**
     * 获取当前图中的所有边数据
     * @return
     */
    public Edata[] getEdata() {
        int index = 0;
        Edata[] edata = new Edata[edgeNum];
        //由于无向图的使用邻接矩阵表示时的对称性，左下角和右上角的数据是相同的。所以只需要统计一个角就可以获取到所有的边
        for (int i = 0; i < edges.length; i++) {
            for (int j = i + 1; j < edges.length; j++) {
                if (edges[i][j] != INF) {
                    Edata data = new Edata(vertexs[i], vertexs[j], edges[i][j]);
                    edata[index] = data;
                    index++;
                }
            }
        }
        return edata;
    }
    /**
     * 克鲁斯卡尔构建最小生成树
     */
    public void kruskal() {
        int[] ends = new int[vertexs.length];
        //存放已经跟随边加入生成树中的顶点
        Edata[] minTree = new Edata[vertexs.length];
        //获取边并对边进行排序
        Edata[] edata = getEdata();
        Arrays.sort(edata);
        //选出权值最小的边
        int index = 0;
        for (int i = 0; i < edata.length; i++) {
            Edata data = edata[i];
            //判断如果加入该边是否会形成回路
            char start = data.start;
            char end = data.end;
            //获取start和end顶点的索引
            int startIndex = getVertexIndex(start);
            int endIndex = getVertexIndex(end);
            //获取start和end对应的终点
            int startTerminus = getEnd(ends, startIndex);
            int endTerminus = getEnd(ends, endIndex);
            if (startTerminus != endTerminus) {
                //将边加入到最小生成树数组
                minTree[index] = data;
                index++;
                //将当前顶点的终点信息加入到终点查询表ends
                ends[startIndex] = endIndex;
                System.out.println("当前加入的边是：" + data);
            }
        }
        System.out.println("最小生成树为");
        for(int i = 0; i < minTree.length; i++) {
            System.out.println(minTree[i]);
        }
    }

    /**
     * 根据顶点内容获取其索引
     * @param vertexVal 顶点的值
     * @return
     */
    public int getVertexIndex(char vertexVal) {
        for (int i = 0; i < vertexs.length; i++) {
            if (vertexs[i] == vertexVal) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据当前顶点索引查找其终点
     * @param ends 保存当前顶点终点的数组
     * @param i 要查询的顶点坐标
     * @return
     */
    public int getEnd(int[] ends, int i) {
        while (ends[i] != 0) {
            //如果当前点有终点，那么用新的点查找终点，一直查找到当前值为0.这时i就是最终的终点
            //A---->B-----C-----D,在访问A获取下一个终点B的索引，再用B去查查到了C，最后一直查到D就是A最终的终点
            i = ends[i];
        }
        return i;
    }
}

/**
 * 用来表示一条边上的两个点
 */
class Edata implements Comparable<Edata> {
    public char start;
    public char end;
    public int weight;

    public Edata(char start, char end, int weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "EData [<" + start + ", " + end + ">= " + weight + "]";
    }

    @Override
    public int compareTo(Edata o) {
        return this.weight - o.weight;
    }
}
