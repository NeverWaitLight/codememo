# Flink Demo

Refer:

- https://zhuanlan.zhihu.com/p/80221477

# [Create Project](https://nightlies.apache.org/flink/flink-docs-release-1.15/docs/dev/configuration/overview/)

The support of Java 8 is now deprecated and will be removed in a future release. We recommend all users to migrate to
Java 11.

As of now, the latest version is 1.15.1 But flink 1.15 deprecated and removed the support of Java 8.
So, using flink latest 1.14 version that is 1.14.4

```bash
 mvn archetype:generate                \
  -DarchetypeGroupId=org.apache.flink   \
  -DarchetypeArtifactId=flink-quickstart-java \
  -DarchetypeVersion=1.14.4 \
  -DgroupId=xyz.demo \
  -DartifactId=flink-client \
  -Dversion=0.0.1-SNAPSHOT \
  -Dpackage=xyz.demo.flink \
  -DinteractiveMode=false
```

# Testing

## 1.Run Data Source

Mock a Server

### Linux/macOS

```shell
nc -lk 9000
```

### Windows

Download and install `ncat` https://nmap.org/ncat/

```shell
ncat -lk 9000
```

## 2.Run flink-demo

Run `flink-demo`, then simulate the client's terminal in the data word, observe the console of `flink-demo`