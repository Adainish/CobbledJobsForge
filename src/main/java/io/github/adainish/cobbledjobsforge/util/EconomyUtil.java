package io.github.adainish.cobbledjobsforge.util;

import net.impactdev.impactor.api.economy.EconomyService;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class EconomyUtil
{
    public static void deduct(UUID uuid, double amount)
    {
        EconomyService economyService = EconomyService.instance();
        try {
            if (economyService.hasAccount(uuid).get())
            {
                economyService.account(uuid).get().withdraw(BigDecimal.valueOf(amount));
            }
        } catch (InterruptedException | ExecutionException e) {
        }
    }

    public static void add(UUID uuid, double amount)
    {
        EconomyService economyService = EconomyService.instance();
        try {
            if (economyService.hasAccount(uuid).get())
            {
                economyService.account(uuid).get().deposit(BigDecimal.valueOf(amount));
            }
        } catch (InterruptedException | ExecutionException e) {
        }
    }

    public static boolean canAfford(UUID uuid, double amount)
    {
        EconomyService economyService = EconomyService.instance();
        try {
            if (economyService.hasAccount(uuid).get())
            {
                return economyService.account(uuid).get().balance().doubleValue() - amount >= 0;
            }
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
        return false;
    }
}
