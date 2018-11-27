package ch.dkrieger.friendsystem.spigot.adapter.settings;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 23.11.18 20:31
 *
 */

import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;

public class OpenSettingsPageAdapter extends Adapter {

    public OpenSettingsPageAdapter() {
        super("openSettingsPage");
    }

    @Override
    public void execute(Player player, Map<String, Object> properties) {
        Bukkit.getScheduler().runTask(SpigotFriendSystemBootstrap.getInstance(), ()-> SpigotFriendSystemBootstrap.getInstance().getInventoryManager().getProfile(player).getSettingsPage().open());
    }
}