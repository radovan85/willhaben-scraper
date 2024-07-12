FROM tomcat:10.1-jdk17

WORKDIR /app

COPY target/willhaben-car-tracker.war /app/app.war

RUN mv app.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]