package ch.dkrieger.friendsystem.bungeecord.listeners;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 20:21
 *
 */

import ch.dkrieger.friendsystem.bungeecord.BungeeCordFriendSystemBootstrap;
import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class PlayerListener implements Listener {

    @EventHandler(priority=80)
    public void onLogin(LoginEvent event){
        if(BungeeCord.getInstance().getConfig().isOnlineMode() && !(event.getConnection().isOnlineMode())) return;
        FriendPlayer player = null;
        try{
            player = FriendSystem.getInstance().getPlayerManager().getPlayerSave(event.getConnection().getUniqueId());
        }catch (Exception exception){
            try{
                player = FriendSystem.getInstance().getPlayerManager().getPlayerSave(event.getConnection().getUniqueId());
            }catch (Exception exception2){
                event.setCancelled(true);
                event.setCancelReason(new TextComponent(Messages.ERROR));
                //PermissionSystem.getInstance().debug(PermissionSystem.PermissionInfoLevel.ERROR,null,"Could not load player "+event.getConnection().getName());
                //PermissionSystem.getInstance().debug(PermissionSystem.PermissionInfoLevel.ERROR,null,exception2.getMessage());
                return;
            }
        }
        System.out.println(player);
        if(player == null){
            player = FriendSystem.getInstance().getPlayerManager().createPlayer(event.getConnection().getUniqueId()
                    ,event.getConnection().getName(),"ColorChange",null);
        }//else PermissionPlayerManager.getInstance().checkName(event.getConnection().getUniqueId(),event.getConnection().getName());
        if(BungeeCord.getInstance().getPlayers().size() == 0){
            BungeeCord.getInstance().getScheduler().runAsync(BungeeCordFriendSystemBootstrap.getInstance(),()->{


            });
        }
    }
}
