package ch.dkrieger.friendsystem.lib.cloudnet;

import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import de.dytanic.cloudnet.api.CloudAPI;
import de.dytanic.cloudnet.lib.player.CloudPlayer;
import de.dytanic.cloudnet.lib.utility.document.Document;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 21.11.18 19:52
 *
 */

public class ExternCloudNetOnlinePlayer implements OnlineFriendPlayer {

    private CloudPlayer cloudPlayer;
    private String lastMessenger;

    public ExternCloudNetOnlinePlayer(CloudPlayer cloudPlayer) {
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
        CloudAPI.getInstance().sendCustomSubProxyMessage("DKFriends","sendMessage"
                ,new Document().append("player",getUUID()).append("message",component),this.cloudPlayer.getProxy());
        System.out.println("sending : "+component);
    }

    @Override
    public void executeCommand(String command) {
        CloudAPI.getInstance().sendCustomSubProxyMessage("DKFriends","executeCommand"
                ,new Document().append("player",getUUID()).append("command",command),this.cloudPlayer.getProxy());
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
    public void setCloudPlayer(CloudPlayer cloudPlayer) {
        this.cloudPlayer = cloudPlayer;
    }
}
