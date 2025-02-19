package com.alekthehero.ece470.project1.datamodel.devices;

import com.alekthehero.ece470.project1.datamodel.CodeDevice;
import com.alekthehero.ece470.project1.datamodel.DeviceType;

public class Alarm extends CodeDevice {
    public Alarm(String name, short initCode) {
        super(name, initCode);
        setType(DeviceType.ALARM);
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
}
