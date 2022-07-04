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
public class GameSettingCMDs implements CommandExecutor {

    public GameSettingCMDs(Plugin plugin){
        plugin.getCommand("gameSetting").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage("Только игрок.");
            return true;
        }

        Player player = (Player) sender;

        if (player.isOp()) {
            if (args[0].equalsIgnoreCase("create")) {
                if (args.length == 1) {
                    player.sendMessage("Укажи имя арены");
                    return true;
                }

                Arena.add(args[1]);
                player.sendMessage("Арена создана!");
                return true;
            } else if (args[0].equalsIgnoreCase("setPos1")) {
                if (args.length == 1) {
                    player.sendMessage("Укажи имя арены");
                    return true;
                }

                Arena arena = Arena.get(args[1]);
                if (arena == null) {
                    player.sendMessage("Такой арены нету!");
                    return true;
                }

                arena.setPos1(player.getLocation());
                player.sendMessage("Позиция один установлена!");
                return true;
            } else if (args[0].equalsIgnoreCase("setPos2")) {
                if (args.length == 1) {
                    player.sendMessage("Укажи имя арены");
                    return true;
                }

                Arena arena = Arena.get(args[1]);
                if (arena == null) {
                    player.sendMessage("Такой арены нету!");
                    return true;
                }

                arena.setPos2(player.getLocation());
                player.sendMessage("Позиция два установлена!");
                return true;
            } else if (args[0].equalsIgnoreCase("setLobby")) {
                if (args.length == 1) {
                    player.sendMessage("Укажи имя арены");
                    return true;
                }

                Arena arena = Arena.get(args[1]);
                if (arena == null) {
                    player.sendMessage("Такой арены нету!");
                    return true;
                }

                arena.setLobby(player.getLocation());
                player.sendMessage("Лобби установлено!");
                return true;
            } else if (args[0].equalsIgnoreCase("setAttackersSpawn")) {
                if (args.length == 1) {
                    player.sendMessage("Укажи имя арены");
                    return true;
                }

                Arena arena = Arena.get(args[1]);
                if (arena == null) {
                    player.sendMessage("Такой арены нету!");
                    return true;
                }

                arena.setAttackersSpawn(player.getLocation());
                player.sendMessage("Спавн атакующих установлено!");
                return true;
            } else if (args[0].equalsIgnoreCase("launch")) {
                if (args.length == 1) {
                    player.sendMessage("Укажи имя арены");
                    return true;
                }

                Arena arena = Arena.get(args[1]);
                if (arena == null) {
                    player.sendMessage("Такой арены нету!");
                    return true;
                }

                if (arena.launch())
                    player.sendMessage("Арена запущена!");
                else
                    player.sendMessage("Невозможно запустить. Возможно, ты забыл указать лобби, точку спавна атакующих или карта слишком маленькая");

                return true;
            }
        }
        else {
            player.sendMessage("Только админ может это использовать");
        }

        return false;
    }
}
