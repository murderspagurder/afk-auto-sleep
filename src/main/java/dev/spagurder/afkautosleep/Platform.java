package dev.spagurder.afkautosleep;

//? fabric {
/*import dev.spagurder.afkautosleep.fabric.FabricPlatformImpl;
*///?}
//? neoforge {
import dev.spagurder.afkautosleep.neoforge.NeoforgePlatformImpl;
//?}

public interface Platform {

    //? fabric {
    /*Platform INSTANCE = new FabricPlatformImpl();
    *///?}
    //? neoforge {
    Platform INSTANCE = new NeoforgePlatformImpl();
    //?}


    boolean isModLoaded(String modid);
    String loader();

}
