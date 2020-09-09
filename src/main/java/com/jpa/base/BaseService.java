package com.jpa.base;

import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

//        ExampleMatcher.GenericPropertyMatchers.storeDefaultMatching()//default
//        ExampleMatcher.GenericPropertyMatchers.contains() 包含关键字
//        ExampleMatcher.GenericPropertyMatchers.startsWith() 前缀匹配
//        ExampleMatcher.GenericPropertyMatchers.ignoreCase() 忽略大小写

public interface BaseService<T> {

    void addBean(T bean);

    void updateBean(T bean);

    void deleteBean(Long id);

    T findBeanById(Long id);

    List<T> findBean(T bean);

    Page<T> searchBean(T bean, Integer pageIndex, Integer pageSize);

    ExampleMatcher getExampleMatcher(T bean, boolean contains);

    //实体类赋值转换
    default void convertBean(T source, T target) {
        Field[] sourceField = target.getClass().getDeclaredFields();
        try {
            for (int i = 0; i < sourceField.length; i++) {
                String fieldName = sourceField[i].getName();
                String fieldName2 = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method mSet = target.getClass().getDeclaredMethod("set" + fieldName2, sourceField[i].getType());
                Method mGet = target.getClass().getDeclaredMethod("get" + fieldName2);

                Object value = mGet.invoke(source);
                if(value!=null){
                   // System.out.println("fieldName= " + fieldName + ", value= " + value);
                    mSet.invoke(target, value);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
