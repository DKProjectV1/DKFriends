package ch.dkrieger.friendsystem.lib.command.defaults.party.subCommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.party.Party;
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
            Party party = FriendSystem.getInstance().getPartyManager().getParty(player);
            if(party == null){
                sender.sendMessage(Messages.PLAYER_PARTY_NO_PARTY_SELF.replace("[prefix]",getPrefix()));
                return;
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
