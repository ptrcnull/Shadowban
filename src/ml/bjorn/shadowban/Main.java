package ml.bjorn.shadowban;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        reloadConfig();
        this.getCommand("sb").setExecutor(new CommandHandler(this));
    }

    @Override
    public void onDisable() {
        //
    }
}