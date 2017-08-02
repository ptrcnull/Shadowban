package ml.bjorn.shadowban;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public class ReloadSubCommand implements SubCommand {
    private Main plugin;
    ReloadSubCommand(Main instance){
        plugin = instance;
    }

    @Override
    public int getMinArgs() {
        return 0;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        plugin.reloadConfig();
        sender.sendMessage("§aKonfiguracja przeladowana!");
        return true;
    }
}
