package com.superyuuki.yuukonfig;


import com.superyuuki.yuukonfig.user.Section;

import java.util.List;
import java.util.UUID;

/**
 * TODO make the most showy flashy fucking config possible - something like YuuKomponent's main config - here
 */
public interface ShowoffConfig extends Section {


    default int integer() {
        return 1;
    }

    default String string() {
        return "hi";
    }

    default double doub() {
        return 2.0;
    }

    default UUID uuid() {
        return UUID.randomUUID();
    }

    default char character() {
        return 'a';
    }

    enum Num {
        TEST,WASTE
    }

    default Num enumerator() {
        return Num.TEST;
    }

    interface SubConfig extends Section {

        default List<String> list() {
            return List.of("hi", "bye", "why");
        }

    }

}
