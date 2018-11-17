package ch.dkrieger.friendsystem.lib.command.defaults.friend.subcommands;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 20:41
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.FriendPlayerManager;

import java.util.List;

public class FriendAddCommand extends SubFriendCommand {

    public FriendAddCommand() {
        super("add");
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            if(player.getFriends().size() >= player.getMaxFriends()){
                sender.sendMessage(Messages.PLAYER_MAX_FRIENDS_REACHED.replace("[max]",""+player.getMaxFriends()));
                return;
            }
            FriendPlayer friend = FriendSystem.getInstance().getPlayerManager().getPlayer(args[0]);
            if(friend == null){
                sender.sendMessage(Messages.PLAYER_NOT_FOUND);
                return;
            }
            //check can add
            if(friend.getFriends().size() >= friend.getMaxFriends()){
                sender.sendMessage(Messages.PLAYER_MAX_FRIENDS_REACHED_FRIEND);
                return;
            }
            if(player.hasRequest(friend)){
                sender.sendMessage(Messages.PLAYER_HAS_ALREADY_REQUEST);
                return;
            }
            //check request by friend
            if(player.isFriend(friend)){
                sender.sendMessage(Messages.PLAYER_ALREADY_FRIENDS);
                return;
            }
            player.addRequest(friend);
            sender.sendMessage(Messages.PLAYER_HAS_ALREADY_REQUEST);
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
