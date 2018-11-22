package ch.dkrieger.friendsystem.spigot;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 19:59
 *
 */

import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.MainInventory;
import ch.dkrieger.friendsystem.spigot.inventories.Profile;
import com.google.gson.reflect.TypeToken;
import org.bukkit.entity.Player;

import java.util.*;

public class InventoryManager {

    private Map<Player, Profile> playerProfiles;
    private Map<String, MainInventory> inventories;

    public InventoryManager() {
        this.playerProfiles = new LinkedHashMap<>();
        this.inventories = new LinkedHashMap<>();
        Map<String, MainInventory> inventoryMap = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getObject("inventories", new TypeToken<Map<String, MainInventory>>(){}.getType());
        inventories.putAll(inventoryMap);
    }

    public Profile getProfile(Player player) {
        return this.playerProfiles.get(player);
    }

    public MainInventory getInventory(String name) {
        return inventories.get(name);
    }

    public Profile createProfile(Player player) {
        return this.playerProfiles.put(player, new Profile(player));
    }

    public void removeProfile(Player player) {
        this.playerProfiles.remove(player);
    }
}