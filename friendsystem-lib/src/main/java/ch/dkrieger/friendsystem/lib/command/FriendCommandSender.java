package ch.dkrieger.friendsystem.lib.command;

import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 08:35
 *
 */

public interface FriendCommandSender {

    public String getName();

    public UUID getUUID();

    public FriendPlayer getASFriendPlayer();

    public Boolean hasPermission(String permission);

    public void sendMessage(String message);

    public void sendMessage(TextComponent component);


}
