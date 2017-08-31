package ml.bjorn.shadowban;

import org.bukkit.command.CommandSender;

public class ReloadSubCommand implements SubCommand {
    private Main plugin = Main.plugin;

    @Override
    public int getMinArgs() {
        return 0;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        if (!sender.hasPermission("shadowban.reload")) {
            sender.sendMessage("§cBrak uprawnien.");
            return true;
        }
        plugin.reloadConfig();
        sender.sendMessage("§aKonfiguracja przeladowana!");
        return true;
    }
}
