package yuukonfig.core.err;


import yuukonstants.GenericPath;

public class MissingTypeException extends BadConfigException {

    public MissingTypeException(String conf, GenericPath path) {
        super(
                "missingType",
                String.format("Your YuuKonfig provider does not generate a parameter type correctly for {%s}", path.getAsTablePath()),
                "Please report this to auriium!"
        );
    }

}
