package io.github.aleksandarharalanov.safepets.listener;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityListener;

import java.util.ArrayList;
import java.util.List;

import static io.github.aleksandarharalanov.safepets.SafePets.getAlivePets;
import static io.github.aleksandarharalanov.safepets.util.AccessUtil.hasPermission;
import static io.github.aleksandarharalanov.safepets.util.ColorUtil.translate;

public class EntityDamageListener extends EntityListener {

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Wolf)) return;

        Wolf wolf = (Wolf) entity;
        if (!wolf.isTamed()) return;

        Player attacker = getAttacker(event);
        if (attacker == null) return;

        String petUniqueId = wolf.getUniqueId().toString();
        List<String> attackerPets = getAlivePets().getStringList(attacker.getName(), new ArrayList<>());
        if ((attackerPets == null || !attackerPets.contains(petUniqueId)) && !hasPermission(attacker, "safepets.bypass")) {
            event.setCancelled(true);
            attacker.sendMessage(translate("&cYou can't harm this pet; it doesn't belong to you."));
        }
    }

    private static Player getAttacker(EntityDamageEvent event) {
        Player attacker = null;
        if (event instanceof EntityDamageByEntityEvent) {
            EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) event;
            Entity damager = damageByEntityEvent.getDamager();
            if (damager instanceof Player) attacker = (Player) damager;
            else if (damager instanceof Arrow) {
                Entity shooter = ((Arrow) damager).getShooter();
                if (shooter instanceof Player) attacker = (Player) shooter;
            }
        }
        return attacker;
    }
}
