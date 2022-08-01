package alexthw.ars_scalaes.datagen;

import alexthw.ars_scalaes.ArsScalaes;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import java.util.*;
import java.util.function.Predicate;

@Mod.EventBusSubscriber(modid = ArsScalaes.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Setup {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
        gen.addProvider(new ModBlockTagsProvider(gen,existingFileHelper));
        //gen.addProvider(new ArsProviders.ImbuementProvider(gen));
        //gen.addProvider(new ArsProviders.GlyphProvider(gen));
        //gen.addProvider(new ArsProviders.EnchantingAppProvider(gen));
        gen.addProvider(new ArsProviders.PatchouliProvider(gen));
        gen.addProvider(new ModLangProvider(gen, "en_us"));
        gen.addProvider(new ModRecipeProvider(gen));
        gen.addProvider(new ModLootTables(gen));
    }

    public static <T> Collection<T> takeAll(Collection<T> src, Predicate<T> pred)
    {
        List<T> ret = new ArrayList<>();

        Iterator<T> iter = src.iterator();
        while (iter.hasNext())
        {
            T item = iter.next();
            if (pred.test(item))
            {
                iter.remove();
                ret.add(item);
            }
        }

        if (ret.isEmpty()) {
            return Collections.emptyList();
        }
        return ret;
    }


}
