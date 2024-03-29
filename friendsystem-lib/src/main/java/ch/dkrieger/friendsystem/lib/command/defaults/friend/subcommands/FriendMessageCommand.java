package ch.dkrieger.friendsystem.lib.command.defaults.friend.subcommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.LinkedList;
import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 17.11.18 20:59
 *
 */

public class FriendMessageCommand extends SubFriendCommand {

    public FriendMessageCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.friend.message.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.message.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.message.permission"),
                "<player> <message>",
                FriendSystem.getInstance().getConfig().getStringListValue("command.friend.message.aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        if(args.length <= 0){
            getMainCommand().sendHelp(sender);
            return;
        }
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            if(args.length <= 2){
                sender.sendMessage(Messages.PLAYER_MESSAGE_NO
                        .replace("[prefix]",getPrefix()));
                return;
            }
            if(!player.getSettings().isMessageEnabled()){
                sender.sendMessage(Messages.PLAYER_MESSAGE_NOT_ENABLED
                        .replace("[prefix]",getPrefix()));
                return;
            }
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
            if(!friend.getSettings().isMessageEnabled()){
                sender.sendMessage(Messages.PLAYER_MESSAGE_NOT_ALLOWED
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            OnlineFriendPlayer online = friend.getOnlinePlayer();
            if(online == null){
                sender.sendMessage(Messages.PLAYER_NOT_ONLINE
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            String message = "";
            for(int i = 1; i< args.length;i++) message +=  args[i]+" "+Messages.PLAYER_MESSAGE_COLOR;
            message = Messages.PLAYER_MESSAGE_FORMAT
                    .replace("[message]",message)
                    .replace("[prefix]",getPrefix())
                    .replace("[sender]",player.getColoredName())
                    .replace("[receiver]",friend.getColoredName());
            sender.sendMessage(message);
            online.sendPrivateMessage(sender.getName(),new TextComponent(message));
        }
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