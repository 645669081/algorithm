package com.atguigu.kruskal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * author:w_liangwei
 * date:2020/4/7
 * Description: 克鲁斯卡尔算法构建最小生成树,对图中环路的检测使用了并查集来实现
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
        //初始化变量用于检测是否构成环路
        int edgeNum = vertexs.length;
        int[] parent = new int[edgeNum];
        int[] rank = new int[edgeNum];
        init(parent, rank);
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
            //查找start和end是否有相同的终点
            int startTerminus = findRoot(startIndex, parent);
            int endTerminus = findRoot(endIndex, parent);
            //终点不同添加到生成树
            if (startTerminus != endTerminus) {
                //将边加入到最小生成树数组
                minTree[index++] = data;
                //合并两个点
                unionVertices(startIndex, endIndex, parent, rank);
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
     * @auther 梁伟
     * @Description 初始化数组元素为-1
     * @Date 2020/4/8 6:03
     * @param parent 要使用的parent数组
     * @param rank 用来做并查集的压缩路径，减小树的查找高度
     * @return void
     **/
    public void init(int[] parent, int[] rank) {
        for (int i = 0; i < parent.length; i++) {
            rank[i] = 0;
            parent[i] = -1;
        }
    }

    /**
     * @auther 梁伟
     * @Description 查找索引为i的顶点的父节点
     * @Date 2020/4/8 6:07
     * @param i 顶点当前索引
     * @param parent 保存顶点的父节点的数组
     * @return int
     **/
    public int findRoot(int i, int[] parent) {
        int iRoot = i;
        while (parent[iRoot] != -1) {
            iRoot = parent[iRoot];
        }
        return iRoot;
    }

    /**
     * @auther 梁伟
     * @Description 合并两个并查集
     * @Date 2020/4/8 6:08
     * @Param [x, y] 每个集合的最终父节点
     * @param rank 保存树的高度
     * @return int 返回1合并成功，返回1合并失败。合并的两个点在同一个集合时会合并失败
     **/
    public int unionVertices(int x, int y, int[] parent, int[] rank) {
        //找两个点的根节点
        int xRoot = findRoot(x, parent);
        int yRoot = findRoot(y, parent);
        //判断根节点是否在同一个集合
        if (xRoot == yRoot) {
            return 0;
        } else {
            //哪个点所在的高度高，在高度高的树上的点就是父节点。通过对树高度的压缩
            //来减小查找根节点的次数
            if (rank[xRoot] > rank[yRoot]) {
                parent[yRoot] = xRoot;
            } else if (rank[yRoot] > rank[xRoot]){
                parent[xRoot] = yRoot;
            } else {
                //当两个相等时，xRoot和yRoot哪个作为根节点都可以，但是需要给作为根节点方的高度加1
                parent[xRoot] = yRoot;
                rank[yRoot] = rank[yRoot] + 1;
            }
            return 1;
        }
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
