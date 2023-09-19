package xyz.auriium.yuukonfig.core.manipulation;

import xyz.auriium.yuukonfig.core.err.BadValueException;
import xyz.auriium.yuukonfig.core.node.Node;

public interface Manipulator {

    int handles();

    Object deserialize(Node node, String exceptionalKey) throws BadValueException;

    Node serializeObject(Object object, String[] comment);
    Node serializeDefault(String[] comment);

}
