package ml.bjorn.shadowban;

import org.bukkit.command.CommandSender;

public abstract class Apology extends SubCommand {

    public abstract void handle(CommandSender sender, String[] args);

    public int getMinArgs() { return 1; }

    protected boolean handleDefault(CommandSender sender, String[] args, String configName) {
        if (!sender.hasPermission("shadowban." + configName)) {
            sender.sendMessage(lang("no-permissions"));
            return false;
        }
        String selector = "players." + args[0] + "." + configName;
        if (!config.contains(selector)) {
            sender.sendMessage(langf("player-is-not", args[0], lang("is-" + configName)));
            return false;
        }
        config.set(selector, null);
        plugin.saveConfig();
        sender.sendMessage(langf("player-can-now", args[0], lang("can-" + configName)));
        return true;
    }
}
