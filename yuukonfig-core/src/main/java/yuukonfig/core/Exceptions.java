package yuukonfig.core;

import xyz.auriium.yuukonstants.GenericPath;
import yuukonfig.core.err.YuuKonfigException;

import static java.lang.String.format;

public class Exceptions {

    public static YuuKonfigException NO_VALID_DEFAULT(GenericPath path, String type) {
        return new YuuKonfigException(
                "noValidDefault",
                format("it is impossible for yuukonfig to determine a sane default for [%s] of type [%s] yet this config method is without a default value", path.tablePath(), type),
                "add a default value by making the interface method default"
        );
    }

    public static YuuKonfigException STRANGE_CONFIG_CONFLICT(String type1, String type2, GenericPath path) {
        return new YuuKonfigException(
                "strangeConfigConflict",
                "in path [" + path.tablePath() + "] the data in the dominant configuration mapping is of type " + type1 + " but the data in the recessive configuration is of type " + type2,
                "something has gone very wrong, either with your config schema or with yuukonfig config loading"
        );
    }

}
