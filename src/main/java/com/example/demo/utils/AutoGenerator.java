package com.example.demo.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;

public class AutoGenerator {
    public static void main(String[] args){
        //代码生成器
        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true) // 是否支持AR模式
                .setAuthor("yunmengmeng") // 作者
                //.setOutputDir("D:\\workspace_mp\\mp03\\src\\main\\java") // 生成路径
                .setOutputDir("D:\\work") // 生成路径
                .setFileOverride(true)  // 文件覆盖
                .setIdType(IdType.AUTO) // 主键策略
                .setServiceName("%sService")  // 设置生成的service接口的名字的首字母是否为I

                // IEmployeeService
                .setBaseResultMap(true)//生成基本的resultMap
                .setBaseColumnList(true);//生成基本的SQL片段

        //2. 数据源配置
        DataSourceConfig  dsConfig  = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
                .setDriverName("com.mysql.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/demo?serverTimezone=GMT")
                .setUsername("root")
                .setPassword("root");
//                .setDriverName("com.mysql.jdbc.Driver")
//                .setUrl("jdbc:mysql://172.18.255.252:3306/asset")
//                .setUsername("asset")
//                .setPassword("Youcheng2017!");

        List<String> tableList=new ArrayList<String>();
        tableList.add("tree");
//        tableList.add("lab_model_auction");
//        tableList.add("lab_model_dishonest");
        /*tableList.add("lab_model_auction_extract_distribute");
        tableList.add("lab_model_auction_extract");
        tableList.add("lab_model_auction_extract_detail");
        tableList.add("lab_model_bankruptcy");*/

        String[] strings = new String[tableList.size()];

        tableList.toArray(strings);
        //3. 策略配置globalConfiguration中
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setCapitalMode(true) //全局大写命名
                //.setDbColumnUnderline(true)  // 指定表名 字段名是否使用下划线
                .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                // .setColumnNaming(NamingStrategy.isPrefixContained())
                //.setTablePrefix("tbl_")
                .setInclude(strings);  // 生成的表




        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent("com.hu")
                .setMapper("mapper.db1")//dao
                .setEntity("entity.db1")
                .setXml("mapper.db1");//mapper.xml

        //5. 整合配置
        com.baomidou.mybatisplus.generator.AutoGenerator ag = new com.baomidou.mybatisplus.generator.AutoGenerator();
        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig);


        //6. 执行
        ag.execute();


    }
}
