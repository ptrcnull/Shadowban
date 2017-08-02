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
        plugin.reloadConfig();
        sender.sendMessage("§aKonfiguracja przeladowana!");
        return true;
    }
}
