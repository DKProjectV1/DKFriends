package ch.dkrieger.friendsystem.lib.command.defaults.party.subcommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.party.PartyMember;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 14:00
 *
 */

public class PartyLeaveCommand extends SubFriendCommand {

    public PartyLeaveCommand() {
        super("leave");
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            Party party = player.getParty();
            if(party == null){
                sender.sendMessage(Messages.PLAYER_PARTY_NO_PARTY_SELF.replace("[prefix]",getPrefix()));
                return;
            }
            if(party.isLeader(player)){
                if(party.getMembers().size() > 2){
                    PartyMember member = null;
                    if(party.getModerators().size() > 0) member = party.getModerators().get(0);
                    else member = party.getNormalMembers().get(0);
                    party.setLeader(member.getUUID());
                    party.sendMessage(Messages.PLAYER_PARTY_NEW_LEADER
                            .replace("[prefix]",getPrefix())
                            .replace("[player]",member.getPlayer().getColoredName()));
                }else{
                    party.sendMessage(Messages.PLAYER_PARTY_DELETED.replace("[prefix]",getPrefix()));
                    FriendSystem.getInstance().getPartyManager().delteParty(party);
                    return;
                }
            }
            party.sendMessage(Messages.PLAYER_PARTY_LEAVED
                    .replace("[prefix]",getPrefix())
                    .replace("[player]",player.getColoredName()));
            party.removeMember(player);
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
