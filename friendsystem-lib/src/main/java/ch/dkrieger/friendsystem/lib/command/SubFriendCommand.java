package ch.dkrieger.friendsystem.lib.command;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 08:35
 *
 */

public abstract class SubFriendCommand extends FriendCommand {

    private FriendCommand mainCommand, commandBefore;

    public SubFriendCommand(String name) {
        super(name);
    }
    public SubFriendCommand(String name, String description) {
        super(name, description);
    }
    public SubFriendCommand(String name, String description, String permission) {
        super(name, description, permission);
    }
    public SubFriendCommand(String name, String description, String permission, String usage, String... aliases) {
        super(name, description, permission, usage, aliases);
    }
    public FriendCommand getMainCommand() {
        return this.mainCommand;
    }
    public FriendCommand getCommandBefore() {
        return this.commandBefore;
    }
    public void init(FriendCommand mainCommand, FriendCommand commandBefore){
        if(this.mainCommand !=null && this.commandBefore !=null) throw new IllegalArgumentException("Command is already registered");
        this.mainCommand = mainCommand;
        this.commandBefore = commandBefore;
    }
}