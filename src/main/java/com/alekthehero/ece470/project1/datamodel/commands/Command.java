package com.alekthehero.ece470.project1.datamodel.commands;

import com.alekthehero.ece470.project1.datamodel.RequestPacket;

public abstract class Command {
    public abstract void toggle(RequestPacket packet);
    public abstract void create(RequestPacket packet);
    public abstract void delete(RequestPacket packet);
}
