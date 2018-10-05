# GIT RACE

## Pre-building instructions
To install Gradle on Mac just execute (if you have Homebrew installed) the following command:
```
$ brew install gradle
```
For those who don't have Homebrew installed, execute this command:
```
$ /usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"
```

## Getting Started

### How to build the code
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
Before you run the code, you must inicialize REDIS

### How to install DOCKER and run REDIS

First of all, you must download docker from de official page: https://www.docker.com/

When you are installing it, it will answer you if you want Linux or Windows containers, you must choose Linux ones.

Once it is installed, you must run Redis with the following command:
```
$ cd lab1-git-race
$ docker-compose -f redis.yml up
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

Now there is also a GUI to use gcd in a simple way, i.e:

Go to /gcdForm (in this example the complete URL is http://localhost:8080/gcdForm) and fill the fields:
![Form for gcd](/images/gcdForm.png?raw=true "gcd form")

and click the Submit button, the result will be shown:
![Form result for gcd](/images/gcdResult.png?raw=true "gcd form result")

Go to /dniForm (in this example the complete URL is http://localhost:8080/dniForm) and fill the fields:
![Form for dni](/images/dniForm.png?raw=true "dni form")

and click the Submit button, the result will be shown:
![Form result for dni](/images/dniResult.png?raw=true "dni form result")

### References
## References
[How to run a Spring Boot .war](https://spring.io/guides/gs/spring-boot/)
[Save movies](http://michaelcgood.com/intro-redis-with-spring-boot/)
