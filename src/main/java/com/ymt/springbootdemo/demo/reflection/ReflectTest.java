package com.ymt.springbootdemo.demo.reflection;

import java.lang.reflect.Array;

/**
 * Created by @author yangmingtian on 2019/12/18
 */
public class ReflectTest {
    public static void main(String[] args) throws ClassNotFoundException {
        int[] intArray = (int[]) Array.newInstance(int.class, 3);
        System.out.println(intArray);
        Array.set(intArray, 0,134);
        Array.set(intArray, 1,234);
        Array.set(intArray, 2,1324);
        System.out.println(Array.get(intArray,2));

        Class<?> aClass = Class.forName("[I");
        System.out.println(aClass);
        Class<?> aClass1 = Class.forName("[Ljava.lang.String;");
        System.out.println(aClass1);
    }
}
