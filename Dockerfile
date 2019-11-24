FROM tomcat
MAINTAINER yunmengmeng<1125131350@qq.com>
ENV DIR_WEBAPP /usr/local/tomcat/webapps
RUN rm -rf $DIR_WEBAPP/*
ADD demo.war $DIR_WEBAPP/ROOT.war
RUN unzip $DIR_WEBAPP/ROOT.war  -d  $DIR_WEBAPP/ROOT/
RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone
RUN chmod +x /usr/local/tomcat/bin/catalina.sh

