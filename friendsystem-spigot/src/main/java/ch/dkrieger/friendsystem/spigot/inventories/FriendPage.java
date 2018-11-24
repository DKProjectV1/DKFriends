package ch.dkrieger.friendsystem.spigot.inventories;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 20.11.18 20:12
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.gui.PrivateGUI;
import ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class FriendPage extends PrivateGUI {

    private int currentPage;
    //private List<ItemStack> itemStacks = new LinkedList<>();

    public FriendPage(Player owner) {
        super("friends", owner);
        this.currentPage = 1;
        //getMainInventory().getConditionInventory("friendRequests").setContent(getInventory());
        //for(int i = 0; i < 75; i++) itemStacks.add(new ItemBuilder(Material.SKULL_ITEM).setDisplayName(UUID.randomUUID().toString()).build());
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
                        nextPage();
                    }else if(defaultItemStack.getKey().equalsIgnoreCase("previousPage")) {
                        previousPage();
                    }
                }
            }
        });*/
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {

    }

    public FriendPlayer getFriendPlayer() {
        return FriendSystem.getInstance().getPlayerManager().getPlayer(getOwner().getUniqueId());
    }

    private boolean setPage(int page) {
        List<Friend> friends = getFriendPlayer().getSortedFriends();
        if(page < 1)return false;
        int skullFirstSlot = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getSkullFirstSlot();
        int skullLastSlot = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getSkullLastSlot();
        int skullsPerPage = skullLastSlot-skullFirstSlot+1;

        int switchPageSlot1 = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getFriendSwitchPageInventorySlot1();
        int switchPageSlot2 = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getFriendSwitchPageInventorySlot2();
        int pages = (int) Math.ceil((double)friends.size() / skullsPerPage);
        if(page > 1) {
            if(page == pages) setItem(switchPageSlot2, new ItemStack(Material.AIR));
            getInventory().setItem((page == pages ? switchPageSlot1 : switchPageSlot2),
                    SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getItem("previousPage").toBukkitItemStack());
            //getMainInventory().getConditionInventory("previousFriendPage").setContent(getInventory());
        }
        if(pages > page) {
            getInventory().setItem(switchPageSlot1, SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getItem("nextPage").toBukkitItemStack());
            //getMainInventory().getConditionInventory("nextFriendPage").setContent(getInventory());
        }
        if(page == 1) setItem(switchPageSlot2, new ItemStack(Material.AIR));


        if(!(friends.size() > page + skullLastSlot * (page - 1))) return false;

        clear(skullFirstSlot, skullLastSlot);
        for(int i = skullFirstSlot; i <= skullLastSlot; i++) {
            int getter = (page > 1 ? i+((page-1)*skullLastSlot)+(page-1) : i);
            if(!(friends.size() > getter)) break;

            Friend friend = friends.get(getter);
            if(friend.isOnline()) getInventory().setItem(i, SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getItems().get("onlinePlayerSkull").toBukkitItemStack(friend));
            else getInventory().setItem(i, SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getItems().get("offlinePlayerSkull").toBukkitItemStack(friend));
        }
        return true;
    }

    public void previousPage() {
        currentPage--;
        if(!setPage(currentPage)) currentPage++;

    }

    public void nextPage() {
        currentPage++;
        if (!setPage(currentPage)) currentPage--;
    }
}