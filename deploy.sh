#!/usr/bin/env bash

#杀死tomcat进程
killTomcat()
{
    #这句话的意思是找到tomcat的进程号   awk '{print $2}'的意思是以空格作为分隔符，打印第二个字段
    pid=`ps -ef|grep tomcat|grep java|awk '{print $2}'`
    echo "tomcat Id list:${pid}"
    if [ "${pid}" = "" ]
    then
        echo "no tomcat pid alive"
    else
        kill -9 "${pid}"
    fi
}
cd $PROJ_PATH/
mvn clean install
#杀死Tomcat
killTomcat

#删除原有工程
rm -rf $TOMCAT_APP_PATH/webapps/ROOT
rm -f $TOMCAT_APP_PATH/webapps/ROOT.war
rm -f $TOMCAT_APP_PATH/webapps/demo.war

#复制新的工程
cp $PROJ_PATH/target/demo.war $TOMCAT_APP_PATH/webapps/

cd $TOMCAT_APP_PATH/webapps/
mv demo.war ROOT.war

#启动tomcat
cd $TOMCAT_APP_PATH/
sh bin/startup.sh