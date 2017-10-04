FROM java:8-jre
ARG version
ENV JAVA_APP_JAR usuario-service-$version.jar
ENV ENVIRONMENT development

ADD ./target/$JAVA_APP_JAR $JAVA_APP_JAR
EXPOSE 8081
EXPOSE 5005
ENTRYPOINT java -Xss256k -XX:+UseSHM -XX:+UseStringDeduplication -XX:+UseParallelGC -XX:MaxGCPauseMillis=1500 -XX:MinHeapFreeRatio=20 -XX:MaxHeapFreeRatio=50 -Djava.security.egd=file:/dev/./urandom -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -Dspring.profiles.active=$ENVIRONMENT -jar $JAVA_APP_JAR