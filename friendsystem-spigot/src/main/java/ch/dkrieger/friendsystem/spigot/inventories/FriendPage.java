package ch.dkrieger.friendsystem.spigot.inventories;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 20:12
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.PrivateGUI;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemBuilder;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStack;
import de.tr7zw.itemnbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class FriendPage extends PrivateGUI {

    private int currentPage;
    private List<org.bukkit.inventory.ItemStack> skulls = new LinkedList<>();

    public FriendPage(Player owner) {
        super("friends", owner);
        this.currentPage = 1;
        getMainInventory().getConditionInventory("friendRequests").setContent(getInventory());
        getMainInventory().getConditionInventory("nextFriendPage").setContent(getInventory());
        getMainInventory().getConditionInventory("previousFriendPage").setContent(getInventory());
        for(int i = 0; i < 100; i++) skulls.add(new ItemBuilder(Material.SKULL_ITEM, 1, 3).setDisplayName(UUID.randomUUID().toString()).build());
        setPage(currentPage);
    }

    @Override
    protected void onOpen(InventoryOpenEvent event) {

    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        /*if(event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)return;
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
                        nextPage();
                    }else if(defaultItemStack.getKey().equalsIgnoreCase("previousPage")) {
                        System.out.println("Previous page");
                        previousPage();
                    }
                }
            }
        });*/
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {

    }

    public boolean setPage(int page) {
        System.out.println(page);
        if(page < 1)return false;
        int skullFirstSlot = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getSkullFirstSlot();
        int skullLastSlot = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getSkullLastSlot();
        if(!(this.skulls.size() > page + skullLastSlot * (page - 1))) return false;
        clear(skullFirstSlot, skullLastSlot);
        for(int i = skullFirstSlot; i <= skullLastSlot; i++) {
            int getter = (page > 1 ? i+((page-1)*skullLastSlot)+(page-1) : i);
            System.out.println(getter + ":" + skulls.size());
            if(!(this.skulls.size() > getter)) break;
            org.bukkit.inventory.ItemStack itemStack = this.skulls.get(getter);
            if(itemStack != null && itemStack.getType() != Material.AIR) getInventory().setItem(i, itemStack);
        }
        return true;
    }

    public void previousPage() {
        currentPage--;
        if(!setPage(currentPage)) currentPage++;
    }

    public void nextPage() {
        currentPage++;
        if(!setPage(currentPage)) currentPage--;
    }
}