package ch.dkrieger.friendsystem.spigot.listener;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import ch.dkrieger.friendsystem.spigot.SpigotFriendSystemBootstrap;
import ch.dkrieger.friendsystem.spigot.api.Reflection;
import com.mojang.authlib.GameProfile;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.lang.reflect.Method;
import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 24.11.18 14:28
 *
 */

public class PlayerListener implements Listener {

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        if(Bukkit.getOnlineMode() && !(event.getPlayer().isOnline())) return;
        FriendPlayer player = null;
        try{
            player = FriendSystem.getInstance().getPlayerManager().getPlayerSave(event.getPlayer().getUniqueId());
        }catch (Exception exception){
            try{
                player = FriendSystem.getInstance().getPlayerManager().getPlayerSave(event.getPlayer().getUniqueId());
            }catch (Exception exception2){
                event.disallow(PlayerLoginEvent.Result.KICK_BANNED,Messages.ERROR.replace("[prefix]",Messages.PREFIX_FRIEND));
                exception.printStackTrace();
                return;
            }
        }
        if(player == null){
            player = FriendSystem.getInstance().getPlayerManager().createPlayer(event.getPlayer().getUniqueId()
                    ,event.getPlayer().getName(),"§8",getGameProfile(event.getPlayer()).toString());
            System.out.println(getGameProfile(event.getPlayer()));
        }else {
            FriendPlayer finalPlayer = player;
            Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(),()->{
                if(FriendSystem.getInstance().getConfig().isBungeeCord()) return;
                else finalPlayer.updateGameProfile(getGameProfile(event.getPlayer()).toString());
                if(FriendSystem.getInstance().getConfig().getBooleanValue("join.requestinfo.enabled")){
                    List<Friend> requests = finalPlayer.getRequests();
                    if(requests.size() > 0){
                        String design = Messages.PLAYER_REQUEST_OPEN_PLURAL;
                        if(requests.size() == 1) design = Messages.PLAYER_REQUEST_OPEN_SINGULAR;
                        TextComponent message = new TextComponent(Messages.PLAYER_REQUEST_OPEN_MESSAGE
                                .replace("[prefix]",Messages.PREFIX_FRIEND)
                                .replace("[amount]",""+requests.size())
                                .replace("[design]",design));
                        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/friend requests"));
                        finalPlayer.sendMessage(message);
                    }
                }
                if(FriendSystem.getInstance().getConfig().getBooleanValue("join.friendinfo.enabled")){
                    List<Friend> friends = finalPlayer.getOnlineFriends();

                    if(friends.isEmpty()) return;
                    if(friends.size() == 1){
                        finalPlayer.sendMessage(new TextComponent(Messages.PLAYER_ONLINE_ONE
                                .replace("[prefix]",Messages.PREFIX_FRIEND)
                                .replace("[player-1]",friends.get(0).getFriendPlayer().getColoredName())));
                    }else if(friends.size() == 2){
                        finalPlayer.sendMessage(new TextComponent(Messages.PLAYER_ONLINE_TWO
                                .replace("[prefix]",Messages.PREFIX_FRIEND)
                                .replace("[player-1]",friends.get(0).getFriendPlayer().getColoredName())
                                .replace("[player-2]",friends.get(1).getFriendPlayer().getColoredName())));
                    }else if(friends.size() == 3){
                        finalPlayer.sendMessage(new TextComponent(Messages.PLAYER_ONLINE_THREE
                                .replace("[prefix]",Messages.PREFIX_FRIEND)
                                .replace("[player-1]",friends.get(0).getFriendPlayer().getColoredName())
                                .replace("[player-2]",friends.get(1).getFriendPlayer().getColoredName())
                                .replace("[player-3]",friends.get(2).getFriendPlayer().getColoredName())));
                    }else{
                        finalPlayer.sendMessage(new TextComponent(Messages.PLAYER_ONLINE_THREE
                                .replace("[prefix]",Messages.PREFIX_FRIEND)
                                .replace("[player-1]",friends.get(0).getFriendPlayer().getColoredName())
                                .replace("[player-2]",friends.get(1).getFriendPlayer().getColoredName())
                                .replace("[more]",""+(friends.size()-2))));
                    }
                    for(Friend friend : friends){
                        OnlineFriendPlayer online = friend.getOnlineFriendPlayer();
                        if(online != null && friend.getFriendPlayer().getSettings().isNotifyEnabled()) online.sendMessage(Messages.PLAYER_NOTIFY_ONLINE
                                .replace("[prefix]",Messages.PREFIX_FRIEND)
                                .replace("[player]",finalPlayer.getColoredName()));
                    }
                }
                finalPlayer.updateInformations(event.getPlayer().getName(),getColor(finalPlayer),getGameProfile(event.getPlayer()).toString());
            });
        }
    }
    @EventHandler
    public void onDisconnect(PlayerQuitEvent event){
        if(FriendSystem.getInstance().getConfig().isBungeeCord()) return;
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(),()->{
            FriendPlayer player = FriendSystem.getInstance().getPlayerManager().getPlayer(event.getPlayer().getUniqueId());
            if(player == null) return;
            if(player.isInParty()) Bukkit.dispatchCommand(event.getPlayer(),"party leave");
            List<Friend> friends = player.getOnlineFriends();
            if(friends.isEmpty()) return;
            for(Friend friend : friends){
                OnlineFriendPlayer online = friend.getOnlineFriendPlayer();
                if(online != null && friend.getFriendPlayer().getSettings().isNotifyEnabled()) online.sendMessage(Messages.PLAYER_NOTIFY_OFFLINE
                        .replace("[prefix]",Messages.PREFIX_FRIEND)
                        .replace("[player]",player.getColoredName()));
            }
            player.updateInformations(event.getPlayer().getName(),getColor(player));
        });
    }
    @EventHandler
    public void onWolrdChange(PlayerChangedWorldEvent event){
        if(FriendSystem.getInstance().getConfig().isBungeeCord()) return;
        Bukkit.getScheduler().runTaskAsynchronously(SpigotFriendSystemBootstrap.getInstance(),()->{
            FriendPlayer player = FriendSystem.getInstance().getPlayerManager().getPlayer(event.getPlayer().getUniqueId());
            if(player != null){
                Party party = player.getParty();
                if(party != null && party.isLeader(player)){
                    party.sendMessage(Messages.PLAYER_PARTY_CONNECTED
                            .replace("[prefix]",Messages.PREFIX_PARTY)
                            .replace("[server]",event.getPlayer().getWorld().getName())
                            .replace("[player]",player.getColoredName()));
                    party.connect(event.getPlayer().getWorld().getName());
                }
            }
        });
    }
    private GameProfile getGameProfile(Player player){
        try{
            Class<?> craftPlayerClass = Reflection.getCraftBukkitClass("entity.CraftPlayer");

            Method getHandle = craftPlayerClass.getMethod("getProfile");
            return (GameProfile)getHandle.invoke(player);
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return null;
    }
    private String getColor(FriendPlayer friendPlayer){
        return "§8";
        /*

        returns colors

         */
    }
}
