package yuukonfig.core.node;

import java.util.List;

public interface Sequence extends Node, Iterable<Node>{

    Node atIndex(int i);

    List<Node> getList();

}
