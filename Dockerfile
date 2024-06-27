FROM tomcat:10.1.25-jdk21-temurin-jammy

WORKDIR /usr/local/tomcat

RUN rmdir webapps

RUN mv webapps.dist webapps

COPY context.xml /usr/local/tomcat/webapps/manager/META-INF

COPY tomcat-users.xml /usr/local/tomcat/conf

COPY target/urlshortner.war /usr/local/tomcat/webapps