package ch.dkrieger.friendsystem.bungeecord.listeners;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 20:21
 *
 */

import ch.dkrieger.friendsystem.bungeecord.BungeeCordFriendSystemBootstrap;
import ch.dkrieger.friendsystem.bungeecord.player.BungeeCordPlayerManager;
import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import net.md_5.bungee.BungeeCord;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectedEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;

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
                event.setCancelReason(new TextComponent(Messages.ERROR.replace("[prefix]",Messages.PREFIX_FRIEND)));
                exception.printStackTrace();
                return;
            }
        }
        if(player == null){
            player = FriendSystem.getInstance().getPlayerManager().createPlayer(event.getConnection().getUniqueId()
                    ,event.getConnection().getName(),"§8",null);
        }else player.updateInformations(event.getConnection().getName(),getColor(player));
        if(BungeeCord.getInstance().getPlayers().size() == 0){
            BungeeCord.getInstance().getScheduler().runAsync(BungeeCordFriendSystemBootstrap.getInstance(),()->{


            });
        }
    }
    @EventHandler(priority=100)
    public void onPostLogin(PostLoginEvent event){
        BungeeCord.getInstance().getScheduler().runAsync(BungeeCordFriendSystemBootstrap.getInstance(),()->{
            FriendPlayer player = FriendSystem.getInstance().getPlayerManager().getPlayer(event.getPlayer().getUniqueId());
            if(player == null){
                event.getPlayer().disconnect(new TextComponent(Messages.ERROR.replace("[prefix]",Messages.PREFIX_FRIEND)));
                return;
            }
            if(FriendSystem.getInstance().getConfig().getBooleanValue("join.requestinfo.enabled")){
                List<Friend> requests = player.getRequests();
                if(requests.size() > 0){
                    String design = Messages.PLAYER_REQUEST_OPEN_PLURAL;
                    if(requests.size() == 1) design = Messages.PLAYER_REQUEST_OPEN_SINGULAR;
                    TextComponent message = new TextComponent(Messages.PLAYER_REQUEST_OPEN_MESSAGE
                            .replace("[prefix]",Messages.PREFIX_FRIEND)
                            .replace("[amount]",""+requests.size())
                            .replace("[design]",design));
                    message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend requests"));
                    player.sendMessage(message);
                }
            }
            if(FriendSystem.getInstance().getConfig().getBooleanValue("join.friendinfo.enabled")){
                List<Friend> friends = player.getOnlineFriends();

                if(friends.isEmpty()) return;
                if(friends.size() == 1){
                    player.sendMessage(new TextComponent(Messages.PLAYER_ONLINE_ONE
                            .replace("[prefix]",Messages.PREFIX_FRIEND)
                            .replace("[player-1]",friends.get(0).getFriendPlayer().getColoredName())));
                }else if(friends.size() == 2){
                    player.sendMessage(new TextComponent(Messages.PLAYER_ONLINE_TWO
                            .replace("[prefix]",Messages.PREFIX_FRIEND)
                            .replace("[player-1]",friends.get(0).getFriendPlayer().getColoredName())
                            .replace("[player-2]",friends.get(1).getFriendPlayer().getColoredName())));
                }else if(friends.size() == 3){
                    player.sendMessage(new TextComponent(Messages.PLAYER_ONLINE_THREE
                            .replace("[prefix]",Messages.PREFIX_FRIEND)
                            .replace("[player-1]",friends.get(0).getFriendPlayer().getColoredName())
                            .replace("[player-2]",friends.get(1).getFriendPlayer().getColoredName())
                            .replace("[player-3]",friends.get(2).getFriendPlayer().getColoredName())));
                }else{
                    player.sendMessage(new TextComponent(Messages.PLAYER_ONLINE_THREE
                            .replace("[prefix]",Messages.PREFIX_FRIEND)
                            .replace("[player-1]",friends.get(0).getFriendPlayer().getColoredName())
                            .replace("[player-2]",friends.get(1).getFriendPlayer().getColoredName())
                            .replace("[more]",""+(friends.size()-2))));
                }
                for(Friend friend : friends){
                    OnlineFriendPlayer online = friend.getOnlineFriendPlayer();
                    if(online != null && friend.getFriendPlayer().getSettings().isNotifyEnabled()) online.sendMessage(Messages.PLAYER_NOTIFY_ONLINE
                            .replace("[prefix]",Messages.PREFIX_FRIEND)
                            .replace("[player]",player.getColoredName()));
                }
            }
        });
    }
    @EventHandler
    public void onDisconnect(PlayerDisconnectEvent event){
        BungeeCord.getInstance().getScheduler().runAsync(BungeeCordFriendSystemBootstrap.getInstance(),()->{
            FriendPlayer player = FriendSystem.getInstance().getPlayerManager().getPlayer(event.getPlayer().getUniqueId());
            if(player == null) return;
            if(player.isInParty()){
                BungeeCord.getInstance().getPluginManager().dispatchCommand(event.getPlayer(),"party leave");
            }
            List<Friend> friends = player.getOnlineFriends();
            if(friends.isEmpty()) return;
            for(Friend friend : friends){
                OnlineFriendPlayer online = friend.getOnlineFriendPlayer();
                if(online != null && friend.getFriendPlayer().getSettings().isNotifyEnabled()) online.sendMessage(Messages.PLAYER_NOTIFY_OFFLINE
                        .replace("[prefix]",Messages.PREFIX_FRIEND)
                        .replace("[player]",player.getColoredName()));
            }
            if(FriendSystem.getInstance().getPlayerManager() instanceof BungeeCordPlayerManager){
                ((BungeeCordPlayerManager)FriendSystem.getInstance().getPlayerManager()).unregisterOnlinePlayer(event.getPlayer().getUniqueId());
            }
            player.updateInformations(event.getPlayer().getName(),getColor(player));
        });
    }
    @EventHandler
    public void onServerConnected(ServerConnectedEvent event){
        BungeeCord.getInstance().getScheduler().runAsync(BungeeCordFriendSystemBootstrap.getInstance(),()->{
            FriendPlayer player = FriendSystem.getInstance().getPlayerManager().getPlayer(event.getPlayer().getUniqueId());
            if(player != null){
                Party party = player.getParty();
                if(party != null && party.isLeader(player)){
                    party.sendMessage(Messages.PLAYER_PARTY_CONNECTED
                            .replace("[prefix]",Messages.PREFIX_PARTY)
                            .replace("[server]",event.getServer().getInfo().getName())
                            .replace("[player]",player.getColoredName()));
                    party.connect(event.getServer().getInfo().getName());
                }
            }
        });
    }
    private String getColor(FriendPlayer player){
        return "§8";
        /*

        returns colors

         */
    }
}
