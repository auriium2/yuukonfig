package yuukonfig.toml.serializer.bad;


import yuukonfig.core.annotate.Section;

interface PackagePrivateConfig extends Section {

    default Integer someValue() {
        return 5;
    }

}
