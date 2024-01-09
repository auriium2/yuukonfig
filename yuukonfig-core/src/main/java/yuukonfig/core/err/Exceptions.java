package yuukonfig.core.err;

import yuukonstants.exception.ExplainedException;

public class Exceptions {

   /* public static final BadConfigException BAD_ARRAY_EXCEPTION = new BadConfigException(
            "badArrayException",
            "no"
    ) */


    public static final BadConfigException INCORRECT_NODE_TYPE_SERIALIZATION = new BadConfigException(
            "incorrectNodeSerialization",
            "A node was serialized completely incorrectly",
            "something went incredibly wrong, report this to superyuuki"
    );

}
