package yt.bebr0.bebr0hunt.cmds;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import yt.bebr0.bebr0hunt.Plugin;
import yt.bebr0.bebr0hunt.arena.Arena;

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
            sender.sendMessage("Только игрок.");
            return true;
        }

        Player player = (Player) sender;
        if (args[0].equalsIgnoreCase("join")){
            if (args.length == 1) {
                player.sendMessage("Укажи имя арены");
                return true;
            }

            Arena arena = Arena.get(args[1]);
            if (arena == null) {
                player.sendMessage("Такой арены нету!");
                return true;
            }

            if (!arena.join(player))
                player.sendMessage("Невозможно присоединиться!");

            return true;
        }
        else if (args[0].equalsIgnoreCase("leave")){
            Arena arena = Arena.get(player);
            if (arena == null){
                player.sendMessage("Ты не на арене!");
                return true;
            }

            arena.leave(player);
            player.sendMessage("Вы покинули арену! :(");
            return true;
        }

        return false;
    }
}
