package ml.bjorn.shadowban;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.Objects;

public class MuteSubCommand implements SubCommand {
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
        config.set("mute." + args[1] + ".reason", reason);
        config.set("mute." + args[0] + ".by", sender.getName());
        config.set("mute." + args[0] + ".end", time);
        config.set("mute." + args[0] + ".silent", silent);
        plugin.saveConfig();
        String message = "§fGracz §e" + args[0] + " §fzostal wyciszony";
        if (time != 0L) {
            message = message + " na §e" + args[1];
        }
        if (!Objects.equals(reason, "")){
            message = message + " §fza §e" + reason;
        }
        if (!silent) {
            plugin.getServer().broadcastMessage("§7[§6§lJada§e§lSwiry§7] " + message);
        }
        sender.sendMessage(message);
        return true;
    }
}
