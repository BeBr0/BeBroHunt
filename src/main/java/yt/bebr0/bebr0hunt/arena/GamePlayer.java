package yt.bebr0.bebr0hunt.arena;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import yt.bebr0.bebr0hunt.util.ChatUtil;

/*
    Made by BeBr0
    Check out my youtube - https://www.youtube.com/c/BeBr0 
*/
public class GamePlayer {

    private int targetDamage = 0;
    private Role role;

    private Player player;

    public GamePlayer(Player player, Role role) {
        this.role = role;
        this.player = player;

        player.sendTitle(ChatColor.translateAlternateColorCodes('&', "Твоя роль - " + role.getName()),
                ChatColor.translateAlternateColorCodes('&', "Твоя роль - " + role.getObjective()), 40, 10, 40);
    }

    public int getTargetDamage() {
        return targetDamage;
    }

    public void setTargetDamage(int targetDamage) {
        this.targetDamage = targetDamage;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
