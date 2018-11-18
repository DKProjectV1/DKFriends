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
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 14:49
 *
 */

public class PartyJumpCommand extends SubFriendCommand {

    public PartyJumpCommand() {
        super("jump");
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
            OnlineFriendPlayer leader = party.getLeader().getOnlinePlayer();
            if(leader == null) return;
            if(leader.getServer() == null){
                sender.sendMessage(Messages.SERVER_NOTFOUND.replace("[prefix]",getPrefix()));
                return;
            }
            OnlineFriendPlayer online = player.getOnlinePlayer();
            if(online == null) return;
            if(online.getServer().equalsIgnoreCase(leader.getServer())){
                sender.sendMessage(Messages.SERVER_ALREADY.replace("[prefix]",getPrefix()));
                return;
            }
            sender.sendMessage(Messages.PLAYER_PARTY_JUMPED
                    .replace("[prefix]",getPrefix())
                    .replace("[player]",player.getColoredName()));
            online.connect(leader.getServer());

        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
