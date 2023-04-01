FROM java:8
EXPOSE 6666

VOLUME /tmp
ADD summer-admin.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]