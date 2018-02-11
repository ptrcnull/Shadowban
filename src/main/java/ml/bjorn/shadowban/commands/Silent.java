package ml.bjorn.shadowban.commands;

import ml.bjorn.shadowban.SubCommand;
import org.bukkit.command.CommandSender;

public class Silent extends SubCommand {

    public int getMinArgs() {
        return 0;
    }

    public void handle(CommandSender sender, String[] args) {
        if (!sender.hasPermission("shadowban.silent")) {
            sender.sendMessage(lang("no-permissions"));
            return;
        }
        String path = "players." + sender.getName() + ".silent";
        config.addDefault(path, false);
        if(config.getBoolean(path, true)) {
            config.set(path, false);
            sender.sendMessage(langf("muted", lang("they-are-not")));
        } else {
            config.set(path, true);
            sender.sendMessage(langf("muted", lang("they-are")));
        }
        plugin.saveConfig();
    }
}
