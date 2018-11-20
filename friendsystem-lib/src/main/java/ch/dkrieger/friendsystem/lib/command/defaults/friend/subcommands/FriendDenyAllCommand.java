package ch.dkrieger.friendsystem.lib.command.defaults.friend.subcommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 17.11.18 20:30
 *
 */

public class FriendDenyAllCommand extends SubFriendCommand {

    public FriendDenyAllCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.friend.denyall.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.denyall.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.denyall.permission"),
                null,
                FriendSystem.getInstance().getConfig().getStringListValue("command.friend.denyall.aliases"));
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
                sender.sendMessage(Messages.PLAYER_REQUEST_DENIED_SELF
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friendPlayer.getColoredName()));
                friendPlayer.sendMessage(Messages.PLAYER_REQUEST_DENIED_OTHER
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",player.getColoredName()));
            }
            player.denyAll();
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
