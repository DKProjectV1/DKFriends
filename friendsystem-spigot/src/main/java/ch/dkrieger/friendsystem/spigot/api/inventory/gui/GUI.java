package ch.dkrieger.friendsystem.spigot.api.inventory.gui;

import ch.dkrieger.friendsystem.spigot.api.inventory.inventory.ConfigInventory;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 24.11.18 12:33
 *
 */

public abstract class GUI implements InventoryHolder {

    @Nullable
    private ConfigInventory configInventory;
    private Inventory inventory;

    protected GUI(){

    }

    public GUI(String name, int size){
        this.inventory = Bukkit.createInventory(this,size,name);
    }

    public GUI(ConfigInventory configInventory) {
        this.configInventory = configInventory;
        this.inventory = configInventory.toBukkitInventory(this);
    }

    public ItemStack getItem(int place){
        return this.inventory.getItem(place);
    }

    @Nullable
    public ConfigInventory getConfigInventory() {
        return this.configInventory;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setItem(int place, ItemStack item){
        this.inventory.setItem(place,item);
    }

    public void setItem(int place, ItemBuilder itembuilder){
        this.inventory.setItem(place,itembuilder.build());
    }

    public void removeItem(int place){
        this.inventory.setItem(place,null);
    }

    public void addItem(ItemStack item){
        this.inventory.addItem(item);
    }

    public void addItem(ItemBuilder itembuilder){
        addItem(itembuilder.build());
    }

    public void fill(ItemBuilder builder){
        fill(builder.build());
    }

    public void fill(ItemStack item){
        for(int i = 0; i < inventory.getContents().length; i++){
            if(this.inventory.getItem(i) == null || this.inventory.getItem(i).getType() == Material.AIR)
                this.inventory.setItem(i,item);
        }
    }

    public void clear(){
        this.inventory.clear();
    }

    public void setConfigInventory(@Nullable ConfigInventory configInventory) {
        this.configInventory = configInventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void createInventory(String name, int size){
        this.inventory = Bukkit.createInventory(this,size,name);
    }

    public void clear(int startPlace, int lastPlace){
        for(int i = startPlace; i <= lastPlace;i++) removeItem(i);
    }

    public void open(Player player){
        if(player.getOpenInventory().getTopInventory().equals(inventory))return;
        player.openInventory(this.inventory);
    }

    public void handleClick(InventoryClickEvent event){
        if(this instanceof PrivateGUI){
            Player clicker = (Player) event.getWhoClicked();
            if(((PrivateGUI)this).getOwner() == clicker) onClick(event);
        }else onClick(event);
    }

    public void handleClose(InventoryCloseEvent event){
        if(this instanceof PrivateGUI){
            Player clicker = (Player) event.getPlayer();
            if(((PrivateGUI)this).getOwner() == clicker) onClose(event);
        }else onClose(event);
    }

    public void handleOpen(InventoryOpenEvent event) {
        if(this instanceof PrivateGUI){
            Player clicker = (Player) event.getPlayer();
            if(((PrivateGUI)this).getOwner() == clicker) onOpen(event);
        }else onOpen(event);
    }

    protected abstract void onOpen(InventoryOpenEvent event);
    protected abstract void onClick(InventoryClickEvent event);
    protected abstract void onClose(InventoryCloseEvent event);
}