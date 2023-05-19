package io.github.adainish.cobbledjobsforge.config;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import io.github.adainish.cobbledjobsforge.CobbledJobsForge;
import io.github.adainish.cobbledjobsforge.obj.configurabledata.JobManager;
import io.github.adainish.cobbledjobsforge.util.Adapters;

import java.io.*;

public class JobsConfig
{
    public JobManager jobManager;

    public JobsConfig()
    {
        jobManager = new JobManager();
        jobManager.initDefaults();

    }


    public static void writeConfig()
    {
        File dir = CobbledJobsForge.getConfigDir();
        dir.mkdirs();
        Gson gson  = Adapters.PRETTY_MAIN_GSON;
        JobsConfig config = new JobsConfig();
        try {
            File file = new File(dir, "jobs.json");
            if (file.exists())
                return;
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            String json = gson.toJson(config);
            writer.write(json);
            writer.close();
        } catch (IOException e)
        {
            CobbledJobsForge.getLog().warn(e);
        }
    }

    public static JobsConfig getConfig()
    {
        File dir = CobbledJobsForge.getConfigDir();
        dir.mkdirs();
        Gson gson  = Adapters.PRETTY_MAIN_GSON;
        File file = new File(dir, "jobs.json");
        JsonReader reader = null;
        try {
            reader = new JsonReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            CobbledJobsForge.getLog().error("Something went wrong attempting to read the Jobs Config");
            return null;
        }

        return gson.fromJson(reader, JobsConfig.class);
    }
}
