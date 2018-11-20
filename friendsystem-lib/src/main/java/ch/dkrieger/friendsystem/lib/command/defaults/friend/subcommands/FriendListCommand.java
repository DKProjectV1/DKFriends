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
 *  * Copyright (c) 2018 Davide Wietlisbach on 17.11.18 18:35
 *
 */

public class FriendListCommand extends SubFriendCommand {

    public FriendListCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.friend.list.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.list.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.friend.list.permission"),
                null,
                FriendSystem.getInstance().getConfig().getStringListValue("command.friend.list.aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            int page = 1;
            if(args.length > 0) if(GeneralUtil.isNumber(args[0])) page = Integer.valueOf(args[0]);

            if(player.getFriends().size() < 1){
                sender.sendMessage(Messages.PLAYER_NO_FRIENDS
                        .replace("[prefix]",getPrefix()));
                return;
            }
            List<Friend> friends = player.getSortedFriends();
            int maxPages = GeneralUtil.getMaxPages(8,friends);
            if(page > maxPages){
                sender.sendMessage(Messages.PAGENOTFOUND
                        .replace("[prefix]",getPrefix()));
                return;
            }
            sender.sendMessage(Messages.PLAYER_LIST_HEADER
                    .replace("[prefix]",getPrefix())
                    .replace("[page]",""+page)
                    .replace("[maxPages]",""+maxPages));
            int from = 1;
            if(page > 1) from = 8 * (page - 1) + 1;
            int to = 8 * page;
            for(int h = from; h <= to; h++) {
                if(h > friends.size()) break;
                Friend friend = friends.get(h - 1);

                FriendPlayer friendPlayer = friend.getFriendPlayer();
                OnlineFriendPlayer online = friend.getOnlineFriendPlayer();
                String text;
                if(online != null) text = Messages.PLAYER_LIST_ONLINE.replace("[server]",online.getServer());
                else text = Messages.PLAYER_LIST_OFFLINE.replace("[lastOnline]",""+friendPlayer.getLastLogin());
                if(friend.isFavorite()) text = text.replace("[symbol]",Messages.PLAYER_LIST_SYMBOL_FAVORITE);
                else text = text.replace("[symbol]",Messages.PLAYER_LIST_SYMBOL_NORMAL);
                text = text.replace("[player]",friendPlayer.getColoredName())
                        .replace("[friendsSince]",""+friend.getTimeStamp())
                        .replace("[prefix]",getPrefix());
                TextComponent component = new TextComponent(text);
                if(online != null) component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND
                        ,"/"+getMainCommand().getName()+" jump "+friendPlayer.getName()));
                player.sendMessage(component);

            }
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
