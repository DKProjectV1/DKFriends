package ch.dkrieger.friendsystem.spigot.player.bungeecord;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 21.11.18 20:07
 *
 */

import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import ch.dkrieger.friendsystem.lib.utils.Document;
import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.UUID;

public class SpigotBungeeCordOnlinePlayer implements OnlineFriendPlayer {

    private UUID uuid;
    private String name, server, lastMessenger;

    public SpigotBungeeCordOnlinePlayer(UUID uuid, String name, String server) {
        this.uuid = uuid;
        this.name = name;
        this.server = server;
    }
    @Override
    public UUID getUUID() {
        return this.uuid;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getLastMessenger() {
        return this.lastMessenger;
    }

    @Override
    public String getServer() {
        return this.server;
    }

    @Override
    public void sendMessage(String message) {
        sendMessage(new TextComponent(message));
    }

    @Override
    public void sendMessage(TextComponent component) {
        SpigotFriendSystemBootstrap.getInstance().getBungeeCordConnection()
                .send("sendMessage",new Document().append("message",component));
    }
    @Override
    public void sendPrivateMessage(String lastMessenger, TextComponent component) {
        this.lastMessenger = lastMessenger;
        SpigotFriendSystemBootstrap.getInstance().getBungeeCordConnection()
                .send("privateMessage",new Document().append("sender",lastMessenger).append("message",component));
    }
    @Override
    public void connect(String server) {
        SpigotFriendSystemBootstrap.getInstance().getBungeeCordConnection()
                .send("connect",new Document().append("server",server));
    }

    @Override
    public void executeCommand(String command) {
        SpigotFriendSystemBootstrap.getInstance().getBungeeCordConnection()
                .send("executeCommand",new Document().append("server",server));
    }
}
