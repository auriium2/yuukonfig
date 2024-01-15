package yuukonfig.core;

import yuukonfig.core.err.BadConfigException;
import yuukonstants.GenericPath;

public class Exceptions {

    public static BadConfigException NO_VALID_DEFAULT(String type) {
        return new BadConfigException(
                "noValidDefault",
                "it is impossible for yuukonfig to determine a sane default for data of type %s yet this config method is without a default value",
                "add a default value by making the interface method default"
        );
    }

    public static BadConfigException STRANGE_CONFIG_CONFLICT(String type1, String type2, GenericPath path) {
        return new BadConfigException(
                "strangeConfigConflict",
                "in path [" + path.getAsTablePath() + "] the data in the dominant configuration mapping is of type " + type1 + " but the data in the recessive configuration is of type " + type2,
                "something has gone very wrong, either with your config schema or with yuukonfig config loading"
        );
    }

}
