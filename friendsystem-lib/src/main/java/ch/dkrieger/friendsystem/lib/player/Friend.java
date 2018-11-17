package ch.dkrieger.friendsystem.lib.player;

import ch.dkrieger.friendsystem.lib.FriendSystem;

import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:47
 *
 */

public class Friend {

    private UUID uuid;
    private long timeStamp;
    private boolean favorite;

    public Friend(UUID uuid, long timeStamp, boolean favorite) {
        this.uuid = uuid;
        this.timeStamp = timeStamp;
        this.favorite = favorite;
    }
    public UUID getUUID() {
        return uuid;
    }
    public long getTimeStamp() {
        return timeStamp;
    }
    public boolean isFavorite() {
        return favorite;
    }
    public boolean isOnline(){
        return FriendSystem.getInstance().getPlayerManager().getOnlinePlayer(this.uuid) != null;
    }
    public FriendPlayer getFriendPlayer(){
        return FriendSystem.getInstance().getPlayerManager().getPlayer(this.uuid);
    }
    public OnlineFriendPlayer getOnlineFriendPlayer(){
        return FriendSystem.getInstance().getPlayerManager().getOnlinePlayer(this.uuid);
    }
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
