# CyclopsGroup organization

## Overview

This is a user-level repository of resources shared between all my repositories.
Learn more at [cyclopsgroup.org](https://www.cyclopsgroup.org).

In this repository, there are currently these contents:

* Parent Maven POM files for open source Java projects.
* Shared Github actions for CI/CD pipelines of other projects.
* Website of cyclopsgroup.org.

## For Java developers

A few things to be aware of when developing open source projects here.

### Code style

As of now, the 0.7.2 version of Java parent POM requires all Java code to
match https://github.com/google/google-java-format. To format all Java code, run

```
mvn fmt:format
```

before submitting a change.

Most repositories have setup CI/CD to verify the style. Therefore if code isn't in the expected shape, the PR will fail to build.


### Release process

* Checkout project, modify the version in POM. Make sure no SNAPSHOT version
exists in the POM file.
* Run maven command


```
mvn clean
mvn -P cg package source:jar javadoc:jar gpg:sign
cd target
jar cvf bundle.jar mypackage*
```

* Upload bundle.jar to oss.sonatype.org, release it.
* If anything went wrong, fix and repeat the process above.
* Tag the repository.

```
git tag --list
git tag mypackage_v_1_0_1
git push --tags
```

Depend on the platform, the GPG command does not always work smoothly. If it
doesn't work the output file can be signed manually.

```
gpg -ab mypackage-1.0.1.jar
gpg -ab mypackage-sources-1.0.1.jar
...
jar cvf bundle.jar mypackage*
```
