package ch.dkrieger.friendsystem.lib.command.defaults.party.subcommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 16:15
 *
 */

public class PartyPublicListCommand extends SubFriendCommand {

    public PartyPublicListCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.party.publiclist.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.publiclist.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.publiclist.permission"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.publiclist.usage"),
                FriendSystem.getInstance().getConfig().getStringListValue("command.party.publiclist.aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            List<Party> parties = FriendSystem.getInstance().getPartyManager().getPublicParties();
            if(parties.size() < 1){
                sender.sendMessage(Messages.PLAYER_PARTY_PUBLIC_NO
                        .replace("[prefix]",getPrefix()));
                return;
            }
            int page = 1;
            if(args.length > 0) if(GeneralUtil.isNumber(args[0])) page = Integer.valueOf(args[0]);
            int maxPages = GeneralUtil.getMaxPages(8,parties);
            if(page > maxPages){
                sender.sendMessage(Messages.PAGENOTFOUND
                        .replace("[prefix]",getPrefix()));
                return;
            }
            sender.sendMessage(Messages.PLAYER_PARTY_PUBLIC_LIST_HEADER
                    .replace("[prefix]",getPrefix())
                    .replace("[page]",""+page)
                    .replace("[maxPages]",""+maxPages));
            int from = 1;
            if(page > 1) from = 8 * (page - 1) + 1;
            int to = 8 * page;
            for(int h = from; h <= to; h++) {
                if(h > parties.size()) break;
                Party party = parties.get(h - 1);
                FriendPlayer leader = party.getLeader().getPlayer();

                TextComponent component = new TextComponent(Messages.PLAYER_PARTY_PUBLIC_LIST_LIST
                        .replace("[prefix]",getPrefix())
                        .replace("[leader]",leader.getName())
                        .replace("[amount]",""+party.getMembers().size())
                        .replace("[created]","created"));
                component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND
                        ,"/"+getMainCommand().getName()+" join "+leader.getName()));
                player.sendMessage(component);

            }
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
