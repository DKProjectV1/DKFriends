package ch.dkrieger.friendsystem.spigot.api.inventory.itemstack;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 17:00
 *
 */

public class ItemStackListener {

    private final String command;
    private final ConsoleSender consoleSender;

    public ItemStackListener(String command, ConsoleSender consoleSender) {
        this.command = command;
        this.consoleSender = consoleSender;
    }

    public String getCommand() {
        return command;
    }

    public ConsoleSender getConsoleSender() {
        return consoleSender;
    }

    public enum ConsoleSender {
        PLAYER,
        CONSOLE;
    }
}