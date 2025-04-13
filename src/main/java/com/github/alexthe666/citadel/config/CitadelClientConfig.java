package com.github.alexthe666.citadel.config;

import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class CitadelClientConfig {
    public static final CitadelClientConfig INSTANCE;
    public static final ModConfigSpec CLIENT_SPEC;

    public final ModConfigSpec.BooleanValue enableSplashText;
    public final ModConfigSpec.BooleanValue removeSplashText;
    public final ModConfigSpec.BooleanValue enableCapes;
    public final ModConfigSpec.BooleanValue enablePatreonEffects;

    static {
        final Pair<CitadelClientConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(CitadelClientConfig::new);
        INSTANCE = specPair.getLeft();
        CLIENT_SPEC = specPair.getRight();
    }

    CitadelClientConfig(ModConfigSpec.Builder builder) {
        enableSplashText = builder
            .comment("Whether to enable custom splash text rendering")
            .define("enableSplashText", true);
        
        removeSplashText = builder
            .comment("Whether to remove splash text completely")
            .define("removeSplashText", false);
        
        enableCapes = builder
            .comment("Whether to enable custom cape rendering")
            .define("enableCapes", true);

        enablePatreonEffects = builder
            .comment("Whether to enable special effects for Patreon supporters")
            .define("enablePatreonEffects", true);
    }

    public ModConfigSpec getSpec() {
        return CLIENT_SPEC;
    }
} 