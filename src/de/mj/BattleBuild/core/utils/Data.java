/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package de.mj.BattleBuild.core.utils;

public class Data {

    private static String prefix = "§7[§6§lBattleBuild§7] §7";
    private static String noperm = prefix + "§cDir fehlt die ben\u00F6tigte Berechtigung.";

    public String getPrefix() {
        return prefix;
    }

    public String getNoperm() {
        return noperm;
    }
}
