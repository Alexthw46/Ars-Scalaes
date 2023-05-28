package alexthw.ars_scalaes.mixin;

import alexthw.ars_scalaes.pmmo.SpellSuccessEvent;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;
import com.hollingsworth.arsnouveau.api.spell.SpellResolver;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SpellResolver.class, remap = false)
public abstract class SpellCastSuccessMixin {

    @Shadow
    public abstract int getResolveCost();

    @Shadow
    public SpellContext spellContext;

    @Inject(method = "expendMana", at = @At("HEAD"), remap = false)
    public void expendMana(CallbackInfo ci) {
        int mana = getResolveCost();
        var event = new SpellSuccessEvent(spellContext.getSpell(), spellContext, mana);
        MinecraftForge.EVENT_BUS.post(event);
    }


}
