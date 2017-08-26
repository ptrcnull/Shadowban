package ml.bjorn.shadowban;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class Apology implements SubCommand {
    private Main plugin = Main.plugin;
    private FileConfiguration config = plugin.getConfig();
    private String configName;

    Apology (String configName) {
        this.configName = configName;
    }

    @Override
    public int getMinArgs() {
        return 1;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if (!sender.hasPermission("shadowban." + configName)) {
            sender.sendMessage("§cBrak uprawnien.");
            return true;
        }
        if (!config.contains(configName + "." + args[0])) {
            String message = "§cGracz " + args[0] + " nie jest ";
            switch (configName) {
                case "mute": message += "wyciszony"; break;
                case "ban": message += "zbanowany"; break;
                case "jail": message += "uwieziony"; break;
            }
            sender.sendMessage(message);
            return true;
        }
        config.set(configName + "." + args[0], null);
        plugin.saveConfig();
        String message = "§aGracz " + args[0];
        switch (configName) {
            case "mute": message += " znow moze mowic."; break;
            case "ban": message += " znow moze chodzic."; break;
            case "jail": message += " znow moze sie ruszac."; break;
        }
        sender.sendMessage(message);
        return true;
    }
}
