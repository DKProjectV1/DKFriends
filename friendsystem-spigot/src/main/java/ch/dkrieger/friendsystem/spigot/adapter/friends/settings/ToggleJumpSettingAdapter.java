package ch.dkrieger.friendsystem.spigot.adapter.friends.settings;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 28.11.18 14:00
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import org.bukkit.entity.Player;

import java.util.Map;

public class ToggleJumpSettingAdapter extends Adapter {

    public ToggleJumpSettingAdapter() {
        super("toggleJumpSetting");
    }

    @Override
    public void execute(Player player, Map<String, Object> properties) {
        FriendSystem.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).getOnlinePlayer().executeCommand("friend toggle jump");
    }
}