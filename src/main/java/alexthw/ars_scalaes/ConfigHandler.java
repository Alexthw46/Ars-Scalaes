package alexthw.ars_scalaes;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class ConfigHandler {

    public static class Common {

        //Pmmo x AN
        public static ForgeConfigSpec.ConfigValue<Double> MANA_XP;
        public static ForgeConfigSpec.ConfigValue<Double> LEVEL_TO_SPELL_DMG;
        public static ForgeConfigSpec.ConfigValue<Double> LEVEL_TO_SPELL_RES;


        //Scaling Health x AN
        public static ForgeConfigSpec.ConfigValue<Double> SCALING_SPELL_DMG;

        //Identity
        public static ForgeConfigSpec.ConfigValue<Integer> FLARE_COOLDOWN;
        public static ForgeConfigSpec.ConfigValue<Integer> STARBY_COOLDOWN;
        public static ForgeConfigSpec.ConfigValue<Integer> WW_COOLDOWN;
        public static ForgeConfigSpec.ConfigValue<Integer> WHIRLI_COOLDOWN;
        public static ForgeConfigSpec.ConfigValue<Integer> WIL_HUNTER_COOLDOWN;
        public static ForgeConfigSpec.ConfigValue<Integer> WIL_STALKER_COOLDOWN;
        public static ForgeConfigSpec.ConfigValue<Integer> WIXIE_COOLDOWN;

        //Hexcasting
        public static ForgeConfigSpec.ConfigValue<Integer> SOURCE_GEM_MEDIA;


        public Common(ForgeConfigSpec.Builder builder) {

            builder.comment("Pmmo scaling").push("PMMO");
            MANA_XP = builder.comment("XP gained per Mana spent").define("ars_mana", .1d);
            LEVEL_TO_SPELL_DMG = builder.comment("% Bonus to Spell Damage per Magic level").define("ars_damage_bonus", .0d);
            LEVEL_TO_SPELL_RES = builder.comment("% Bonus to Spell Resistance per Magic level").define("ars_defense_bonus", .0d);

            builder.pop();

            builder.comment("Scaling Health scaling").push("SCALING HEALTH");
            SCALING_SPELL_DMG = builder.comment("Spell Damage bonus per crystal").define("scaling_ars_damage", .0d);
            builder.pop();

            builder.comment("Identity Abilities").push("IDENTITY MORPHS");

            FLARE_COOLDOWN = builder.comment("cooldown for the active ability of flarecannon").define("flarecannon_cooldown", 120);
            STARBY_COOLDOWN = builder.comment("cooldown for the active ability of starbuncle").define("starby_cooldown", 3600);
            WW_COOLDOWN = builder.comment("cooldown for the active ability of weald walker").define("ww_cooldown", 100);
            WHIRLI_COOLDOWN = builder.comment("cooldown for the active ability of whirlisprig").define("whirli_cooldown", 400);
            WIL_HUNTER_COOLDOWN = builder.comment("cooldown for the active ability of wilden hunter").define("wil_hunter_cooldown", 800);
            WIL_STALKER_COOLDOWN = builder.comment("cooldown for the active ability of wilden stalker").define("wil_stalker_cooldown", 1300);
            WIXIE_COOLDOWN = builder.comment("cooldown for the active ability of wixie").define("wixie_cooldown", 100);

            builder.pop();

            builder.comment("Hexcasting Compat").push("HEX CASTING");

            SOURCE_GEM_MEDIA = builder.comment("media value of a source gem").define("source_media", 50000);

            builder.pop();
        }

    }

    public static class Client {
        public Client(ForgeConfigSpec.Builder builder) {
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

        final Pair<Client, ForgeConfigSpec> specClientPair = new ForgeConfigSpec.Builder().configure(Client::new);
        CLIENT_SPEC = specClientPair.getRight();
        CLIENT = specClientPair.getLeft();

    }

}
