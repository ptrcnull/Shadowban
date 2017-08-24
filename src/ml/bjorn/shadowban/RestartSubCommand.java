package ml.bjorn.shadowban;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;

public class RestartSubCommand implements SubCommand {
    private Main plugin = Main.plugin;
    private PluginManager manager = plugin.getServer().getPluginManager();
    @Override
    public int getMinArgs() {
        return 0;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if (!sender.hasPermission("shadowban.restart")) {
            sender.sendMessage("§cBrak uprawnien.");
            return true;
        }
        plugin.saveConfig();
        manager.disablePlugin(plugin);
        manager.enablePlugin(plugin);
        return true;
    }
}
