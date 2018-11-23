package ch.dkrieger.friendsystem.spigot.player.local;

import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 21.11.18 20:05
 *
 */

public class LocalSpigotOnlinePlayer implements OnlineFriendPlayer {

    private Player player;
    private String lastMessenger;

    public LocalSpigotOnlinePlayer(Player player) {
        this.player = player;
    }
    @Override
    public UUID getUUID() {
        return this.player.getUniqueId();
    }

    @Override
    public String getName() {
        return this.player.getName();
    }

    @Override
    public String getLastMessenger() {
        return this.lastMessenger;
    }

    @Override
    public String getServer() {
        return player.getWorld().getName();
    }

    @Override
    public void sendMessage(String message) {
        player.sendMessage(message);
    }

    @Override
    public void sendMessage(TextComponent component) {
        player.sendMessage(component.getText());
        //send textMessage
    }
    @Override
    public void sendPrivateMessage(String lastMessenger, TextComponent component) {
        this.lastMessenger = lastMessenger;
        sendMessage(component);
    }
    @Override
    public void connect(String server) {
        World world = Bukkit.getWorld(server);
        if(world != null && !(player.getWorld().equals(world))) player.teleport(world.getSpawnLocation());
    }
    @Override
    public void executeCommand(String command) {
        Bukkit.dispatchCommand(this.player,command);
    }
}
