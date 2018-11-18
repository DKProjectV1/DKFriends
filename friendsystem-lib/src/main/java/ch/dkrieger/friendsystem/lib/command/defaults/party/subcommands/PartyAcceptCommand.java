package ch.dkrieger.friendsystem.lib.command.defaults.party.subcommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 12:43
 *
 */

public class PartyAcceptCommand extends SubFriendCommand {

    public PartyAcceptCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.party.accept.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.accept.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.accept.permission"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.accept.usage"),
                FriendSystem.getInstance().getConfig().getStringListValue("command.party.accept.aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            FriendPlayer friend = FriendSystem.getInstance().getPlayerManager().getPlayer(args[0]);
            if(friend == null){
                sender.sendMessage(Messages.PLAYER_NOT_FOUND
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",args[0]));
                return;
            }
            Party party = friend.getParty();
            if(party == null){
                sender.sendMessage(Messages.PLAYER_PARTY_NOT_PARTY
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            if(!(party.hasRequest(player)) && !(party.isPublic())){
                sender.sendMessage(Messages.PLAYER_PARTY_REQUEST_NOT
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            if(party.isBanned(player)){
                sender.sendMessage(Messages.PLAYER_PARTY_NOT_ALLOWED_JOIN
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            if(player.getParty() != null){
                sender.sendMessage(Messages.PLAYER_PARTY_ALREADY_OWN
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            party.addMember(player);
            party.sendMessage(Messages.PLAYER_PARTY_JOINED
                    .replace("[prefix]",getPrefix())
                    .replace("[player]",player.getColoredName()));
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
