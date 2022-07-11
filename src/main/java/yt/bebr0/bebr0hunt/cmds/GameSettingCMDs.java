package yt.bebr0.bebr0hunt.cmds;

import org.bukkit.Bukkit;
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
public class GameSettingCMDs implements CommandExecutor {

    public GameSettingCMDs(Plugin plugin){
        plugin.getCommand("gameSetting").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            ChatUtil.sendMessage(sender, "Только игрок может выполнять эту команду!", true);
            return true;
        }

        Player player = (Player) sender;

        if (player.isOp()) {
            if (args[0].equalsIgnoreCase("list")){
                for (Arena arena: Arena.getArenas()){
                    ChatUtil.sendMessage(player, "- " + arena.getName(), false);
                }
            }
            else if (args[0].equalsIgnoreCase("joinAll")){
                if (args.length == 1) {
                    ChatUtil.sendMessage(player, "Укажи имя арены", true);
                    return true;
                }

                Arena arena = Arena.get(args[1]);
                if (arena == null) {
                    ChatUtil.sendMessage(player, "Такой арены не существует!", true);
                    return true;
                }

                for (Player onlinePlayer: Bukkit.getOnlinePlayers()){
                    arena.join(onlinePlayer);
                }

                ChatUtil.sendMessage(player, "Выполнено!", false);
            }
            else if (args[0].equalsIgnoreCase("create")) {
                if (args.length == 1) {
                    ChatUtil.sendMessage(player, "Укажи имя арены", true);
                    return true;
                }

                Arena arena = Arena.get(args[1]);
                if (arena != null) {
                    ChatUtil.sendMessage(player, "Арена с таким именем уже существует!", true);

                    return true;
                }

                Arena.add(args[1]);

                ChatUtil.sendMessage(player, "Арена создана!", false);
                return true;
            } else if (args[0].equalsIgnoreCase("setPos1")) {
                if (args.length == 1) {
                    ChatUtil.sendMessage(player, "Укажи имя арены", true);
                    return true;
                }

                Arena arena = Arena.get(args[1]);
                if (arena == null) {
                    ChatUtil.sendMessage(player, "Такой арены не существует", true);

                    return true;
                }

                arena.setPos1(player.getLocation());
                ChatUtil.sendMessage(player, "Первая позиция установлена", false);
                return true;
            } else if (args[0].equalsIgnoreCase("setPos2")) {
                if (args.length == 1) {
                    ChatUtil.sendMessage(player, "Укажи имя арены", true);
                    return true;
                }

                Arena arena = Arena.get(args[1]);
                if (arena == null) {
                    ChatUtil.sendMessage(player, "Такой арены не существует", true);
                    return true;
                }

                arena.setPos2(player.getLocation());
                ChatUtil.sendMessage(player, "Вторая позиция установлена", false);

                return true;
            } else if (args[0].equalsIgnoreCase("setLobby")) {
                if (args.length == 1) {
                    ChatUtil.sendMessage(player, "Укажи имя арены", true);
                    return true;
                }

                Arena arena = Arena.get(args[1]);
                if (arena == null) {
                    ChatUtil.sendMessage(player, "Такой арены не существует", true);
                    return true;
                }

                arena.setLobby(player.getLocation());
                ChatUtil.sendMessage(player, "Лобби установлено", false);

                return true;
            } else if (args[0].equalsIgnoreCase("setAttackersSpawn")) {
                if (args.length == 1) {
                    ChatUtil.sendMessage(player, "Укажи имя арены", true);
                    return true;
                }

                Arena arena = Arena.get(args[1]);
                if (arena == null) {
                    ChatUtil.sendMessage(player, "Такой арены не существует", true);
                    return true;
                }

                arena.setAttackersSpawn(player.getLocation());
                ChatUtil.sendMessage(player, "Спавн атакующих установлен", false);

                return true;
            } else if (args[0].equalsIgnoreCase("launch")) {
                if (args.length == 1) {
                    ChatUtil.sendMessage(player, "Укажи имя арены", true);
                    return true;
                }

                Arena arena = Arena.get(args[1]);
                if (arena == null) {
                    ChatUtil.sendMessage(player, "Такой арены не существует", true);
                    return true;
                }

                if (arena.launch())
                    ChatUtil.sendMessage(player, "Арена запущена", false);

                else
                    ChatUtil.sendMessage(player, "Невозможно запустить. Возможно, ты забыл указать лобби, " +
                            "точку спавна атакующих или карта слишком маленькая", true);

                return true;
            }
        }
        else {
            ChatUtil.sendMessage(player, "Недостаточно прав!", true);
        }

        return false;
    }
}
