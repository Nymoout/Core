package main.de.mj.bb.core.managers;

import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private final CoreSpigot coreSpigot;
    private File portalFile = new File("plugins/BBCoreSpigot/", "portal.yml");
    private File logBlockFile = new File("plugins/BBCoreSpigot/", "logBlock.yml");
    private File finishFile = new File("plugins/BBCoreSpigot/", "finished.yml");
    private YamlConfiguration portalConfig = YamlConfiguration.loadConfiguration(portalFile);
    private YamlConfiguration logBlockConfig = YamlConfiguration.loadConfiguration(logBlockFile);
    private YamlConfiguration finishConfig = YamlConfiguration.loadConfiguration(finishFile);

    public FileManager(CoreSpigot coreSpigot) {
        this.coreSpigot = coreSpigot;
    }

    public void loadConfigFile() {
        coreSpigot.getConfig().options().copyDefaults(true);
        coreSpigot.saveDefaultConfig();
    }

    public void loadPortalConfig() {
        if (portalConfig.getConfigurationSection("Portals") == null)
            portalConfig.createSection("Portals");
        try {
            portalConfig.save(portalFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void setBlockConfig(String dateTime, String player, String blockName, int x, int y, int z, boolean placed) {
        if (placed)
            logBlockConfig.set(dateTime, player + " hat den Block " + blockName + " an der Position X: " + x + ", Y: " + y + ", Z:" + z + " gesetzt!");
        else
            logBlockConfig.set(dateTime, player + " hat den Block " + blockName + " an der Position X: " + x + ", Y: " + y + ", Z:" + z + " entfernt!");
        try {
            logBlockConfig.save(logBlockFile);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void reloadConfig() {
        coreSpigot.reloadConfig();
    }

    public boolean getBooleanFormConfig(String path) {
        return coreSpigot.getConfig().getBoolean(path);
    }

    public CoreSpigot getCoreSpigot() {
        return this.coreSpigot;
    }

    public File getPortalFile() {
        return this.portalFile;
    }

    public File getLogBlockFile() {
        return this.logBlockFile;
    }

    public YamlConfiguration getPortalConfig() {
        return this.portalConfig;
    }

    public YamlConfiguration getLogBlockConfig() {
        return this.logBlockConfig;
    }

    public File getFinishFile() {
        return finishFile;
    }

    public YamlConfiguration getFinishConfig() {
        return finishConfig;
    }
}
