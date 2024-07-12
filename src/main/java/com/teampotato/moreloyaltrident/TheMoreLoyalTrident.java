package com.teampotato.moreloyaltrident;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(TheMoreLoyalTrident.MOD_ID)
public class TheMoreLoyalTrident {
    public static final String MOD_ID = "moreloyaltrident";

    private static final ForgeConfigSpec CONFIG;
    public static final ForgeConfigSpec.IntValue LEVEL;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        builder.push("TheMoreLoyalTrident");
        LEVEL = builder.defineInRange("RequiredLoyaltyLevel", 1, 0, Integer.MAX_VALUE);
        builder.pop();
        CONFIG = builder.build();
    }

    public TheMoreLoyalTrident() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG);
    }
}
