package io.github.aleksandarharalanov.petx.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerListener;

import java.util.ArrayList;
import java.util.List;

import static io.github.aleksandarharalanov.petx.PetX.getPets;

public class PlayerInteractEntityListener extends PlayerListener {
    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (!(entity instanceof Wolf)) return;

        Wolf wolf = (Wolf) entity;
        if (!wolf.isTamed()) return;

        Player owner = (Player) wolf.getOwner();
        if (owner == null) return;

        String playerUsername = event.getPlayer().getName();
        String ownerUsername = owner.getName();
        if (playerUsername.equals(ownerUsername)) {
            List<String> petsList = getPets().getStringList(ownerUsername, new ArrayList<>());
            String petUniqueId = wolf.getUniqueId().toString();
            if (!petsList.contains(petUniqueId)) {
                petsList.add(petUniqueId);
                getPets().setProperty(ownerUsername, petsList);
                getPets().save();
            }
        }
    }
}
