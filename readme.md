# YuuKonfig

Infinitely extensible configuration library

# Introduction

**Objectives**
- Typesafe, threadsafe, timesafe config
- Reloadable
- Auto update
- Really expandable
- Never write a config parser again
- Lightweight (Compiled Jar is 50 KB, great for shading)

# Repos

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

**Then pick either**
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

# Use

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

Gives you

```yaml
nestedConfig:
  someint: 10
number: 5
bool: true
```

Configs are immutable (cannot change values) and threadsafe.

Configs support any object or interface you can think of, even user defined ones.
