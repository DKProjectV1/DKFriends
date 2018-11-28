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

public class PartyMember {

    private final UUID uuid;
    private boolean leader, moderator;
    private long joined;

    public PartyMember(UUID uuid) {
        this(uuid,false);
    }
    public PartyMember(UUID uuid, boolean leader) {
        this(uuid,leader,leader);
    }
    public PartyMember(UUID uuid, boolean leader, boolean moderator) {
        this(uuid,leader,moderator,System.currentTimeMillis());
    }
    public PartyMember(UUID uuid, boolean leader, boolean moderator, long joined) {
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

    public boolean canIntegrate(UUID player){
        return FriendSystem.getInstance().getPartyManager().getParty(this.uuid).canIntegrate(this.uuid, player);
    }

    public void setLeader(boolean leader) {
        this.leader = leader;
    }

    public void setModerator(boolean moderator) {
        this.moderator = moderator;
    }
}
