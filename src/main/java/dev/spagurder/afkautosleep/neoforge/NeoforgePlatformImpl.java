package dev.spagurder.afkautosleep.neoforge;

//? neoforge {
import dev.spagurder.afkautosleep.Platform;
import net.neoforged.fml.ModList;

public class NeoforgePlatformImpl implements Platform {

    @Override
    public boolean isModLoaded(String modid) {
        return ModList.get().isLoaded(modid);
    }

    @Override
    public String loader() {
        return "neoforge";
    }

}
//?}