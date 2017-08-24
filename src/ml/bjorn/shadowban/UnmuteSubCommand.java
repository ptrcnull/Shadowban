package ml.bjorn.shadowban;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class UnmuteSubCommand implements SubCommand {
    private Main plugin = Main.plugin;
    private FileConfiguration config = plugin.getConfig();

    @Override
    public int getMinArgs() {
        return 1;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if (!sender.hasPermission("shadowban.mute")) {
            sender.sendMessage("§cBrak uprawnien.");
        }
        if (!config.contains("mute." + args[0])) {
            sender.sendMessage("§cGracz " + args[0] + " nie jest wyciszony.");
        }
        config.set("mute." + args[0], null);
        plugin.saveConfig();
        sender.sendMessage("§aGracz " + args[0] + " znow moze mowic.");
        return true;
    }
}
