package ch.dkrieger.friendsystem.spigot.event;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 25.11.18 16:00
 *
 */

public class BukkitOnlineFriendPlayerUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private final UUID uuid;
    private final OnlineFriendPlayer onlinePlayer;

    public BukkitOnlineFriendPlayerUpdateEvent(UUID uuid, OnlineFriendPlayer onlinePlayer) {
        this.uuid = uuid;
        this.onlinePlayer = onlinePlayer;
    }

    public UUID getUUID() {
        return uuid;
    }
    public FriendPlayer getPlayer(){
        return FriendSystem.getInstance().getPlayerManager().getPlayer(uuid);
    }
    public OnlineFriendPlayer getOnlinePlayer(){
        return this.onlinePlayer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
