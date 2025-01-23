package io.github.aleksandarharalanov.safepets.listener;

import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerListener;

import java.util.ArrayList;
import java.util.List;

import static io.github.aleksandarharalanov.safepets.SafePets.getAlivePets;
import static io.github.aleksandarharalanov.safepets.SafePets.getDeadPets;

public class PlayerJoinListener extends PlayerListener {

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        String playerName = event.getPlayer().getName();

        List<String> alivePets = getAlivePets().getStringList(playerName, new ArrayList<>());
        List<String> deadPets = getDeadPets().getStringList("dead-pets", new ArrayList<>());

        ArrayList<String> matchedPets = new ArrayList<>(alivePets);
        matchedPets.retainAll(deadPets);

        if (!matchedPets.isEmpty()) {
            alivePets.removeAll(matchedPets);
            deadPets.removeAll(matchedPets);

            getAlivePets().setProperty(playerName, alivePets);
            getAlivePets().save();

            getDeadPets().setProperty("dead-pets", deadPets);
            getDeadPets().save();
        }

        if (alivePets.isEmpty() && getAlivePets().getProperty(playerName) != null) {
            getAlivePets().removeProperty(playerName);
            getAlivePets().save();
        }
    }
}