package com.alekthehero.ece470.project1.datamodel.devices;

import com.alekthehero.ece470.project1.datamodel.CodeDevice;
import com.alekthehero.ece470.project1.datamodel.DeviceType;

public class Lock extends CodeDevice {
    public Lock(String name, short initCode) {
        super(name, initCode);
        setType(DeviceType.LOCK);
    }

    @Override
    public void codeTurnOn(short code) {
        if (validateCode(code)) {
            turnOn();
        }
    }

    @Override
    public void codeTurnOff(short code) {
        if (validateCode(code)) {
            turnOff();
        }
    }

    public void addCode(short code) {
        if (!codes.contains(code))
            codes.add(code);
    }

    public void removeCode(short code) {
        codes.remove(code);
    }
}
