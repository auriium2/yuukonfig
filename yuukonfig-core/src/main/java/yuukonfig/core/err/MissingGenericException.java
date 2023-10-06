package yuukonfig.core.err;

import yuukonstants.GenericPath;

public class MissingGenericException extends BadConfigException {

    public MissingGenericException(String conf, GenericPath path, int quantity) {
        super(
                "missingGeneric",
                String.format("collection in config %s at {%s} expected %s type parameters but less than that were defined!", conf, path.getAsTablePath(), quantity),
                String.format("add %s more generics to the type parameters (<these>) of {%s}", quantity , path.getAsTablePath())
        );
    }

}
