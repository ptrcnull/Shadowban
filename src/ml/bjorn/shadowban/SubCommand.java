package ml.bjorn.shadowban;

import org.bukkit.command.CommandSender;

public interface SubCommand {
    public int getMinArgs();

    public boolean handle(CommandSender sender, String[] args);
}
