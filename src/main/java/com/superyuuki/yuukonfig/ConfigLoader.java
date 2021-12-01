package com.superyuuki.yuukonfig;

public interface ConfigLoader<C> {

    /**
     * Load a configuration from a config file. If a value is missing and
     * defaults are defined in the configuration interface, load value from defaults
     * and save to config.
     *
     * @return a deserialized configuration
     */
    C load();

    /**
     * Loads a configuration from a configuration file. If a value is missing throw an exception
     * @return a deserialized configuration
     */
    C loadWithoutDefaults();

}
