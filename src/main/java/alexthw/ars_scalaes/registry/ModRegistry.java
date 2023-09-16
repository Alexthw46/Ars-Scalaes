package alexthw.ars_scalaes.registry;

import alexthw.ars_scalaes.ArsScalaes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ArsScalaes.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ArsScalaes.MODID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ArsScalaes.MODID);

    public static void registerRegistries(IEventBus bus) {
        BLOCKS.register(bus);
        ITEMS.register(bus);
        EFFECTS.register(bus);

    }

    public static RegistryObject<Block> addBlock(String name, Supplier<Block> blockSupp) {
        RegistryObject<Block> block = BLOCKS.register(name, blockSupp);
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

}
/*
    //simple sourcestone
    public static final DecoBlockPack SOURCESTONE;
    public static final DecoBlockPack SOURCESTONE_MOSAIC;
    public static final DecoBlockPack SOURCESTONE_BASKET;
    public static final DecoBlockPack SOURCESTONE_ALTERNATING;
    public static final DecoBlockPack SOURCESTONE_LARGE_BRICKS;
    public static final DecoBlockPack SOURCESTONE_SMALL_BRICKS;

    static {
        SOURCESTONE = addDecoBlockPack(LibBlockNames.SOURCESTONE);

        SOURCESTONE_MOSAIC = addDecoBlockPack(LibBlockNames.SOURCESTONE_MOSAIC);
        SOURCESTONE_BASKET = addDecoBlockPack(LibBlockNames.SOURCESTONE_BASKET);
        SOURCESTONE_ALTERNATING = addDecoBlockPack(LibBlockNames.SOURCESTONE_ALTERNATING);
        SOURCESTONE_LARGE_BRICKS = addDecoBlockPack(LibBlockNames.SOURCESTONE_LARGE_BRICKS);
        SOURCESTONE_SMALL_BRICKS = addDecoBlockPack(LibBlockNames.SOURCESTONE_SMALL_BRICKS);

    }

    //gilded sourcestone
    public static final DecoBlockPack GILDED_SOURCESTONE_MOSAIC;
    public static final DecoBlockPack GILDED_SOURCESTONE_BASKET;
    public static final DecoBlockPack GILDED_SOURCESTONE_ALTERNATING;
    public static final DecoBlockPack GILDED_SOURCESTONE_LARGE_BRICKS;
    public static final DecoBlockPack GILDED_SOURCESTONE_SMALL_BRICKS;

    static {
        GILDED_SOURCESTONE_MOSAIC = addDecoBlockPack(LibBlockNames.GILDED_SOURCESTONE_MOSAIC);
        GILDED_SOURCESTONE_BASKET = addDecoBlockPack(LibBlockNames.GILDED_SOURCESTONE_BASKET);
        GILDED_SOURCESTONE_ALTERNATING = addDecoBlockPack(LibBlockNames.GILDED_SOURCESTONE_ALTERNATING);
        GILDED_SOURCESTONE_LARGE_BRICKS = addDecoBlockPack(LibBlockNames.GILDED_SOURCESTONE_LARGE_BRICKS);
        GILDED_SOURCESTONE_SMALL_BRICKS = addDecoBlockPack(LibBlockNames.GILDED_SOURCESTONE_SMALL_BRICKS);
    }

    //smooth sourcestone
    public static final DecoBlockPack SMOOTH_SOURCESTONE;

    public static final DecoBlockPack SMOOTH_SOURCESTONE_MOSAIC;
    public static final DecoBlockPack SMOOTH_SOURCESTONE_BASKET;
    public static final DecoBlockPack SMOOTH_SOURCESTONE_ALTERNATING;
    public static final DecoBlockPack SMOOTH_SOURCESTONE_LARGE_BRICKS;
    public static final DecoBlockPack SMOOTH_SOURCESTONE_SMALL_BRICKS;

    static {
        SMOOTH_SOURCESTONE = addDecoBlockPack(LibBlockNames.SMOOTH_SOURCESTONE);
        SMOOTH_SOURCESTONE_MOSAIC = addDecoBlockPack(LibBlockNames.SMOOTH_SOURCESTONE_MOSAIC);
        SMOOTH_SOURCESTONE_BASKET = addDecoBlockPack(LibBlockNames.SMOOTH_SOURCESTONE_BASKET);
        SMOOTH_SOURCESTONE_ALTERNATING = addDecoBlockPack(LibBlockNames.SMOOTH_SOURCESTONE_ALTERNATING);
        SMOOTH_SOURCESTONE_LARGE_BRICKS = addDecoBlockPack(LibBlockNames.SMOOTH_SOURCESTONE_LARGE_BRICKS);
        SMOOTH_SOURCESTONE_SMALL_BRICKS = addDecoBlockPack(LibBlockNames.SMOOTH_SOURCESTONE_SMALL_BRICKS);

    }

    public static final DecoBlockPack SMOOTH_GILDED_SOURCESTONE_MOSAIC;
    public static final DecoBlockPack SMOOTH_GILDED_SOURCESTONE_BASKET;
    public static final DecoBlockPack SMOOTH_GILDED_SOURCESTONE_ALTERNATING;
    public static final DecoBlockPack SMOOTH_GILDED_SOURCESTONE_LARGE_BRICKS;
    public static final DecoBlockPack SMOOTH_GILDED_SOURCESTONE_SMALL_BRICKS;

    static {

        SMOOTH_GILDED_SOURCESTONE_MOSAIC = addDecoBlockPack(LibBlockNames.SMOOTH_GILDED_SOURCESTONE_MOSAIC);
        SMOOTH_GILDED_SOURCESTONE_BASKET = addDecoBlockPack(LibBlockNames.SMOOTH_GILDED_SOURCESTONE_BASKET);
        SMOOTH_GILDED_SOURCESTONE_ALTERNATING = addDecoBlockPack(LibBlockNames.SMOOTH_GILDED_SOURCESTONE_ALTERNATING);
        SMOOTH_GILDED_SOURCESTONE_LARGE_BRICKS = addDecoBlockPack(LibBlockNames.SMOOTH_GILDED_SOURCESTONE_LARGE_BRICKS);
        SMOOTH_GILDED_SOURCESTONE_SMALL_BRICKS = addDecoBlockPack(LibBlockNames.SMOOTH_GILDED_SOURCESTONE_SMALL_BRICKS);

    }


    static DecoBlockPack addDecoBlockPack(String name) {
        return (new DecoBlockPack(BLOCKS, name, stoneBlockProps())).addWall();
    }

    static public Item.Properties addTabProp() {
        return new Item.Properties().tab(ArsScalaes.TAB);
    }



    static BlockBehaviour.Properties stoneBlockProps() {
        return BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_PURPLE).sound(SoundType.STONE)
                .strength(1.6F, 3.0F);
    }

}
 */