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

## War generating instructions
If you want to generate a WAR file to deploy it in a server just execute the following commands:
```
$ cd lab1-git-race
$ ./gradlew bootWar
```

### How to test the code
To run this project as a Spring Boot application type the following commands:
```
$ cd lab1-git-race
$ ./gradlew bootRun
```

Once the .war is generated, it can be deployed using the command:

```
$ cd lab1-git-race
$ java -jar ./build/libs/lab1-git-race.war
```

In order to debug the tests, you can execute the following command to see the System.out.println("...")

```
$ cd lab1-git-race
$ gradle check -i
```

### How to build and run the code
To run the unit tests of the application type the following commands:
```
$ cd lab1-git-race
$ ./gradlew test
```

In order to make this easy, let's build and run the application with only one line:

```
$ cd lab1-git-race
$ ./gradlew build && java -jar ./build/libs/lab1-git-race.war
```

In the windows way:

```
$ cd lab1-git-race
$ .\gradlew build && java -jar .\build\libs\lab1-git-race.war
```

You can test the web application is running writing in your default web explorer:
http://localhost:8080

### Which are the technologies used in the code

This is a Web Application built with Spring Boot.
Tests are made with JUnit.
Tests to check if the API is working propery uses Mock functions. This kind of functions are used only to check if the method calls this functions. It can check the kind of the parameters, etc.

### New functionalities
The API can resolve the gcd with a POST request, i.e:

With postman:
![Example for gcd](/images/gcdExample.PNG?raw=true "gcd example")

### References
[How to run a Spring Boot .war](https://spring.io/guides/gs/spring-boot/)

