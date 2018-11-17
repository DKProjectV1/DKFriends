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
 *  * Copyright (c) 2018 Davide Wietlisbach on 17.11.18 20:59
 *
 */

public class FriendMessageCommand extends SubFriendCommand {

    public FriendMessageCommand() {
        super("messages");
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
            //check can send a message
            OnlineFriendPlayer online = player.getOnlinePlayer();
            if(online == null){
                sender.sendMessage(Messages.PLAYER_NOT_ONLINE
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            String message = "";
            for(int i = 1; i< args.length;i++){
                if(i > 2 ) message = message + " §e" + args[i];
                else  message = args[i];
            }
            message = getPrefix()+Messages.PLAYER_MESSAGE_FORMAT.replace("[message]",message)
                    .replace("[sender]",player.getColoredName())
                    .replace("[receiver]",friend.getColoredName());
            sender.sendMessage(message);
            player.sendMessage(message);
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
