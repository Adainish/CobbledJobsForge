package io.github.adainish.cobbledjobsforge.subscriptions;

import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import io.github.adainish.cobbledjobsforge.enumerations.JobAction;
import io.github.adainish.cobbledjobsforge.obj.data.Player;
import io.github.adainish.cobbledjobsforge.storage.PlayerStorage;
import kotlin.Unit;

public class EventSubscriptions
{
    public EventSubscriptions()
    {
        subscribeToPlayerLogin();
        subscribeToPlayerLogout();
        subscribeToCapture();
        subscribeToFainting();
        subscribeToEvolving();
    }

    public void subscribeToEvolving()
    {
        CobblemonEvents.EVOLUTION_COMPLETE.subscribe(Priority.NORMAL, event -> {
            try {
                Player player = PlayerStorage.getPlayer(event.component1().getOwnerPlayer().getUUID());
                if (player != null)
                {
                    //update job data for evolving
                    player.updateJobData(JobAction.Evolve, event.component1().getSpecies().resourceIdentifier.toString());
                    player.updateCache();
                }
            } catch (Exception e)
            {
                return Unit.INSTANCE;
            }

            return Unit.INSTANCE;
        });

    }

    public void subscribeToFainting()
    {
        CobblemonEvents.POKEMON_FAINTED.subscribe(Priority.NORMAL, event -> {
            try {
                Player player = PlayerStorage.getPlayer(event.component1().getOwnerPlayer().getUUID());
                if (player != null)
                {
                    //update job data for killing
                    player.updateJobData(JobAction.Kill, event.component1().getSpecies().resourceIdentifier.toString());
                    player.updateCache();
                }
            } catch (Exception e)
            {
                return Unit.INSTANCE;
            }

            return Unit.INSTANCE;
        });

    }

    public void subscribeToCapture()
    {
        CobblemonEvents.POKEMON_CAPTURED.subscribe(Priority.NORMAL, event -> {
            Player player = PlayerStorage.getPlayer(event.getPlayer().getUUID());
            if (player != null)
            {
                //update job data for capturing
                player.updateJobData(JobAction.Capture, event.component1().getSpecies().resourceIdentifier.toString());
                player.updateCache();
            }
            return Unit.INSTANCE;
        });

    }

    public void subscribeToPlayerLogin()
    {
        CobblemonEvents.PLAYER_JOIN.subscribe(Priority.NORMAL, event -> {


            Player player = PlayerStorage.getPlayer(event.getUUID());
            if (player == null) {
                PlayerStorage.makePlayer(event);
                player = PlayerStorage.getPlayer(event.getUUID());
            }

            if (player != null) {
                player.userName = event.getName().plainCopy().toString();
                player.syncWithJobManager();
                player.updateCache();
            }

            return Unit.INSTANCE;
        });
    }

    public void subscribeToPlayerLogout()
    {
        CobblemonEvents.PLAYER_QUIT.subscribe(Priority.NORMAL, event -> {
            Player player = PlayerStorage.getPlayer(event.getUUID());
            if (player != null) {
                player.save();
            }
            return Unit.INSTANCE;
        });

    }
}
