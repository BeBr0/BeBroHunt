package yt.bebr0.bebr0hunt.util;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import yt.bebr0.bebr0hunt.Plugin;

public class ChatUtil {

    private static final String signature = "&4[" + Plugin.getInstance().getName() + "] >>> ";
    private static final String errorSignature = "&4[" + Plugin.getInstance().getName() + " &cERROR] >>> ";

    public static void sendMessage(CommandSender player, String msg, boolean isError){
        if (isError)
            player.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', errorSignature + "&6&l" + msg)));
        else
            player.sendMessage(Component.text(ChatColor.translateAlternateColorCodes('&', signature + "&6&l" + msg)));
    }
}
