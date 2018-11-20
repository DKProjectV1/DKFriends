package ch.dkrieger.friendsystem.bungeecord.player.online;

import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.player.CloudPlayer;
import de.dytanic.cloudnet.lib.utility.document.Document;
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

public class ExternBungeeCordOnlinePlayer implements OnlineFriendPlayer {

    private CloudPlayer cloudPlayer;
    private String lastMessenger;

    public ExternBungeeCordOnlinePlayer(CloudPlayer cloudPlayer) {
        this.cloudPlayer = cloudPlayer;
    }
    @Override
    public UUID getUUID() {
        return this.cloudPlayer.getUniqueId();
    }
    @Override
    public String getName() {
        return this.cloudPlayer.getName();
    }
    @Override
    public String getLastMessenger() {
        return this.lastMessenger;
    }
    @Override
    public String getServer() {
        return this.cloudPlayer.getServer();
    }
    @Override
    public void sendMessage(String message) {
        sendMessage(new TextComponent(message));
    }
    @Override
    public void sendMessage(TextComponent component) {
        CloudAPI.getInstance().sendCustomSubProxyMessage("DKFriends","message"
                ,new Document().append("player",getUUID()).append("message",component),this.cloudPlayer.getProxy());
    }
    @Override
    public void sendPrivateMessage(String lastMessenger, TextComponent component) {
        this.lastMessenger = lastMessenger;
        CloudAPI.getInstance().sendCustomSubProxyMessage("DKFriends","privateMessage"
                ,new Document().append("player",getUUID()).append("message",component).append("sender",lastMessenger)
                ,this.cloudPlayer.getProxy());
    }
    @Override
    public void connect(String server) {
        CloudAPI.getInstance().sendCustomSubProxyMessage("DKFriends","connect"
                ,new Document().append("player",getUUID()).append("server",server),this.cloudPlayer.getProxy());
    }
}
