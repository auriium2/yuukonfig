package com.superyuuki.yuukonfig.serializer.bad;

import com.superyuuki.yuukonfig.user.Section;

interface PackagePrivateConfig extends Section {

    default Integer someValue() {
        return 5;
    }

}
