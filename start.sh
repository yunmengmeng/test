#!/bin/bash

whoami
# ----------------------------------------------------------------------------------------------------------------------------------------
# example:
# windows: java -jar -server -Xms2048m -Xmx2048m -XX:+UseG1GC -XX:+PrintGCDateStamps -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=C:\Users\admin\Desktop\fsdownload\logs -Dfile.encoding=utf-8 -Djavax.net.ssl.sessionCacheSize=20480 -Dspring.profiles.active=test C:\Users\admin\Desktop\fsdownload\demo.jar
# linux: nohup java -jar -server -Xms2048m -Xmx2048m -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:/home/jenkins/demo/logs/gc/gc-%t.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/home/jenkins/demo/logs -Dfile.encoding=utf-8 -Djavax.net.ssl.sessionCacheSize=20480 -Dspring.profiles.active=test /home/jenkins/demo/demo.jar  >/dev/null 2>&1 &
# docker: java -jar -server -Xms2048m -Xmx2048m -XX:+UseG1GC -XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:/home/jenkins/demo/logs/gc/gc-%t.log -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/home/jenkins/demo/logs -Dfile.encoding=utf-8 -Djavax.net.ssl.sessionCacheSize=20480 -Dspring.profiles.active=test /home/jenkins/demo/demo.jar
# linux会忽略\，所以\.后缀名就是.后缀名
# ----------------------------------------------------------------------------------------------------------------------------------------

# Spring-Boot 常规启动脚本，基于HotSpot Java8
# 使用方式：xx.sh [start|restart|status|dump]
# 将Spring-Boot Jar包和此脚本放在同一目录下，之后配置APP_NAME/PROFILE即可

cd `dirname $0`
# 应用名（boot jar包名）
APP_NAME=demo

# Spring-Boot环境名（profiles）
PROFILE=test

JAR_NAME=$APP_NAME\.jar
PID=$APP_NAME\.pid
APP_HOME=`pwd`
LOG_PATH=$APP_HOME/logs
GC_LOG_PATH=$LOG_PATH/gc
# 填写debug时，将开启远程调试
DEBUG_FLAG=

if [ ! -d $LOG_PATH ]; then
  mkdir $LOG_PATH
fi

if [ ! -d $GC_LOG_PATH ]; then
  mkdir $GC_LOG_PATH
fi

# DUMP父目录
DUMP_DIR=$LOG_PATH/dump
if [ ! -d $DUMP_DIR ]; then
  mkdir $DUMP_DIR
fi

# DUMP目录前缀
DUMP_DATE=`date +%Y%m%d%H%M%S`

# DUMP目录
DATE_DIR=$DUMP_DIR/$DUMP_DATE
if [ ! -d $DATE_DIR ]; then
  mkdir $DATE_DIR
fi


# GC日志参数
GC_LOG_OPTS="-XX:+PrintGCDetails -XX:+PrintGCDateStamps -Xloggc:$GC_LOG_PATH/gc-%t.log"

# OOM Dump内存参数
DUMP_OPTS="-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=$LOG_PATH"

# JVM DEBUG参数，用于调试，默认不开启

# ClassLoader和Method Compile日志，用于调试
COMPILE_LOADER_OPTS="-XX:+TraceClassLoading -XX:+TraceClassUnloading -XX:-PrintCompilation"

# 远程调试参数
REMOTE_DEBUG_OPTS="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5011"
JMX_OPTS=" -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=1109 -Dcom.sun.management.jmxremote.rmi.port=1109 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Djava.rmi.server.hostname=172.16.100.38"
# DEBUG参数
DEBUG_OPTS="$COMPILE_LOADER_OPTS $REMOTE_DEBUG_OPTS"

# 至于Garbage Collector，虽然Java8已经支持G1了，但是不一定必须用，CMS在默认场景下也是一个优秀的回收器
GC_OPTS="-XX:+UseG1GC"

OTHER_OPTS="-Dfile.encoding=utf-8 -Djavax.net.ssl.sessionCacheSize=20480"

# JVM 启动参数，如无特殊需求，推荐只配置堆+元空间
#JVM_OPTIONS="-server -Xms1024m -Xmx1024m -XX:MetaspaceSize=256m $GC_OPTS $GC_LOG_OPTS $DUMP_OPTS $OTHER_OPTS"
JVM_OPTIONS="-server -Xms2048m -Xmx2048m   $GC_OPTS $GC_LOG_OPTS $DUMP_OPTS $OTHER_OPTS"

start(){
  if [ "$DEBUG_FLAG" = "debug" ]; then
    JVM_OPTIONS="$JVM_OPTIONS $DEBUG_OPTS"
    echo "\033[33m Warning: currently running in debug mode! This mode enables remote debugging, printing, compiling, and other information \033[0m"
  elif [ "$DEBUG_FLAG" = "jmx" ]; then
	  JVM_OPTIONS="$JVM_OPTIONS $JMX_OPTS"
    echo " currently running is JMX mode"
  else
    echo "JVM_OPTIONS : "
    echo "$JVM_OPTIONS"
  fi
  # echo  " java -jar $JVM_OPTIONS -Dspring.profiles.active=$PROFILE $APP_HOME/$JAR_NAME"
  java -jar $JVM_OPTIONS $APP_HOME/$JAR_NAME

}

start


