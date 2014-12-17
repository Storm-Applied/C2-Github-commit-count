Pre-requisites for running project:

*  Have an installation of Maven.  For more information, see http://maven.apache.org/
*  Have an installation of JDK version 6 or later.  For more information, see http://www.oracle.com/technetwork/java/javaee/overview/index.html

To run this topology in local mode:

*  Compile the project source, producing a jar file.  Do this by running the following on the command-line:

```
mvn clean package
```

*  Execute the main class in the jar file by running the following:

```
java -jar target/github-commit-count-1.0.0-jar-with-dependencies.jar
```

*  Sit back and watch the topology continually process messages based on the data in changelog.txt.