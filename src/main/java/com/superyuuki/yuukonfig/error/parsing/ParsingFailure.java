package com.superyuuki.yuukonfig.error.parsing;

import com.superyuuki.yuukonfig.serialize.decompose.DeserializerContext;
import com.superyuuki.yuukonfig.serialize.RequestContext;
import com.superyuuki.yuukonstants.Failure;

public class ParsingFailure extends Failure {

    public ParsingFailure(String conf, String key, String message) {
        super(String.format("Error while parsing config: %s key: %s, %s", conf, key, message));
    }

    public ParsingFailure(String conf, String key, Throwable throwable) {
        super(String.format("While parsing config: %s key: %s exception was thrown", conf, key), throwable);
    }

    public ParsingFailure(RequestContext<?> rq, DeserializerContext ctx, String message) {
        this(ctx.configDisplayName(), rq.keyDisplayName(), message);
    }

    public ParsingFailure(RequestContext<?> rq, DeserializerContext ctx, Throwable throwable) {
        this(ctx.configDisplayName(), rq.keyDisplayName(), throwable);
    }

}
