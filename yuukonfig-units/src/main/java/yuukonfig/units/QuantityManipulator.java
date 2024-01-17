package yuukonfig.units;

import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.spi.DimensionalModel;
import tech.units.indriya.unit.Units;
import yuukonfig.core.err.BadValueException;
import yuukonfig.core.err.MissingGenericException;
import yuukonfig.core.err.MissingTypeException;
import yuukonfig.core.impl.BaseManipulation;
import yuukonfig.core.impl.safe.ManipulatorSafe;
import yuukonfig.core.manipulation.Contextual;
import yuukonfig.core.node.Node;
import yuukonfig.core.node.RawNodeFactory;
import xyz.auriium.yuukonstants.GenericPath;

import javax.measure.Dimension;
import javax.measure.Quantity;
import javax.measure.Unit;
import javax.measure.spi.ServiceProvider;
import javax.measure.spi.SystemOfUnits;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Set;

@SuppressWarnings("rawtypes")
public class QuantityManipulator implements ManipulatorSafe<Quantity> {

    //TODO make this better
    static final SystemOfUnits SOU = ServiceProvider.current().getSystemOfUnitsService().getSystemOfUnits();

    final Class<?> useClass;
    final Contextual<Type> typeContextual; //<Volume>
    final BaseManipulation manipulation;
    final RawNodeFactory factory;

    public QuantityManipulator(BaseManipulation manipulation, Class<?> useClass, Contextual<Type> typeContextual,  RawNodeFactory factory) {
        this.typeContextual = typeContextual;
        this.manipulation = manipulation;
        this.factory = factory;
        this.useClass = useClass;
    }

    boolean isValidDimensions(Dimension dimension1, Dimension dimension2) {
        if (dimension1.equals(dimension2)) return true;
        DimensionalModel model = DimensionalModel.current();
        return model.getFundamentalDimension(dimension1).equals(model.getFundamentalDimension(dimension2));
    }

    @SuppressWarnings("unchecked") //Typecasting disaster, but who cares?
    @Override
    public Quantity<?> deserialize(Node node) throws BadValueException {

        String scalar = node.asScalar().value();
        Class<?> expectedParameter = getGenericType(node.path());
        Class<? extends Quantity> asQuantityParameter = (Class<? extends Quantity>) expectedParameter;
        Dimension desiredDimension = Units.getInstance().getUnit(asQuantityParameter).getDimension();

        Quantity<?> quantity = null;
        try {
            quantity = Quantities.getQuantity(scalar).asType(asQuantityParameter); //compare units
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("No Unit")) {
                throw new BadValueException(
                        "the value does not have a unit",
                        String.format("please add a unit to the value of type %s", asQuantityParameter.getSimpleName()),
                        manipulation.configName(),
                        node.path()
                );
            }
        }
        Dimension actualDimension = quantity.getUnit().getDimension();

        if (!isValidDimensions(desiredDimension, actualDimension)) {

            throw new BadValueException(
                    String.format("config expected a value of dimension %s but got one of dimension %s", desiredDimension.toString(), actualDimension.toString()),
                    String.format("please change the value [%s] in your config to one which matches dimension %s", scalar, desiredDimension.toString()),
                    manipulation.configName(),
                    node.path()
            );
        }

        //dimensions are valid!
        return quantity;
    }

    @Override
    public Node serializeObject(Quantity o, GenericPath path) {
        return factory.scalarOf( path, o.toSystemUnit().toString() );
    }

    @SuppressWarnings("unchecked")
    @Override
    public Node serializeDefault(GenericPath path) {

        Class<?> expectedParameter = getGenericType(new GenericPath(useClass.getName()));
        Class<? extends Quantity> asQuantityParameter = (Class<? extends Quantity>) expectedParameter;
        Unit desiredUnit = Units.getInstance().getUnit(asQuantityParameter);
        return factory.scalarOf(path, Quantities.getQuantity(0,desiredUnit).toString());
    }

    <T> T getFirstInSet(Set<T> set ) {
        for (T t : set) {
            return t;
        }

        return null;
    }

    Class<?> getGenericType(GenericPath key) { //generics disaster. Unless you understand how they work dont tuoch
        if (!this.typeContextual.present()) {
            throw new MissingTypeException(key);
        } else {
            try {
                return (Class<?>) ((ParameterizedType) typeContextual.get()).getActualTypeArguments()[0];
            } catch (ArrayIndexOutOfBoundsException var4) {
                throw new MissingGenericException(manipulation.configName(), key, 1);
            }
        }
    }

}
