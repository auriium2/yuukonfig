package yuukonfig.core.err;


import xyz.auriium.yuukonstants.GenericPath;

public class MissingTypeException extends YuuKonfigException {

    public MissingTypeException(GenericPath path) {
        super(
                "missingType",
                String.format("Your YuuKonfig provider does not generate a parameter type correctly for {%s}", path.tablePath()),
                "Please report this to auriium!"
        );
    }

}
