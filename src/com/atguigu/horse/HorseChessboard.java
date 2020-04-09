package com.atguigu.horse;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author:w_liangwei
 * date:2020/4/9
 * Description: 骑士周游问题
 */
public class HorseChessboard {
    //列
    public static int X;

    //行
    public static int Y;

    //该点是否已经走过
    public static boolean[] visited;

    //是否已经走完了棋盘上的所有点
    public boolean finish;

    public static void main(String[] args) {
        X = 8;
        Y = 8;
        //创建棋盘
        int[][] chessboard = new int[X][Y];
        //初始化标记位，初始化为全部未访问
        visited = new boolean[X * Y];

        //马儿初始位置的行，从0开始编号
        int row = 0;
        //马儿初始位置的列，从0开始编号
        int column = 0;
        traversalChessboard(chessboard, row, column, 1);
    }

    /**
     * 完成骑士周游问题的算法
     * @param chessboard 棋盘
     * @param row 马儿当前的位置的行 从0开始
     * @param column 马儿当前的位置的列  从0开始
     * @param step 是第几步 ,初始位置就是第1步
     */
    public static void traversalChessboard(int[][] chessboard, int row, int column, int step) {
        //获取当前位置的所有可走的点
        List<Point> pointList = getNext(new Point(row, column));
        //设置当前位置为已访问
        visited[row * X + column] = true;

        //走旁边的每一个点
        if (!pointList.isEmpty()) {
            for (Point point : pointList) {
                //如果未访问就去访问
                if (!visited[point.y * X + point.x]) {
                    traversalChessboard(chessboard, point.x, point.y, step++);
                }
            }
        }
    }

    /**
     * 查询当前位置可以走哪些位置
     * @param currentPoint 当前点所在位置
     * @return
     */
    public static List<Point> getNext(Point currentPoint) {
        List<Point> list = new ArrayList<>();
        //0号点
        if (currentPoint.x + 2 < X && currentPoint.y + 1 < Y) {
            list.add(new Point(currentPoint.x + 2, currentPoint.y + 1));
        }
        //1号点
        if (currentPoint.x + 2 < X && currentPoint.y - 1 > 0 ) {
            list.add(new Point(currentPoint.x + 2, currentPoint.y -1));
        }
        //2号点
        if (currentPoint.x + 1 < X && currentPoint.y - 2 > 0) {
            list.add(new Point(currentPoint.x + 1, currentPoint.y - 2));
        }
        //3号点
        if (currentPoint.x - 1 > 0 && currentPoint.y - 2 > 0) {
            list.add(new Point(currentPoint.x - 1, currentPoint.y - 2));
        }
        //4号点
        if (currentPoint.x - 2 > 0 && currentPoint.y - 1 > 0) {
            list.add(new Point(currentPoint.x - 2, currentPoint.y - 1));
        }
        //5号点
        if (currentPoint.x - 2 > 0 && currentPoint.y + 1 < Y) {
            list.add(new Point(currentPoint.x - 2, currentPoint.y + 1));
        }
        //6号点
        if (currentPoint.x - 1 > 0 && currentPoint.y + 2 < Y) {
            list.add(new Point(currentPoint.x - 1, currentPoint.y + 2));
        }
        //7号点
        if (currentPoint.x + 1 < X && currentPoint.y + 2 < Y) {
            list.add(new Point(currentPoint.x + 1, currentPoint.y + 2));
        }
        return list;
    }
}
