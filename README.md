# This application is designed to deploy to tomcat 7

# Tomcat settings - $TOMCAT_HOME/conf/tomcat-users.xml
<role rolename="manager-gui"/>
<role rolename="manager-script"/>
<user username="admin" password="admin" roles="manager-gui,manager-script" />

# maven settings - $MAVEN_HOME/conf/settings.xml
<server>
    <id>TomcatServer</id>
    <username>admin</username>
    <password>admin</password>
</server>

