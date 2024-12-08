package io.github.aleksandarharalanov.petx.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Wolf;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;

public class EntityDeathListener extends EntityListener {
    @Override
    public void onEntityDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (!(entity instanceof Wolf)) return;

        Wolf wolf = (Wolf) entity;
        if (!wolf.isTamed()) return;

        String petUniqueId = wolf.getUniqueId().toString();
        // TODO
    }
}
