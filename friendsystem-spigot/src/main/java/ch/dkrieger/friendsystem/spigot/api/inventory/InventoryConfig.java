package ch.dkrieger.friendsystem.spigot.api.inventory;

import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.Inventory;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 15:28
 *
 */

public class InventoryConfig {

    private Map<String, Inventory> inventories;

    public InventoryConfig() {
        this.inventories = new LinkedHashMap<>();
    }

    public Map<String, Inventory> getInventories() {
        return inventories;
    }

    public Inventory getInventory(String name) {
        return this.inventories.get(name);
    }

    public void addInventory(String name, Inventory inventory) {
        this.inventories.put(name, inventory);
    }
}