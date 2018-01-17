package demos.libraries;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import java.lang.reflect.Field;

public class Singletons {

    enum Singleton {
        INSTANCE
    }

    public static void main(String... args) throws Exception {
        Singleton s = copyEnumConstant(Singleton.class, Singleton.INSTANCE);

        System.out.println("Original: " + Singleton.INSTANCE);
        System.out.println("Copy:     " + s);
        System.out.println("Same?     " + Singleton.INSTANCE.equals(s));
    }





















    /* HOW DOES IT WORK? */


    private static final Objenesis OBJENESIS = new ObjenesisStd();

    private static <T> void setPrivateField(Class<T> type, String fieldName, T receiver, Object newValue) throws Exception {
        Field field = type.getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(receiver, newValue);
    }

    private static <E extends Enum<?>> E copyEnumConstant(Class<E> type, E constant) throws Exception {
        E newInstance = OBJENESIS.newInstance(type);
        setPrivateField(Enum.class, "ordinal", newInstance, constant.ordinal());
        setPrivateField(Enum.class, "name", newInstance, constant.name());
        return newInstance;
    }
}
