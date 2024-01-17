package yuukonfig.core.node;

import java.util.List;

public interface Sequence extends Node, Iterable<Node>{

    Node atIndexPossiblyEmpty(int i);
    Node atIndexGuaranteed(int i);

    List<Node> getList();

}
