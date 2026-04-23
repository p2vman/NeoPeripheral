package io.p2vman.neoperipheral.peripheral.socket;

public abstract class AbstractModule implements Module {
    protected boolean enabled = false;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
