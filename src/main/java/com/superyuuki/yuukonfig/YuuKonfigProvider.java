package com.superyuuki.yuukonfig;

public interface YuuKonfigProvider {

    YuuKonfigAPI create();

    byte priority();

}
