package ch.dkrieger.friendsystem.lib.party;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 18.11.18 11:32
 *
 */

import ch.dkrieger.friendsystem.lib.FriendSystem;
import ch.dkrieger.friendsystem.lib.Messages;
import ch.dkrieger.friendsystem.lib.player.Friend;
import ch.dkrieger.friendsystem.lib.player.FriendPlayer;
import ch.dkrieger.friendsystem.lib.player.OnlineFriendPlayer;

import java.util.*;

public class Party {

    private UUID uuid;
    private boolean public0;
    private long created, timeOut;
    private List<PartyMember> members;
    private List<UUID> requests;
    private List<UUID> bans;

    public Party(UUID uuid, FriendPlayer leader) {
        this(uuid,leader.getUUID());
    }
    public Party(UUID uuid, OnlineFriendPlayer leader) {
        this(uuid,leader.getUUID());
    }
    public Party(UUID uuid, UUID player) {
        this.uuid = uuid;
        this.members = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.bans = new ArrayList<>();
        this.created = System.currentTimeMillis();
        this.members.add(new PartyMember(player,true));
    }
    public UUID getUUID() {
        return uuid;
    }
    public List<PartyMember> getMembers() {
        return members;
    }
    public List<String> getMemberNames() {
        List<String> names = new LinkedList<>();
        for(PartyMember member : this.members) names.add(member.getOnlinePlayer().getName());
        return names;
    }
    public List<PartyMember> getSortedMembers() {
        List<PartyMember> members = new ArrayList<>();
        members.add(getLeader());
        members.addAll(getModerators());
        members.addAll(getNormalMembers());
        return members;
    }
    public List<UUID> getBans() {
        return this.bans;
    }
    public List<String> getBanNames() {
        List<String> names = new LinkedList<>();
        for(UUID ban : this.bans) names.add(FriendSystem.getInstance().getPlayerManager().getPlayer(ban).getOnlinePlayer().getName());
        return names;
    }
    public List<UUID> getRequests() {
        return requests;
    }
    public long getTimeOut() {
        return timeOut;
    }
    public long getCreated() {
        return created;
    }
    public PartyMember getLeader(){
        Iterator<PartyMember> iterator = new ArrayList<>(this.members).iterator();
        PartyMember player = null;
        while(iterator.hasNext() && (player= iterator.next()) != null) if(player.isLeader()) return player;
        return null;
    }
    public List<PartyMember> getModerators(){
        List<PartyMember> players = new ArrayList<>();
        Iterator<PartyMember> iterator = new ArrayList<>(this.members).iterator();
        PartyMember player = null;
        while(iterator.hasNext() && (player= iterator.next()) != null) if(!(player.isLeader()) && player.isModerator()) players.add(player);
        return players;
    }
    public List<PartyMember> getNormalMembers(){
        List<PartyMember> players = new ArrayList<>();
        Iterator<PartyMember> iterator = new ArrayList<>(this.members).iterator();
        PartyMember player = null;
        while(iterator.hasNext() && (player= iterator.next()) != null) if(!(player.isLeader()) && !(player.isModerator())) players.add(player);
        return players;
    }
    public PartyMember getMember(FriendPlayer player){
        return getMember(player.getUUID());
    }
    public PartyMember getMember(OnlineFriendPlayer player){
        return getMember(player.getUUID());
    }
    public PartyMember getMember(UUID uuid){
        Iterator<PartyMember> iterator = new ArrayList<>(this.members).iterator();
        PartyMember player = null;
        while(iterator.hasNext() && (player= iterator.next()) != null) if(player.getUUID().equals(uuid)) return player;
        return null;
    }
    public boolean isPublic() {
        return public0;
    }
    public boolean isMember(FriendPlayer player) {
        return isMember(player.getUUID());
    }
    public boolean isMember(OnlineFriendPlayer player) {
        return  isMember(player.getUUID());
    }
    public boolean isMember(UUID uuid) {
        return getMember(uuid) != null;
    }
    public boolean isLeader(FriendPlayer player) {
        return isLeader(player.getUUID());
    }
    public boolean isLeader(OnlineFriendPlayer player) {
        return isLeader(player.getUUID());
    }
    public boolean isLeader(UUID uuid) {
        PartyMember member =  getMember(uuid);
        return member != null && member.isLeader();
    }
    public boolean isModerator(FriendPlayer player) {
        return isModerator(player.getUUID());
    }
    public boolean isModerator(OnlineFriendPlayer player) {
        return isModerator(player.getUUID());
    }
    public boolean isModerator(UUID uuid) {
        PartyMember member =  getMember(uuid);
        return member != null && member.isModerator();
    }
    public boolean hasRequest(FriendPlayer player) {
        return hasRequest(player.getUUID());
    }
    public boolean hasRequest(OnlineFriendPlayer player) {
        return hasRequest(player.getUUID());
    }
    public boolean hasRequest(UUID uuid) {
        return this.requests.contains(uuid);
    }
    public void setPublic(boolean public0) {
        this.public0 = public0;
        update();
    }
    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
        update();
    }
    public void addRequest(FriendPlayer player){
        addRequest(player.getUUID());
    }
    public void addRequest(OnlineFriendPlayer player) {
        addRequest(player.getUUID());
    }
    public void addRequest(UUID uuid){
        this.requests.add(uuid);
        update();
    }
    public void removeRequest(FriendPlayer player){
        removeRequest(player.getUUID());
    }
    public void removeRequest(OnlineFriendPlayer player){
        removeRequest(player.getUUID());
    }
    public void removeRequest(UUID uuid){
        this.requests.remove(uuid);
        update();
    }
    public void addMember(FriendPlayer player){
        addMember(player.getUUID());
    }
    public void addMember(OnlineFriendPlayer player) {
        addMember(player.getUUID());
    }
    public void addMember(UUID uuid){
        this.requests.remove(uuid);
        this.members.add(new PartyMember(uuid));
        update();
    }
    public void removeMember(FriendPlayer player){
        removeMember(player.getUUID());
    }
    public void removeMember(OnlineFriendPlayer player){
        removeMember(player.getUUID());
    }
    public void removeMember(UUID uuid){
        this.members.remove(getMember(uuid));
        update();
    }
    public void setModerator(FriendPlayer player, boolean moderator){
        setModerator(player.getUUID(),moderator);
    }
    public void setModerator(OnlineFriendPlayer player, boolean moderator){
        setModerator(player.getUUID(),moderator);
    }
    public void setModerator(UUID uuid, boolean moderator){
        PartyMember member = getMember(uuid);
        if(member != null){
            member.setModerator(moderator);
            update();
        }
    }
    public void setLeader(FriendPlayer player){
        setLeader(player.getUUID());
    }
    public void setLeader(OnlineFriendPlayer player){
        setLeader(player.getUUID());
    }
    public void setLeader(UUID uuid){
        getLeader().setLeader(false);
        PartyMember member = getMember(uuid);
        if(member != null){
            member.setLeader(true);
            update();
        }
    }
    public void ban(OnlineFriendPlayer player){
        ban(player.getUUID());
    }
    public void ban(FriendPlayer player){
        ban(player.getUUID());
    }
    public void ban(UUID uuid){
        this.bans.add(uuid);
        this.requests.remove(uuid);
        this.members.remove(getMember(uuid));
        update();
    }
    public void unban(OnlineFriendPlayer player){
        unban(player.getUUID());
    }
    public void unban(FriendPlayer player){
        unban(player.getUUID());
    }
    public void unban(UUID uuid){
        this.bans.remove(uuid);
        update();
    }
    public boolean isBanned(OnlineFriendPlayer player){
        return isBanned(player.getUUID());
    }
    public boolean isBanned(FriendPlayer player){
        return isBanned(player.getUUID());
    }
    public boolean isBanned(UUID uuid){
        return this.bans.contains(uuid);
    }
    public boolean canIntegrate(FriendPlayer integrator, FriendPlayer player){
        if(!isLeader(integrator)){
            if(isModerator(integrator)){
                if(isLeader(player) || isModerator(player)) return false;
                else return true;
            }else return false;
        }
        return true;
    }
    public void sendMessage(String message){
        for(PartyMember member : this.members){
            OnlineFriendPlayer online = member.getOnlinePlayer();
            if(online != null) online.sendMessage(message);
        }
    }
    public void connect(String server){
        for(PartyMember member : this.members){
            OnlineFriendPlayer online = member.getOnlinePlayer();
            if(online != null) online.connect(server);
        }
    }
    public void update(){
        FriendSystem.getInstance().getPartyManager().update(this);
    }
}
