package io.github.adainish.cobbledjobsforge;

import ca.landonjw.gooeylibs2.api.tasks.Task;
import com.cobblemon.mod.common.api.Priority;
import com.cobblemon.mod.common.api.events.CobblemonEvents;
import com.mojang.logging.LogUtils;
import io.github.adainish.cobbledjobsforge.cmd.Command;
import io.github.adainish.cobbledjobsforge.config.JobsConfig;
import io.github.adainish.cobbledjobsforge.config.LanguageConfig;
import io.github.adainish.cobbledjobsforge.listener.Listener;
import io.github.adainish.cobbledjobsforge.subscriptions.EventSubscriptions;
import io.github.adainish.cobbledjobsforge.tasks.PlayerDataTask;
import io.github.adainish.cobbledjobsforge.wrapper.DataWrapper;
import kotlin.Unit;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLConfig;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(CobbledJobsForge.MODID)
public class CobbledJobsForge {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "cobbledjobsforge";
    // Directly reference a slf4j logger
    public static CobbledJobsForge instance;
    public static final String MOD_NAME = "CobbledJobs";
    public static final String VERSION = "1.0.0-Beta";
    public static final String AUTHORS = "Winglet";
    public static final String YEAR = "2023";
    private static final Logger log = LogManager.getLogger(MOD_NAME);
    private static File configDir;
    private static File storage;
    private static File playerStorageDir;
    private static MinecraftServer server;
    public static JobsConfig jobsConfig;
    public static LanguageConfig languageConfig;
    public static DataWrapper dataWrapper;

    public static EventSubscriptions eventSubscriptions;



    public static org.apache.logging.log4j.Logger getLog() {
        return log;
    }

    public static MinecraftServer getServer() {
        return server;
    }

    public static void setServer(MinecraftServer server) {
        CobbledJobsForge.server = server;
    }

    public static File getConfigDir() {
        return configDir;
    }

    public static void setConfigDir(File configDir) {
        CobbledJobsForge.configDir = configDir;
    }

    public static File getStorage() {
        return storage;
    }

    public static void setStorage(File storage) {
        CobbledJobsForge.storage = storage;
    }

    public static File getPlayerStorageDir() {
        return playerStorageDir;
    }

    public static void setPlayerStorageDir(File playerStorageDir) {
        CobbledJobsForge.playerStorageDir = playerStorageDir;
    }

    public CobbledJobsForge() {
        instance = this;
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        log.info("Booting up %n by %authors %v %y"
                .replace("%n", MOD_NAME)
                .replace("%authors", AUTHORS)
                .replace("%v", VERSION)
                .replace("%y", YEAR)
        );
    }

    @SubscribeEvent
    public void onCommandRegistry(RegisterCommandsEvent event) {

        //register commands
        event.getDispatcher().register(Command.getCommand());

    }


    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event)
    {
        setServer(ServerLifecycleHooks.getCurrentServer());
        reload();

        CobblemonEvents.SERVER_STARTED.subscribe(Priority.NORMAL, minecraftServer -> {
            setServer(minecraftServer);
            //init subscriptions
            eventSubscriptions = new EventSubscriptions();
            MinecraftForge.EVENT_BUS.register(new Listener());
            dataWrapper = new DataWrapper();
            reload();
            Task.builder().execute(new PlayerDataTask()).infinite().interval((20 * 60) * 30).build();
            return Unit.INSTANCE;
        });

    }

    public void initDirs() {
        getLog().warn("Writing paths/directories if they don't exist");
        setConfigDir(new File(FMLPaths.GAMEDIR.get().resolve(FMLConfig.defaultConfigPath()) + "/CobbledJobs/"));
        getConfigDir().mkdir();
        setStorage(new File(getConfigDir(), "/storage/"));
        getStorage().mkdirs();
        setPlayerStorageDir(new File(storage, "/playerdata/"));
        getPlayerStorageDir().mkdirs();
    }



    public void initConfigs() {
        log.warn("Loading Config Files");
        JobsConfig.writeConfig();
        jobsConfig = JobsConfig.getConfig();
        LanguageConfig.writeConfig();
        languageConfig = LanguageConfig.getConfig();
    }

    public void reload() {
        initDirs();
        initConfigs();

    }

}
