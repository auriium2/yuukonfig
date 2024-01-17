package yuukonfig.core.node;
;

import yuukonfig.core.err.BadValueException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;

public interface Mapping extends Node {


    Mapping yamlMapping(String key) throws BadValueException;
    Sequence yamlSequence(String key) throws BadValueException;
    String string(String key) throws BadValueException;
    String foldedBlockScalar(String key) throws BadValueException;

    /**
     * This can return empty nodes
     * @param key
     * @return
     * @throws BadValueException
     */
    Node valuePossiblyMissing(String key) throws BadValueException;

    /**
     * This will throw exceptions if empty
     * @param key
     * @return
     * @throws BadValueException
     */
    Node valueGuaranteed(String key) throws BadValueException;


    int integer(String key) throws BadValueException;
    float floatNumber(String key) throws BadValueException;
    double doubleNumber(String key) throws BadValueException;
    long longNumber(String key) throws BadValueException;
    LocalDate date(String key) throws BadValueException;
    LocalDateTime dateTime(String key) throws BadValueException;

    Map<String, Node> getMap();




}
