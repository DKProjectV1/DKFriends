package ch.dkrieger.friendsystem.spigot;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 19:59
 *
 */

import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.MainInventory;
import com.google.gson.reflect.TypeToken;

import java.util.*;

public class InventoryManager {

    private Map<String, MainInventory> inventories;

    public InventoryManager() {
        this.inventories = new LinkedHashMap<>();
        Map<String, MainInventory> inventoryMap = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getObject("inventories", new TypeToken<Map<String, MainInventory>>(){}.getType());
        inventories.putAll(inventoryMap);
    }

    public MainInventory getInventory(String name) {
        return inventories.get(name);
    }
}