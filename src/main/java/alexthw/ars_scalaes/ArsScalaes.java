package alexthw.ars_scalaes;

import alexthw.ars_scalaes.registry.ModRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.example.registry.ItemRegistry;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ArsScalaes.MODID)
public class ArsScalaes
{
    public static final String MODID = "ars_scalaes";
    public static final CreativeModeTab TAB = new CreativeModeTab(MODID) {
        @Override
        public ItemStack makeIcon() {
            return ModRegistry.ARCANE_STONE.getStairs().asItem().getDefaultInstance();
        }
    } ;

    public ArsScalaes() {
        IEventBus modbus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigHandler.COMMON_SPEC);
        ModRegistry.registerRegistries(modbus);
        //ArsNouveauRegistry.registerGlyphs();
        if (ModList.get().isLoaded("pmmo")){
            MinecraftForge.EVENT_BUS.register(PmmoCompatEventHandler.class);
        }
        if (ModList.get().isLoaded("scaling_health")){
            MinecraftForge.EVENT_BUS.register(ScalingHealthCompatEventHandler.class);
        }
        modbus.addListener(this::setup);
        modbus.addListener(this::doClientStuff);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }


}
