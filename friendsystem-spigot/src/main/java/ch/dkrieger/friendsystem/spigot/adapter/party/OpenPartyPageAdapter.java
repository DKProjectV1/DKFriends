package ch.dkrieger.friendsystem.spigot.adapter.party;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 23.11.18 20:30
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.FriendAdapter;
import org.bukkit.entity.Player;

public class OpenPartyPageAdapter extends FriendAdapter {

    public OpenPartyPageAdapter() {
        super("openPartyPage");
    }

    @Override
    public void execute(Player player, Object... objects) {
        SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getProfile(player).getPartyPage().open();
    }
}