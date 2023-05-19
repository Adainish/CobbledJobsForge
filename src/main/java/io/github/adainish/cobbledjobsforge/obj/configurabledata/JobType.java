package io.github.adainish.cobbledjobsforge.obj.configurabledata;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JobType
{
    public HashMap<String, ActionKey> actionKeys = new HashMap<>();
    public String prettyTitle = "";
    public List<String> description = new ArrayList<>();
    public ItemStack displayStack = new ItemStack(Items.DIRT);
    public int displayOrder = 0;
    public JobType()
    {

    }
}
