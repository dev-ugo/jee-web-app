FROM eclipse-temurin:17-jdk AS build
WORKDIR /project

COPY Servers/apache-tomcat-9.0.123/ Servers/apache-tomcat-9.0.123/
COPY student-core/src/main/java/ student-core/src/main/java/
COPY student-web/src/main/java/ student-web/src/main/java/
COPY student-web/src/main/webapp/ student-web/src/main/webapp/
COPY servlet-demo/src/ servlet-demo/src/
COPY mysql-connector-java-5.1.7-bin.jar ./
COPY scripts/app.sh scripts/app.sh
RUN bash scripts/app.sh build

FROM eclipse-temurin:17-jre
ENV CATALINA_HOME=/opt/tomcat
ENV CATALINA_BASE=/opt/tomcat
WORKDIR /opt/tomcat

COPY --from=build /project/Servers/apache-tomcat-9.0.123/bin/ bin/
COPY --from=build /project/Servers/apache-tomcat-9.0.123/conf/ conf/
COPY --from=build /project/Servers/apache-tomcat-9.0.123/lib/ lib/
COPY --from=build /project/.runtime/tomcat/webapps/ webapps/
RUN mkdir -p logs temp work

EXPOSE 8080
CMD ["bash", "bin/catalina.sh", "run"]
