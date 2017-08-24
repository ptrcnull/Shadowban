package ml.bjorn.shadowban;

import org.bukkit.command.CommandSender;

public class HelpSubCommand implements SubCommand {

    @Override
    public int getMinArgs(){
        return 0;
    }

    @Override
    public boolean handle(CommandSender sender, String[] args) {
        sender.sendMessage("§aPomoc dla pluginu Shadowban");
        sender.sendMessage("§a help - wyswietla ta wiadomosc");
        if(sender.hasPermission("shadowban.see.others")) {
            sender.sendMessage("§a show [gracz] - pokazuje aktywne kary na koncie");
        } else {
            sender.sendMessage("§a show - pokazuje aktywne kary na twoim koncie");
        }
        if(sender.hasPermission("shadowban.mute")) {
            sender.sendMessage("§a mute <gracz> [czas] [powod] - wycisza gracza");
            sender.sendMessage("§a unmute <gracz> - odwycisza gracza");
        }
        if(sender.hasPermission("shadowban.ban")){
            sender.sendMessage("§a ban <gracz> [czas] [powod] - banuje gracza");
        }

        if(sender.hasPermission("shadowban.silent")) {
            sender.sendMessage("§a silent - przelacza tryb cichy");
        }
        return true;
    }
}
