package yt.bebr0.bebr0hunt.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import yt.bebr0.bebr0hunt.arena.Arena;
import yt.bebr0.bebr0hunt.arena.Game;
import yt.bebr0.bebr0hunt.arena.Role;

/*
    Made by BeBr0
    Check out my youtube - https://www.youtube.com/c/BeBr0 
*/
public class ArenaEvents implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK){
            Player player = event.getPlayer();
            Arena arena = Arena.get(player);
            if (arena != null) {
                if (player.getInventory().getItemInMainHand().isSimilar(Game.compass)) {
                    player.setCompassTarget(arena.getGame().getTarget().getPlayer().getLocation());
                }
            }
        }
    }

    @EventHandler
    public void onAttack(EntityDamageByEntityEvent event){
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            Arena arena = Arena.get((Player) event.getDamager());

            Player damager = (Player) event.getDamager();
            Player player = (Player) event.getEntity();

            if (arena != null){
                if (!arena.isOpen()) {
                    event.setCancelled(true);
                    return;
                }

                if (arena.getGame().getRole(damager) == arena.getGame().getRole(player) ||
                        (arena.getGame().getRole(damager) == Role.GUARD && arena.getGame().getRole(player) == Role.TARGET) ||
                        (arena.getGame().getRole(damager) == Role.TARGET && arena.getGame().getRole(player) == Role.GUARD)){

                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onLeave(PlayerMoveEvent event){
        Player player = event.getPlayer();
        Arena arena = Arena.get(player);
        if (arena != null) {
            if (event.getTo() != null) {
                if (event.getTo().getX() > arena.getPos2().getX() || event.getTo().getZ() > arena.getPos2().getZ() ||
                        event.getTo().getX() < arena.getPos1().getX() || event.getTo().getZ() < arena.getPos1().getZ()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onServerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        Arena arena = Arena.get(player);

        if (arena != null){
            arena.leave(player);
        }
    }

    @EventHandler
    public void onTargetDeath(PlayerDeathEvent event){
        Player player = event.getEntity();
        Arena arena = Arena.get(player);

        if (arena != null){
            if (arena.getGame().getTarget().getPlayer().equals(player)){
                arena.getGame().onTargetDeath(player.getKiller());
            }
        }
    }
}
