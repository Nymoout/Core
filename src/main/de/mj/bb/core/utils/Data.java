/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.utils;

import lombok.Getter;

@Getter
public class Data {

    private static String prefix = "§7[§6§lBattleBuild§7] §7";
    private static String noPerm = prefix + "§cDir fehlt die ben\u00F6tigte Berechtigung.";

    public String getPrefix() {
        return prefix;
    }

    public String getNoPerm() {
        return noPerm;
    }
}
