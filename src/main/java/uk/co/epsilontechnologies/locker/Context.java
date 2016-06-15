package uk.co.epsilontechnologies.locker;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private static Map<Class,Object> objects = new HashMap<>();

    public static <T> void put(final Class<T> type, T instance) {
        objects.put(type, instance);
    }

    public static <T> T get(final Class<T> type) {
        if (!objects.containsKey(type)) throw new IllegalArgumentException("No context object defined for type: "+type);
        return (T) objects.get(type);
    }

}
