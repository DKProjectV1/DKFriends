package ch.dkrieger.friendsystem.lib.command.defaults.party.subcommands;

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
        super(FriendSystem.getInstance().getConfig().getStringValue("command.party.jump.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.jump.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.jump.permission"),
                null,
                FriendSystem.getInstance().getConfig().getStringListValue("command.party.jump.aliases"));
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
                    .replace("[server]",leader.getServer())
                    .replace("[player]",player.getColoredName()));
            online.connect(leader.getServer());

        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
