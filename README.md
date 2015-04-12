[![Maven Central](https://maven-badges.herokuapp.com/maven-central/eu.hinsch/spring-boot-actuator-endpoint-list/badge.svg)](https://maven-badges.herokuapp.com/maven-central/eu.hinsch/spring-boot-actuator-endpoint-list/)

# spring-boot-actuator-endpoint-list
Simple entry page for spring boot actuator that lists links to all management endpoints under the root url of the management endpoints.

## Howto use
Simply add dependency the dependency to your build and point browser to root of actuator url (including training slash).

### Maven
```
<dependency>
  <groupId>eu.hinsch</groupId>
  <artifactId>spring-boot-actuator-endpoint-list</artifactId>
  <version>0.1.6</version>
</dependency>
```

### Gradle
```
runtime("eu.hinsch:spring-boot-actuator-endpoint-list:0.1.6")
```
### Configuration

(Optional) Exclude endpoints from list

```
endpoints.list.excludes=shutdown,...
```
