package ml.bjorn.shadowban;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main plugin;
    @Override
    public void onEnable() {
        plugin = this;
        this.getCommand("sb").setExecutor(new CommandHandler());
    }

    @Override
    public void onDisable() {
        plugin = null;
        // nothing
    }
}