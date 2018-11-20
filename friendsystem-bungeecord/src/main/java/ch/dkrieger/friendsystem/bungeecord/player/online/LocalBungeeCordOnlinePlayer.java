package ch.dkrieger.friendsystem.bungeecord.player.online;

import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 20.11.18 20:10
 *
 */

public class LocalBungeeCordOnlinePlayer implements OnlineFriendPlayer {

    private ProxiedPlayer proxiedPlayer;
    private String lastMessenger;

    public LocalBungeeCordOnlinePlayer(ProxiedPlayer proxiedPlayer) {
        this.proxiedPlayer = proxiedPlayer;
    }
    @Override
    public UUID getUUID() {
        return this.proxiedPlayer.getUniqueId();
    }
    @Override
    public String getName() {
        return this.proxiedPlayer.getName();
    }

    @Override
    public String getLastMessenger() {
        return this.lastMessenger;
    }

    @Override
    public String getServer() {
        return this.proxiedPlayer.getServer().getInfo().getName();
    }
    @Override
    public void sendMessage(String message) {
        sendMessage(new TextComponent(message));
    }
    @Override
    public void sendMessage(TextComponent component) {
        this.proxiedPlayer.sendMessage(component);
    }
    @Override
    public void sendPrivateMessage(String lastMessenger, TextComponent component) {
        this.lastMessenger = lastMessenger;
        sendMessage(component);
    }
    @Override
    public void connect(String server) {
        ServerInfo info = BungeeCord.getInstance().getServerInfo(server);
        if(info != null && !(this.proxiedPlayer.getServer().getInfo().equals(info))) this.proxiedPlayer.connect(info);
    }
}
