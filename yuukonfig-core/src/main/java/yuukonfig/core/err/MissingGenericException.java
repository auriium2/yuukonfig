package yuukonfig.core.err;

import xyz.auriium.yuukonstants.GenericPath;

public class MissingGenericException extends YuuKonfigException {

    public MissingGenericException(String conf, GenericPath path, int quantity) {
        super(
                "missingGeneric",
                String.format("collection in config %s at {%s} expected %s type parameters but less than that were defined!", conf, path.tablePath(), quantity),
                String.format("add %s more generics to the type parameters (<these>) of {%s}", quantity , path.tablePath())
        );
    }

}
