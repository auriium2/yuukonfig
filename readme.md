# YuuKonfig
> Infinitely extensible configuration library

# TLDR
- typesafe, threadsafe, timesafe config
- toml and yaml support
- extensible to any language, any object
- lightweight (Compiled Jar is 50 KB, great for shading)
- never write a config parser again

## What is this?
YuuKonfig is a library that allows you to annotate an interface that describes your application's configuration As a result, reading from your configuration is **type safe**

YuuKonfig uses bytecode generation to implement your configuration interface as a simple java object, so configuration reads are fast as well as friendly to your developers.

## How do I use this?
```java

public interface InternalConfig extends Section {

    @Key("number")
    default Integer defaultInt() {
        return 5;
    }

    @Key("bool")
    default Boolean defaultBool() {
        return true;
    }

    @Key("nestedConfig")
    NestedConfig notDefaultConfig();


    interface NestedConfig extends Section {

        @Key("someint")
        default Integer nestedInteger() {
            return 10;
        }

    }
}

```

Serializes/Deserializes from
```yaml
nestedConfig:
  someint: 10
number: 5
bool: true
```
And also supports
```toml
[nestedConfig]
someint = 10
number = 5
bool = true
```

## Can i use json?
YuuKonfig doesn't have json support, but the flexibility of the library structure should make it relatively easy to implement (adding TOML support was a 2 hour task). Please send me an email if you need json support!

## I want to use this
Repositories:
```xml

<dependencies>
    <dependency>
      <groupId>com.superyuuki</groupId>
      <artifactId>yuukonfig-core</artifactId>
      <version>3.1.9-SNAPSHOT</version>
    </dependency>
</dependencies>

<repositories>
    <repository>
        <id>yuuki-releases</id>
        <name>releases</name>
        <url>https://repo.superyuuki.com/releases</url>
    </repository>
</repositories>

```
Flavors:
```
<dependency>
  <groupId>com.superyuuki</groupId>
  <artifactId>yuukonfig-toml</artifactId>
  <version>3.1.9-SNAPSHOT</version>
</dependency>
```
or
```
<dependency>
  <groupId>com.superyuuki</groupId>
  <artifactId>yuukonfig-yaml</artifactId>
  <version>3.1.9-SNAPSHOT</version>
</dependency>
```
