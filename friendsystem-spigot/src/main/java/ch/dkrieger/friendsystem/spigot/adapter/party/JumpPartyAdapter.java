package ch.dkrieger.friendsystem.spigot.adapter.party;

/*
 *
 *  * Copyright (c) 2018 Philipp Elvin Friedhoff on 25.11.18 19:33
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.spigot.adapter.FriendAdapter;
import org.bukkit.entity.Player;

public class JumpPartyAdapter extends FriendAdapter {

    public JumpPartyAdapter() {
        super("jumpToPlayer");
    }

    @Override
    public void execute(Player player, Object... objects) {
        FriendSystem.getInstance().getPlayerManager().getPlayer(player.getUniqueId()).getOnlinePlayer().executeCommand("friend jump " + String.valueOf(objects[0]));
    }
}