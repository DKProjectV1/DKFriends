package ch.dkrieger.friendsystem.lib.command.defaults.friend.subcommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 17.11.18 16:58
 *
 */

public class FriendDenyCommand extends SubFriendCommand {

    public FriendDenyCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.friend.deny.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.deny.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.deny.permission"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.deny.usage"),
                FriendSystem.getInstance().getConfig().getStringListValue("command.friend.deny.aliases"));
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
            if(!player.hasRequest(friend)){
                sender.sendMessage(Messages.PLAYER_NOT_REQUEST
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            player.removeRequest(friend);
            sender.sendMessage(Messages.PLAYER_REQUEST_DENIED_SELF
                    .replace("[prefix]",getPrefix())
                    .replace("[player]",friend.getColoredName()));
            friend.sendMessage(Messages.PLAYER_REQUEST_DENIED_OTHER
                    .replace("[prefix]",getPrefix())
                    .replace("[player]",player.getColoredName()));
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
