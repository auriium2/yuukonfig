package com.superyuuki.yuukonfig.request;

import java.lang.reflect.Type;
import java.util.Optional;

public interface Request {

    /**
     * The requested class to serialize or deserialize.
     * @return the class
     */
    Class<?> requestedClass();

    /**
     * Additional type information about the class required to serialize or deserialize
     * @return the type if present or empty if no additional type information was able to be provided
     */
    Optional<Type> requestedType();

    /**
     * Additional type information about the yaml's visual key representation to provide context to the user during errors.
     * Do not rely on this for accuracy
     * @return the key value or empty if no key was provided e.g. base node representing the whole configuration is not keyed.
     */
    Optional<String> keyDisplayName();

}
