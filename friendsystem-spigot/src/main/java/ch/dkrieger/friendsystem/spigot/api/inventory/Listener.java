package ch.dkrieger.friendsystem.spigot.api.inventory;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 18:39
 *
 */

import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;
import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import ch.dkrieger.friendsystem.spigot.adapter.FriendAdapter;
import ch.dkrieger.friendsystem.spigot.adapter.inventory.OpenInventoryAdapter;
import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.ConfigInventory;
import com.google.gson.reflect.TypeToken;
import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    public void execute(Player player, Map<String, Object> properties) {
        if(getCommand() != null && getCommandRunner() != null) {
            if(getCommandRunner() == Listener.CommandRunner.CONSOLE) Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), getCommand());
            else player.chat("/" + getCommand());
        }
        if(getAdapter() != null) {
            Adapter adapter = SpigotFriendSystemBootstrap.getInstance().getAdapter(getAdapter());
            if(adapter instanceof FriendAdapter) ((FriendAdapter) adapter).execute(player, properties);
            else if(adapter instanceof OpenInventoryAdapter) ((OpenInventoryAdapter) adapter).execute(player, getAdapter());
        }
    }

    public static void execute(DefaultEvent event, Player player, ItemStack itemStack) {
        if(itemStack == null || itemStack.getType() == Material.AIR) return;
        NBTItem nbtItem = new NBTItem(itemStack);
        if(nbtItem.hasKey("listeners") && nbtItem.getString("listeners") != null) {
            List<Listener> listeners = GeneralUtil.GSON_NOT_PRETTY.fromJson(nbtItem.getString("listeners"), new TypeToken<List<Listener>>(){}.getType());
            for(Listener listener : listeners) {
                if (listener.getEvent().equalsIgnoreCase(event.getName())) {
                    Map<String, Object> properties = new LinkedHashMap<>();
                    properties.put("friendPlayer", UUID.fromString(nbtItem.getString("friendPlayer")));
                    properties.put("friend", nbtItem.getString("friend"));
                    listener.execute(player, properties);
                }
            }
        }
    }

    public static void execute(DefaultEvent event, Player player, ConfigInventory inventory) {
        inventory.getListeners().forEach(listener -> {
            if(listener.getEvent().equalsIgnoreCase(event.getName())) listener.execute(player, new LinkedHashMap<>());
        });
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