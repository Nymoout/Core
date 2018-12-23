/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import main.de.mj.bb.core.CoreSpigot;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.UUID;

public class ItemCreator {

    private final CoreSpigot coreSpigot;

    public ItemCreator(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

    public ItemStack createItemWithMaterial(Material m, int subId, int amount, String DisplayName, List<String> lore) {
        ItemStack is = new ItemStack(m, amount, (short) subId);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(DisplayName);
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

    public ItemStack createItemWithMaterial(Material m, int subId, int amount, String DisplayName) {
        ItemStack is = new ItemStack(m, amount, (short) subId);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(DisplayName);
        is.setItemMeta(im);
        return is;
    }

    public ItemStack createItemWithMaterial(Material m, int subId, int amount) {
        ItemStack is = new ItemStack(m, amount, (short) subId);
        ItemMeta im = is.getItemMeta();
        is.setItemMeta(im);
        return is;
    }

    public ItemStack createItemWithSkull(String value, int amount, String displayName, List<String> lore, boolean unsafe) {
        ItemStack is;
        if (unsafe) is = getRawSkullUnsafe(value, amount);
        else is = getRawSkull(value, amount, false);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(displayName);
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

    public ItemStack createItemWithSkull(String value, int amount, String displayName, boolean unsafe) {
        ItemStack is;
        if (unsafe) is = getRawSkullUnsafe(value, amount);
        else is = getRawSkull(value, amount, false);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(displayName);
        is.setItemMeta(im);
        return is;
    }

    public ItemStack createItemWithPlayer(String value, int amount, String displayName) {
        ItemStack is = getRawSkull(value, amount, true);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(displayName);
        is.setItemMeta(im);
        return is;
    }

    public ItemStack getRawSkull(String value, int amount, boolean isPlayer) {
        String url;
        if (isPlayer) {
                String mojang = "https://sessionserver.mojang.com/session/minecraft/profile/" + value.replace("-", "");
                try {
                    URL mjUrl = new URL(mojang);
                    URLConnection request = mjUrl.openConnection();
                    request.connect();
                    JsonParser jp = new JsonParser();
                    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
                    JsonObject rootobj = root.getAsJsonObject();
                    JsonArray jsonArray = rootobj.getAsJsonArray("properties");
                    byte[] decoded = Base64.decodeBase64(jsonArray.get(0).getAsJsonObject().getAsJsonPrimitive("value").getAsString());
                    JsonElement element = jp.parse(new String(decoded));
                    JsonObject jsonObject = element.getAsJsonObject();
                    url = jsonObject.getAsJsonObject("textures").getAsJsonObject("SKIN").getAsJsonPrimitive("url").getAsString();
                    coreSpigot.getModuleManager().getSettingsAPI().setValue(UUID.fromString(value), jsonArray.get(0).getAsJsonObject().getAsJsonPrimitive("value").getAsString());
                } catch (IOException e) {
                    JsonParser jp = new JsonParser();
                    if (coreSpigot.getModuleManager().getSettingsAPI().getValue(UUID.fromString(value)).equalsIgnoreCase("none"))
                        getRawSkull(value, amount, isPlayer);
                    byte[] decoded = Base64.decodeBase64(coreSpigot.getModuleManager().getSettingsAPI().getValue(UUID.fromString(value)));
                    JsonElement element = jp.parse(new String(decoded));
                    JsonObject jsonObject = element.getAsJsonObject();
                    url = jsonObject.getAsJsonObject("textures").getAsJsonObject("SKIN").getAsJsonPrimitive("url").getAsString();
                }
        } else url = "http://textures.minecraft.net/texture/" + value;
        ItemStack head = new ItemStack(Material.SKULL_ITEM, amount, (short) 3);
        if (url.isEmpty()) return head;


        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public ItemStack getRawSkullUnsafe(String value, int amount) {
        String url = "http://textures.minecraft.net/texture/" + value;
        ItemStack head = new ItemStack(Material.SKULL_ITEM);
        head.setDurability((short) 3);
        if (url.isEmpty()) return head;


        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        byte[] encodedData = Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField;
        try {
            profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);
        } catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e1) {
            e1.printStackTrace();
        }
        head.setItemMeta(headMeta);
        net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(head);
        nmsStack.count = amount;
        return CraftItemStack.asCraftMirror(nmsStack);
    }

}
