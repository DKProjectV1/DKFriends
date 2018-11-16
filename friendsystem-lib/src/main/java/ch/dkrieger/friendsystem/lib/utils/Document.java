package ch.dkrieger.friendsystem.lib.utils;

import com.google.gson.JsonObject;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 18:56
 *
 */

public class Document {

    private JsonObject datas;

    public Document(){
        this.datas = new JsonObject();
    }
    public Document(JsonObject data){
        this.datas = data;
    }
    public JsonObject getJsonObject(){
        return this.datas;
    }
    public String getString(String key){
        if(this.datas.has(key)) return this.datas.get(key).getAsString();
        return null;
    }
    public int getInt(String key){
        if(this.datas.has(key)) return this.datas.get(key).getAsInt();
        return 0;
    }
    public Long getLong(String key){
        if(this.datas.has(key)) return this.datas.get(key).getAsLong();
        return 0L;
    }
    public Boolean getBoolean(String key){
        if(this.datas.has(key)) return this.datas.get(key).getAsBoolean();
        return false;
    }
    public Document getDocument(String key){
        JsonObject value = this.datas.get(key).getAsJsonObject();
        if(value != null) return new Document(value);
        return new Document();
    }
    public <T> T getObject(String key,Class<T> classof){
        if(!this.datas.has(key)) return null;
        return GeneralUtil.GSON.fromJson(this.datas.get(key),classof);
    }
    public <T> T getObject(String key, Type type) {
        return GeneralUtil.GSON.fromJson(datas.get(key), type);
    }
    public Boolean contains(String key){
        return this.datas.has(key);
    }
    public Boolean contains(String... keys){
        for(String key : keys) if(!contains(key)) return false;
        return true;
    }
    public Document append(String key, String value){
        if(value != null) this.datas.addProperty(key,value);
        return this;
    }
    public Document append(String key,Boolean value){
        if(value != null) this.datas.addProperty(key,value);
        return this;
    }
    public Document append(String key, Number value){
        if(value != null) this.datas.addProperty(key,value);
        return this;
    }
    public Document append(String key, Document value){
        if(value != null) return append(key,value.toJson());
        return this;
    }
    public Document append(String key, Object value){
        if(value != null) this.datas.add(key, GeneralUtil.GSON.toJsonTree(value));
        return this;
    }
    public Document appendDefault(String key, String value){
        if(contains(key) || value == null) return this;
        this.datas.addProperty(key,value);
        return this;
    }
    public Document appendDefault(String key,Boolean value){
        if(contains(key) || value == null) return this;
        this.datas.addProperty(key,value);
        return this;
    }
    public Document appendDefault(String key, Number value){
        if(contains(key) || value == null) return this;
        this.datas.addProperty(key,value);
        return this;
    }
    public Document appendDefault(String key, Document value){
        if(contains(key) || value == null) return this;
        this.datas.add(key,value.getJsonObject());
        return this;
    }
    public Document appendDefault(String key, Object value){
        if(contains(key) || value == null) return this;
        this.datas.add(key, GeneralUtil.GSON.toJsonTree(value));
        return this;
    }
    public Document remove(String key){
        this.datas.remove(key);
        return this;
    }
    public String toJson(){
        return datas.toString();
    }
    public void setDatas(JsonObject datas){
        this.datas = datas;
    }
    public void saveData(File file){
        try{
            saveDataSave(file);
        }catch (Exception exception) {}
    }
    public void saveDataSave(File file) throws Exception{
        file.createNewFile();
        OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()), "UTF-8");
        GeneralUtil.GSON.toJson(this.datas,writer);
        writer.close();
    }
    public static Document loadData(String gsonstring){
        try{
            return loadDataSave(gsonstring);
        }catch (Exception exception) {}
        return new Document();
    }
    public static Document loadDataSave(String gsonstring) throws Exception{
        InputStreamReader reader = new InputStreamReader(new StringBufferInputStream(gsonstring), "UTF-8");
        return new Document(GeneralUtil.PARSER.parse(new BufferedReader(reader)).getAsJsonObject());
    }
    public static Document loadData(File file){
        try{
            return loadDataSave(file);
        }catch (Exception exception) {}
        return new Document();
    }
    public static Document loadDataSave(File file) throws Exception{
        InputStreamReader reader = new InputStreamReader(Files.newInputStream(file.toPath()), "UTF-8");
        BufferedReader bufferedreader = new BufferedReader(reader);
        JsonObject datas = GeneralUtil.PARSER.parse(bufferedreader).getAsJsonObject();
        return new Document(datas);
    }
    @Override
    public String toString() {
        return toJson();
    }
    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Document)) return false;
        return ((Document)obj).toJson().equals(toJson());
    }
}