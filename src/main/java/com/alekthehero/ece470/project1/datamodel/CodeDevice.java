package com.alekthehero.ece470.project1.datamodel;

import java.util.ArrayList;
import java.util.List;

public abstract class CodeDevice extends Device {
    protected List<Short> codes = new ArrayList<>();

    public CodeDevice(String name, short initCode) {
        super(name);
        this.codes.add(initCode);
    }

    public boolean validateCode(short code) {
        return this.codes.contains(code);
    }

    @Override
    public void turnOn() {
        // Just Default
        setOn(true);
    }

    @Override
    public void turnOff() {
        // Just Default
        setOn(false);
    }

    public abstract boolean codeTurnOn(short code);
    public abstract boolean codeTurnOff(short code);
}
