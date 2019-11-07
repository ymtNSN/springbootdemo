package com.ymt.springbootdemo.task.elasticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.Arrays;
import java.util.List;

/**
 * Created by @author yangmingtian on 2019/11/6
 */
public class MyDataFlowJob implements DataflowJob<String> {
    @Override
    public List<String> fetchData(ShardingContext shardingContext) {
        System.out.println("开始获取数据");
        return Arrays.asList("ymt", "yang", "ming");
    }

    @Override
    public void processData(ShardingContext shardingContext, List<String> list) {
        for (String s : list) {
            System.out.println("开始处理数据：" + s);
        }
    }
}
