package ch.dkrieger.friendsystem.lib.command;

import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:47
 *
 */

public interface FriendCommandSender {

    public String getName();

    public UUID getUUID();

    public FriendPlayer getAsFriendPlayer();

    public boolean hasPermission(String permission);

    public void sendMessage(String message);

    public void sendMessage(TextComponent component);


}
