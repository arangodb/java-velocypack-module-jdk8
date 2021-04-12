![ArangoDB-Logo](https://www.arangodb.com/docs/assets/arangodb_logo_2016_inverted.png)

# [DEPRECATED]: `velocypack-module-jdk8` has been deprecated.

Please consider using [jackson-dataformat-velocypack](https://github.com/arangodb/jackson-dataformat-velocypack)
in combination with [jackson-modules-java8](https://github.com/FasterXML/jackson-modules-java8) instead. For usage in
the ArangoDB Java driver, refer to the
official [serialization documentation](https://www.arangodb.com/docs/stable/drivers/java-reference-serialization.html).

# ArangoDB VelocyPack Java Module jdk8

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.arangodb/velocypack-module-jdk8/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.arangodb/velocypack-module-jdk8)

Java 8 module for [Java VelocyPack](https://github.com/arangodb/java-velocypack).

Added support for:

- `java.time.Instant`
- `java.time.LocalDate`
- `java.time.LocalDateTime`
- `java.time.ZonedDateTime`
- `java.time.OffsetDateTime`
- `java.time.ZoneId`
- `java.util.Optional`
- `java.util.OptionalDouble`
- `java.util.OptionalInt`
- `java.util.OptionalLong`

## Maven

To add the dependency to your project with maven, add the following code to your pom.xml:

```XML
<dependencies>
  <dependency>
    <groupId>com.arangodb</groupId>
    <artifactId>velocypack-module-jdk8</artifactId>
    <version>1.0.3</version>
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

```Java
VPack vpack = new VPack.Builder().registerModule(new VPackJdk8Module()).build();
```

## Learn more

- [ArangoDB](https://www.arangodb.com/)
- [Changelog](ChangeLog.md)
