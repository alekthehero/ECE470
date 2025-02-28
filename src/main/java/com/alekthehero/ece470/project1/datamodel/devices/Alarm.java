package com.alekthehero.ece470.project1.datamodel.devices;

import com.alekthehero.ece470.project1.datamodel.CodeDevice;
import com.alekthehero.ece470.project1.datamodel.DeviceType;
import lombok.ToString;

@ToString
public class Alarm extends CodeDevice {
    public Alarm(String name, short initCode) {
        super(name, initCode);
        setType(DeviceType.ALARM);
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
}
