# GIT RACE

## Getting Started

### How to build de code
Do the following steps to just build the project.
```
$ cd lab1-git-race
$ ./gradlew assemble
```

However if you want to execute tests too, then run:
```
$ cd lab1-git-race
$ ./gradlew build
```

Both will produce a build folder.

### How to test the code
To run the unit tests of the application type the following commands:
```
$ cd lab1-git-race
$ ./gradlew test
```

In order to debug the tests, you can execute the following command to see the System.out.println("...")
```
$ cd lab1-git-race
$ ./gradlew check -i
```

###  How to run the code
To run this project as a Spring Boot application type the following commands:
```
$ cd lab1-git-race
$ ./gradlew bootRun
```

If there are any changes since last time you compile, the project will be recompiled too.


You can test the web application is running writing in your default web browser:
```
http://localhost:8080
```

### How to generate a WAR
If you want to generate a WAR file to deploy it in a server just execute the following commands:
```
$ cd lab1-git-race
$ ./gradlew bootWar
```

Once the .war is generated, it can be deployed using the commands:
```
$ cd lab1-git-race
$ java -jar ./build/libs/lab1-git-race.war
```

## Which are the technologies used in the code

This is a Web Application built with Spring Boot.
Tests are made with JUnit.
Tests to check if the API is working property uses Mock functions. This kind of functions are used only to check if the method calls this functions. It can check the kind of the parameters, etc.

## New functionalities
The API can resolve the gcd with a POST request, i.e:

With postman:
![Example for gcd](images/gcdExample.PNG?raw=true "gcd example")

## References
[How to run a Spring Boot .war](https://spring.io/guides/gs/spring-boot/)

