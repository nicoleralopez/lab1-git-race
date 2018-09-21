## Build instructions
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

## Run instructions
To run this project as a Spring Boot application type the following commands:
```
$ cd lab1-git-race
$ ./gradlew bootRun
```

## Tests instructions
To run the unit tests of the application type the following commands:
```
$ cd lab1-git-race
$ ./gradlew test
```

## War generating instructions
If you want to generate a WAR file to deploy it in a server just execute the following commands:
```
$ cd lab1-git-race
$ ./gradlew bootWar
```