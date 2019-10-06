export GRAALVM_HOME=$HOME/opt/graalvm-ce/Contents/Home

mvn io.quarkus:quarkus-maven-plugin:0.23.2:create \
    -DprojectGroupId=ch.m1m.quarkus               \
    -DprojectArtifactId=quarkus-example-ping      \
    -DclassName="ch.m1.quarkus.example.Ping"      \
    -Dpath="/ping"



./mvnw compile quarkus:dev

./mvnw package -Pnative
https://quarkus.io/guides/building-native-image-guide.html

./mvnw package -Pnative -Dnative-image.container-runtime=docker
docker build -f src/main/docker/Dockerfile.native -t quarkus-quickstart/getting-started .

docker run -i --rm -p 8080:8080 quarkus-quickstart/getting-started


tracing:
========

<dependency>
  <groupId>io.quarkus</groupId>
  <artifactId>quarkus-smallrye-opentracing</artifactId>
</dependency>

docker:
docker run -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 -p 9411:9411 jaegertracing/all-in-one:latest

jaeger UI:
http://localhost:16686/search

config:
quarkus.jaeger.service-name=myservice
quarkus.jaeger.sampler-type=const
quarkus.jaeger.sampler-param=1
quarkus.jaeger.endpoint=http://localhost:14268/api/traces

docu:
https://dzone.com/articles/opentracing-ejb-instrumentation-on-wildfly-swarm
https://dzone.com/articles/using-opentracing-to-collect-application-metrics-i-1
https://blog.codecentric.de/2018/02/einfuehrung-in-opentracing/
https://blog.kumuluz.com/developers/community/2019/02/11/kumuluzee-opentracing-jaeger.html



plugins
=======

swagger
-------
./mvnw quarkus:add-extension -Dextensions="openapi"

application.properties:
quarkus.swagger-ui.always-include=true


metrics
-------

./mvnw quarkus:add-extension -Dextensions="metrics"

http://localhost:8080/metrics/application


MICROPROFILE
============
https://jaxenter.de/microprofile-1-3-openapi-opentracing-type-safe-rest-client-68061
