package alexthw.ars_scalaes.pmmo;

import com.hollingsworth.arsnouveau.api.event.SpellCastEvent;
import com.hollingsworth.arsnouveau.api.spell.Spell;
import com.hollingsworth.arsnouveau.api.spell.SpellContext;

public class SpellSuccessEvent extends SpellCastEvent {

    int expendedMana;

    public SpellSuccessEvent(Spell spell, SpellContext context, int expendedMana) {
        super(spell, context);
        this.expendedMana = expendedMana;
    }

}
