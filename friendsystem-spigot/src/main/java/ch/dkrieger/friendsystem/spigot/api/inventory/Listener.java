package ch.dkrieger.friendsystem.spigot.api.inventory;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 18:39
 *
 */

public class Listener {

    private final String event, command;
    private final CommandRunner commandRunner;

    public Listener(String event, String command, CommandRunner commandRunner) {
        this.event = event;
        this.command = command;
        this.commandRunner = commandRunner;
    }

    public Listener(DefaultEvent event, String command, CommandRunner commandRunner) {
        this(event.getName(), command, commandRunner);
    }

    public String getEvent() {
        return event;
    }

    public String getCommand() {
        return command;
    }

    public CommandRunner getCommandRunner() {
        return commandRunner;
    }

    public enum CommandRunner {
        PLAYER,
        CONSOLE;
    }

    public enum DefaultEvent {
        CLICK("click"),
        INVENTORY_OPEN("inventoryOpen"),
        INVENTORY_CLOSE("inventoryClose");

        private final String name;

        DefaultEvent(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}