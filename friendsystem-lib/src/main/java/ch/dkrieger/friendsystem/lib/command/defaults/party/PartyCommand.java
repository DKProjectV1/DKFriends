package ch.dkrieger.friendsystem.lib.command.defaults.party;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 11:28
 *
 */

import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommand;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.defaults.party.subCommands.PartyAcceptCommand;
import ch.dkrieger.friendsystem.lib.command.defaults.party.subCommands.PartyInviteCommand;

import java.util.List;

public class PartyCommand extends FriendCommand {

    public PartyCommand() {
        super("party","Party command",null,"/party ","p");
        setPrefix(Messages.PREFIX_PARTY);
        registerSubCommand(new PartyInviteCommand());
        registerSubCommand(new PartyAcceptCommand());
    }

    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        sendHelp(sender);
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }
}
