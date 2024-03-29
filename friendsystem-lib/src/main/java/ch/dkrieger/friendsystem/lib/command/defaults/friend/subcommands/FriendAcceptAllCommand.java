package ch.dkrieger.friendsystem.lib.command.defaults.friend.subcommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import com.google.common.collect.ImmutableSet;

import java.util.*;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 17.11.18 20:24
 *
 */

public class FriendAcceptAllCommand extends SubFriendCommand {

    public FriendAcceptAllCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.friend.acceptall.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.acceptall.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.acceptall.permission"),
                null,
                FriendSystem.getInstance().getConfig().getStringListValue("command.friend.acceptall.aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            List<Friend> requests = player.getRequests();
            if(requests.size() < 1){
                sender.sendMessage(Messages.PLAYER_NO_REQUESTS
                        .replace("[prefix]",getPrefix()));
                return;
            }
            for(Friend friend : requests){
                FriendPlayer friendPlayer = friend.getFriendPlayer();
                sender.sendMessage(Messages.PLAYER_REQUEST_ACCEPTED_SELF
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friendPlayer.getColoredName()));
                friendPlayer.sendMessage(Messages.PLAYER_REQUEST_ACCEPTED_OTHER
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",player.getColoredName()));
            }
            player.acceptAll();
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
