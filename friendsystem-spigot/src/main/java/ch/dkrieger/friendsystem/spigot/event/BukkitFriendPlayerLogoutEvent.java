package ch.dkrieger.friendsystem.spigot.event;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 25.11.18 16:01
 *
 */

public class BukkitFriendPlayerLogoutEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private UUID uuid;

    public BukkitFriendPlayerLogoutEvent(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }
    public FriendPlayer getPlayer(){
        return FriendSystem.getInstance().getPlayerManager().getPlayer(uuid);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
