package alexthw.ars_scalaes.identity.ability;

import com.hollingsworth.arsnouveau.api.scrying.CompoundScryer;
import com.hollingsworth.arsnouveau.api.scrying.TagScryer;
import com.hollingsworth.arsnouveau.common.entity.Starbuncle;
import com.hollingsworth.arsnouveau.common.ritual.RitualScrying;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;
import draylar.identity.ability.IdentityAbility;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

public class StarbuncleAbility<S extends Starbuncle> extends IdentityAbility<S> {
    @Override
    public void onUse(Player player, S s, Level level) {
        RitualScrying.grantScrying((ServerPlayer) player, 3 * 20 * 60, new CompoundScryer(new TagScryer(Tags.Blocks.ORES_GOLD), new TagScryer(BlockTags.GOLD_ORES)));
    }

    @Override
    public int getCooldown(S entity) {
        return 3600;
    }

    @Override
    public Item getIcon() {
        return ItemsRegistry.STARBUNCLE_CHARM.asItem();
    }
}
