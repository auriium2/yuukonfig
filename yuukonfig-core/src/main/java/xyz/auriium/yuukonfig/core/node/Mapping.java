package xyz.auriium.yuukonfig.core.node;
;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public interface Mapping extends Node {


    Mapping yamlMapping(String key);
    Sequence yamlSequence(String key);
    String string(String key);
    String foldedBlockScalar(String key);
    Collection<String> literalBlockScalar(String key);
    Node value(String key);
    int integer(String key);
    float floatNumber(String key);
    double doubleNumber(String key);
    long longNumber(String key);
    LocalDate date(String key);
    LocalDateTime dateTime(String key);


}
