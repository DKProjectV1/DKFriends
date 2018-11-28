package ch.dkrieger.friendsystem.spigot.adapter.settings;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 28.11.18 17:53
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import org.bukkit.entity.Player;

import java.util.Map;

public class OpenDesignSettingsPageAdapter extends Adapter {

    public OpenDesignSettingsPageAdapter() {
        super("openDesignSettingsPage");
    }

    @Override
    public void execute(Player player, Map<String, Object> properties) {
        SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getProfile(player).getDesignSettingsPage().open();
    }
}