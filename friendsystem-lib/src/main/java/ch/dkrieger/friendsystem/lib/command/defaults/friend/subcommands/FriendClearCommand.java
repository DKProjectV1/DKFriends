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
 *  * Copyright (c) 2018 Davide Wietlisbach on 17.11.18 20:32
 *
 */

public class FriendClearCommand extends SubFriendCommand {

    public FriendClearCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.friend.clear.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.clear.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.clear.permission"),
                null,
                FriendSystem.getInstance().getConfig().getStringListValue("command.friend.clear.aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            List<Friend> friends = player.getFriends();
            if(friends.size() < 1){
                sender.sendMessage(Messages.PLAYER_NO_REQUESTS
                        .replace("[prefix]",getPrefix()));
                return;
            }
            for(Friend friend : friends){
                FriendPlayer friendPlayer = friend.getFriendPlayer();
                sender.sendMessage(Messages.PLAYER_REMOVED_SELF
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friendPlayer.getColoredName()));
                friendPlayer.sendMessage(Messages.PLAYER_REMOVED_OTHER
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",player.getColoredName()));
            }
            player.clearFriends();
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
