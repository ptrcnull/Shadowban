package ml.bjorn.shadowban;

import ml.bjorn.shadowban.messages.PluginFile;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main plugin;
    public static FileConfiguration config;
    static PluginFile lang;

    @Override
    public void onEnable() {
        plugin = this;
        config = getConfig();
        this.getCommand("sb").setExecutor(new CommandHandler());
        getServer().getPluginManager().registerEvents(new EventListener(), this);

        lang = new PluginFile(this, "messages.yml", "messages.yml");
    }

    @Override
    public void onDisable() {
        saveConfig();
        plugin = null;
        // nothing
    }
}