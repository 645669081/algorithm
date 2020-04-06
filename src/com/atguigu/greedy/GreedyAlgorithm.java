package com.atguigu.greedy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * @Date 2020/4/7 5:59
 * @Auther 梁伟
 * @Description 贪心算法是每次都选择最优的，但最终得到的结果可能不是最优的，只能接近最优解。而且可能会出现多种选择的情况
 *
 * 从k1,k2,k3,k4,k5这几个电台中选择几个能覆盖最多地区的电台，使用贪心算法就是每次选择的电台都是未被覆盖区域最多的，即选一个可以覆盖最多未覆盖区域的
 * 电台，通过局部最优来靠近全局最优。结果可能有多种情况
 */
public class GreedyAlgorithm {
    public static void main(String[] args) {
        //创建广播电台,放入到Map
        HashMap<String, HashSet<String>> broadcasts = new HashMap<String, HashSet<String>>();
        //将各个电台放入到broadcasts
        HashSet<String> k1Region = new HashSet<String>();
        k1Region.add("北京");
        k1Region.add("上海");
        k1Region.add("天津");

        HashSet<String> k2Region = new HashSet<String>();
        k2Region.add("广州");
        k2Region.add("北京");
        k2Region.add("深圳");

        HashSet<String> k3Region = new HashSet<String>();
        k3Region.add("成都");
        k3Region.add("上海");
        k3Region.add("杭州");


        HashSet<String> k4Region = new HashSet<String>();
        k4Region.add("上海");
        k4Region.add("天津");

        HashSet<String> k5Region = new HashSet<String>();
        k5Region.add("杭州");
        k5Region.add("大连");

        //加入到map
        broadcasts.put("K1", k1Region);
        broadcasts.put("K2", k2Region);
        broadcasts.put("K3", k3Region);
        broadcasts.put("K4", k4Region);
        broadcasts.put("K5", k5Region);

        //allAreas存放所有的地区，用于在选择时和未选择的电台做比较
        HashSet<String> allAreas = new HashSet<String>();
        allAreas.add("北京");
        allAreas.add("上海");
        allAreas.add("天津");
        allAreas.add("广州");
        allAreas.add("深圳");
        allAreas.add("成都");
        allAreas.add("杭州");
        allAreas.add("大连");

        //用于挑选出覆盖最多区域的电台
        String maxKey;

        //用于记录最终选择结果
        List<String> result = new ArrayList<>();

        //只要还有区域未被覆盖就一直找
        while (allAreas.size() > 0) {
            maxKey = null;
            //比较哪个电台覆盖的未被覆盖的区域多，即当前电台区域和allAreas交集最多
            for (String radioName : broadcasts.keySet()) {
                HashSet<String> region = broadcasts.get(radioName);
                region.retainAll(allAreas);
                //比较该区域覆盖是否大于maxkey
                if (maxKey == null || region.size() > broadcasts.get(maxKey).size()) {
                    maxKey = radioName;
                }
            }
            //将已经覆盖的区域从allAreas移除
            allAreas.removeAll(broadcasts.get(maxKey));
            //添加到已选择结果中
            result.add(maxKey);
        }
        System.out.println("选择结果是：" + result);
    }
}
