package com.superyuuki.yuukonfig.impl.decompose;

import com.superyuuki.yuukonfig.decompose.DeserializerContext;
import com.superyuuki.yuukonfig.request.Request;
import com.superyuuki.yuukonstants.Failure;

public class ParsingFailure extends Failure {

    public ParsingFailure(String conf, String key, String message) {
        super(String.format("Error while parsing config: %s key: %s, %s", conf, key, message));
    }

    public ParsingFailure(String conf, String key, Throwable throwable) {
        super(String.format("While parsing config: %s key: %s exception was thrown", conf, key), throwable);
    }

    public ParsingFailure(Request rq, DeserializerContext ctx, String message) {
        this(ctx.configDisplayName(), rq.keyDisplayName().orElse("(key not provided - developer error)"), message);
    }

    public ParsingFailure(Request rq, DeserializerContext ctx, Throwable throwable) {
        this(ctx.configDisplayName(), rq.keyDisplayName().orElse("(key not provided - developer error)"), throwable);
    }

}
