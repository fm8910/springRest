Minimal [Spring Boot](http://projects.spring.io/spring-boot/) springTest app.

## Requirements

For building and running the application you need:

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.ni.fmgarcia.NicaTestApplication` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
# Check Out the Swagger UI

Open a browser and key in URL:

```
$ open http://localhost:8080/nisum/swagger-ui/index.html
```


> Note:
> - All the APIs can be playing through the UI