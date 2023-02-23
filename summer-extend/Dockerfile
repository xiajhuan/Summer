FROM java:8
EXPOSE 8888

VOLUME /tmp
ADD summer-extend.jar /app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]
