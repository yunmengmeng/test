### jenkins+docker构建Spring Boot服务
#### 说明
之前的部署方式是Docker拉Tomcat的镜像，用maven构建war包，放到Tomcat执行，每次发布时重启Tomcat，这种方式已经过时，现改为用jar包的方式部署。
<br/>jar包部署有两种方式，一种是直接在服务器执行`java -jar`命令，这种方式需要安装jdk环境；另一种方式是拉jdk镜像，把jar包放到Docker中执行。本文采用第二种。
<br/>Docker构建有两种方式，一种是直接`docker build`写完所有启动参数，在构建后不能修改启动参数，如果要修改，必须删除容器再重新创建；另一种是写DockerFile，配置参数写到文件里，支持动态修改。本文采用第一种。
#### jenkins
新建任务，从代码管理器拉取代码，使用mvn命令编译，在目标服务器<a href="https://github.com/yunmengmeng/test/blob/master/jenkins_ssh.md">执行脚本文件</a>，过程略。
#### 使用docker构建服务
判断目标服务器是否有容器，没有的话要先创建容器，语句如下，之后用`docker restart`重启容器即可。
<br/>判断脚本略，下面的所有参数都可以当做变量进行配置，构建后无法修改。如需修改，需删除容器重新创建。
```
docker pull ${java_image}
docker run --name ${java_name}  --privileged --restart=always \
    -v /home/jenkins/test:/home/jenkins/test \
    -v /home/jenkins/test/logs:/logs \
    -p ${port}:${application_port} -p ${port}:${debug_port} \
    -e TZ=Asia/Shanghai \
    -d ${java_image} sh /home/jenkins/test/start.sh
```
说明：对于Java8，镜像名称为`java:8`
<br/>`/home/jenkins/test`是将jar包所在目录映射到docker容器中
<br/>`/home/jenkins/test/logs`是映射java的日志文件，以便在宿主机中查看
<br/>`${application_port}`是应用端口，`${debug_port}`是应用远程debug端口(与是否开启debug无关)
<br/><a href="https://github.com/yunmengmeng/test/blob/master/start.sh">start.sh</a>是java启动脚本，在此脚本修改一些参数（也可以通过jenkins传入变量），达到动态配置的效果。
