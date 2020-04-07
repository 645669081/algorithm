package com.atguigu.prim;

import java.util.Arrays;

/**
 * @Date 2020/4/7 6:54
 * @Auther 梁伟
 * @Description 使用普利姆算法获得最小生成树，解决修路问题。将顶点分为生成树内的和生成树外的，每次都选择一个外部顶点保证该外部顶点与内部顶点的权值
 * 最小，找到后加入到内部。依次这样直到最后完成最小生成树的构建
 *
 * 在7个村子间修路，每条路的远近不同，在哪几个村子间修路可以使总的路的长度最短并使这些村子全部可以通行
 */
public class PrimAlgorithm {
    public static void main(String[] args) {
        String[] vertex = {"A","B","C","D","E","F","G"};
        //邻接矩阵的关系使用二维数组表示,10000这个大数，表示两个点不联通
        int[][] edges = new int[][]{
                {10000,5,7,10000,10000,10000,2},
                {5,10000,10000,9,10000,10000,3},
                {7,10000,10000,10000,8,10000,10000},
                {10000,9,10000,10000,10000,4,10000},
                {10000,10000,8,10000,10000,5,4},
                {10000,10000,10000,4,5,10000,6},
                {2,3,10000,10000,4,6,10000},};
        Graph graph = new Graph(edges, vertex);
        graph.showGraph();

        graph.prim(0);
    }
}


class Graph {
    //保存边的关系，填写的是两个顶点之间的权值
    public int[][] edges;

    //保存顶点数据
    public String[] vertexs;

    public Graph(int[][] edges, String[] vertexs) {
        if (edges.length <= 0 && edges.length != vertexs.length) {
            throw new RuntimeException("边和顶点不对应");
        }
        this.edges = edges;
        this.vertexs = vertexs;
    }

    //显示图的邻接矩阵
    public void showGraph() {
        for(int[] link: this.edges) {
            System.out.println(Arrays.toString(link));
        }
    }

    /**
     * 使用prim算法获取最小生成树
     * @param vertexNo 最小生成树的起始顶点，为顶点在数组中下标索引的位置
     */
    public void prim(int vertexNo) {
        //用于记录哪些点已经进入到了最小生成树中，0为未进入，1为已进入
        int[] visited = new int[vertexs.length];
        //把第一个点放入生成树
        visited[vertexNo] = 1;
        //最小权重记录，初始化为大值，表示顶点间未联通
        int minWeight = 10000;
        int minWeightVertex1 = -1;
        int minWeightVertex2 = -1;
        //每次遍历都只能让一个顶点加入最小生成树,最开始的一个节点已经加入，所以从1开始
        for (int k = 1; k < vertexs.length; k++) {
            //获取生成树中的所有顶点与未进入生成树中的顶点之间的权值，看哪个最小就加入到生成树中
            for (int i = 0; i < vertexs.length; i++) {//遍历已经进入生成树的
                if (visited[i] != 1) {
                    //排除没有进入生成树的节点
                    continue;
                }
                for (int j = 0; j < vertexs.length; j++) {//遍历未进入生成树的
                    if (visited[j] == 0 && edges[i][j] < minWeight) {
                        //还没有进入生成树的顶点
                        minWeight = edges[i][j];
                        minWeightVertex1 = i;
                        minWeightVertex2 = j;
                    }
                }
            }
            //找到一条边是最小
            System.out.println("边<" + vertexs[minWeightVertex1] + "," + vertexs[minWeightVertex2] + "> 权值:" + minWeight);
            //将j顶点加入生成树
            visited[minWeightVertex2] = 1;
            //将值置为初始状态开始下一次查找
            minWeight = 10000;
        }
    }
}
