# YuuKonfig

Configuration for idiots

# Introduction

**Objectives**
- Typesafe, threadsafe, timesafe config
- Reloadable
- Auto update
- Really expandable
- Never write a config parser again

# Warning

Filled with reflection hacks as most annotation config libraries are. 

# Repos

```xml

<dependencies>
    <dependency>
        
    </dependency>
</dependencies>

```

# Use

```java

import com.superyuuki.yuukonfig.deserializer.section.Section;
import com.superyuuki.yuukonfig.deserializer.Subsection;
import com.superyuuki.yuukonfig.annotate.ConfKey;

public interface SomeMasterConfig {

    @ConfKey("customKeyNames")
    SomeObject someValue();

    default Integer otherValue() {
        return 50;
    }

    default CustomType customType() {
        return new CustomType("Jim");
    }

    interface SomeObject extends Section {
        default Integer val1() {
            return 0;
        }

        default String val2() {
            return "lol";
        }
    }

    class SomeTypeSerializer implements Deserializer<CustomType> {

        private final Server server;

        CustomType deserialize(YamlNode node, DeserializerRegistry registry) {
            String user = node.asScalar().value();

            return new CustomType(server, user);
        }

        class CustomType {
            private final Server server;
            private final String user;

            void logic() {
                server.getUser(user).kill();
            }
        }
    }

}

```

Gives you

```yaml
customKeyNames:
  val1: 1
  val2: Hello
otherValue: 50
customType: Jim
```

The power is in the deserializer: Instead of populating your config
with dumb data classes, you can insert live, ready to use **objects** which
actually do something with their stored data, backed by the yaml config.

Configs are checked for errors when it is loaded from configs, not when you access it.
Configs are immutable - they cannot set values. This makes them threadsafe.

# Vs. DazzleConf

YuuKonfig is clearly inspired by DazzleConf. If you need a config library for a production environment, you are better off using DazzleConf.

| Feature | YuuKonfig | DazzleConf |
| --- | --- | --- |
| Production Philosophy | Intent on allowing for extensive configuration modification, allowing externally defined configs | Intent on fast, easy to write and heavily checked configs |
| Feature Set | Barebones built in features since extensibility is a priority | Contains many built in features such as nested sections, static serializers, range annotations, etc |
| Backing Libraries | YuuKonfig is tied to YML and uses EO-Yaml | DazzleConf is platform agnostic and uses a variety of libraries |
| Extensibility | Priority | Neutral |
| Maintainability | Neutral | Priority |
| Security and Safety | Neutral | Priority |
| Runtime-Defined configs | Priority | X |
| Serializers as a first class citizen | Priority | X |
| Simplicity | Incredible | Overcomplicated |
| Battle Tested | X | Very much so |

