FROM quay.io/drsylent/cubix/block2/homework-base:java21

LABEL cubix.homework.owner="Vencli Balazs"

ENV CUBIX_HOMEWORK="Vencli Balazs"
ENV APP_DEFAULT_MESSAGE=

COPY ./target/*.jar  app.jar

CMD ["java", "-jar", "app.jar"]