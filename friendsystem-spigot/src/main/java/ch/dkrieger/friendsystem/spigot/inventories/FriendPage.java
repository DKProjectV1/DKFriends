package ch.dkrieger.friendsystem.spigot.inventories;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 20:12
 *
 */

import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.PrivateGUI;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemBuilder;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStack;
import ch.dkrieger.friendsystem.spigot.util.SpigotUtil;
import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class FriendPage extends PrivateGUI {

    private List<Friend> friends;

    public FriendPage(Player owner) {
        super("friends", owner);
        getMainInventory().getConditionInventory("friendRequests").setContent(getInventory());
        getMainInventory().getConditionInventory("nextFriendPage").setContent(getInventory());
        getMainInventory().getConditionInventory("previousFriendPage").setContent(getInventory());
        this.friends = new LinkedList<>();
        for(int i = 0; i < 100; i++) {
            friends.add(new Friend(UUID.randomUUID(), 0, false));
        }
        int freePlaces = SpigotUtil.getFreeInventoryPlaces(getInventory());
        //for(int i = 0; i < freePlaces; i++) getInventory().addItem(buildSkull());
    }

    @Override
    protected void onOpen(InventoryOpenEvent event) {

    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        if(event.getCurrentItem() == null)return;
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), ()-> {
            NBTItem nbtItem = new NBTItem(event.getCurrentItem());
            if(nbtItem.hasKey("defaultitem")) {
                ItemStack defaultItemStack = getMainInventory().getItem(nbtItem.getString("defaultitem"), true);
                if(defaultItemStack != null) {
                    if(defaultItemStack.getKey().equalsIgnoreCase("friends")) {

                    }else if(defaultItemStack.getKey().equalsIgnoreCase("clans")) {

                    }else if(defaultItemStack.getKey().equalsIgnoreCase("parties")) {

                    }else if(defaultItemStack.getKey().equalsIgnoreCase("settings")) {

                    }else if(defaultItemStack.getKey().equalsIgnoreCase("friendRequests")) {

                    }else if(defaultItemStack.getKey().equalsIgnoreCase("nextPage")) {
                        System.out.println("next page");
                    }else if(defaultItemStack.getKey().equalsIgnoreCase("previousPage")) {
                        System.out.println("Previous page");
                    }
                }
            }
        });
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {

    }

    private org.bukkit.inventory.ItemStack buildSkull(Player player) {
        //ItemBuilder itemBuilder = new ItemBuilder(((CraftPlayer)player).getProfile(), "");//Get gameprofile by friendplayer
        //return itemBuilder.build();
        return null;
    }
}