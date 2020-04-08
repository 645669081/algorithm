package com.atguigu.dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * author:w_liangwei
 * date:2020/4/8
 * Description: 迪杰斯特拉算法获取最短路径
 */
public class DijkstraAlgorithm {
    public static void main(String[] args) {
        char[] vertex = { 'A', 'B', 'C', 'D', 'E', 'F', 'G' };
        //邻接矩阵
        int[][] matrix = new int[vertex.length][vertex.length];
        final int N = 65535;// 表示不可以连接
        matrix[0]=new int[]{N,5,7,N,N,N,2};
        matrix[1]=new int[]{5,N,N,9,N,N,3};
        matrix[2]=new int[]{7,N,N,N,8,N,N};
        matrix[3]=new int[]{N,9,N,N,N,4,N};
        matrix[4]=new int[]{N,N,8,N,N,5,4};
        matrix[5]=new int[]{N,N,N,4,5,N,6};
        matrix[6]=new int[]{2,3,N,N,4,6,N};
        Graph graph = new Graph(vertex, matrix);
        graph.show();
        System.out.println("==============================");

        graph.dijkstra(0);
    }
}

class Graph {

    public char[] vertexs;

    public int[][] edges;

    public int NOT_LINK = 65535;

    public Graph(char[] vertexs, int[][] edges) {
        this.vertexs = vertexs;
        this.edges = edges;
    }

    public void show() {
        for (int i = 0; i < edges.length; i++) {
            System.out.println(Arrays.toString(edges[i]));
        }
    }

    /**
     * 根据当前顶点的索引查询其邻接点
     * @param vertexIndex 顶点的索引
     * @return
     */
    public List<Edata> getAdjoinInfo(int vertexIndex) {
        //保存邻接点的索引和当前点之间的权值
        List<Edata> adjoinInfo = new ArrayList<>();
        for (int i = 0; i < edges[vertexIndex].length; i++) {
            if (edges[vertexIndex][i] < NOT_LINK) {
                adjoinInfo.add(new Edata(i, edges[vertexIndex][i]));
            }
        }
        return adjoinInfo;
    }

    /**
     * 最短路径获取算法
     * @param source 起始点索引
     */
    public void dijkstra(int source) {
        //第一个节点source先要入队
        PriorityQueue<Edata> priorityQueue = new PriorityQueue<>();
        priorityQueue.add(new Edata(source, 0));
        int[] seen = new int[vertexs.length];//使用顶点索引对应，0未访问过，1访问过了
        int[] parent = new int[vertexs.length];//使用顶点索引，获取到的是该顶点的前一个顶点
        //初始化所有节点的前驱节点为-1，表示此时没有前驱节点
        for (int i = 0; i < parent.length; i++) {
            parent[i] =-1;
        }
        int[] distance = new int[vertexs.length];//当前节点和source的距离
        //除了source自己，其它节点全部初始化为65535
        for (int i = 0; i < distance.length; i++) {
            distance[i] = 65535;
        }
        distance[0] = 0;

        while (!priorityQueue.isEmpty()) {
            Edata pair = priorityQueue.poll();
            int dist = pair.distance;
            int vertex = pair.vertex;
            seen[vertex] = 1;
            //获取邻接点
            List<Edata> nodes = getAdjoinInfo(vertex);

            for (int i = 0; i < nodes.size(); i++) {
                Edata node = nodes.get(i);
                //如果没有访问过
                if (seen[node.vertex] != 1) {
                    //当前点的邻接点距离起始点的距离，等于当前点距离起始点距离 + 邻接点距当前点距离。新计算的距离和原来的距离比哪个小就选哪个
                    if (dist + node.distance < distance[node.vertex]) {
                        //设置邻接点的前驱节点为当前顶点
                        parent[node.vertex] = vertex;
                        //跟新邻接点到起始点的距离
                        distance[node.vertex] = dist + node.distance;
                        //将邻接点插入到队列
                        node.distance = dist + node.distance;
                        priorityQueue.add(node);
                    }
                }
            }
        }

        System.out.println("parent数组是：" + Arrays.toString(parent));
        System.out.println("距离数组是：" + Arrays.toString(distance));

        Map<String, Integer> map = new HashMap<>();

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < parent.length; i++) {
            //获取路径
            int j = i;
            while (parent[j] != -1) {
                int preIndex = parent[j];
                stringBuilder.append(vertexs[j]);
                j = preIndex;
            }
            stringBuilder.append(vertexs[source]);
            map.put(stringBuilder.reverse().toString(), distance[i]);
            stringBuilder.delete(0, stringBuilder.length());
        }
        System.out.println("路径对应的长度为：" + map);
    }
}

/**
 * 存放当前点索引和与起始点的距离
 */
class Edata implements Comparable<Edata> {

    //点前点
    public int vertex;

    //该点距离起始点的距离
    public int distance;

    public Edata(int vertex, int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    @Override
    public int compareTo(Edata o) {
        return this.distance - o.distance;
    }

    @Override
    public String toString() {
        return "Edata{" +
                "vertex=" + vertex +
                ", distance=" + distance +
                '}';
    }
}
