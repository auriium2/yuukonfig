package com.superyuuki.yuukonfig.error;

import com.superyuuki.yuukonstants.DeveloperFailure;

public class UnexpectedRequestFailure extends DeveloperFailure {

    public UnexpectedRequestFailure(Class<?> request, Class<?> supported) {
        super(String.format("A request for a parser for class: %s was made to a parser that only handles class: %s!", request, supported));
    }
}
