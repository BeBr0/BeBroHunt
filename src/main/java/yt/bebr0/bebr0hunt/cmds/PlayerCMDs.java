package yt.bebr0.bebr0hunt.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yt.bebr0.bebr0hunt.Plugin;
import yt.bebr0.bebr0hunt.arena.Arena;
import yt.bebr0.bebr0hunt.util.ChatUtil;

/*
    Made by BeBr0
    Check out my youtube - https://www.youtube.com/c/BeBr0 
*/
public class PlayerCMDs implements CommandExecutor {

    public PlayerCMDs(Plugin plugin){
        plugin.getCommand("bebroHunt").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            ChatUtil.sendMessage(sender, "Только игрок может выполнять эту команду!", true);
            return true;
        }

        Player player = (Player) sender;
        if (args[0].equalsIgnoreCase("join")){
            if (args.length == 1) {
                ChatUtil.sendMessage(player, "Укажи имя арены", true);
                return true;
            }

            Arena arena = Arena.get(args[1]);
            if (arena == null) {
                ChatUtil.sendMessage(player, "Такой арены не существует.", true);
                return true;
            }

            if (!arena.join(player))
                ChatUtil.sendMessage(player, "Невозможно присоединиться.", true);

            return true;
        }
        else if (args[0].equalsIgnoreCase("leave")){
            Arena arena = Arena.get(player);
            if (arena == null){
                ChatUtil.sendMessage(player, "Ты не на арене.", true);
                return true;
            }

            arena.leave(player);
            ChatUtil.sendMessage(player, "Вы покинули арену.", true);
            return true;
        }

        return false;
    }
}
