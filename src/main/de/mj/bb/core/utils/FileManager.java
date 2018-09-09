package main.de.mj.bb.core.utils;

import main.de.mj.bb.core.CoreSpigot;

public class FileManager {

    private final CoreSpigot coreSpigot;

    public FileManager(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

    public void loadConfigFile() {
        coreSpigot.getConfig().options().copyDefaults(true);
        coreSpigot.saveDefaultConfig();
    }

    public void reloadConfig() {
        coreSpigot.reloadConfig();
    }

    public boolean getBooleanFormConfig(String path) {
        return coreSpigot.getConfig().getBoolean(path);
    }
}
