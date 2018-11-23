package ch.dkrieger.friendsystem.lib.command.defaults.friend;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;

import java.util.LinkedList;
import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 20.11.18 18:49
 *
 */

public class FriendRespondCommand extends SubFriendCommand {

    public FriendRespondCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.friendrespond.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friendrespond.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friendrespond.permission"),
                "<message>",
                FriendSystem.getInstance().getConfig().getStringListValue("command.friendrespond.aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        if(args.length > 0){
            String message = "";
            for(int i = 0; i< args.length;i++) message += args[i]+" "+Messages.PLAYER_MESSAGE_COLOR;
            sender.executeCommand(FriendSystem.getInstance().getConfig().getStringValue("command.friend.name")
                    +" "+FriendSystem.getInstance().getConfig().getStringValue("command.friend.respond.name")
                    +" "+message);
        }else{
            sender.sendMessage(Messages.PLAYER_MESSAGE_NO
                    .replace("[prefix]",getPrefix()));
        }
    }

    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return new LinkedList<>();
    }
}