package ml.bjorn.shadowban;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class SilentSubCommand implements SubCommand {
    private Main plugin = Main.plugin;
    private FileConfiguration config = plugin.getConfig();

    @Override
    public int getMinArgs() {
        return 0;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if (!sender.hasPermission("shadowban.silent")) {
            sender.sendMessage("§cBrak permisji.");
            return true;
        }
        String path = "silent." + sender.getName();
        if(config.isSet(path) && config.getBoolean(path)) {
            config.set(path, false);
            sender.sendMessage("§aTeraz twoje kary §4nie sa §awyciszone");
        } else {
            config.set(path, true);
            sender.sendMessage("§aTeraz twoje kary §4sa §awyciszone");
        }
        plugin.saveConfig();
        return true;
    }
}
