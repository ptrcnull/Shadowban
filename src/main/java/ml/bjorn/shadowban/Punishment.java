package ml.bjorn.shadowban;

import com.google.common.collect.ImmutableSet;

import org.bukkit.command.CommandSender;

import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

public abstract class Punishment extends SubCommand {

    public abstract void handle(CommandSender sender, String[] args);

    public int getMinArgs() { return 1; }

    protected boolean handleDefault(CommandSender sender, String[] args, String configName) {
        if (!sender.hasPermission("shadowban." + configName)) {
            sender.sendMessage(lang("no-permissions"));
            return false;
        }
        if (plugin.getServer().getPlayerExact(args[0]) == null) {
            sender.sendMessage(lang("player-not-found"));
            return false;
        }
        Long time;
        String reason;
        if (args.length < 2) {
            time = 0L;
            reason = "";
        } else {
            time = getTime(args[1]);
            if (time == 0L) {
                reason = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
            } else {
                reason = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
            }
        }

        String silentSel = "players." + sender.getName() + ".silent";
        config.addDefault(silentSel, false);
        String selector = "players." + args[0] + "." + configName;
        config.set(selector + ".reason", reason);
        config.set(selector + ".by", sender.getName());
        config.set(selector + ".end", time);
        config.set(selector + ".silent", config.getBoolean(silentSel));
        plugin.saveConfig();

        String message = langf("player-is-now", args[0], lang("is-" + configName));
        if (time != 0L) {
            message += langf("time", args[1]);
        }
        if (!Objects.equals(reason, "")){
            message += langf("reason", reason);
        }
        if (!config.getBoolean(silentSel)) {
            plugin.getServer().broadcastMessage(langf("prefix", message));
        } else {
            sender.sendMessage(message);
        }
        return true;
    }

    private long getTime(String input){
        Set<String> units = ImmutableSet.of("d", "h", "m", "s");
        Long length;
        try {
            length = Long.parseLong(input.substring(0, input.length() - 1));
        } catch (Throwable error) {
            return 0;
        }
        String unit = input.substring(input.length() - 1);
        if(!units.contains(unit)){
            return 0;
        }
        switch(unit){
            case "d": length = length * 24 * 60 * 60 * 1000; break;
            case "h": length = length * 60 * 60 * 1000; break;
            case "m": length = length * 60 * 1000; break;
            case "s": length = length * 1000; break;
        }
        return Instant.now().toEpochMilli() + length;
    }
}
