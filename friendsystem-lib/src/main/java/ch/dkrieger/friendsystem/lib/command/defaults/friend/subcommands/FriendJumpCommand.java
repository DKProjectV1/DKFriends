package ch.dkrieger.friendsystem.lib.command.defaults.friend.subcommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 17.11.18 20:34
 *
 */

public class FriendJumpCommand extends SubFriendCommand {

    public FriendJumpCommand() {
        super("jump");
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            FriendPlayer friend = FriendSystem.getInstance().getPlayerManager().getPlayer(args[0]);
            if(friend == null){
                sender.sendMessage(Messages.PLAYER_NOT_FOUND
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",args[0]));
                return;
            }
            if(!player.isFriend(friend)){
                sender.sendMessage(Messages.PLAYER_NOT_FRIENDS
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            //check can jump
            OnlineFriendPlayer online = friend.getOnlinePlayer();
            if(online == null){
                sender.sendMessage(Messages.PLAYER_NOT_ONLINE
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            if(online.getServer() == null){
                sender.sendMessage(Messages.SERVER_NOTFOUND
                        .replace("[prefix]",getPrefix()));
                return;
            }
            /*

            implement world for singe spigot

             */

            sender.sendMessage(Messages.PLAYER_JUMP
                    .replace("[prefix]",getPrefix())
                    .replace("[player]",friend.getColoredName())
                    .replace("[server]",online.getServer()));
            player.getOnlinePlayer().connect(online.getServer());
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
