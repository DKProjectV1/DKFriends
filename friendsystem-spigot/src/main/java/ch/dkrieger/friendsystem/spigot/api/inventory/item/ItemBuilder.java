package ch.dkrieger.friendsystem.spigot.api.inventory.item;

import ch.dkrieger.friendsystem.spigot.api.Reflection;
import ch.dkrieger.friendsystem.spigot.SpigotUtil;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;
import net.minecraft.server.v1_8_R1.NBTTagCompound;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.block.banner.Pattern;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/*
 *
 *  * Copyright (c) 2018 Davide Wietlisbach on 16.11.18 17:46
 *
 */

public class ItemBuilder {

    private ItemStack itemStack;
    List<String> lore = new LinkedList<>();

    public ItemBuilder(){
    }
    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }
    public ItemBuilder(ItemStack itemstack){
        this.itemStack = itemstack;
    }
    public ItemBuilder(Material material, int amount) {
        this.itemStack = new ItemStack(material, amount);
    }
    public ItemBuilder(Material material, int amount, short damage) {
        this.itemStack = new ItemStack(material, amount, damage);
    }
    public ItemBuilder(Material material, short damage) {
        this.itemStack = new ItemStack(material, damage);
    }
    public ItemBuilder(Material material, byte id) {
        this.itemStack = new ItemStack(material, 1,id);
    }
    public ItemBuilder(Material material, int amount, int damage) {
        this.itemStack = new ItemStack(material, amount, (short) damage);
    }
    public ItemBuilder(int id) {
        this.itemStack = new ItemStack(id);
    }
    public ItemBuilder(int id, int amount) {
        this.itemStack = new ItemStack(id, amount);
    }
    public ItemBuilder(int id, int amount, short damage) {
        this.itemStack = new ItemStack(id, amount, damage);
    }
    public ItemBuilder(int id, int amount, int damage) {
        this.itemStack = new ItemStack(id, amount, (short) damage);
    }
    public ItemBuilder(String item, int amount) {
        try {
            this.itemStack = new ItemStack(Material.valueOf(item.toUpperCase()), amount);
        } catch (NullPointerException e) {
            this.itemStack = new ItemStack(Material.AIR);
            e.printStackTrace();
        }
    }
    public ItemBuilder(String item, int amount, short damage) {
        try {
            this.itemStack = new ItemStack(Material.valueOf(item.toUpperCase()), amount, damage);
        } catch (NullPointerException e) {
            this.itemStack = new ItemStack(Material.AIR);
            e.printStackTrace();
        }
    }
    public ItemBuilder(String item, int amount, int damage) {
        try {
            this.itemStack = new ItemStack(Material.valueOf(item.toUpperCase()), amount, (short) damage);
        } catch (NullPointerException e) {
            this.itemStack = new ItemStack(Material.AIR);
            e.printStackTrace();
        }
    }
    public ItemBuilder(short data){
        if(data <= -1) data = 15;
        this.itemStack = new ItemStack(Material.STAINED_GLASS_PANE, 1,data);
        setName("§7§8§0");
    }
    public ItemBuilder(GameProfile profile, String displayname){
        itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta meta = itemStack.getItemMeta();
        Class<?> headMetaClass = meta.getClass();
        Reflection.getField(headMetaClass, "profile", GameProfile.class).set(meta, profile);
        meta.setDisplayName(displayname);
        itemStack.setItemMeta(meta);
    }
    public ItemBuilder(String url, String displayname){
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        PropertyMap propertyMap = profile.getProperties();
        if (propertyMap == null) {
            throw new IllegalStateException("Profile doesn't contain a property map");
        }
        byte[] encodedData = SpigotUtil.BASE64.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        propertyMap.put("textures", new Property("textures", new String(encodedData)));
        itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        ItemMeta meta = itemStack.getItemMeta();
        Class<?> headMetaClass = meta.getClass();
        Reflection.getField(headMetaClass, "profile", GameProfile.class).set(meta, profile);
        meta.setDisplayName(displayname);
        itemStack.setItemMeta(meta);
    }
    public ItemBuilder(String itemid){
        if(itemid != null){
            try{
                String[] value = itemid.split(":");
                Material material = Material.getMaterial(Integer.valueOf(value[0]));
                if(material != null){
                    this.itemStack = new ItemStack(Material.getMaterial(Integer.valueOf(value[0])), 1,Integer.valueOf(value[1]).byteValue());
                    if(itemStack.getType() != null) return;
                    if(this.itemStack != null) return;
                }
            }catch (Exception exception){}
        }
        this.itemStack = new ItemStack(Material.PAPER);
    }
    public ItemBuilder setMaterial(Material material) {
        this.itemStack.setType(material);
        return this;
    }
    public ItemBuilder setMaterial(int id) {
        this.itemStack.setType(Material.getMaterial(id));
        return this;
    }
    public ItemBuilder setMaterial(String material) {
        this.itemStack.setType(Material.getMaterial(material));
        return this;
    }
    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }
    public ItemBuilder setDurability(short damage) {
        this.itemStack.setDurability(damage);
        return this;
    }
    public ItemBuilder setDurability(int damage) {
        this.itemStack.setDurability((short) damage);
        return this;
    }
    public ItemBuilder setItemMeta(ItemMeta itemMeta) {
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }
    public ItemBuilder setSkull(String skullowner, String displayname){
        itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta meta = (SkullMeta) itemStack.getItemMeta();
        meta.setOwner(skullowner);
        meta.setDisplayName(displayname);
        itemStack.setItemMeta(meta);
        return this;
    }
    public ItemBuilder setDisplayName(String displayname) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setDisplayName(displayname);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }
    public ItemBuilder setName(String name) {
        return setDisplayName(name);
    }
    public ItemBuilder addLore(String lore) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        this.lore.add(lore);
        itemMeta.setLore(this.lore);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }
    public ItemBuilder setLore(String... lore) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setLore(Arrays.asList(lore));
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }
    public ItemBuilder setLore(List<String> lore) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.setLore(lore);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }
    public ItemBuilder setGlowing() {
        return setGlowing(true);
    }
    public ItemBuilder setGlowing(boolean glowing) {
        if(!glowing)return this;
        this.itemStack.addUnsafeEnchantment(Enchantment.DIG_SPEED,1);
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemStack.setItemMeta(meta);
        return this;
    }
    public ItemBuilder clearlore() {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        lore.clear();
        itemMeta.setLore(lore);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }
    public ItemBuilder setUnbreakable() {
        return setUnbreakable(true);
    }
    public ItemBuilder setUnbreakable(boolean unbreakable) {
        if(!unbreakable)return this;
        net.minecraft.server.v1_8_R1.ItemStack stack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound tag = stack.hasTag() ? stack.getTag() : new NBTTagCompound();
        tag.setBoolean("Unbreakable",true);
        stack.setTag(tag);
        itemStack = CraftItemStack.asBukkitCopy(stack);
        return this;
    }
    public ItemBuilder setItemFlags(ItemFlag... itemFlags) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addItemFlags(itemFlags);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }
    public ItemBuilder setLeatherColor(Color leatherColor) {
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.itemStack.getItemMeta();
        leatherArmorMeta.setColor(leatherColor);
        this.itemStack.setItemMeta(leatherArmorMeta);
        return this;
    }
    public ItemBuilder addFireworkEffect(FireworkEffect fireworkEffect) {
        FireworkMeta fireworkMeta = (FireworkMeta) this.itemStack.getItemMeta();
        fireworkMeta.addEffect(fireworkEffect);
        this.itemStack.setItemMeta(fireworkMeta);
        return this;
    }
    public ItemBuilder addFireworkEffects(FireworkEffect... fireworkEffects) {
        FireworkMeta fireworkMeta = (FireworkMeta) this.itemStack.getItemMeta();
        fireworkMeta.addEffects(fireworkEffects);
        this.itemStack.setItemMeta(fireworkMeta);
        return this;
    }
    public ItemBuilder setFireworkEffect(FireworkEffect fireworkEffect) {
        FireworkEffectMeta fireworkMeta = (FireworkEffectMeta) this.itemStack.getItemMeta();
        fireworkMeta.setEffect(fireworkEffect);
        this.itemStack.setItemMeta(fireworkMeta);
        return this;
    }
    public ItemBuilder setFireworkPower(int fireworkPower) {
        FireworkMeta fireworkMeta = (FireworkMeta) this.itemStack.getItemMeta();
        fireworkMeta.setPower(fireworkPower);
        this.itemStack.setItemMeta(fireworkMeta);
        return this;
    }
    public ItemBuilder addEnchant(Enchantment enchantment, int level, boolean force) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addEnchant(enchantment, level, force);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }
    public ItemBuilder removeEnchant(Enchantment enchantment) {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.removeEnchant(enchantment);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }
    public ItemBuilder addUnsafeEnchantment(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }
    public ItemBuilder addGlow() {
        ItemMeta itemMeta = this.itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.DURABILITY, 1, false);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        this.itemStack.setItemMeta(itemMeta);
        return this;
    }
    public ItemBuilder addPotionEffect(PotionEffectType potionEffectType) {
        PotionMeta potionMeta = (PotionMeta) this.itemStack.getItemMeta();
        potionMeta.setMainEffect(potionEffectType);
        this.itemStack.setItemMeta(potionMeta);
        return this;
    }
    public ItemBuilder addCustomPotionEffect(PotionEffect potionEffect, boolean overwrite) {
        PotionMeta potionMeta = (PotionMeta) this.itemStack.getItemMeta();
        potionMeta.addCustomEffect(potionEffect, overwrite);
        this.itemStack.setItemMeta(potionMeta);
        return this;
    }
    public ItemBuilder removeCustomPotionEffect(PotionEffectType potionEffectType) {
        PotionMeta potionMeta = (PotionMeta) this.itemStack.getItemMeta();
        potionMeta.removeCustomEffect(potionEffectType);
        this.itemStack.setItemMeta(potionMeta);
        return this;
    }
    public ItemBuilder clearCustomPotionEffects() {
        PotionMeta potionMeta = (PotionMeta) this.itemStack.getItemMeta();
        potionMeta.clearCustomEffects();
        this.itemStack.setItemMeta(potionMeta);
        return this;
    }
    public ItemBuilder setBookTitle(String bookTitle) {
        BookMeta bookMeta = (BookMeta) this.itemStack.getItemMeta();
        bookMeta.setTitle(bookTitle);
        this.itemStack.setItemMeta(bookMeta);
        return this;
    }
    public ItemBuilder setBookAuthor(String bookAuthor) {
        BookMeta bookMeta = (BookMeta) this.itemStack.getItemMeta();
        bookMeta.setAuthor(bookAuthor);
        this.itemStack.setItemMeta(bookMeta);
        return this;
    }
    public ItemBuilder setPage(int page, String content) {
        BookMeta bookMeta = (BookMeta) this.itemStack.getItemMeta();
        bookMeta.setPage(page, content);
        this.itemStack.setItemMeta(bookMeta);
        return this;
    }
    public ItemBuilder setPages(List<String> pages) {
        BookMeta bookMeta = (BookMeta) this.itemStack.getItemMeta();
        bookMeta.setPages(pages);
        this.itemStack.setItemMeta(bookMeta);
        return this;
    }
    public ItemBuilder addPages(String... pages) {
        BookMeta bookMeta = (BookMeta) this.itemStack.getItemMeta();
        bookMeta.addPage(pages);
        this.itemStack.setItemMeta(bookMeta);
        return this;
    }
    public ItemBuilder setBannerBaseColor(DyeColor bannerBaseColor) {
        BannerMeta bannerMeta = (BannerMeta) this.itemStack.getItemMeta();
        bannerMeta.setBaseColor(bannerBaseColor);
        this.itemStack.setItemMeta(bannerMeta);
        return this;
    }
    public ItemBuilder setBannerPatterns(Pattern... bannerPatterns) {
        BannerMeta bannerMeta = (BannerMeta) this.itemStack.getItemMeta();
        for(Pattern pattern : bannerPatterns) bannerMeta.addPattern(pattern);
        this.itemStack.setItemMeta(bannerMeta);
        return this;
    }
    public ItemBuilder setSkullOwner(String skullOwner) {
        SkullMeta skullMeta = (SkullMeta) this.itemStack.getItemMeta();
        skullMeta.setOwner(skullOwner);
        this.itemStack.setItemMeta(skullMeta);
        return this;
    }
    public ItemStack build() {
        return this.itemStack;
    }
}