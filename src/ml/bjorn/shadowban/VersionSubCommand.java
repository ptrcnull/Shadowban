package ml.bjorn.shadowban;

import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;

public class VersionSubCommand implements SubCommand {
    private Main plugin = Main.plugin;
    private PluginManager manager = plugin.getServer().getPluginManager();
    @Override
    public int getMinArgs() {
        return 0;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        sender.sendMessage("Shadowban v0.5.0");
        return true;
    }
}
