package ch.dkrieger.friendsystem.lib.command;

import ch.dkrieger.friendsystem.lib.utils.GeneralUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 08:35
 *
 */

public abstract class FriendCommand {

    private String name, description, permission, usage;
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
        this.name = name;
        this.description = description;
        this.permission = permission;
        this.usage = usage;
        this.aliases = new LinkedList<>(Arrays.asList(aliases));
        this.subCommands = new LinkedList<>();
    }
    public String getName() {
        return this.name;
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
        this.subCommands.add(subCommand);
        return this;
    }
    public void sendHelp(FriendCommandSender sender){
        sendHelp(sender,1);
    }

    //finish and set message
    public void sendHelp(FriendCommandSender sender, int page) {
        int maxPages = getMaxPages();
        if(page > maxPages) page = 1;
        int nextPage = page+1;
        if(nextPage > maxPages) nextPage = 1;
        if(nextPage == page) sender.sendMessage("Seite "+page+"/"+maxPages);
        else sender.sendMessage("Seite "+page+"/"+ maxPages + " | Weitere Hilfe mit " + getName() + " " + nextPage);
        int from = 1;
        if(page > 1) from = 8 * (page - 1) + 1;
        int to = 8 * page;
        for(int h = from; h <= to; h++) {
            if(h > subCommands.size()) break;
            SubFriendCommand subCommand = subCommands.get(h - 1);
            if(sender.hasPermission(subCommand.getPermission())) {
                sender.sendMessage(getName()+(subCommand.getUsage() != null ? " " + subCommand.getUsage() : "") + (subCommand.getDescription() != null ? " " + subCommand.getDescription() : ""));
                if(!subCommand.getSubCommands().isEmpty()){
                    String helpMessage = "";
                    for(SubFriendCommand nextSubCommand : subCommand.getSubCommands()) {
                        helpMessage+=getName() +" "+ subCommand.getName()+(nextSubCommand.getUsage() != null ? " " + nextSubCommand.getUsage() : "")+(nextSubCommand.getDescription() != null ? " " + nextSubCommand.getDescription() : "")+"\n";
                    }
                    sender.sendMessage(helpMessage);
                }
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
    public List<String> tabComplete(FriendCommandSender sender, String[] args){
        if(args.length >= 1) {
            for(SubFriendCommand subCommand : subCommands) {
                if(subCommand.hasAliase(args[0])) return subCommand.tabComplete(sender,Arrays.copyOfRange(args,1,args.length));
            }
        }
        return onTabComplete(sender,args);
    }
    public abstract void onExecute(FriendCommandSender sender, String[] args);
    public abstract List<String> onTabComplete(FriendCommandSender sender, String[] args);
}