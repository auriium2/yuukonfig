package xyz.auriium.yuukonfig.core.node;

public interface Sequence extends Node, Iterable<Node>{

    Node atIndex(int i);

}
