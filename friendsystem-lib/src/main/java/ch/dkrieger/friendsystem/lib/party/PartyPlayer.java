package ch.dkrieger.friendsystem.lib.party;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 11:54
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;

import java.util.UUID;

public class PartyPlayer {

    private UUID uuid;
    private boolean leader, moderator;
    private long joined;

    public PartyPlayer(UUID uuid) {
        this(uuid,false);
    }
    public PartyPlayer(UUID uuid, boolean leader) {
        this(uuid,leader,leader);
    }
    public PartyPlayer(UUID uuid, boolean leader, boolean moderator) {
        this(uuid,leader,moderator,System.currentTimeMillis());
    }
    public PartyPlayer(UUID uuid, boolean leader, boolean moderator, long joined) {
        this.uuid = uuid;
        this.leader = leader;
        this.moderator = moderator;
        this.joined = joined;
    }

    public UUID getUUID() {
        return uuid;
    }
    public FriendPlayer getPlayer(){
        return FriendSystem.getInstance().getPlayerManager().getPlayer(this.uuid);
    }
    public OnlineFriendPlayer getOnlinePlayer(){
        return FriendSystem.getInstance().getPlayerManager().getOnlinePlayer(this.uuid);
    }
    public boolean isLeader() {
        return leader;
    }

    public boolean isModerator() {
        return moderator;
    }

    public long getJoined() {
        return joined;
    }
}
