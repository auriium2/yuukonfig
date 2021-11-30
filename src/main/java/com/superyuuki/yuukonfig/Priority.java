package com.superyuuki.yuukonfig;

public interface Priority {

    /**
     * Value used to say that a parser cannot handle a value provided
     */
    Integer DONT_HANDLE = 0;

    /**
     * Value used to say that a parser will parse a value, but only if there are no better alternatives provided
     */
    Integer HANDLE = 1;

    /**
     * Value used to say that a parser will parse a value
     */
    Integer PRIORITY_HANDLE = 2;

    /**
     * Value used to say that a parser will override other parsers and be considered the most important
     */
    Integer IMMEDIATE_HANDLE = 3;

}
