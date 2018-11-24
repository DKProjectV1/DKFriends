package ch.dkrieger.friendsystem.spigot.event;

import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 24.11.18 14:57
 *
 */

public class FriendPlayerColorSetEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private String color;
    private FriendPlayer player;
    private Player bukkitPlayer;

    public FriendPlayerColorSetEvent(String color, FriendPlayer player, Player bukkitPlayer) {
        this.color = color;
        this.player = player;
        this.bukkitPlayer = bukkitPlayer;
    }
    public String getColor(){
        return this.color;
    }
    public FriendPlayer getPlayer() {
        return this.player;
    }
    public Player getBukkitPlayer() {
        return bukkitPlayer;
    }
    public void setColor(String color){
        this.color = color;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
