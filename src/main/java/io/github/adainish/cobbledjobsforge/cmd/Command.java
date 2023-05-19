package io.github.adainish.cobbledjobsforge.cmd;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.adainish.cobbledjobsforge.CobbledJobsForge;
import io.github.adainish.cobbledjobsforge.obj.data.Player;
import io.github.adainish.cobbledjobsforge.storage.PlayerStorage;
import io.github.adainish.cobbledjobsforge.util.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;

public class Command
{
    public static LiteralArgumentBuilder<CommandSourceStack> getCommand() {
        return Commands.literal("jobs")
                .executes(cc -> {
                    try {
                        Player player = PlayerStorage.getPlayer(cc.getSource().getPlayerOrException().getUUID());
                        if (player != null)
                        {
                            player.viewGUI(cc.getSource().getPlayer());
                        } else {
                            Util.send(cc.getSource(), "&cUnable to load your jobs data...");
                        }
                    } catch (Exception e)
                    {

                    }
                    return 1;
                })
                .then(Commands.literal("reload")
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(4))
                        .executes(cc -> {
                            CobbledJobsForge.instance.reload();
                            Util.send(cc.getSource(), "&eReloaded the jobs add-on, please check the console for any errors.");
                            return 1;
                        })
                )
                ;

    }
}
