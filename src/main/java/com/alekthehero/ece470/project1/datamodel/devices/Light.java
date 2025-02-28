package com.alekthehero.ece470.project1.datamodel.devices;

import com.alekthehero.ece470.project1.datamodel.Device;
import com.alekthehero.ece470.project1.datamodel.DeviceType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.slf4j.Logger;
import java.awt.*;

import static org.slf4j.LoggerFactory.getLogger;

@ToString
public class Light extends Device {
    Logger logger = getLogger(Light.class);

    @Getter
    @Setter
    private int brightness;
    @Getter
    @Setter
    private Color color;

    public Light(String name) {
        super(name);
        setType(DeviceType.LIGHT);
    }

    @Override
    public void turnOn() {
        logger.info("Turning on light: {}", getName());
        setOn(true);
    }
    @Override
    public void turnOff() {
        logger.info("Turning off light: {}", getName());
        setOn(false);
    }
}
