package ml.bjorn.shadowban.commands;

import ml.bjorn.shadowban.SubCommand;
import org.bukkit.command.CommandSender;

public class Reload extends SubCommand {
    public int getMinArgs() {
        return 0;
    }

    public void handle(CommandSender sender, String[] args) {
        if (!sender.hasPermission("shadowban.reload")) {
            sender.sendMessage(lang("no-permissions"));
            return;
        }
        plugin.reloadConfig();
        sender.sendMessage(lang("config-reloaded"));
    }
}
