package ch.dkrieger.friendsystem.lib.player;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 24.11.18 15:03
 *
 */

public class PlayerColor {

    private String permission, color;

    public PlayerColor(String permission, String color){
        this.permission = permission;
        this.color = color;
    }
    public String getPermission(){
        return this.permission;
    }
    public String getColor(){
        return this.color;
    }
}
