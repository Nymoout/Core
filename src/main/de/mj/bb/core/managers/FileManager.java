package main.de.mj.bb.core.managers;

import lombok.Getter;
import main.de.mj.bb.core.CoreSpigot;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Getter
public class FileManager {

    private final CoreSpigot coreSpigot;
    private File portalFile = new File("plugins/BBCoreSpigot/", "portal.yml");
    private YamlConfiguration portalConfig = YamlConfiguration.loadConfiguration(portalFile);

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

    public void reloadConfig() {
        coreSpigot.reloadConfig();
    }

    public boolean getBooleanFormConfig(String path) {
        return coreSpigot.getConfig().getBoolean(path);
    }
}
