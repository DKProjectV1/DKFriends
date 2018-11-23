package ch.dkrieger.friendsystem.spigot.adapter.settings;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 23.11.18 20:31
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.FriendAdapter;
import org.bukkit.entity.Player;

public class OpenSettingsPageAdapter extends FriendAdapter {

    public OpenSettingsPageAdapter() {
        super("openSettingsPage");
    }

    @Override
    public void execute(Player player, Object... objects) {
        SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getProfile(player).getSettingsPage().open();
    }
}