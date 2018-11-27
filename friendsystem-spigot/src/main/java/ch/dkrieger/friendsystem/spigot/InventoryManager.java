package ch.dkrieger.friendsystem.spigot;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 19:59
 *
 */

import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.ConfigInventory;
import ch.dkrieger.friendsystem.spigot.inventories.Profile;
import org.bukkit.entity.Player;

import java.util.*;

public class InventoryManager {

    private Map<Player, Profile> playerProfiles;
    private Map<String, ConfigInventory> configInventories;

    public InventoryManager(Map<String, ConfigInventory> inventoryMap) {
        this.playerProfiles = new LinkedHashMap<>();
        this.configInventories = inventoryMap;
    }

    public Profile getProfile(Player player) {
        return this.playerProfiles.get(player);
    }

    public Map<Player, Profile> getProfiles() {
        return playerProfiles;
    }

    public ConfigInventory getInventory(String name) {
        ConfigInventory configInventory = configInventories.get(name);
        if(configInventory == null) {
            for(ConfigInventory inventory : configInventories.values()) {
                if(inventory.getTitle().equalsIgnoreCase(name)) configInventory = inventory;
            }
        }
        return configInventory;
    }

    public Profile createProfile(Player player) {
        return this.playerProfiles.put(player, new Profile(player));
    }

    public void removeProfile(Player player) {
        this.playerProfiles.remove(player);
    }

    public void updateFriendPages() {
        SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getProfiles().forEach((player, profile) -> {
            profile.getFriendPage().setPage(profile.getFriendPage().getCurrentPage());
        });
    }
}