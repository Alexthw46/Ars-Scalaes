package alexthw.ars_scalaes.datagen;

import alexthw.ars_elemental.registry.ModRegistry;
import alexthw.ars_scalaes.ArsScalaes;
import alexthw.ars_scalaes.hexerei.HexereiCompat;
import at.petrak.hexcasting.api.HexAPI;
import net.joefoxe.hexerei.util.HexereiTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {

    public ModItemTagProvider(DataGenerator pGenerator, CompletableFuture<HolderLookup.Provider> lookupProvider, BlockTagsProvider pBlockTagsProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(pGenerator.getPackOutput(), lookupProvider, pBlockTagsProvider.contentsGetter(), ArsScalaes.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(ModRegistry.CURIO_BAGGABLE)
                .addOptionalTag(hexLoc("staves"))
                .addOptional(hexLoc("abacus"))
                .addOptional(hexLoc("focus"))
                .addOptional(hexLoc("abacus"))
                .addOptional(hexLoc("lens"));
        tag(ModRegistry.CASTER_BAGGABLE)
                .addOptional(hexLoc("artifact"))
                .addOptional(hexLoc("trinket"))
                .addOptional(hexLoc("battery"))
                .addOptional(hexLoc("spellbook"))
                .addOptional(hexLoc("cypher"));
        //tag(HexereiTags.Items.BROOM_BRUSH).addOptional(HexereiCompat.MAGEBLOOM_BRUSH.getId());

    }

    public static ResourceLocation hexLoc(String path) {
        return new ResourceLocation(HexAPI.MOD_ID, path);
    }

    @Override
    public @NotNull String getName() {
        return "Ars Scalaes Item Tags";
    }


}
