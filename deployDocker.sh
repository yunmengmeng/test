#！/usr/bin/env /bash
cd $PROJ_PATH/
mvn clean package
/bin/cp -r target/demo.war /usr/local/tomcat/webapps/
cd /usr/local/tomcat/webapps/
mv demo.war ROOT.war
rm -rf ROOT
unzip  ROOT.war -d ROOT
docker restart hx-docker