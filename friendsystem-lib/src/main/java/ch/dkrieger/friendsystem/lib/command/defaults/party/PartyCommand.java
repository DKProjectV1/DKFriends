package ch.dkrieger.friendsystem.lib.command.defaults.party;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 11:28
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommand;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.defaults.party.subCommands.*;

import java.util.List;

public class PartyCommand extends FriendCommand {

    public PartyCommand() {
        super(FriendSystem.getInstance().getConfig().getStringValue("command.party.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.name"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.permission"),
                FriendSystem.getInstance().getConfig().getStringValue("command.party.usage"),
                FriendSystem.getInstance().getConfig().getStringListValue("command.party.aliases"));
        setPrefix(Messages.PREFIX_PARTY);
        registerSubCommand(new PartyInviteCommand());
        registerSubCommand(new PartyAcceptCommand());
        registerSubCommand(new PartyDenyCommand());
        registerSubCommand(new PartyLeaveCommand());
        registerSubCommand(new PartyInfoCommand());
        registerSubCommand(new PartyJumpCommand());
        registerSubCommand(new PartyKickCommand());
        registerSubCommand(new PartyMessageCommand());
        registerSubCommand(new PartyJoinCommand());
        registerSubCommand(new PartyPublicListCommand());
        registerSubCommand(new PartyRandomCommand());
        registerSubCommand(new PartyPromoteCommand());
        registerSubCommand(new PartyDemoteCommand());
        registerSubCommand(new PartyPublicCommand());
        registerSubCommand(new PartyPrivateCommand());
        registerSubCommand(new PartyBanCommand());
        registerSubCommand(new PartyUnbanCommand());
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
