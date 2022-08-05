package alexthw.ars_scalaes.registry;

import alexthw.ars_scalaes.ArsScalaes;
import alexthw.ars_scalaes.block.DecoBlockPack;
import com.hollingsworth.arsnouveau.common.lib.LibBlockNames;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;


public class ModRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ArsScalaes.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ArsScalaes.MODID);
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ArsScalaes.MODID);

    public static void registerRegistries(IEventBus bus){
        BLOCKS.register(bus);
        ITEMS.register(bus);
        EFFECTS.register(bus);
    }

    public static final DecoBlockPack ARCANE_STONE;
    public static final DecoBlockPack ARCANE_STONE_CLOVER;
    public static final DecoBlockPack ARCANE_STONE_GOLD;
    public static final DecoBlockPack ARCANE_STONE_GOLD_ALT;
    public static final DecoBlockPack ARCANE_STONE_GOLD_ASHLAR;
    public static final DecoBlockPack ARCANE_STONE_GOLD_BASKET;
    public static final DecoBlockPack ARCANE_STONE_GOLD_CLOVER;
    public static final DecoBlockPack ARCANE_STONE_GOLD_HERRING;
    public static final DecoBlockPack ARCANE_STONE_GOLD_MOSAIC;
    public static final DecoBlockPack ARCANE_STONE_GOLD_SLAB;

    static {
        ARCANE_STONE = addDecoBlockPack("arcane_stone");
        ARCANE_STONE_CLOVER = addDecoBlockPack("as_clover");
        ARCANE_STONE_GOLD = addDecoBlockPack("as_gold_stone");
        ARCANE_STONE_GOLD_ALT = addDecoBlockPack("as_gold_alt");
        ARCANE_STONE_GOLD_ASHLAR = addDecoBlockPack("as_gold_ashlar");
        ARCANE_STONE_GOLD_BASKET = addDecoBlockPack("as_gold_basket");
        ARCANE_STONE_GOLD_CLOVER = addDecoBlockPack("as_gold_clover");
        ARCANE_STONE_GOLD_HERRING = addDecoBlockPack("as_gold_herring");
        ARCANE_STONE_GOLD_MOSAIC = addDecoBlockPack("as_gold_mosaic");
        ARCANE_STONE_GOLD_SLAB = addDecoBlockPack("as_gold_slab");
    }

    public static final DecoBlockPack ARCANE_BRICK;
    public static final DecoBlockPack ARCANE_BRICK_ALT;
    public static final DecoBlockPack ARCANE_BRICK_BASKET;
    public static final DecoBlockPack ARCANE_BRICK_HERRING;
    public static final DecoBlockPack ARCANE_BRICK_MOSAIC;

    static{
        ARCANE_BRICK = addDecoBlockPack("arcane_bricks");
        ARCANE_BRICK_ALT = addDecoBlockPack("ab_alternating");
        ARCANE_BRICK_BASKET = addDecoBlockPack("ab_basket");
        ARCANE_BRICK_HERRING = addDecoBlockPack("ab_herring");
        ARCANE_BRICK_MOSAIC = addDecoBlockPack("ab_mosaic");
    }

    public static final DecoBlockPack ARCANE_STONE_SMOOTH;
    //public static final DecoBlockPack ARCANE_STONE_SMOOTH_SLAB;
    public static final DecoBlockPack ARCANE_BRICK_SMOOTH_ALT;
    public static final DecoBlockPack ARCANE_BRICK_SMOOTH_ASHLAR;
    public static final DecoBlockPack ARCANE_BRICK_SMOOTH_CLOVER;
    public static final DecoBlockPack ARCANE_BRICK_SMOOTH_BASKET;
    public static final DecoBlockPack ARCANE_BRICK_SMOOTH_HERRING;
    public static final DecoBlockPack ARCANE_BRICK_SMOOTH_MOSAIC;

    static {
        ARCANE_STONE_SMOOTH = addDecoBlockPack("ab_smooth");
        //ARCANE_STONE_SMOOTH_SLAB = addDecoBlockPack("ab_smooth_slab");
        ARCANE_BRICK_SMOOTH_ALT = addDecoBlockPack("sas_alternating");
        ARCANE_BRICK_SMOOTH_ASHLAR = addDecoBlockPack("sas_ashlar");
        ARCANE_BRICK_SMOOTH_BASKET = addDecoBlockPack("sas_basket");
        ARCANE_BRICK_SMOOTH_CLOVER = addDecoBlockPack("sas_clover");
        ARCANE_BRICK_SMOOTH_HERRING = addDecoBlockPack("sas_herring");
        ARCANE_BRICK_SMOOTH_MOSAIC = addDecoBlockPack("sas_mosaic");
    }


    static DecoBlockPack addDecoBlockPack(String name){
        return (new DecoBlockPack(BLOCKS, name, stoneBlockProps())).addWall();
    }

    static Item.Properties addTabProp() {
        return new Item.Properties().tab(ArsScalaes.TAB);
    }

    public static RegistryObject<Block> addBlock(String name, Supplier<Block> blockSupp) {
        RegistryObject<Block> block = BLOCKS.register(name, blockSupp);
        ITEMS.register(name, () -> new BlockItem(block.get(), addTabProp()));
        return block;
    }

    static BlockBehaviour.Properties stoneBlockProps() {
        return BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_PURPLE).sound(SoundType.STONE)
                .strength(1.6F, 3.0F);
    }

}
