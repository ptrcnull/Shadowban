package ml.bjorn.shadowban;

import ml.bjorn.shadowban.commands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.*;
import java.util.stream.Collectors;

public class CommandHandler implements CommandExecutor, TabCompleter {
    private Main plugin = Main.plugin;

    private final Map<String, SubCommand> subcommands = new LinkedHashMap<>();
    {
        subcommands.put("help", new Help());
        subcommands.put("show", new Show());
        subcommands.put("mute", new Mute());
        subcommands.put("unmute", new Unmute());
        subcommands.put("ban", new Ban());
        subcommands.put("unban", new Unban());
        subcommands.put("jail", new Jail());
        subcommands.put("unjail", new Unjail());
        subcommands.put("silent", new Silent());
        subcommands.put("reload", new Reload());
        subcommands.put("version", new Version());
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] arguments) {
        SubCommand subcommand = arguments.length == 0 ? subcommands.get("help") : subcommands.get(arguments[0]);
        if(subcommand == null){
            sender.sendMessage("§cNieprawidłowa komenda! Zobacz /sb help");
            return true;
        }
        String[] subcommandargs = arguments.length != 0 ? Arrays.copyOfRange(arguments, 1, arguments.length) : new String[]{};
        if(subcommandargs.length < subcommand.getMinArgs()) {
            sender.sendMessage("§cZa mało argumentów! Zobacz /sb help");
            return true;
        }
        subcommand.handle(sender, subcommandargs);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        if (strings.length == 1 ) {
            return subcommands.keySet().stream()
                    .filter(subcommand -> subcommand.startsWith(strings[0]))
                    .collect(Collectors.toList());
        } else {
            List<String> players = new ArrayList<>();
            plugin.getServer().getOnlinePlayers().forEach(player -> players.add(player.getName()));
            return players;
        }
    }
}
