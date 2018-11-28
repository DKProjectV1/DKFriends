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

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 28.11.18 13:56
 *
 */

public class FriendRequestsPage extends PrivateGUI {

    private int currentPage;

    public FriendRequestsPage(Player owner) {
        super("friendRequests", owner);
        setPage(currentPage);
    }

    public FriendPlayer getFriendPlayer() {
        return FriendSystem.getInstance().getPlayerManager().getPlayer(getOwner().getUniqueId());
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

    public void updateCurrentPage() {
        setPage(this.currentPage);
    }

    private boolean setPage(int page) {
        if(page < 1)return false;


        List<Friend> requests = getFriendPlayer().getRequests();
        int skullFirstSlot = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getSettingAsInt("friendRequestsPageSkullFirstSlot");
        int skullLastSlot = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getSettingAsInt("friendRequestsPageSkullLastSlot");
        int skullsPerPage = skullLastSlot-skullFirstSlot+1;

        int switchPageSlot1 = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getSettingAsInt("friendRequestsSwitchPageInventorySlot1");
        int switchPageSlot2 = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getSettingAsInt("friendRequestsSwitchPageInventorySlot2");
        int pages = (int) Math.ceil((double)requests.size() / skullsPerPage);

        if(page > 1) {
            if(page == pages) setItem(switchPageSlot2, new ItemStack(Material.AIR));
            getInventory().setItem((page == pages ? switchPageSlot1 : switchPageSlot2),
                    SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getItem("previousFriendRequestsPage").toBukkitItemStack());

        }
        if(pages > page) {
            getInventory().setItem(switchPageSlot1, SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getItem("nextFriendRequestsPage").toBukkitItemStack());
        }
        if(page == 1) setItem(switchPageSlot2, new ItemStack(Material.AIR));

        if(!(requests.size() > page + skullLastSlot * (page - 1))) return false;

        clear(skullFirstSlot, skullLastSlot);

        for(int i = skullFirstSlot; i <= skullLastSlot; i++) {
            int getter = (page > 1 ? i+((page-1)*skullLastSlot)+(page-1) : i);
            if(!(requests.size() > getter)) break;
            Friend friend = requests.get(getter);
            getInventory().setItem(i, SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getItem("requestItem").toBukkitItemStack(friend));
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