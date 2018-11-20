package ch.dkrieger.friendsystem.lib.command.defaults.party;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 11:28
 *
 */

import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.command.FriendCommand;
import ch.dkrieger.friendsystem.lib.command.FriendCommandSender;
import ch.dkrieger.friendsystem.lib.command.defaults.party.subcommands.*;
import ch.dkrieger.friendsystem.lib.command.defaults.party.subcommands.PartyMessageCommand;
import ch.dkrieger.friendsystem.lib.config.Config;

import java.util.List;

public class PartyCommand extends FriendCommand {

    private static PartyCommand instance;

    public PartyCommand(Config config) {
        super(config.getStringValue("command.party.name"),
                config.getStringValue("command.party.name"),
                config.getStringValue("command.party.permission"),
                config.getStringValue("command.party.usage"),
                config.getStringListValue("command.party.aliases"));
        setPrefix(Messages.PREFIX_PARTY);
        instance = this;
        if(config.getBooleanValue("command.party.info.enabled")) registerSubCommand(new PartyInfoCommand());
        if(config.getBooleanValue("command.party.invite.enabled")) registerSubCommand(new PartyInviteCommand());
        if(config.getBooleanValue("command.party.accept.enabled")) registerSubCommand(new PartyAcceptCommand());
        if(config.getBooleanValue("command.party.deny.enabled")) registerSubCommand(new PartyDenyCommand());
        registerSubCommand(new PartyLeaveCommand());
        if(config.getBooleanValue("command.party.jump.enabled")) registerSubCommand(new PartyJumpCommand());
        if(config.getBooleanValue("command.party.message.enabled"))  registerSubCommand(new PartyMessageCommand());
        if(config.getBooleanValue("command.party.join.enabled")) registerSubCommand(new PartyJoinCommand());
        if(config.getBooleanValue("command.party.publiclist.enabled")) registerSubCommand(new PartyPublicListCommand());
        if(config.getBooleanValue("command.party.random.enabled"))  registerSubCommand(new PartyRandomCommand());
        if(config.getBooleanValue("command.party.promote.enabled"))  registerSubCommand(new PartyPromoteCommand());
        if(config.getBooleanValue("command.party.demote.enabled")) registerSubCommand(new PartyDemoteCommand());
        if(config.getBooleanValue("command.party.public.enabled")) registerSubCommand(new PartyPublicCommand());
        if(config.getBooleanValue("command.party.private.enabled"))  registerSubCommand(new PartyPrivateCommand());
        if(config.getBooleanValue("command.party.kick.enabled")) registerSubCommand(new PartyKickCommand());
        if(config.getBooleanValue("command.party.ban.enabled"))  registerSubCommand(new PartyBanCommand());
        if(config.getBooleanValue("command.party.unban.enabled")) registerSubCommand(new PartyUnbanCommand());
    }
    @Override
    public void onExecute(FriendCommandSender sender, String[] args) {
        sendHelp(sender,args);
    }
    @Override
    public List<String> onTabComplete(FriendCommandSender sender, String[] args) {
        return null;
    }

}
