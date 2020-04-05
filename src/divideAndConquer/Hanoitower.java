package divideAndConquer;

/**
 * @Date 2020/4/5 10:31
 * @Auther 梁伟
 * @Description  使用分治的思想解决汉诺塔问题
 * 使用分治算法最难的问题是能否合理的确定最小化的问题是什么，只有对最小化问题有了解法，通过分解解决从而完成对大的问题的解决
 */
public class Hanoitower {

    private static int count;

    public static void main(String[] args) {
        hanoiTower(5, 'A', 'B', 'C');
        System.out.println("移动的次数：" + count);
    }

    /**
     * 以最简单的2个盘子为例的流程，柱子从左到右的顺序分别命名为 a,b,c 3个柱子
     * 1.将a柱子最上边的移动到b
     * 2.将a柱子中间的那个移动到c。
     * 3.将b柱子上的移动到c
     * @auther 梁伟
     * @Description 盘在柱子上按照上边编号小，下边编号大。如总共6个盘子，最上边是第6个，最下边就是第一个
     * @Date 2020/4/5 10:32
     * @param num 代表盘的数量
     * @param a,b,c 代表3根柱子,其中a代表起始柱子，c代表目的地柱子
     * @return void
     **/
    public static void hanoiTower(int num, char a, char b, char c) {
        //当盘子只有一个盘子的时候，直接移动到c柱子
        count++;
        if (num == 1) {
            System.out.println("第1个盘从 " + a + "->" + c);
        } else {
            //最上边从a移动到b，不管有多少个只留最下边的一个，其它的看成一个整体搬走
            hanoiTower(num -1, a, c, b);
            //将a柱子最下边的盘子移动到c柱子,此处移动的其实是一个编号为num的盘子，如果使用了递归那就是移动了num个盘子，不是我们的预期操作。
            //我们的预期是将编号为num的盘子从a移动到c
            System.out.println("第" + num + "个盘从 " + a + "->" + c);
            //将b柱子上最开始搬走的盘子移动到c柱子，游戏完成
            hanoiTower(num -1, b, a, c);
        }
    }
}
