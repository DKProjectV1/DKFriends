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

    private String mainInventory;
    private String condition;

    public ConditionInventory(Inventory mainInventory, String condition) {
        super(mainInventory.getName(), mainInventory.getSize());
        this.mainInventory = mainInventory.getName();
        this.condition = condition;
    }

    public ConditionInventory(String mainInventory, String condition) {
        super(SpigotFriendSystemBootstrap.getInstance().getInventory(mainInventory).getName(), SpigotFriendSystemBootstrap.getInstance().getInventory(mainInventory).getSize());
        this.mainInventory = mainInventory;
        this.condition = condition;
    }

    public Inventory getMainInventory() {
        return SpigotFriendSystemBootstrap.getInstance().getInventory(this.mainInventory);
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public org.bukkit.inventory.Inventory toBukkitInventory() {
        org.bukkit.inventory.Inventory inventory = Bukkit.createInventory(null, getSize(), getName());
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
        return (ConditionInventory) new ConditionInventory(this.mainInventory, this.condition).setItems(getItems());
    }
}