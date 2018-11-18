package ch.dkrieger.friendsystem.spigot.inventory;

import java.util.LinkedHashMap;
import java.util.Map;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 18.11.18 21:06
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