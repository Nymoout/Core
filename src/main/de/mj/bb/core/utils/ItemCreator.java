/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.apache.commons.codec.binary.Base64;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

public class ItemCreator {

    public ItemCreator() {
    }

    public ItemStack createItemWithMaterial(Material m, int subid, int ammount, String DisplayName, List<String> lore) {
        ItemStack is = new ItemStack(m, ammount, (short) subid);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(DisplayName);
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

    public ItemStack createItemWithMaterial(Material m, int subid, int ammount, String DisplayName) {
        ItemStack is = new ItemStack(m, ammount, (short) subid);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(DisplayName);
        is.setItemMeta(im);
        return is;
    }

    public ItemStack createItemWithMaterial(Material m, int subid, int ammount) {
        ItemStack is = new ItemStack(m, ammount, (short) subid);
        ItemMeta im = is.getItemMeta();
        is.setItemMeta(im);
        return is;
    }

    public ItemStack createItemWithSkull(String value, int amount, String displayName, List<String> lore, boolean unsafe) {
        ItemStack is;
        if (unsafe) is = getRawSkullUnsafe(value, amount);
        else is = getRawSkull(value, amount);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(displayName);
        im.setLore(lore);
        is.setItemMeta(im);
        return is;
    }

    public ItemStack createItemWithSkull(String value, int amount, String displayName, boolean unsafe) {
        ItemStack is;
        if (unsafe) is = getRawSkullUnsafe(value, amount);
        else is = getRawSkull(value, amount);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName(displayName);
        is.setItemMeta(im);
        return is;
    }

    public ItemStack getRawSkull(String value, int amount) {
        String url = "http://textures.minecraft.net/texture/" + value;
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
