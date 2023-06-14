# Second homework

You have two applications in the __frontapp__ and __backapp__ folders.

You can test the applications with the Postman collection: Second Homework.postman_collection.json

## Frontapp application

You can run the application with these commands:

```
./mvnw clean package
java -jar target/cubix-second-homework-frontapp-0.0.1-SNAPSHOT.jar
```


## Backapp application

You can run the application with these commands:

```
./mvnw clean package
java -jar target/cubix-second-homework-backapp-0.0.1-SNAPSHOT.jar
```

## Running the two applications together

By default both applications will bind to the localhost's 8080 port - only one of them can do this simultaneously.

The backapp application can be started with a modified port binding like this:

```
java -jar target/cubix-second-homework-backapp-0.0.1-SNAPSHOT.jar --server.port=8081
```

The frontapp can reach the backapp only if it's URL is provided.
The backapp application's URL can be configured like this with the startup command (the URL here is http://localhost:8081):

```
java -jar target/cubix-second-homework-frontapp-0.0.1-SNAPSHOT.jar --back.url=http://localhost:8081
```
