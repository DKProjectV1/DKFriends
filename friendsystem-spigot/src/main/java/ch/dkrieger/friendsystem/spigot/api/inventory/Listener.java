package ch.dkrieger.friendsystem.spigot.api.inventory;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 18:39
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import ch.dkrieger.friendsystem.spigot.adapter.FriendAdapter;
import ch.dkrieger.friendsystem.spigot.adapter.friends.FriendRemoveAdapter;
import ch.dkrieger.friendsystem.spigot.adapter.inventory.OpenInventoryAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Listener {

    private final String event;
    private String command, adapter;
    private CommandRunner commandRunner;

    public Listener(String event, String command, CommandRunner commandRunner) {
        this.event = event;
        this.command = command;
        this.commandRunner = commandRunner;
    }

    public Listener(DefaultEvent event, String command, CommandRunner commandRunner) {
        this(event.getName(), command, commandRunner);
    }

    public Listener(String event, String adapter) {
        this.event = event;
        this.adapter = adapter;
    }

    public Listener(DefaultEvent event, String adapter) {
        this.event = event.getName();
        this.adapter = adapter;
    }

    public String getEvent() {
        return event;
    }

    public String getCommand() {
        return command;
    }

    public String getAdapter() {
        return adapter;
    }

    public CommandRunner getCommandRunner() {
        return commandRunner;
    }

    public void execute(Player player, Object... objects) {
        System.out.println("listener execute");
        if(getCommand() != null && getCommandRunner() != null) {
            if(getCommandRunner() == Listener.CommandRunner.CONSOLE) Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), getCommand());
            else player.chat("/" + getCommand());
        }
        System.out.println(getAdapter());
        if(getAdapter() != null) {
            Adapter adapter = SpigotFriendSystemBootstrap.getInstance().getAdapter(getAdapter());
            if(adapter instanceof FriendAdapter) ((FriendAdapter) adapter).execute(player, objects);
            else if(adapter instanceof OpenInventoryAdapter) ((OpenInventoryAdapter) adapter).execute(player, getAdapter());
        }
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