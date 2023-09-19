package xyz.auriium.yuukonfig.toml.serializer.bad;


import xyz.auriium.yuukonfig.core.annotate.Section;

interface PackagePrivateConfig extends Section {

    default Integer someValue() {
        return 5;
    }

}
