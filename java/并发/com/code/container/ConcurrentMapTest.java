package com.code.container;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @auther 阳少文
 * @create 2020-12-25 22:16
 */
public class ConcurrentMapTest {


    public  static void main(String[] args) {
        baseOperation();
    }



    //基本使用
    private static void baseOperation(){
        ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
        //与原有put方法不同的是，putIfAbsent方法中如果插入的key相同，则不替换原有的value值；
        map.putIfAbsent("1", "2");
        System.out.println("执行putIfAbsent方法后: "+map);
        //与原有remove方法不同的是，新remove方法中增加了对value的判断，
        // 如果要删除的key--value不能与Map中原有的key--value对应上，则不会删除该元素;
        map.remove("1", "3");
        System.out.println("执行remove方法后: "+map);
        //增加了对value值的判断，如果key--oldValue能与Map中原有的key--value对应上，才进行替换操作
        map.replace("1", "2", "3");
        System.out.println("执行replace方法后: "+map);
        //与上面的replace不同的是，此replace不会对Map中原有的key--value进行比较，如果key存在则直接替换；
        map.replace("1", "2");
        System.out.println("执行replace方法后: "+map);
    }


}
