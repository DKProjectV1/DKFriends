package ch.dkrieger.friendsystem.spigot.adapter.friends;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 23.11.18 16:57
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.FriendAdapter;
import org.bukkit.entity.Player;

public class PreviousPageFriendAdapter extends FriendAdapter {

    public PreviousPageFriendAdapter() {
        super("previousFriendPage");
    }

    @Override
    public void execute(Player player, Object... objects) {
        SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getProfile(player).getFriendPage().previousPage();
    }
}