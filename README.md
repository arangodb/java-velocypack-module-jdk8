
![ArangoDB-Logo](https://docs.arangodb.com/assets/arangodb_logo_2016_inverted.png)

# ArangoDB VelocyPack Java Module jdk8

Java 8 module for [Java VelocyPack](https://github.com/arangodb/java-velocypack).

Added support for:
* java.time.Instant
* java.time.LocalDate
* java.time.LocalDateTime

## Maven

To add the dependency to your project with maven, add the following code to your pom.xml:

```XML
<dependencies>
  <dependency>
    <groupId>com.arangodb</groupId>
    <artifactId>velocypack-module-jdk8</artifactId>
    <version>1.0.0</version>
  </dependency>
</dependencies>
```

If you want to test with a snapshot version (e.g. 1.0.0-SNAPSHOT), add the staging repository of oss.sonatype.org to your pom.xml:

```XML
<repositories>
  <repository>
    <id>arangodb-snapshots</id>
    <url>https://oss.sonatype.org/content/groups/staging</url>
  </repository>
</repositories>
```

## Compile

```
mvn clean install -DskipTests=true -Dgpg.skip=true -Dmaven.javadoc.skip=true -B
```

## Usage / registering module

``` Java
VPack vpack = new VPack.Builder().registerModule(new VPackJdk8Module()).build();
``` 