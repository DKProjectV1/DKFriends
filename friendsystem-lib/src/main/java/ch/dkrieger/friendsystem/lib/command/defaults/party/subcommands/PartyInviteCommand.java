package ch.dkrieger.friendsystem.lib.command.defaults.party.subcommands;

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.SubFriendCommand;
import ch.dkrieger.friendsystem.lib.party.Party;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;
import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;

import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 11:47
 *
 */

public class PartyInviteCommand extends SubFriendCommand {

    public PartyInviteCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.party.invite.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.invite.description"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.invite.permission"),
                "<player>",
                FriendSystem.getInstance().getConfig().getStringListValue("command.party.invite.aliases"));
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        if(args.length <= 0){
            getMainCommand().sendHelp(sender);
            return;
        }
        FriendPlayer player = sender.getAsFriendPlayer();
        if(player != null){
            FriendPlayer friend = FriendSystem.getInstance().getPlayerManager().getPlayer(args[0]);
            if(friend == null){
                sender.sendMessage(Messages.PLAYER_NOT_FOUND
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",args[0]));
                return;
            }
            OnlineFriendPlayer online = friend.getOnlinePlayer();
            if(online == null){
                sender.sendMessage(Messages.PLAYER_NOT_ONLINE
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            /*
            check can invite
             */
            Party party = player.getParty();
            if(party == null){
                party = FriendSystem.getInstance().getPartyManager().createParty(player);
            }
            else if(!party.isLeader(player) && !party.isModerator(player)){
                sender.sendMessage(Messages.PLAYER_PARTY_NOT_LEADER
                        .replace("[prefix]",getPrefix()));
                return;
            }
            if(party.isMember(friend)){
                sender.sendMessage(Messages.PLAYER_PARTY_ALREADY_MY
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            if(party.hasRequest(friend)){
                sender.sendMessage(Messages.PLAYER_PARTY_REQUEST_ALREADY
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            if(friend.getParty() != null){
                sender.sendMessage(Messages.PLAYER_PARTY_ALREADY_OTHER
                        .replace("[prefix]",getPrefix())
                        .replace("[player]",friend.getColoredName()));
                return;
            }
            party.addRequest(friend);
            party.sendMessage(Messages.PLAYER_PARTY_REQUEST_SENDED
                    .replace("[prefix]",getPrefix())
                    .replace("[player]",friend.getColoredName()));
            online.sendMessage(Messages.replaceAcceptDeny(Messages.PLAYER_PARTY_REQUEST_RECEIVED
                    .replace("[prefix]",getPrefix())
                    .replace("[player]",player.getColoredName()),
                    "/"+getMainCommand().getName()+" accept "+player.getName()
                    ,"/"+getMainCommand().getName()+" deny "+player.getName()));
        }
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        FriendPlayer player = sender.getAsFriendPlayer();
        String search = "";
        if(args.length >= 1) search = args[0].toLowerCase();
        return GeneralUtil.startsWith(search,player.getFriendNames());
    }
}
