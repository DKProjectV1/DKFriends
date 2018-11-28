package ch.dkrieger.friendsystem.spigot.adapter.friends.settings;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 28.11.18 17:46
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.spigot.adapter.Adapter;
import org.bukkit.entity.Player;

import java.util.Map;

public class ChangeDesignSettingAdapter extends Adapter {

    public ChangeDesignSettingAdapter() {
        super("changeDesignSetting");
    }

    @Override
    public void execute(Player player, Map<String, Object> properties) {
        FriendPlayer friendPlayer = FriendSystem.getInstance().getPlayerManager().getPlayer(player.getUniqueId());
        friendPlayer.getSettings().setDesign(Short.parseShort(((String)properties.get("adapter")).split("[)(]")[1]));
    }
}