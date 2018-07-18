# faveeo-sample

This project is a boilerplate project using Spring Boot, Elasticsearch REST Client, MockHttp library, Assert4J, Mockito, Swagger, PojoBuilder.

The API is not secured yet but it's a good base.

It's working with the Elasticsearch version 6.3 and the standard dataset (bank endpoint).

## How to deploy

just execute the executable jar file and configure your application.properties.

## Integration tests

I have provided two integration tests.

One is mocking the DAO Implementation (to test the Rest Controller).

The second one is mocking the Elasticsearch REST API by providing traces stored inside `src/test/resources/traces`.
To commute between the Proxy and the mock mode, just change the mode for MODE.LOGGING from MODE.MOCKING.


## Access to the Swagger UI

http://localhost:8080/swagger-ui.html