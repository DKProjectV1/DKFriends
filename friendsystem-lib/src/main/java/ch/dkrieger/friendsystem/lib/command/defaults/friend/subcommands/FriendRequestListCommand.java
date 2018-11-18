package ch.dkrieger.friendsystem.lib.command.defaults.friend.subcommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 17.11.18 19:57
 *
 */

public class FriendRequestListCommand extends SubFriendCommand {

    public FriendRequestListCommand(String name) {
        super(name,
                FriendSystem.getInstance().getConfig().getStringValue("command.friend."+name+".description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend."+name+".permission"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend."+name+".usage"),
                FriendSystem.getInstance().getConfig().getStringListValue("command.friend."+name+".aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            int page = 1;
            if(args.length > 0) if(GeneralUtil.isNumber(args[0])) page = Integer.valueOf(args[0]);

            List<Friend> requests = player.getRequests();
            if(requests.size() < 1){
                sender.sendMessage(Messages.PLAYER_NO_REQUESTS
                        .replace("[prefix]",getPrefix()));
                return;
            }
            int maxPages = GeneralUtil.getMaxPages(8,requests);
            if(page > maxPages){
                sender.sendMessage(Messages.PAGENOTFOUND
                        .replace("[prefix]",getPrefix()));
                return;
            }
            sender.sendMessage(Messages.PLAYER_REQUEST_LIST_HEADER
                    .replace("[prefix]",getPrefix())
                    .replace("[page]",""+page)
                    .replace("[maxPages]",""+maxPages));
            int from = 1;
            if(page > 1) from = 8 * (page - 1) + 1;
            int to = 8 * page;
            for(int h = from; h <= to; h++) {
                if(h > requests.size()) break;
                Friend request = requests.get(h - 1);
                FriendPlayer requestPlayer = request.getFriendPlayer();

                sender.sendMessage(Messages.replaceAcceptDeny(
                        Messages.PLAYER_REQUEST_LIST_LIST
                                .replace("[prefix]",getPrefix())
                                .replace("[player]",requestPlayer.getColoredName())
                        ,"/"+getMainCommand().getName()+" accept "+requestPlayer.getName()
                        ,"/"+getMainCommand().getName()+" deny "+requestPlayer.getName()));

            }
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
