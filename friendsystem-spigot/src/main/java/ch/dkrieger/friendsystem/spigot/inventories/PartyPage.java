package ch.dkrieger.friendsystem.spigot.inventories;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 21.11.18 19:42
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.party.PartyMember;
import ch.dkrieger.friendsystem.lib.player.Friend;
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
import java.util.function.Consumer;

public class PartyPage extends PrivateGUI {

    private int currentPage;

    public PartyPage(Player owner) {
        super("parties", owner);
        this.currentPage = 1;
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

    private boolean setPage(int page) {
        if(page < 1)return false;
        Party party = FriendSystem.getInstance().getPartyManager().getParty(getOwner().getUniqueId());
        if(party != null) {
            List<PartyMember> partyMembers = party.getSortedMembers();
            int skullFirstSlot = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getSettingAsInt("skullFirstSlot");
            int skullLastSlot = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getSettingAsInt("skullLastSlot");
            int skullsPerPage = skullLastSlot-skullFirstSlot+1;

            int switchPageSlot1 = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getSettingAsInt("partySwitchPageInventorySlot1");
            int switchPageSlot2 = SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getSettingAsInt("partySwitchPageInventorySlot2");
            int pages = (int) Math.ceil((double)partyMembers.size() / skullsPerPage);

            if(page > 1) {
                if(page == pages) setItem(switchPageSlot2, new ItemStack(Material.AIR));
                getInventory().setItem((page == pages ? switchPageSlot1 : switchPageSlot2),
                        SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getItem("previousPartyPage").toBukkitItemStack());

            }
            if(pages > page) {
                getInventory().setItem(switchPageSlot1, SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getItem("nextPartyPage").toBukkitItemStack());
            }
            if(page == 1) setItem(switchPageSlot2, new ItemStack(Material.AIR));

            if(!(partyMembers.size() > page + skullLastSlot * (page - 1))) return false;

            clear(skullFirstSlot, skullLastSlot);

            for(int i = skullFirstSlot; i <= skullLastSlot; i++) {
                int getter = (page > 1 ? i+((page-1)*skullLastSlot)+(page-1) : i);
                if(!(partyMembers.size() > getter)) break;
                PartyMember partyMember = partyMembers.get(getter);
                getInventory().setItem(i, SpigotFriendSystemBootstrap.getInstance().getAdvancedConfig().getItem("partyPlayerSkull").toBukkitItemStack(partyMember));
            }
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