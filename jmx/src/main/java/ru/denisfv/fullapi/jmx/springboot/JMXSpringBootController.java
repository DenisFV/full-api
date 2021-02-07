package ru.denisfv.fullapi.jmx.springboot;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@Component
@ManagedResource(objectName = "JMXSetting:name=JMXController")
public class JMXSpringBootController {

    private boolean enabled;

    @ManagedAttribute
    public boolean isEnabled() {
        return enabled;
    }

    @ManagedAttribute
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
