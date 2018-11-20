package ch.dkrieger.friendsystem.lib.player;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:55
 *
 */

import net.md_5.bungee.api.chat.TextComponent;

import java.util.UUID;

public interface OnlineFriendPlayer {

    public UUID getUUID();

    public String getName();

    public String getLastMessenger();

    public String getServer();

    public void sendMessage(String message);

    public void sendMessage(TextComponent component);

    public void sendPrivateMessage(String lastMessenger, TextComponent component);

    public void connect(String server);

}
