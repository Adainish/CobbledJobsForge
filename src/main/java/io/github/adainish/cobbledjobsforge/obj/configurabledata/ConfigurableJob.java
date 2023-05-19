package io.github.adainish.cobbledjobsforge.obj.configurabledata;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigurableJob
{
    public String id = "";
    public int displayOrder = 0;
    public ItemStack displayStack = new ItemStack(Items.DIRT);
    public HashMap<String, JobType> jobTypes = new HashMap<>();
    public HashMap<Integer, ConfigurableLevel> levels = new HashMap<>();

    public String prettyName = "";
    public List<String> description = new ArrayList<>();

    public boolean enabled = true;

    public ConfigurableJob()
    {

    }
}
