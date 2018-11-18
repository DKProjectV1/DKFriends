package ch.dkrieger.friendsystem.lib.command.defaults.party.subCommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 14:52
 *
 */

public class PartyKickCommand extends SubFriendCommand {

    public PartyKickCommand() {
        super("kick");
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
            FriendPlayer friend = FriendSystem.getInstance().getPlayerManager().getPlayer(args[0]);
            if(friend == null){
                sender.sendMessage(Messages.PLAYER_NOT_FOUND
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",args[0]));
                return;
            }
            if(!party.isMember(friend)){
                sender.sendMessage(Messages.PLAYER_PARTY_NO_PARTY_OTHER
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",player.getColoredName()));
                return;
            }
            if(!party.isLeader(player)){
                if(party.isModerator(player)){
                    if(party.isLeader(friend) || party.isModerator(friend)){
                        sender.sendMessage(Messages.PLAYER_PARTY_NOT_ALLOWED_KICK
                                .replace("[prefix]",getPrefix())
                                .replace("[player]",friend.getColoredName()));
                        return;
                    }
                }else{
                    sender.sendMessage(Messages.PLAYER_PARTY_NOT_ALLOWED_KICK
                            .replace("[prefix]",getPrefix())
                            .replace("[player]",friend.getColoredName()));
                    return;
                }
            }
            party.sendMessage(Messages.PLAYER_PARTY_KICKED
                    .replace("[prefix]",getPrefix())
                    .replace("[player]",friend.getColoredName())
                    .replace("[kicker]",player.getColoredName()));
            party.removeMember(friend);
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
