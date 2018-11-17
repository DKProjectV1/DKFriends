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
    private ProxiedPlayer player;
    private FriendPlayer friendPlayer;

    public FriendPlayerColorSetEvent(String color, ProxiedPlayer player, FriendPlayer friendPlayer) {
        this.color = color;
        this.player = player;
        this.friendPlayer = friendPlayer;
    }
    public String getColor(){
        return this.color;
    }
    public ProxiedPlayer getPlayer(){
        return this.player;
    }
    public FriendPlayer getFriendPlayer() {
        return this.friendPlayer;
    }
    public void setColor(String color){
        this.color = color;
    }
}
