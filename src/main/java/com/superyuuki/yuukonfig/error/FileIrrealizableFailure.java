package com.superyuuki.yuukonfig.error;

import com.superyuuki.yuukonstants.DeveloperFailure;

public class FileIrrealizableFailure extends DeveloperFailure {

    public FileIrrealizableFailure(String message, Throwable cause) {
        super(String.format("Error creating file: %s, we were unable to load an existing copy or write a new file.", message), cause);
    }
}
