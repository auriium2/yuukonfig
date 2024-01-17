package yuukonfig.core.err;

import yuukonfig.core.node.Node;
import xyz.auriium.yuukonstants.GenericPath;

import static java.lang.String.format;

public class Exceptions {

   /* public static final BadConfigException BAD_ARRAY_EXCEPTION = new BadConfigException(
            "badArrayException",
            "no"
    ) */

    public static YuuKonfigException GENERIC_WTF_EXCEPTION() {
        throw new YuuKonfigException(
                "genericWtfException",
                "you are doing something very wrong and it has caused yuukonfig to break inexplicably",
                "make an issue on the yuukonfig github"
        );
    }

    public static YuuKonfigException SEQUENCE_MISSING(GenericPath path, int indexExpected, int indexActual) {
        throw new YuuKonfigException(
                "sequenceMissing",
                format("the config sequence at [%s] was expected to be at least size [%s], but was actually size [%s]", path.tablePath(), indexExpected, indexActual),
                format("add more contents to [%s]", path.tablePath())
        );
    }

    public static YuuKonfigException UNEXPECTED_NODE_TYPE(GenericPath path, Node.Type expected, Node.Type actual) {
        throw new YuuKonfigException(
                "unexpectedNodeType",
                format("the config node at [%s] was expected to be of type [%s], but was actually of type [%s]", path.tablePath(), expected.name(), actual.name()),
                format("make node [%s] a [%s]", path.tablePath(), actual.name())
        );
    }

    public static YuuKonfigException UNEXPECTED_EMPTY_NODE(GenericPath path) {
        throw new YuuKonfigException(
                "unexpectedEmptyNode",
                format("the config node at [%s] was expected to exist, but it wasn't even present!", path.tablePath()),
                format("add some data for config node [%s]", path.tablePath())
        );
    }

    public static YuuKonfigException UNEXPECTED_EMPTY_SCALAR(GenericPath path) {
        throw new YuuKonfigException(
                "unexpectedEmptyScalar",
                format("the config node at [%s] was expected to be a scalar, but it wasn't even present!", path.tablePath()),
                format("add some data for config node [%s]", path.tablePath())
        );
    }

    public static YuuKonfigException UNEXPECTED_EMPTY_MAPPING(GenericPath path) {
        throw new YuuKonfigException(
                "unexpectedEmptyMapping",
                format("the config node at [%s] was expected to be a mapping, but it wasn't even present!", path.tablePath()),
                format("add a mapping (multiple values) for config node [%s]", path.tablePath())
        );
    }

    public static YuuKonfigException UNEXPECTED_EMPTY_SEQUENCE(GenericPath path) {
        throw new YuuKonfigException(
                "unexpectedEmptySequence",
                format("the config node at [%s] was expected to be a sequence, but it wasn't even present!", path.tablePath()),
                format("add some ordered, listlike data for config node [%s]", path.tablePath())
        );
    }

    public static final YuuKonfigException INCORRECT_NODE_TYPE_SERIALIZATION = new YuuKonfigException(
            "incorrectNodeSerialization",
            "A node was serialized completely incorrectly",
            "something went incredibly wrong, report this to superyuuki"
    );

}
