package ch.dkrieger.friendsystem.lib.command.defaults.party.subCommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.party.PartyMember;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 14:06
 *
 */

public class PartyInfoCommand extends SubFriendCommand {

    public PartyInfoCommand() {
        super("list");
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            Party party = FriendSystem.getInstance().getPartyManager().getParty(player);
            if(party == null){
                sender.sendMessage(Messages.PLAYER_PARTY_NO_PARTY_SELF.replace("[prefix]",getPrefix()));
                return;
            }
            sender.sendMessage(Messages.PLAYER_PARTY_INFO_HEADER
                    .replace("[prefix]",getPrefix())
                    .replace("[leader]",party.getLeader().getPlayer().getColoredName()));
            sender.sendMessage(Messages.PLAYER_PARTY_INFO_CREATED
                    .replace("[prefix]",getPrefix())
                    .replace("[time]","time"));
            sender.sendMessage(Messages.PLAYER_PARTY_INFO_PUBLIC_TEXT
                    .replace("[prefix]",getPrefix())
                    .replace("[enabled]",(party.isPublic()?Messages.PLAYER_PARTY_INFO_PUBLIC_ENABLED:Messages.PLAYER_PARTY_INFO_PUBLIC_DISABLED)));
            sender.sendMessage(Messages.PLAYER_PARTY_INFO_MEMBER_HEADER
                    .replace("[prefix]",getPrefix())
                    .replace("[size]",""+party.getMembers().size()));
            for(PartyMember member : party.getSortedMembers()){
                FriendPlayer friend = member.getPlayer();
                OnlineFriendPlayer online = member.getOnlinePlayer();
                if(friend != null && online != null){
                    String symbol = Messages.PLAYER_PARTY_INFO_MEMBER_SYMBOL_NORMAL;
                    if(member.isLeader()) symbol = Messages.PLAYER_PARTY_INFO_MEMBER_SYMBOL_LEADER;
                    else if(member.isModerator()) symbol = Messages.PLAYER_PARTY_INFO_MEMBER_SYMBOL_MODERATOR;
                    sender.sendMessage(Messages.PLAYER_PARTY_INFO_MEMBER_LIST
                            .replace("[prefix]",getPrefix())
                            .replace("[player]",friend.getColoredName())
                            .replace("[joined]",""+member.getJoined())
                            .replace("[server]",""+online.getServer())
                            .replace("[symbol]",symbol));
                }
            }
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
