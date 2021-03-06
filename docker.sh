#！/usr/bin/env /bash
cd $PROJ_PATH/
mvn clean package
/bin/cp -r target/demo.war /usr/jenkins/
containId=`docker ps |grep hx-docker|awk '{print $1}'`
# 删掉容器
if [ "${containId}" = "" ]
then
        echo "not exist"
else
        docker rm -f "${containId}"
fi
# 删掉镜像
imageId=`docker images|grep hx-docker|awk '{print $3}'`
if [ "${imageId}" = "" ]
then
        echo "not exist"
else
        docker rmi -f "${imageId}"
fi
cd /usr/jenkins/
docker build -t hx-docker .
docker run -d --name=hx-docker  --restart=always  -v /disk/logs/docker/apps-logs/asset-guest:/usr/local/tomcat/logs -p 8081:8080 hx-docker
