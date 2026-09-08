package dev.spagurder.afkautosleep;

import dev.spagurder.afkautosleep.core.ClientConfig;
import net.minecraft.client.Minecraft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AfkAutoSleep {

    public static final String MOD_ID = "afkautosleep";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);

    public static void initClient() {
        LOG.info("Initializing {} on {}", MOD_ID, Platform.INSTANCE.loader());
        ClientConfig.load(Minecraft.getInstance().gameDirectory.toPath());
    }

}
