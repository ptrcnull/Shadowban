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
        sender.sendMessage("§a version - wyswietla wersje pluginu");
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
            sender.sendMessage("§a unban <gracz> - odbanowuje gracza");
        }

        if(sender.hasPermission("shadowban.jail")){
            sender.sendMessage("§a jail <gracz> [czas] [powod] - wiezi gracza");
            sender.sendMessage("§a unjail <gracz> - uwalnia gracza");
        }

        if(sender.hasPermission("shadowban.silent")) {
            sender.sendMessage("§a silent - przelacza tryb cichy");
        }

        if(sender.hasPermission("shadowban.reload")) {
            sender.sendMessage("§a reload - przeladowuje konfiguracje pluginu");
        }

        return true;
    }
}
