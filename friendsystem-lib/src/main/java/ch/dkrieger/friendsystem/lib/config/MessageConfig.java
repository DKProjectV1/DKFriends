package ch.dkrieger.friendsystem.lib.config;

import ch.dkrieger.friendsystem.lib.DKFriendsPlatform;
import ch.dkrieger.friendsystem.lib.storage.StorageType;

import java.io.File;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 22:30
 *
 */

public class MessageConfig extends SimpleConfig {

    private final DKFriendsPlatform platform;

    public MessageConfig(DKFriendsPlatform platform) {
        super(new File(platform.getFolder(),"messages.yml"));
        this.platform = platform;
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void registerDefaults() {
    }
}