package ch.dkrieger.friendsystem.spigot.api.inventory.inventory;

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.itemstack.ItemStack;
import org.bukkit.Bukkit;
import java.util.Map;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 18.11.18 21:59
 *
 */

public class ConditionInventory extends Inventory {

    private String condition, mainInventory;

    public ConditionInventory(MainInventory mainInventory, String condition) {
        super(mainInventory.getName(), mainInventory.getSize());
        this.condition = condition;
        this.mainInventory = mainInventory.getName();
    }

    public MainInventory getMainInventory() {
        return SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory(this.mainInventory);
    }

    public String getCondition() {
        return condition;
    }


    @Override
    public org.bukkit.inventory.ItemStack[] getContentAsArray() {
        org.bukkit.inventory.ItemStack[] itemStacks = new org.bukkit.inventory.ItemStack[getSize()];
        for(int i = 1; i < getSize(); i++) itemStacks[i-1] = getMainInventory().getItem(i).toBukkitItemStack();
        for(int i = 1; i < getSize(); i++) itemStacks[i-1] = getItem(i).toBukkitItemStack();
        return itemStacks;
    }

    @Override
    public org.bukkit.inventory.Inventory toBukkitInventory() {
        System.out.println(getMainInventory());
        System.out.println(getMainInventory().getName());
        System.out.println(getMainInventory().getSize());
        org.bukkit.inventory.Inventory inventory = Bukkit.createInventory(null, getMainInventory().getSize(), getMainInventory().getName());
        for(Map.Entry<Integer, ItemStack> entry : getMainInventory().getItems().entrySet()) {
            inventory.setItem(entry.getKey()-1, entry.getValue().toBukkitItemStack());
        }
        for(Map.Entry<Integer, ItemStack> entry : getItems().entrySet()) {
            inventory.setItem(entry.getKey()-1, entry.getValue().toBukkitItemStack());
        }
        return inventory;
    }

    @Override
    public ConditionInventory clone() {
        return (ConditionInventory) new ConditionInventory(getMainInventory(), getCondition()).setItems(getItems());
    }
}