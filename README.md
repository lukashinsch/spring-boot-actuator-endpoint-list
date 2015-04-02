# spring-boot-actuator-endpoint-list
Simple entry page for spring boot actuator that lists links to all management endpoints under the root url of the management endpoints.

## Howto use
Simply add dependency the dependency to your build

### Maven
```
<dependency>
  <groupId>eu.hinsch</groupId>
  <artifactId>spring-boot-actuator-endpoint-list</artifactId>
  <version>0.1.2</version>
</dependency>
```

### Gradle
```
runtime("eu.hinsch:spring-boot-actuator-endpoint-list:0.1.2")
```
### Configuration
In your application.properties (or yaml), configure actuator to use a separate context path, such as
```
management.context-path=/manage
```
Then start the application and go to http://localhost:8080/manage/ to get a list of all available MvcEndpoints.
