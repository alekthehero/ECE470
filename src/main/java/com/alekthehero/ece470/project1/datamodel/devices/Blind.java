package com.alekthehero.ece470.project1.datamodel.devices;

import com.alekthehero.ece470.project1.datamodel.Device;
import com.alekthehero.ece470.project1.datamodel.DeviceType;
import lombok.ToString;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

@ToString
public class Blind extends Device {
    private static final Logger logger = getLogger(Blind.class);

    public Blind(String name) {
        super(name);
        setType(DeviceType.BLIND);
    }

    @Override
    public void turnOn() {
        logger.info("Opening blind: {}", getName());
        setOn(true);
    }
    @Override
    public void turnOff() {
        logger.info("Closing blind: {}", getName());
        setOn(false);
    }
}
