PrisonRankup
============

The highest-rated and most practical rankup plugin designed specifically for the ever-popular Prison server is finally here. This plugin features a unique database built into the config to house each individual player’s rank to prevent losing ranks, glitching out of the system, and allowing multiple groups to be compatible with popular permissions plugins, including PermissionsEX and GroupManager. Accessing player’s information has never been easier, as one can simply type “/rankup get (name)” to retrieve the player’s display name, rank, and current balance. In order for the balance feature to work, as well as the plugin itself, Vault is required.

Compiling
=========

- Download & Install [Maven 3](http://maven.apache.org/download.html)
- Clone the repository: `git clone https://github.com/mazentheamazin/PrisonRankup`
- Compile and create the plugin package using Maven: `mvn clean install`

Maven will download all required dependencies and build a ready-for-use plugin package!

Adding PrisonRankup as a Maven Dependency
=========================================

Adding PrisonRankup as a Maven dependency is easy, lets go over how to do it.

First, add the PrisonRankup repo.

``` xml

<repository>
    <id>prisonrankup-repo</id>
    <url>https://raw.github.com/mazentheamazin/PrisonRankup/mvn-repo/</url>
</repository>

```

Now, we just need to add the dependency:

``` xml

<dependency>
    <groupId>io.mazenmc</groupId>
    <artifactId>PrisonRankup</artifactId>
    <scope>compile</scope>
    <version>3.0</version>
</dependency>

```