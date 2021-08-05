### seata+eureka分布式数据库事务配置
#### 下载1.4.0版本的seata-server
地址：https://github.com/seata/seata/releases/download/v1.4.0/seata-server-1.4.0.zip

解压后，进入conf目录，打开`registry.conf`文件，将type属性修改为eureka，服务名称改为seata

进入bin目录，启动脚本

#### 配置数据库
执行sd-eurake项目下的initial.sql

#### 项目配置
- 修改每个项目下的yml数据库配置
- 修改每个项目下的`file.conf`文件，注意这里的`vgroupMapping.my_test_tx_group = "seata"`属性，其中my_test_tx_group是yml文件配置的，seata是eureka的服务名称

启动sd-eureka、sd-account、sd-order、sd-storage

浏览器打开`http://localhost:8761`,如果显示了四个服务，说明没有问题
#### 调用接口
调用`http://localhost:8083/create?userId=1&productId=1&count=10&money=100`， 执行成功后看到order表生成记录，account和storage数量减少，说明正确

注意order表主键的自增值

将OrderServiceImpl的那一行注释放开，再次调用接口，返回失败

查看order表主键的自增值，主键+1且order表没有新数据，分布式事务调用成功

#### ### 数据库存储
seata默认以文件存储，如果要以数据库存储，需要编辑bin目录下的file.conf，设置为db，同时执行sd-eurake项目下的`seata-eureka.sql`