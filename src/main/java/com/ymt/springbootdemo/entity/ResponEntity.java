package com.ymt.springbootdemo.entity;

import lombok.Data;

/**
 * Created by @author yangmingtian on 2019/10/25
 */
@Data
public class ResponEntity<T> {

    private int code;

    private String msg;

    private T data;
}
