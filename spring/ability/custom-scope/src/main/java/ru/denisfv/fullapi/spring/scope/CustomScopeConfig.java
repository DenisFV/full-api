package ru.denisfv.fullapi.spring.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

import java.time.Duration;
import java.time.LocalTime;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

public class CustomScopeConfig implements Scope {
    Map<String, Map.Entry<LocalTime, Object>> map = new HashMap<>();

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        if (map.containsKey(name)) {
            Map.Entry<LocalTime, Object> pair = map.get(name);
            long sec = Duration.between(pair.getKey(), LocalTime.now()).getSeconds();
            if (sec > 2L) {
                map.put(name, new AbstractMap.SimpleImmutableEntry<>(LocalTime.now(), objectFactory.getObject()));
            }
        } else {
            map.put(name, new AbstractMap.SimpleImmutableEntry<>(LocalTime.now(), objectFactory.getObject()));
        }
        return map.get(name).getValue();
    }

    public Object remove(String s) {
        return null;
    }

    public void registerDestructionCallback(String s, Runnable runnable) {

    }

    public Object resolveContextualObject(String s) {
        return null;
    }

    public String getConversationId() {
        return null;
    }
}
