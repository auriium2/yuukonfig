package com.superyuuki.yuukonfig.serializer.bad;

import com.superyuuki.yuukonfig.Section;

interface PackagePrivateConfig extends Section {

    default Integer someValue() {
        return 5;
    }

}
