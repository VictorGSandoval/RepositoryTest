
FROM adoptopenjdk/openjdk11:x86_64-alpine-jre-11.0.6_10

RUN apk add --no-cache tzdata
ENV TZ='America/Lima'
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

WORKDIR /app

COPY . /app
COPY ./lib/jr-acreditacion-susalud-1.0.0.jar /app/lib/jr-acreditacion-susalud-1.0.0.jar

RUN mvn install:install-file -Dfile=/app/lib/jr-acreditacion-susalud-1.0.0.jar -DgroupId=pe.gob.susalud -DartifactId=jr-acreditacion-susalud -Dversion=1.0.0 -Dpackaging=jar -DgeneratePom=true

CMD ["java","-jar","/app/target/fps-bus-ms-sited-1.0-SNAPSHOT.jar"]