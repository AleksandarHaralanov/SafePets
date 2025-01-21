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
        List<String> alivePets = getAlivePets().getStringList(event.getPlayer().getName(), new ArrayList<>());
        List<String> deadPets = getDeadPets().getStringList("dead-pets", new ArrayList<>());

        ArrayList<String> matchedPets = new ArrayList<>(alivePets);
        matchedPets.retainAll(deadPets);

        if (!matchedPets.isEmpty()) {
            alivePets.removeAll(matchedPets);
            deadPets.removeAll(matchedPets);

            getAlivePets().setProperty(event.getPlayer().getName(), alivePets);
            getAlivePets().save();

            getDeadPets().setProperty("dead-pets", deadPets);
            getDeadPets().save();
        }
    }
}