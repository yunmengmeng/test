项目说明：

以电商项目为例，分为订单服务、用户服务、库存服务，每个服务用SpringBoot搭建，服务间通过nacos注册，使用feign进行服务间调用，hystrix作熔断降级，seata强一致性保证分布式事务

前期准备：

1. 代码地址：https://github.com/yunmengmeng/test/tree/seata-nacos-demo
1. 新建数据库，导入sd-order项目下的initial.sql文件
1. 后期将会补充gateway作网关

### 启动nacos

#### 下载2.0.3版本的nacos
地址：https://github.com/alibaba/nacos/releases/download/2.0.3/nacos-server-2.0.3.zip

#### 配置数据库
解压后，进入conf目录，将nacos-mysql.sql导入数据库，修改application.properties的数据库配置


![image.png](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/54eea2e39b0d43bdaaa153cbd3483dfa~tplv-k3u1fbpfcp-watermark.image)



#### 启动
进入bin目录，执行`.\startup.cmd -m standalone`，启动成功后，输入地址`http://localhost:8848/nacos`，账号密码为nacos

### 服务注册
#### 引入jar包
```
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-discovery</artifactId>
    <version>2.0.3.RELEASE</version>
</dependency>
```

#### yml配置
```
spring:
  application:
    name: sd-account
  cloud:
    nacos:
      discovery:
        server-addr: 127.0.0.1:8848
        # 默认为true，如果属性为false，则不注册到nacos
        registerEnabled: true
```

#### 启动
启动项目后，可以在nacos中心看到服务已注册

![image.png](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0403e5ad73d34d67894361af953a4a5f~tplv-k3u1fbpfcp-watermark.image)

<b>其余项目依次按上述步骤注册到nacos中心</b>

### 服务配置

#### 引入jar包

```
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-nacos-config</artifactId>
    <version>2.0.3.RELEASE</version>
</dependency>
```

#### yml配置
新建`bootstrap.yml`，并写入以下配置
```
spring:
  cloud:
    nacos:
      config:
        server-addr: 127.0.0.1:8848
        # 与配置中心配置格式相同
        file-extension: yaml
        # 与配置中心data-id相同
        name: sd-account
```

#### nacos配置

在nacos配置列表新增配置

![image.png](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/25013f8d0ce0429698eec670fb343a64~tplv-k3u1fbpfcp-watermark.image)

#### 项目配置
新建`Person`类，并写入以下代码

![image.png](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/bedc649d2382470c983cf153e3527562~tplv-k3u1fbpfcp-watermark.image)

项目启动后，可以看到控制台输出`Person(name=zhangsan, age=14)`

<b>至此，nacos整合完成</b>

## Feign与Hystrix
### Feign
#### 引入jar包
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

#### 启用Feign
在启动类或配置类加注解`@EnableFeignClients`

#### 服务间调用
以订单服务类调用用户服务类为例，新增`AccountClient`类
```
// 调用nacos的sd-account服务
@FeignClient(name = "sd-account")
public interface AccountClient {
    @GetMapping("/decrease")
    String decrease(@RequestParam Long userId, @RequestParam BigDecimal money);
}
```

### Hystrix
Hystrix用来熔断、降级，Feign的jar包含有Hystrix相关部分，不用再引jar包

#### 启用Hystrix

```
feign:
  hystrix:
    enabled: true
```

#### 项目配置
新建`AccountClientHystrix`类，并写入以下代码
```
@Component
public class AccountClientHystrix implements AccountClient {
    @Override
    public String decrease(Long userId, BigDecimal money) {
        return "invoke failed";
    }
}
```

指定`AccountClientHystrix`为熔断类
```
@FeignClient(name = "sd-account", fallback = AccountClientHystrix.class)
```

当调用超时或sd-account服务未启动时，触发回退，最终调用`AccountClientHystrix->decrease()`方法。

## Seata
### 启动Seata
#### 下载1.4.0版本的seata-server
地址：https://github.com/seata/seata/releases/download/v1.4.0/seata-server-1.4.0.zip

解压后，进入conf目录，打开`registry.conf`文件，将type属性修改为nacos，nacos配置下应用名称改为seata


![image.png](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/0d1fe560a55e4022988b2fa8352e1d44~tplv-k3u1fbpfcp-watermark.image)

进入bin目录，启动脚本

#### 配置数据库
执行sd-order目录下的`seata-initial.sql`，本文仅使用一个库，如果是多个数据库，每个库下都要导入该文件

#### 引入jar包
```
<!-- TM事务发起者 -->
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-alibaba-seata</artifactId>
    <version>${spring-cloud-alibaba-seata.version}</version>
</dependency>
<!-- 连接TC，创建分布式数据源 -->
<dependency>
    <groupId>io.seata</groupId>
    <artifactId>seata-all</artifactId>
    <version>${seata.version}</version>
</dependency>
```

#### 项目配置
- 添加各个项目配置文件里的事务组的名称

![image.png](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/2efa6991df284aa0b4486a65c538d8d8~tplv-k3u1fbpfcp-watermark.image)

- 将seata的`registry.conf`复制到每个项目的resources目录下

- 修改每个项目下的`file.conf`文件，注意这里的`vgroupMapping.my_test_tx_group = "default"`属性，其中my_test_tx_group是yml文件配置的，default是seata.nacos的cluster名称

- 创建分布式数据源

![image.png](https://p1-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/72b5024c3fe346d887cf5619d3fa94c5~tplv-k3u1fbpfcp-watermark.image)

### 分布式事务

#### 申明分布式事务
`OrderServiceImpl`类方法添加`@GlobalTransactional`注解

![image.png](https://p9-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/c694c68d44f4475a9d4b72e798ba6785~tplv-k3u1fbpfcp-watermark.image)

#### 调用接口
调用`http://localhost:8083/create?userId=1&productId=1&count=10&money=100`， 执行成功后看到order表生成记录，account和storage数量减少，说明提交成功

注意order表主键的自增值


![image.png](https://p3-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/5f5c8303c2c54eefb8ed0aaa8fc43cd5~tplv-k3u1fbpfcp-watermark.image)

将OrderServiceImpl的那一行注释放开，再次调用接口，返回失败

查看order表主键的自增值，主键+1且order表没有新数据，分布式事务回滚成功

![image.png](https://p6-juejin.byteimg.com/tos-cn-i-k3u1fbpfcp/d0156d62a5b34ff8a5e77d2189e55705~tplv-k3u1fbpfcp-watermark.image)

#### 数据库存储
seata默认以文件存储，如果要以数据库存储，需要编辑bin目录下的file.conf，设置为db，同时执行sd-order项目下的`seata-db.sql`

