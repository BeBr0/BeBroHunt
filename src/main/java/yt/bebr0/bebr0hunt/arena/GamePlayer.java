package yt.bebr0.bebr0hunt.arena;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

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

        player.sendMessage(ChatColor.GOLD + "[BeBr0Hunt]: " + ChatColor.AQUA + " Вам выдана роль " + role.getName());
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
