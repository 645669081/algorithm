package com.atguigu.binarysearchnorecursion;

/**
 * @Date 2020/4/5 10:03
 * @Auther 梁伟
 * @Description 二分查找的非递归算法
 */
public class BinarySearchNoRecur {
    public static void main(String[] args) {
        int[] arr = {1,3, 8, 10, 11, 67, 100};
        int index = binarySearch(arr, 11);
        System.out.println("index=" + index);
    }

    /**
     * 二分查找的非递归实现
     * @param arr 待查找的数组, arr是升序排序
     * @param target 需要查找的数
     * @return 返回对应下标，-1表示没有找到
     */
    public static int binarySearch(int[] arr, int target) {
        if (arr == null || arr.length == 0) {
            return -1;
        }
        int left = 0;
        int right = arr.length -1;
        //只要指针没有越过就一直搜索
        while (left <= right) {
            int mid = (left + right) / 2;
            if (target == arr[mid]) {
                return mid;
            } else if (arr[mid] > target) {
                //右指针左移
                right = mid -1;
            } else {
                //左指针右移
                left = mid + 1;
            }
        }
        return -1;
    }
}
