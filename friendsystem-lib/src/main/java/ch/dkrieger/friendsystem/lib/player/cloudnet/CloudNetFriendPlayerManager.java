package ch.dkrieger.friendsystem.lib.player.cloudnet;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 17.11.18 11:58
 *
 */

import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import de.dytanic.cloudnet.lib.player.CloudPlayer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.UUID;

public class CloudNetFriendPlayerManager implements OnlineFriendPlayer {

    private CloudPlayer cloudPlayer;

    @Override
    public UUID getUUID() {
        return this.cloudPlayer.getUniqueId();
    }
    @Override
    public String getName() {
        return this.cloudPlayer.getName();
    }
    @Override
    public String getServer() {
        return this.cloudPlayer.getServer();
    }

    /*

    send in own message

     */
    @Override
    public void sendMessage(String message) {
        this.cloudPlayer.getPlayerExecutor().sendMessage(this.cloudPlayer,message);
    }
    @Override
    public void sendMessage(TextComponent component) {
        this.cloudPlayer.getPlayerExecutor().sendMessage(this.cloudPlayer,component.toString());//https://github.com/Bukkit/mc-dev/blob/master/net/minecraft/server/ChatSerializer.java
    }
    @Override
    public void connect(String server) {
        this.cloudPlayer.getPlayerExecutor().sendPlayer(this.cloudPlayer,server);
    }
}

