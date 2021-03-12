# ZotGamer

A tile matching game engine that provides functionality for implementing tile matching based games, launching them, and allowing a player to choose which one they want to play.

## Building the Jar / Running in IDE
We used Gradle as our build tool, so the easiest way to build from source is to import the project into an IDE that has 
Gradle support. In IntelliJ for example, Gradle tasks are listed on the right side of the screen. Under ZotGamer/main/Tasks/build
run the Jar task to build a runnable jar. Alternatively, you can run the application directly through your IDE by running
the "run" task under ZotGamer/main/Tasks/application.

## Running the Jar
We used Java 11 to package our JARs, thus it is suggested that you also use this version. To run the application 
simply call the correct jar for your OS : 

```
java -jar ZotGamer-WIN.jar
```
or
```
java -jar ZotGamer-LINUX.jar
```
or
```
java -jar ZotGamer-OSX.jar
```