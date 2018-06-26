# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/en/1.0.0/) and this project adheres to [Semantic Versioning](http://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [1.1.0] - 2018-04-19

### Changed

- changed handling of `java.time.*` (use of `DateTimeFormatter` instead `SimpleDateFormatter`)

## [1.0.3] - 2017-09-19

### Added

- added support for de-/serializing ZonedDateTime (issue #2)
- added support for de-/serializing OffsetDateTime (issue #2)
- added support for de-/serializing ZoneId

## [1.0.2] - 2017-06-13

### Added

- added support for deserializing null values into Optional.empty()
- added support for deserializing null values into OptionalDouble.empty()
- added support for deserializing null values into OptionalInt.empty()
- added support for deserializing null values into OptionalLong.empty()

## [1.0.1] - 2017-03-22

### Added

- added support for de-/serializing Optional
- added support for de-/serializing OptionalDouble
- added support for de-/serializing OptionalInt
- added support for de-/serializing OptionalLong
