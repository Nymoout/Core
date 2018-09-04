/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.core.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ItemCreator {

    public ItemCreator() {
    }

    public ItemStack createItemWithMaterial(Material m, int subid, int ammount, String DisplayName, ArrayList<String> lore) {
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
}
