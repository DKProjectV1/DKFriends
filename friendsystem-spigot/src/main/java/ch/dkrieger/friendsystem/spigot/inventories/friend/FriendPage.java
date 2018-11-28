package ch.dkrieger.friendsystem.spigot.inventories.friend;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.inventory.Listener;
import ch.dkrieger.friendsystem.spigot.api.inventory.gui.PrivateGUI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 28.11.18 13:56
 *
 */

public class FriendPage extends PrivateGUI {

    private int currentPage;
    //private List<ItemStack> itemStacks = new LinkedList<>();

    public FriendPage(Player owner) {
        super("friends", owner);
        this.currentPage = 1;
        //getMainInventory().getConditionInventory("friendRequests").setContent(getInventory());
        //for(int i = 0; i < 75; i++) itemStacks.add(new ItemBuilder(Material.SKULL_ITEM).setDisplayName(UUID.randomUUID().toString()).build());
        for(Map.Entry<Integer, ch.dkrieger.friendsystem.spigot.api.inventory.item.ItemStack> entry : SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getDefaultInventoryItems().entrySet()) {
            getInventory().setItem(entry.getKey(), entry.getValue().toBukkitItemStack());
        }
        setPage(currentPage);
    }

    @Override
    protected void onOpen(InventoryOpenEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), ()-> Listener.execute(Listener.DefaultEvent.INVENTORY_OPEN, (Player) event.getPlayer(), getConfigInventory()));
    }

    @Override
    protected void onClick(InventoryClickEvent event) {
        event.setCancelled(true);
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), ()-> Listener.execute(Listener.DefaultEvent.CLICK, (Player) event.getWhoClicked(), event.getCurrentItem()));
    }

    @Override
    protected void onClose(InventoryCloseEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(), ()-> Listener.execute(Listener.DefaultEvent.INVENTORY_CLOSE, (Player) event.getPlayer(), getConfigInventory()));
    }

    public FriendPlayer getFriendPlayer() {
        return FriendSystem.getInstance().getPlayerManager().getPlayer(getOwner().getUniqueId());
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void updateCurrentPage() {
        setPage(this.currentPage);
    }

    public boolean setPage(int page) {
        if(getFriendPlayer().hasRequests()) getConfigInventory().getConditionItems("hasRequests").forEach((slot, itemStack) -> getInventory().setItem(slot, itemStack.toBukkitItemStack(getFriendPlayer())));

        List<Friend> friends = getFriendPlayer().getSortedFriends();
        if(page < 1)return false;
        int skullFirstSlot = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getSettingAsInt("friendPageSkullFirstSlot");
        int skullLastSlot = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getSettingAsInt("friendPageSkullLastSlot");
        int skullsPerPage = skullLastSlot-skullFirstSlot+1;

        int switchPageSlot1 = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getSettingAsInt("friendSwitchPageInventorySlot1");
        int switchPageSlot2 = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getSettingAsInt("friendSwitchPageInventorySlot2");
        int pages = (int) Math.ceil((double)friends.size() / skullsPerPage);
        if(page > 1) {
            if(page == pages) setItem(switchPageSlot2, new ItemStack(Material.AIR));
            getInventory().setItem((page == pages ? switchPageSlot1 : switchPageSlot2),
                    SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getItem("previousFriendPage").toBukkitItemStack());
        }
        if(pages > page) {
            getInventory().setItem(switchPageSlot1, SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getItem("nextFriendPage").toBukkitItemStack());
        }
        if(page == 1) setItem(switchPageSlot2, new ItemStack(Material.AIR));


        if(!(friends.size() > page + skullLastSlot * (page - 1))) return false;

        clear(skullFirstSlot, skullLastSlot);
        for(int i = skullFirstSlot; i <= skullLastSlot; i++) {
            int getter = (page > 1 ? i+((page-1)*skullLastSlot)+(page-1) : i);
            if(!(friends.size() > getter)) break;
            Friend friend = friends.get(getter);
            if(friend.isOnline()) getInventory().setItem(i, SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getItem("onlinePlayerSkull").toBukkitItemStack(friend));
            else getInventory().setItem(i, SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getItem("offlinePlayerSkull").toBukkitItemStack(friend));
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