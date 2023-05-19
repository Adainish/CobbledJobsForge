package io.github.adainish.cobbledjobsforge.util;

import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import io.github.adainish.cobbledjobsforge.CobbledJobsForge;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Util
{
    public static ServerPlayer getPlayer(UUID uuid) {
        return CobbledJobsForge.getServer().getPlayerList().getPlayer(uuid);
    }

    public static boolean isOnline(UUID uuid)
    {
        return CobbledJobsForge.getServer().getPlayerList().getPlayer(uuid) != null;
    }
    public static ItemStack returnIcon(Pokemon pokemon) {
        return PokemonItem.from(pokemon, 1);
    }


    public static void send(ServerPlayer sender, String message) {
        sender.sendSystemMessage(Component.literal(((TextUtil.getMessagePrefix()).getString() + message).replaceAll("&([0-9a-fk-or])", "\u00a7$1")));
    }
    public static void send(CommandSourceStack sender, String message) {
        sender.sendSystemMessage(Component.literal(((TextUtil.getMessagePrefix()).getString() + message).replaceAll("&([0-9a-fk-or])", "\u00a7$1")));
    }
    public static String formattedString(String s) {
        return s.replaceAll("&", "§");
    }

    public static List<String> formattedArrayList(List<String> list) {

        List<String> formattedList = new ArrayList<>();
        for (String s:list) {
            formattedList.add(formattedString(s));
        }

        return formattedList;
    }
}
