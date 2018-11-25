package ch.dkrieger.friendsystem.spigot.adapter.party;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 15:07
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.FriendAdapter;
import org.bukkit.entity.Player;

public class PreviousPartyPageAdapter extends FriendAdapter {

    public PreviousPartyPageAdapter() {
        super("previousPartyPage");
    }

    @Override
    public void execute(Player player, Object... objects) {
        SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getProfile(player).getPartyPage().previousPage();
    }
}