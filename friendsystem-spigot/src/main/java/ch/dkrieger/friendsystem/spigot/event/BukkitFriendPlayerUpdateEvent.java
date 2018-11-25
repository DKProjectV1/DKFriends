package ch.dkrieger.friendsystem.spigot.event;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 25.11.18 15:54
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class BukkitFriendPlayerUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private UUID uuid;

    public BukkitFriendPlayerUpdateEvent(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }
    public FriendPlayer getPlayer(){
        return FriendSystem.getInstance().getPlayerManager().getPlayer(uuid);
    }
    public OnlineFriendPlayer getOnlinePlayer(){
        return FriendSystem.getInstance().getPlayerManager().getOnlinePlayer(uuid);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
