package alexthw.ars_scalaes;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigHandler {

    public static class Common{

        public static ForgeConfigSpec.ConfigValue<Boolean> ALL_MAGIC;

        //Pmmo x AN
        public static ForgeConfigSpec.ConfigValue<Double> MANA_XP;
        public static ForgeConfigSpec.ConfigValue<Double> MAX_BONUS;
        public static ForgeConfigSpec.ConfigValue<Double> REGEN_BONUS;
        public static ForgeConfigSpec.ConfigValue<Double> LEVEL_TO_SPELL_DMG;
        public static ForgeConfigSpec.ConfigValue<Double> LEVEL_TO_SPELL_RES;


        //Scaling Health x AN
        public static ForgeConfigSpec.ConfigValue<Double> SCALING_SPELL_DMG;

        public Common(ForgeConfigSpec.Builder builder) {

            builder.push("general configs");
            ALL_MAGIC = builder.comment("Enable or disable magic flag on spell damage, for interaction with magic reduction or proficiency").define("turn spells magic", true);
            builder.pop();

            builder.comment("Pmmo scaling").push("PMMO");
            MANA_XP = builder.comment("XP gained per Mana spent").define("ars_mana", .1d);
            MAX_BONUS = builder.comment("% Bonus to Max Mana per level").define("ars_max_bonus", .01d);
            REGEN_BONUS = builder.comment("% Bonus to Mana Regen per level").define("ars_regen_bonus", .00666d);
            LEVEL_TO_SPELL_DMG = builder.comment("% Bonus to Spell Damage per level").define("ars_damage_bonus", .0d);
            LEVEL_TO_SPELL_RES = builder.comment("% Bonus to Spell Resistance per level").define("ars_defense_bonus", .0d);

            builder.pop();

            builder.comment("Scaling Health scaling").push("SCALING HEALTH");
            SCALING_SPELL_DMG = builder.comment("Spell Damage bonus per crystal").define("scaling_ars_damage", .0d);
            builder.pop();

        }

    }

    public static class Client{
        public Client(ForgeConfigSpec.Builder builder){
        }

    }

    public static final Common COMMON;
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final Client CLIENT;
    public static final ForgeConfigSpec CLIENT_SPEC;

    static {

        final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
        COMMON_SPEC = specPair.getRight();
        COMMON = specPair.getLeft();

        final Pair<Client,ForgeConfigSpec> specClientPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = specClientPair.getRight();
        CLIENT = specClientPair.getLeft();

    }

}
