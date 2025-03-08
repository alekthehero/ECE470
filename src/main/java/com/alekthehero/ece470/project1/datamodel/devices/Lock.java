package com.alekthehero.ece470.project1.datamodel.devices;

import com.alekthehero.ece470.project1.datamodel.CodeDevice;
import com.alekthehero.ece470.project1.datamodel.DeviceType;


public class Lock extends CodeDevice {
    public Lock(String name, short initCode) {
        super(name, initCode);
        setType(DeviceType.LOCK);
    }

    @Override
    public boolean codeTurnOn(short code) {
        if (validateCode(code)) {
            turnOn();
            return true;
        }
        return false;
    }

    @Override
    public boolean codeTurnOff(short code) {
        if (validateCode(code)) {
            turnOff();
            return true;
        }
        return false;
    }

    public void addCode(short code) {
        if (!codes.contains(code))
            codes.add(code);
    }

    public void removeCode(short code) {
        codes.remove(code);
    }
}
