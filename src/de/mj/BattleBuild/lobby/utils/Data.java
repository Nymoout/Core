package de.mj.BattleBuild.lobby.utils;

public class Data {

    private static String prefix = "§7[§6§lBattleBuild§7] §7";
    private static String noperm = prefix + "§cDir fehlt die ben\u00F6tigte Berechtigung.";

    public String getPrefix() {
        return prefix;
    }
    public String getNoperm() { return noperm; }
}
