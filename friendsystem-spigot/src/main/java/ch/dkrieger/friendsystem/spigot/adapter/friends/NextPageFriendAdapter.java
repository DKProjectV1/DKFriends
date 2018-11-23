package ch.dkrieger.friendsystem.spigot.adapter.friends;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 23.11.18 14:34
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.FriendAdapter;
import org.bukkit.entity.Player;

public class NextPageFriendAdapter extends FriendAdapter {

    public NextPageFriendAdapter() {
        super("nextFriendPage");
    }

    @Override
    public void execute(Player player, Object... objects) {
        SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getProfile(player).getFriendPage().nextPage();
    }
}