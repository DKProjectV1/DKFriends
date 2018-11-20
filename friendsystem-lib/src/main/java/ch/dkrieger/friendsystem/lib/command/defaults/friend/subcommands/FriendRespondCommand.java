package ch.dkrieger.friendsystem.lib.command.defaults.friend.subcommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.LinkedList;
import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 20.11.18 18:39
 *
 */

public class FriendRespondCommand extends SubFriendCommand {

    public FriendRespondCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.friend.respond.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.respond.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.respond.permission"),
                "<message>",
                FriendSystem.getInstance().getConfig().getStringListValue("command.friend.respond.aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            if(player.getOnlinePlayer().getLastMessenger() == null){
                sender.sendMessage(Messages.PLAYER_MESSAGE_NO_RESPOND
                        .replace("[prefix]",getPrefix()));
                return;
            }
            String message = "";
            for(int i = 0; i< args.length;i++) message = message + " "+Messages.PLAYER_MESSAGE_COLOR + args[i];
            sender.executeCommand(getMainCommand().getName()
                    +" "+FriendSystem.getInstance().getConfig().getStringValue("command.friend.message.name")
                    +" "+player.getOnlinePlayer().getLastMessenger()+" "+message);
        }
    }

    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return new LinkedList<>();
    }
}