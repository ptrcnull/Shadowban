package ml.bjorn.shadowban.commands;

import ml.bjorn.shadowban.SubCommand;
import org.bukkit.command.CommandSender;

public class Version extends SubCommand {

    public int getMinArgs() {
        return 0;
    }

    public void handle(CommandSender sender, String[] args) {
        sender.sendMessage("Shadowban v" + plugin.getDescription().getVersion());
    }
}
