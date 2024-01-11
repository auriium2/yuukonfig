package yuukonfig.core;

import yuukonfig.core.err.BadConfigException;

public class Exceptions {

    public static BadConfigException NO_VALID_DEFAULT(String type) {
        return new BadConfigException(
                "noValidDefault",
                "it is impossible for yuukonfig to determine a sane default for data of type %s yet this config method is without a default value",
                "add a default value by making the interface method default"
        );
    }

}
