package yuukonfig.units;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.Units;
import yuukonfig.core.YuuKonfig;
import yuukonfig.core.annotate.Section;
import yuukonfig.core.impl.safe.HandlesSafeManipulator;

import javax.measure.Quantity;
import javax.measure.quantity.Mass;
import javax.measure.quantity.Speed;
import java.io.IOException;

public class TestQuantities {

    static String toTest = """
            simpleDefault = "2 kg"
            complexDefault = "2 m/s"
            """;

    static String toTestDifferently = """
            simpleDefault = "2000 g"
            complexDefault = "200 cm/s"
            """;

    interface QuantityConfig extends Section {

        Quantity<Mass> simpleDefault();
        Quantity<Speed> complexDefault();

    }

    @Test
    public void testQuantitiesFunction() throws IOException {
        QuantityConfig c = YuuKonfig.instance()
                .register(HandlesSafeManipulator.ofSpecific(Quantity.class, QuantityManipulator::new))
                .test()
                .deserializeTest(toTest, QuantityConfig.class);

        Assertions.assertEquals(2, c.simpleDefault().to(Units.KILOGRAM).getValue());
        Assertions.assertEquals(2, c.complexDefault().to(Units.METRE_PER_SECOND).getValue());

        QuantityConfig c2 = YuuKonfig.instance()
                .register(HandlesSafeManipulator.ofSpecific(Quantity.class, QuantityManipulator::new))
                .test()
                .deserializeTest(toTestDifferently, QuantityConfig.class);

        Assertions.assertEquals(2, c.simpleDefault().to(Units.KILOGRAM).getValue());
        Assertions.assertEquals(2, c.complexDefault().to(Units.METRE_PER_SECOND).getValue());


    }

}
