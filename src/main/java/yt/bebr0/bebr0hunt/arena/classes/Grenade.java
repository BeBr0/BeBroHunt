package yt.bebr0.bebr0hunt.arena.classes;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import yt.bebr0.bebr0hunt.util.ItemUtil;

import java.util.List;

public class Grenade {

    private static final Grenade instance = new Grenade();

    public static Grenade getInstance() {
        return instance;
    }

    private final ItemStack grenadeItem = ItemUtil.createItemStack("&6Граната", List.of("ПКМ, чтобы бросить, взрывается с силой ТНТ"), Material.SNOWBALL);

    private final int grenadePower = 5;
    private final int grenadeCoolDown = 5;

    public ItemStack getGrenadeItem() {
        return grenadeItem;
    }

    public int getGrenadePower() {
        return grenadePower;
    }

    public int getGrenadeCoolDown() {
        return grenadeCoolDown;
    }
}
