package ch.dkrieger.friendsystem.lib.command;

import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:47
 *
 */

public abstract class FriendCommand {

    private String name, prefix, description, permission, usage;
    private List<String> aliases;
    private List<SubFriendCommand> subCommands;

    public FriendCommand(String name) {
        this(name,"none");
    }
    public FriendCommand(String name, String description) {
        this(name,description,null);
    }
    public FriendCommand(String name, String description, String permission) {
        this(name,description,permission,name);
    }
    public FriendCommand(String name, String description, String permission, String usage, String... aliases) {
        this(name, description, permission, usage, Arrays.asList(aliases));
    }
    public FriendCommand(String name, String description, String permission, String usage, List<String> aliases) {
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.usage = usage;
        this.aliases = aliases;
        this.subCommands = new LinkedList<>();
    }
    public String getName() {
        return this.name;
    }
    public String getPrefix() {
        return this.prefix;
    }
    public String getDescription() {
        return this.description;
    }
    public String getPermission() {
        return this.permission;
    }
    public String getUsage() {
        return usage;
    }
    public boolean hasUsage() {
        return usage != null;
    }
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
    public int getMaxPages() {
        return GeneralUtil.getMaxPages(8,subCommands);
    }
    public List<String> getAliases() {
        return this.aliases;
    }
    public List<SubFriendCommand> getSubCommands() {
        return this.subCommands;
    }
    public Boolean hasAliase(String name){
        if(this.name.equalsIgnoreCase(name) || this.aliases.contains(name.toLowerCase())) return true;
        return false;
    }
    public FriendCommand setUsage(String usage) {
        this.usage = usage;
        return this;
    }
    public FriendCommand setDescription(String description) {
        this.description = description;
        return this;
    }
    public FriendCommand setPermission(String permission) {
        this.permission = permission;
        return this;
    }
    public FriendCommand addAlias(String... aliases) {
        this.aliases.addAll(Arrays.asList(aliases));
        return this;
    }
    public FriendCommand registerSubCommand(SubFriendCommand subCommand){
        subCommand.init(this,this);
        if(subCommand.getPrefix() == null) subCommand.setPrefix(this.prefix);
        this.subCommands.add(subCommand);
        return this;
    }
    public void sendHelp(FriendCommandSender sender){
        sendHelp(sender,1);
    }
    public void sendHelp(FriendCommandSender sender,String[] args){
        int page = 1;
        if(args.length > 0 && GeneralUtil.isNumber(args[0])) page = Integer.valueOf(args[0]);
        sendHelp(sender,page);
    }

    //finish and set message
    public void sendHelp(FriendCommandSender sender, int page) {
        int maxPages = getMaxPages();
        if(page > maxPages) page = 1;
        int nextPage = page+1;
        if(nextPage > maxPages) nextPage = 1;
        if(page == maxPages) sender.sendMessage(Messages.HELP_HEADER_ONE
                .replace("[prefix]",this.prefix)
                .replace("[page]",""+page)
                .replace("[maxPage]",""+maxPages)
                .replace("[command]",this.name)
                .replace("[nextPage]",""+nextPage));
        else sender.sendMessage(Messages.HELP_HEADER_MORE
                .replace("[prefix]",this.prefix)
                .replace("[page]",""+page)
                .replace("[maxPage]",""+maxPages)
                .replace("[command]",this.name)
                .replace("[nextPage]",""+nextPage));
        int from = 1;
        if(page > 1) from = 8 * (page - 1) + 1;
        int to = 8 * page;
        for(int h = from; h <= to; h++) {
            if(h > subCommands.size()) break;
            SubFriendCommand subCommand = subCommands.get(h - 1);
            if(subCommand.getPermission() == null || sender.hasPermission(subCommand.getPermission())) {
                sender.sendMessage(Messages.HELP_HEADER_LINE
                        .replace("[name]",getName())
                        .replace("[usage]",(subCommand.getUsage() != null?subCommand.getUsage():""))
                        .replace("[_usage]",(subCommand.getUsage() != null?
                                (subCommand.getUsage().equalsIgnoreCase("")?"":" "+subCommand.getUsage()):""))
                        .replace("[subCommand]",subCommand.getName())
                        .replace("[description]",subCommand.getDescription()));
            }
        }
    }

    public void execute(FriendCommandSender sender, String[] args){
        if(args.length >= 1) {
            for(SubFriendCommand subCommand : subCommands) {
                if(subCommand.hasAliase(args[0])) {
                    subCommand.execute(sender,Arrays.copyOfRange(args,1,args.length));
                    return;
                }
            }
        }
        onExecute(sender,args);
    }
    public List<String> tabComplete(FriendCommandSender sender, String[] args) {
        List<String> complete = null;
        if(args.length >= 1) {
            for(SubFriendCommand subCommand : subCommands) {
                if(subCommand.hasAliase(args[0])) complete = subCommand.tabComplete(sender, Arrays.copyOfRange(args, 1, args.length));
            }
        }
        if(complete == null) {
            complete = onTabComplete(sender,args);
            if(complete == null) complete = new LinkedList<>();
        }
        return complete;
    }
    public abstract void onExecute(FriendCommandSender sender, String[] args);
    public abstract List<String> onTabComplete(FriendCommandSender sender, String[] args);
}