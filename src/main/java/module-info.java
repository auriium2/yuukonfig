import com.superyuuki.yuukonfig.BaseProvider;

module com.superyuuki.yuukonfig {
    requires com.amihaiemil.eoyaml;

    exports com.superyuuki.yuukonfig;
    exports com.superyuuki.yuukonfig.manipulation;
    exports com.superyuuki.yuukonfig.user;
    exports com.superyuuki.yuukonfig.loader;
    exports com.superyuuki.yuukonfig.inbuilt;
    exports com.superyuuki.yuukonfig.inbuilt.section;

    provides com.superyuuki.yuukonfig.YuuKonfigProvider with BaseProvider;

    uses com.superyuuki.yuukonfig.YuuKonfigProvider;
}