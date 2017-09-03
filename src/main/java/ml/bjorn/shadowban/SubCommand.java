package ml.bjorn.shadowban;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

public abstract class SubCommand {
    public abstract int getMinArgs();
    public abstract void handle(CommandSender sender, String[] args);

    protected Main plugin = Main.plugin;
    protected FileConfiguration config = Main.config;

    protected String lang(String path) { return ChatColor.translateAlternateColorCodes('&', Main.lang.getString(path)); }
    protected String langf(String path, String... args) { return String.format(lang(path), (Object[]) args); }
}
