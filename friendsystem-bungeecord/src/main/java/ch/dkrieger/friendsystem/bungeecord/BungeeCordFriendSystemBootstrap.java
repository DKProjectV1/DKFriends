package ch.dkrieger.friendsystem.bungeecord;

import ch.dkrieger.friendsystem.bungeecord.event.FriendPlayerColorSetEvent;
import ch.dkrieger.friendsystem.bungeecord.listeners.CloudNetMessageChannelListener;
import ch.dkrieger.friendsystem.bungeecord.listeners.PlayerListener;
import ch.dkrieger.friendsystem.bungeecord.listeners.PluginMessageChannelListener;
import ch.dkrieger.friendsystem.bungeecord.party.BungeeCordCloudNetPartyManager;
import ch.dkrieger.friendsystem.bungeecord.party.BungeeCordPartyManager;
import ch.dkrieger.friendsystem.bungeecord.player.BungeeCordCloudNetPlayerManager;
import ch.dkrieger.friendsystem.bungeecord.player.BungeeCordPlayerManager;
import ch.dkrieger.friendsystem.lib.DKFriendsPlatform;
import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandManager;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.PlayerColor;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.util.concurrent.TimeUnit;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:47
 *
 */

public class BungeeCordFriendSystemBootstrap extends Plugin implements DKFriendsPlatform {

    private static BungeeCordFriendSystemBootstrap instance;
    private BungeeCordCommandManager commandManager;

    public BungeeCordFriendSystemBootstrap() {
        instance = this;
    }

    @Override
    public void onLoad() {
        instance = this;
        this.commandManager = new BungeeCordCommandManager();

        new FriendSystem(this,new BungeeCordPlayerManager(),new BungeeCordPartyManager());
    }
    @Override
    public void onEnable() {
        BungeeCord.getInstance().getPluginManager().registerListener(this,new PlayerListener());
        BungeeCord.getInstance().getPluginManager().registerListener(this,new PluginMessageChannelListener());

        BungeeCord.getInstance().getScheduler().schedule(this,()->{
            if(isCloudNet()){
                BungeeCord.getInstance().getPluginManager().registerListener(this,new CloudNetMessageChannelListener());
                FriendSystem.getInstance().setPlayerManager(new BungeeCordCloudNetPlayerManager());
                FriendSystem.getInstance().setPartyManager(new BungeeCordCloudNetPartyManager());
            }else BungeeCord.getInstance().getPluginManager().registerListener(this,new BungeeCordPartyManager());
        },1L,TimeUnit.SECONDS);
    }
    @Override
    public void onDisable() {
        FriendSystem.getInstance().shutdown();
    }

    public static BungeeCordFriendSystemBootstrap getInstance() {
        return instance;
    }
    @Override
    public String getPlatformName() {
        return "BungeeCord";
    }
    @Override
    public String getServerVersion() {
        return BungeeCord.getInstance().getVersion()+" | "+BungeeCord.getInstance().getGameVersion();
    }
    @Override
    public File getFolder() {
        return new File("plugins/DKFriends/");
    }
    @Override
    public FriendCommandManager getCommandManager() {
        return this.commandManager;
    }

    @Override
    public String getColor(FriendPlayer player) {
        ProxiedPlayer proxyPlayer = BungeeCord.getInstance().getPlayer(player.getUUID());
        if(proxyPlayer == null) return null;
        String color = FriendSystem.getInstance().getConfig().getDefaultColor();
        for(PlayerColor colors : FriendSystem.getInstance().getConfig().getPlayerColors()){
            if(proxyPlayer.hasPermission(colors.getPermission())){
                color = colors.getColor();
                break;
            }
        }
        FriendPlayerColorSetEvent event = new FriendPlayerColorSetEvent(color,player,proxyPlayer);
        BungeeCord.getInstance().getPluginManager().callEvent(event);
        if(event.getColor() != null) color = event.getColor();
        return color;
    }
    private boolean isCloudNet(){
        Plugin cloudnet = BungeeCord.getInstance().getPluginManager().getPlugin("CloudNetAPI");
        if(cloudnet != null && cloudnet.getDescription() != null){
            System.out.println(Messages.SYSTEM_PREFIX+"CloudNet found");
            return true;
        }
        return false;
    }
}
