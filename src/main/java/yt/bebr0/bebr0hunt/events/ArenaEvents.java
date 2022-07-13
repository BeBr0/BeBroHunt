package yt.bebr0.bebr0hunt.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
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
    public void onRightClick(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
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
    public void onAttack(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player damager && event.getEntity() instanceof Player player) {

            Arena arena = Arena.get(damager);

            if (arena != null) {
                if (arena.getGame().getRole(damager) == arena.getGame().getRole(player) ||
                        (arena.getGame().getRole(damager) == Role.GUARD && arena.getGame().getRole(player) == Role.TARGET) ||
                        (arena.getGame().getRole(damager) == Role.TARGET && arena.getGame().getRole(player) == Role.GUARD)) {

                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onLeaveZone(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Arena arena = Arena.get(player);
        if (arena != null) {
            if (!arena.isOpen()) {
                if (event.getTo().getX() > arena.getPos2().getX() || event.getTo().getZ() > arena.getPos2().getZ() ||
                        event.getTo().getX() < arena.getPos1().getX() || event.getTo().getZ() < arena.getPos1().getZ()) {
                    event.setCancelled(true);
                    player.sendMessage(ChatColor.GOLD + "[" + arena.getName() + "]: " + ChatColor.AQUA + "Ты пытаешься покинуть зону игры!");
                }
            }
        }
    }

    @EventHandler
    public void onServerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Arena arena = Arena.get(player);

        if (arena != null) {
            arena.leave(player);
        }
    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onDeath(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player && !event.isCancelled()) {
            Arena arena = Arena.get(player);

            if (arena != null) {
                if (player.getHealth() <= event.getDamage()) {
                    if (arena.getGame().getTarget().getPlayer().equals(player))
                        arena.getGame().onTargetDeath(player);
                    else
                        arena.getGame().onDeath(player, player);

                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler (priority = EventPriority.HIGHEST)
    public void onDeath(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player && event.getDamager() instanceof Player damager) {
            Arena arena = Arena.get(player);

            if (arena != null) {
                if (player.getHealth() <= event.getDamage()) {
                    if (player.getLastDamageCause() != null)
                        player.getLastDamageCause().setCancelled(true);

                    if (arena.getGame().getTarget().getPlayer().equals(player))
                        arena.getGame().onTargetDeath(damager);
                    else
                        arena.getGame().onDeath(player, damager);

                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockBrake(BlockBreakEvent event) {
        if (Arena.get(event.getPlayer()) != null) {
            event.setCancelled(true);
        }
    }
}
