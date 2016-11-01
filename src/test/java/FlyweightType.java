import java.math.BigInteger;

/**
 * Created by wangqing on 16/8/12.
 */
public enum FlyweightType {
    /**
     * java.lang.Enum
     */
    ENUM("abc") {
        @Override
        boolean isShared(final Object obj) {
            System.out.println(obj);
            return true;
        }
    },

    BIGINTEGER("eeee") {
        @Override
        boolean isShared(final Object obj) {
            return obj == BigInteger.ZERO || obj == BigInteger.ONE || obj == BigInteger.TEN;
        }
    };

    private final String str;

    FlyweightType(String str) {
        this.str = str;
    }

    /**
     * Will return the Flyweight enum instance for the flyweight Class, or null if type isn't flyweight
     *
     * @param aClazz the class we need the FlyweightType instance for
     * @return the FlyweightType, or null
     */
//    static  FlyweightType getFlyweightType(final Class<?> aClazz) {
//        if (aClazz.isEnum() || (aClazz.getSuperclass() != null && aClazz.getSuperclass().isEnum())) {
//            return ENUM;
//        } else {
//           return null;
//        }
//    }
    abstract boolean isShared(Object obj);


}
