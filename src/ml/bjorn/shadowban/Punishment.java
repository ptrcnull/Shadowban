package ml.bjorn.shadowban;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.Objects;

public class Punishment implements SubCommand {
    private Main plugin = Main.plugin;
    private FileConfiguration config = plugin.getConfig();

    private String configName;

    Punishment (String configName) {
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
        Long time;
        String reason;
        if (args.length < 2) {
            time = 0L;
            reason = "";
        } else {
            time = EndTimeCalculator.get(args[1]);
            if (time == 0L) {
                reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            } else {
                reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
            }
        }
        config.addDefault("silent." + sender.getName(), false);
        plugin.saveConfig();
        Boolean silent = config.getBoolean("silent." + sender.getName());
        String node = configName + "." + args[0];
        config.set(node + ".reason", reason);
        config.set(node + ".by", sender.getName());
        config.set(node + ".end", time);
        config.set(node + ".silent", silent);
        plugin.saveConfig();
        String message = "§fGracz §e" + args[0] + " §fzostal ";
        switch (configName) {
            case "mute": message += "wyciszony"; break;
            case "ban": message += "zbanowany"; break;
            case "jail": message += "uwieziony"; break;
        }
        if (time != 0L) {
            message += " na §e" + args[1];
        }
        if (!Objects.equals(reason, "")){
            message += " §fza §e" + reason;
        }
        if (!silent) {
            plugin.getServer().broadcastMessage("§7[§6§lJada§e§lSwiry§7] " + message);
        } else {
            sender.sendMessage(message);
        }

        if (Objects.equals(configName, "ban")) {
            plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), "spawn " + args[0]);
        }

        return true;
    }
}
