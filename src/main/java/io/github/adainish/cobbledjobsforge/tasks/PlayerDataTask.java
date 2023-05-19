package io.github.adainish.cobbledjobsforge.tasks;

import io.github.adainish.cobbledjobsforge.CobbledJobsForge;
import io.github.adainish.cobbledjobsforge.obj.data.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerDataTask implements Runnable{
    @Override
    public void run() {
        if (CobbledJobsForge.dataWrapper.playerCache.isEmpty())
            return;

        List<Player> toUpdate = new ArrayList<>();

        CobbledJobsForge.dataWrapper.playerCache.forEach((key, player) -> toUpdate.add(player));

        toUpdate.forEach(Player::save);
    }
}
