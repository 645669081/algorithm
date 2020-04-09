package com.atguigu.horse;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
    public static boolean finish;

    public static void main(String[] args) {
        X = 6;
        Y = 6;
        //创建棋盘
        int[][] chessboard = new int[X][Y];
        //初始化标记位，初始化为全部未访问
        visited = new boolean[X * Y];

        //马儿初始位置的行，从0开始编号
        int row = 0;
        //马儿初始位置的列，从0开始编号
        int column = 0;

        //测试一下耗时
        long start = System.currentTimeMillis();
        System.out.println("起始时间：" + start);
        traversalChessboard(chessboard, row, column, 1);
        long end = System.currentTimeMillis();
        System.out.println("共耗时: " + (end - start) + " 毫秒");

        //输出棋盘的最后情况
        for(int[] rows : chessboard) {
            for(int step: rows) {
                System.out.print(step + "\t");
            }
            System.out.println();
        }
    }

    /**
     * 完成骑士周游问题的算法,在棋盘的每一个位置标记当前走的步数，如果该位置失败就修改标记为未走过和步数为0，直到步数和棋盘位置一样多时说明成功
     * @param chessboard 棋盘
     * @param row 马儿当前的位置的行 从0开始
     * @param column 马儿当前的位置的列  从0开始
     * @param step 是第几步 ,初始位置就是第1步
     */
    public static void traversalChessboard(int[][] chessboard, int row, int column, int step) {
        //设置棋盘上的当前位置为当前的步数
        chessboard[row][column] = step;
        //设置当前位置为已访问
        visited[row * X + column] = true;
        //获取当前位置的所有可走的点,列对应的是x,行对应的是y
        List<Point> pointList = getNext(new Point(column, row));
        sort(pointList);
        //走旁边的每一个点
        while (!pointList.isEmpty()) {
            Point point = pointList.remove(0);
            //如果未访问就去访问
            if (!visited[point.y * X + point.x]) {
                traversalChessboard(chessboard, point.y, point.x, step + 1);
            }
        }
        /*判断马儿是否完成了任务，使用step和应该走的步数比较.没有走成功就将当前位置设置未访问并把棋盘上的步数置为0
        说明: step < X * Y  成立的情况有两种
        1. 棋盘到目前位置,仍然没有走完
        2. 棋盘处于一个回溯过程
        */
        if (step < X * Y && !finish) {
            chessboard[row][column] = 0;
            visited[row * X + column] = false;
        } else {
            finish = true;
        }
    }

    //根据当前这个一步的所有的下一步的选择位置，进行非递减排序, 减少回溯的次数
    public static void sort(List<Point> ps) {
        ps.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                int count1 = getNext(o1).size();
                int count2 = getNext(o2).size();
                if(count1 < count2) {
                    return -1;
                } else if (count1 == count2) {
                    return 0;
                } else {
                    return 1;
                }
            }
        });
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
        if (currentPoint.x + 2 < X && currentPoint.y - 1 >= 0 ) {
            list.add(new Point(currentPoint.x + 2, currentPoint.y -1));
        }
        //2号点
        if (currentPoint.x + 1 < X && currentPoint.y - 2 >= 0) {
            list.add(new Point(currentPoint.x + 1, currentPoint.y - 2));
        }
        //3号点
        if (currentPoint.x - 1 >= 0 && currentPoint.y - 2 >= 0) {
            list.add(new Point(currentPoint.x - 1, currentPoint.y - 2));
        }
        //4号点
        if (currentPoint.x - 2 >= 0 && currentPoint.y - 1 >= 0) {
            list.add(new Point(currentPoint.x - 2, currentPoint.y - 1));
        }
        //5号点
        if (currentPoint.x - 2 >= 0 && currentPoint.y + 1 < Y) {
            list.add(new Point(currentPoint.x - 2, currentPoint.y + 1));
        }
        //6号点
        if (currentPoint.x - 1 >= 0 && currentPoint.y + 2 < Y) {
            list.add(new Point(currentPoint.x - 1, currentPoint.y + 2));
        }
        //7号点
        if (currentPoint.x + 1 < X && currentPoint.y + 2 < Y) {
            list.add(new Point(currentPoint.x + 1, currentPoint.y + 2));
        }
        return list;
    }
}
