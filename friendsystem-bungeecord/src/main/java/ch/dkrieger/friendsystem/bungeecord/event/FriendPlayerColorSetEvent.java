package ch.dkrieger.friendsystem.bungeecord.event;

import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Event;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 17.11.18 23:08
 *
 */

public class FriendPlayerColorSetEvent extends Event{

    private String color;
    private FriendPlayer player;
    private ProxiedPlayer proxiedPlayer;

    public FriendPlayerColorSetEvent(String color, FriendPlayer player, ProxiedPlayer proxiedPlayer) {
        this.color = color;
        this.player = player;
        this.proxiedPlayer = proxiedPlayer;
    }

    public String getColor(){
        return this.color;
    }
    public FriendPlayer getPlayer() {
        return this.player;
    }
    public ProxiedPlayer getProxiedPlayer() {
        return this.proxiedPlayer;
    }
    public void setColor(String color){
        this.color = color;
    }
}
