package io.github.adainish.cobbledjobsforge.listener;

import com.cobblemon.mod.common.entity.pokemon.PokemonEntity;
import io.github.adainish.cobbledjobsforge.enumerations.JobAction;
import io.github.adainish.cobbledjobsforge.obj.data.Player;
import io.github.adainish.cobbledjobsforge.storage.PlayerStorage;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Listener
{

    @SubscribeEvent
    public void onFishEvent(ItemFishedEvent event)
    {
        if (event.isCanceled())
            return;
        try {
            Player player = PlayerStorage.getPlayer(event.getEntity().getUUID());
            if (player != null) {
                //update job data for fishing
                event.getDrops().forEach(stack -> {
                    ResourceLocation location = Registry.ITEM.getKey(stack.getItem());
                    player.updateJobData(JobAction.Mine, location.toString());
                });
                player.updateCache();
            }
        } catch (Exception e) {

        }
    }

    @SubscribeEvent
    public void onKillEvent(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayer) {
            try {
                Player player = PlayerStorage.getPlayer(event.getSource().getEntity().getUUID());
                if (player != null) {
                    //update job data for killing
                    ResourceLocation location = null;
                    LivingEntity killedEntity = event.getEntity();
                    if (event.getEntity() instanceof PokemonEntity) {
                        location = ((PokemonEntity) killedEntity).getPokemon().getSpecies().getResourceIdentifier();
                    } else {
                        location = Registry.ENTITY_TYPE.getKey(event.getEntity().getType());
                    }
                    player.updateJobData(JobAction.Kill, location.toString());
                    player.updateCache();
                }
            } catch (Exception e) {

            }
        }
    }
    @SubscribeEvent
    public void onMineEvent(BlockEvent.BreakEvent event)
    {
        if (event.isCanceled())
            return;
        try {
            Player player = PlayerStorage.getPlayer(event.getPlayer().getUUID());
            if (player != null) {
                //update job data for mining

                Block block = event.getState().getBlock();
                ResourceLocation location = Registry.ITEM.getKey(block.asItem());
                player.updateJobData(JobAction.Mine, location.toString());
                player.updateCache();
            }
        } catch (Exception e) {

        }
    }

    @SubscribeEvent
    public void onCraftEvent(PlayerEvent.ItemCraftedEvent event)
    {
     if (event.isCanceled())
         return;

        try {
            Player player = PlayerStorage.getPlayer(event.getEntity().getUUID());
            if (player != null) {
                //update job data for mining

                ResourceLocation location = Registry.ITEM.getKey(event.getCrafting().getItem());
                player.updateJobData(JobAction.Craft, location.toString());
                player.updateCache();
            }
        } catch (Exception e) {

        }
    }

}
