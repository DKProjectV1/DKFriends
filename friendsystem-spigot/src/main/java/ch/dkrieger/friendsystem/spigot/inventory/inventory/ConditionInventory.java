package ch.dkrieger.friendsystem.spigot.inventory.inventory;

import ch.dkrieger.friendsystem.spigot.inventory.itemstack.ItemStack;
import org.bukkit.Bukkit;

import java.util.Map;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 18.11.18 21:59
 *
 */

public class ConditionInventory extends Inventory {

    private Inventory mainInventory;
    private String condition;

    public ConditionInventory(Inventory mainInventory, String condition) {
        super(mainInventory.getName(), mainInventory.getSize());
        this.mainInventory = mainInventory;
        this.condition = condition;
    }

    @SuppressWarnings("Only for other size and name of inventory")
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

    @Override
    public ConditionInventory clone() {
        return (ConditionInventory) new ConditionInventory(this.mainInventory, this.condition, getName(), getSize()).setItems(getItems());
    }
}