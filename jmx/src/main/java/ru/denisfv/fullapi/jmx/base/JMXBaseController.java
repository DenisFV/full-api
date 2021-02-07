package ru.denisfv.fullapi.jmx.base;

public class JMXBaseController implements InterfaceMXBean {

    private boolean enabled;

    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
