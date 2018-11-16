package ch.dkrieger.friendsystem.lib.storage.sql.table;

import ch.dkrieger.permissionsystem.lib.storage.mysql.MySQL;
import ch.dkrieger.permissionsystem.lib.storage.mysql.query.*;
import ch.dkrieger.permissionsystem.lib.storage.mysql.query.CreateQuery;
import ch.dkrieger.permissionsystem.lib.storage.mysql.query.CustomQuery;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 20:57
 *
 */

public class Table {

    private String name;
    private MySQL mysql;

    public Table(MySQL mysql, String name){
        this.mysql = mysql;
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public CreateQuery create(){
        return new CreateQuery(mysql.getConnecion(),"CREATE TABLE IF NOT EXISTS `"+this.name+"` (");
    }
    public InsertQuery insert(){
        return new InsertQuery(mysql.getConnecion(),"INSERT INTO `"+this.name+"` (");
    }
    public UpdateQuery update(){
        return new UpdateQuery(mysql.getConnecion(),"UPDATE `"+this.name+"` SET");
    }
    public SelectQuery select(){
        return select("*");
    }
    public SelectQuery select(String selection){
        return new SelectQuery(mysql.getConnecion(), "SELECT "+selection+" FROM `"+this.name+"`");
    }
    public DeleteQuery delete(){
        return new DeleteQuery(mysql.getConnecion(), "DELETE FROM `"+this.name+"`");
    }
    public CustomQuery query(){
        return new CustomQuery(mysql.getConnecion());
    }
}
