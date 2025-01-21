package io.github.aleksandarharalanov.safepets.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerListener;

import java.util.ArrayList;
import java.util.List;

import static io.github.aleksandarharalanov.safepets.SafePets.getAlivePets;

public class PlayerInteractEntityListener extends PlayerListener {

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (!(entity instanceof Wolf)) return;

        Wolf wolf = (Wolf) entity;
        if (!wolf.isTamed()) return;

        Player owner = (Player) wolf.getOwner();
        if (owner == null) return;

        String playerName = event.getPlayer().getName();
        String ownerName = owner.getName();
        if (playerName.equals(ownerName)) {
            List<String> petsList = getAlivePets().getStringList(ownerName, new ArrayList<>());
            String petUniqueId = wolf.getUniqueId().toString();
            if (!petsList.contains(petUniqueId)) {
                petsList.add(petUniqueId);
                getAlivePets().setProperty(ownerName, petsList);
                getAlivePets().save();
            }
        }
    }
}
