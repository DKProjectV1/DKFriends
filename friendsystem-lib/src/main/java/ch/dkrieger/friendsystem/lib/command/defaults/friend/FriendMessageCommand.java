package ch.dkrieger.friendsystem.lib.command.defaults.friend;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommand;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 20.11.18 18:33
 *
 */

public class FriendMessageCommand extends FriendCommand {

    public FriendMessageCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.friendmessage.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friendmessage.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friendmessage.permission"),
                "<player> <message>",
                FriendSystem.getInstance().getConfig().getStringListValue("command.friendmessage.aliases"));
        setPrefix(Messages.PREFIX_PARTY);
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        if(args.length <= 1){
            sender.sendMessage(Messages.PLAYER_MESSAGE_NO
                    .replace("[prefix]",getPrefix()));
            return;
        }
        String message = "";
        for(int i = 1; i< args.length;i++)  message += args[i]+" "+Messages.PLAYER_MESSAGE_COLOR;
        sender.executeCommand(FriendSystem.getInstance().getConfig().getStringValue("command.friend.name")
                +" "+FriendSystem.getInstance().getConfig().getStringValue("command.friend.message.name")
                +" "+args[0]+" "+message);
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player == null) return null;
        String search = "";
        if(args.length >= 1) search = args[0].toLowerCase();
        return GeneralUtil.startsWith(search,player.getFriendNames());
    }
}
