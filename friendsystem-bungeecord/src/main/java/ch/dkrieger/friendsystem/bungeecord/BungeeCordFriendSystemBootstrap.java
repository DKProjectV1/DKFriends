package ch.dkrieger.friendsystem.bungeecord;

import ch.dkrieger.friendsystem.bungeecord.listeners.CloudNetMessageChannelListener;
import ch.dkrieger.friendsystem.bungeecord.listeners.PlayerListener;
import ch.dkrieger.friendsystem.bungeecord.player.BungeeCordCloudNetPlayerManager;
import ch.dkrieger.friendsystem.bungeecord.player.BungeeCordPlayerManager;
import ch.dkrieger.friendsystem.lib.DKFriendsPlatform;
import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandManager;
import net.md_5.bungee.BungeeCord;
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

        new FriendSystem(this,new BungeeCordPlayerManager());
    }
    @Override
    public void onEnable() {
        BungeeCord.getInstance().getPluginManager().registerListener(this,new PlayerListener());

        BungeeCord.getInstance().getScheduler().schedule(this,()->{
            if(isCloudNet()){
                BungeeCord.getInstance().getPluginManager().registerListener(this,new CloudNetMessageChannelListener());
                FriendSystem.getInstance().setPlayerManager(new BungeeCordCloudNetPlayerManager());
            }
        },3L,TimeUnit.SECONDS);
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
    private boolean isCloudNet(){
        Plugin cloudnet = BungeeCord.getInstance().getPluginManager().getPlugin("CloudNetAPI");
        if(cloudnet != null && cloudnet.getDescription() != null){
            System.out.println(Messages.SYSTEM_PREFIX+"CloudNet found");
            return true;
        }
        return false;
    }
}
