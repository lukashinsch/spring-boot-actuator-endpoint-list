[![Coverage Status](https://coveralls.io/repos/lukashinsch/spring-boot-actuator-endpoint-list/badge.svg?branch=master)](https://coveralls.io/r/lukashinsch/spring-boot-actuator-endpoint-list?branch=master)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/eu.hinsch/spring-boot-actuator-endpoint-list/badge.svg)](https://maven-badges.herokuapp.com/maven-central/eu.hinsch/spring-boot-actuator-endpoint-list/)
[![Build Status](https://travis-ci.org/lukashinsch/spring-boot-actuator-endpoint-list.svg?branch=master)](https://travis-ci.org/lukashinsch/spring-boot-actuator-endpoint-list)

# spring-boot-actuator-endpoint-list
Simple entry page for spring boot actuator that lists links to all management endpoints under the root url of the management endpoints.

## Howto use
Simply add dependency the dependency to your build and point browser to root of actuator url (including trailing slash).

### Maven
```
<dependency>
  <groupId>eu.hinsch</groupId>
  <artifactId>spring-boot-actuator-endpoint-list</artifactId>
  <version>0.1.8</version>
</dependency>
```

### Gradle
```
runtime("eu.hinsch:spring-boot-actuator-endpoint-list:0.1.8")
```
### Configuration

(Optional) Exclude endpoints from list

```
endpoints.list.excludes=shutdown,...
```

(Optional) Configure path

By default the endpoint listens on the root context of the management endpoint. 
This can cause conflicts with some security configurations (or may conflict with the main app), so to move to a subpath, set id property:

```
endpoints.list.id=my-path
```

