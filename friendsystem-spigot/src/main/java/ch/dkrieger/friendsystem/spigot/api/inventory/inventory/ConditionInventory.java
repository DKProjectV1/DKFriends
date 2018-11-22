package ch.dkrieger.friendsystem.spigot.api.inventory.inventory;

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.util.Map;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 18.11.18 21:59
 *
 */

public class ConditionInventory extends Inventory {

    private String condition, mainInventory;

    public ConditionInventory(String mainInventoryName, MainInventory mainInventory, String condition) {
        super(mainInventory.getTitle(), mainInventory.getSize());
        this.condition = condition;
        this.mainInventory = mainInventoryName;
    }

    public MainInventory getMainInventory() {
        return SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getInventory(this.mainInventory);
    }

    public String getCondition() {
        return condition;
    }


    @Override
    public org.bukkit.inventory.ItemStack[] getContent() {
        org.bukkit.inventory.ItemStack[] itemStacks = new org.bukkit.inventory.ItemStack[getSize()-1];
        for(int i = 1; i < getSize(); i++) {
            ItemStack itemStack = getMainInventory().getItem(i);
            if(itemStack != null)itemStacks[i - 1] = getMainInventory().getItem(i).toBukkitItemStack();
        }
        for(int i = 1; i < getSize(); i++) {
            ItemStack itemStack = getItem(i);
            if(itemStack != null) itemStacks[i-1] = getItem(i).toBukkitItemStack();
        }

        return itemStacks;
    }

    public void setContent(org.bukkit.inventory.Inventory inventory) {
        for(int i = 1; i < getSize(); i++) {
            ItemStack itemStack = getMainInventory().getItem(i);
            if(itemStack != null) inventory.setItem(i-1, itemStack.toBukkitItemStack());
        }
        for(int i = 1; i < getSize(); i++) {
            ItemStack itemStack = getItem(i);
            if(itemStack != null && itemStack.getKey() != null) System.out.println(itemStack.getKey());
            if(itemStack != null) inventory.setItem(i-1, itemStack.toBukkitItemStack());
        }
    }

    @Override
    public org.bukkit.inventory.Inventory toBukkitInventory() {
        org.bukkit.inventory.Inventory inventory = Bukkit.createInventory(null, getMainInventory().getSize(), getMainInventory().getTitle());
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
        return (ConditionInventory) new ConditionInventory(mainInventory, getMainInventory(), getCondition()).setItems(getItems());
    }
}