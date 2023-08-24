package xyz.auriium.yuukonfig.core;

public interface YuuKonfigProvider {

    YuuKonfigAPI create();

    byte priority();

}
