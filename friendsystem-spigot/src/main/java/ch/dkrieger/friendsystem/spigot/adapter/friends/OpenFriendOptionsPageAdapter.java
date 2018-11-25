package ch.dkrieger.friendsystem.spigot.adapter.friends;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 15:45
 *
 */

import ch.dkrieger.friendsystem.spigot.adapter.FriendAdapter;
import ch.dkrieger.friendsystem.spigot.inventories.FriendOptionsPage;
import org.bukkit.entity.Player;

import java.util.UUID;

public class OpenFriendOptionsPageAdapter extends FriendAdapter {

    public OpenFriendOptionsPageAdapter() {
        super("openFriendOptionsPage");
    }

    @Override
    public void execute(Player player, Object... objects) {
        new FriendOptionsPage(player, UUID.fromString(String.valueOf(objects[0]))).open();
    }
}