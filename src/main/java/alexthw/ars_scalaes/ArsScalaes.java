package alexthw.ars_scalaes;

import alexthw.ars_scalaes.identity.IdentityReg;
import alexthw.ars_scalaes.origins.OriginCompat;
import alexthw.ars_scalaes.pehkui.PkCompatHandler;
import alexthw.ars_scalaes.pmmo.PmmoCompatEventHandler;
import alexthw.ars_scalaes.registry.ModRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArsScalaes.MODID)
public class ArsScalaes {
    public static final String MODID = "ars_scalaes";

    public ArsScalaes() {
        //ArsNouveauAPI.ENABLE_DEBUG_NUMBERS = true;
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_SPEC);
        ModRegistry.registerRegistries(modbus);
        if (ModList.get().isLoaded("pmmo")) {
            MinecraftForge.EVENT_BUS.register(PmmoCompatEventHandler.class);
        }
        /* disabled for now
        if (ModList.get().isLoaded("scalinghealth")) {
            MinecraftForge.EVENT_BUS.register(ScalingHealthCompatEventHandler.class);
        }
         */
        if (ModList.get().isLoaded("pehkui")) {
            PkCompatHandler.init();
        }
        if (ModList.get().isLoaded("identity")) {
            IdentityReg.preInit();
        }
        /* disabled for now
        if (ModList.get().isLoaded("hexcasting")) {
            HexCompat.init();
        }
        if (ModList.get().isLoaded("hexerei")) {
            HexereiCompat.init();
        }
        if (ModList.get().isLoaded("malum")) {
            MalumCompat.init();
        }*/
        if (ModList.get().isLoaded("origins")) {
            OriginCompat.init();
        }

        ArsNouveauRegistry.registerCompatGlyphs();
        modbus.addListener(this::setup);
        modbus.addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        if (ModList.get().isLoaded("identity")) {
            event.enqueueWork(IdentityReg::postInit);
        }

        /* disabled for now
        if (ModList.get().isLoaded("pmmo")) {
            event.enqueueWork(PmmoCompatEventHandler::setupPerks);
        }
        if (ModList.get().isLoaded("hexerei")) {
            event.enqueueWork(HexereiCompat::postInit);
        }
        if (ModList.get().isLoaded("malum")) {
            event.enqueueWork(MalumCompat::postInit);
        }
         */

    }

    private void doClientStuff(final EntityRenderersEvent.RegisterLayerDefinitions event) {
        /*
        if (ModList.get().isLoaded("hexerei")) {
            HexereiCompat.layerDefinitions(event);
        }*/
    }


}
