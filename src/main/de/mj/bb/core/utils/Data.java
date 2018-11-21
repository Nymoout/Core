/*
 * @author MJ
 * Created in 25.08.2018
 * Copyright (c) 2017 - 2018 by MJ. All rights reserved.
 *
 */

package main.de.mj.bb.core.utils;

public class Data {

    private String prefix = "§8[§6§lBattleBuild§8] §7";
    private String noPerm = prefix + "§cDir fehlt die ben\u00F6tigte Berechtigung!";
    private String onlyPlayer = prefix + "Du musst ein Spieler sein um diesen Befehl nutzen zu können!";

    public String getPrefix() {
        return this.prefix;
    }

    public String getNoPerm() {
        return this.noPerm;
    }

    public String getOnlyPlayer() {
        return this.onlyPlayer;
    }
}
