package ch.dkrieger.friendsystem.spigot.inventory;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 18.11.18 19:42
 *
 */

import org.bukkit.Bukkit;

import java.util.Map;

public class ConditionInventory extends Inventory {

    private Inventory mainInventory;
    private String condition;

    public ConditionInventory(Inventory mainInventory, String condition) {
        super(mainInventory.getName(), mainInventory.getSize());
        this.mainInventory = mainInventory;
        this.condition = condition;
    }

    @SuppressWarnings("Only for other size and name")
    public ConditionInventory(Inventory mainInventory, String condition, String name, int size) {
        super(name, size);
        this.mainInventory = mainInventory;
        this.condition = condition;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public org.bukkit.inventory.Inventory toBukkitInventory() {
        org.bukkit.inventory.Inventory inventory = Bukkit.createInventory(null, getSize(), getName());
        for(Map.Entry<Integer, ItemStack> entry : mainInventory.getItems().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().toBukkitItemStack());
        }
        for(Map.Entry<Integer, ItemStack> entry : getItems().entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue().toBukkitItemStack());
        }
        return inventory;
    }
}