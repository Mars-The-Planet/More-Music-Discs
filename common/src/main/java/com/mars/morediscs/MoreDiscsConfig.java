package com.mars.morediscs;

import com.mars.deimos.config.DeimosConfig;
import com.google.common.collect.Lists;

import java.util.List;

public class MoreDiscsConfig extends DeimosConfig {
    @Entry public static boolean enable_loot_modifiers = true;
    @Entry public static List<String> discs_loot_list = Lists.newArrayList(
            "minecraft:chests/desert_pyramid, music_disc_tall, music_disc_42, 1",
            "minecraft:chests/end_city_treasure, music_disc_flight_of_the_voids_ship, music_disc_void, music_disc_justyhebeginning, music_disc_dreams, music_disc_avian, music_disc_tall, music_disc_chorus, music_disc_squiggles, music_disc_antiremake, music_disc_reloaded, music_disc_scopophobia, 1",
            "minecraft:chests/pillager_outpost, music_disc_omen, music_disc_raid, 1",
            "minecraft:chests/ancient_city, music_disc_silence, music_disc_sound, 1",
            "minecraft:entities/mooshroom, music_disc_shroom, S",
            "minecraft:chests/nether_bridge, music_disc_stridehop, music_disc_witherdance, music_disc_blazetrap, music_disc_soul, music_disc_victory, 1",
            "minecraft:entities/ender_dragon, music_disc_droopylovesjean, 1",
            "minecraft:chests/jungle_temple, music_disc_jungle, music_disc_jungler, 1",
            "minecraft:entities/witch, music_disc_shallow, music_disc_potion_of_swiftness, S",
            "minecraft:chests/abandoned_mineshaft, music_disc_mesa_depth, music_disc_before, 1",
            "minecraft:entities/skeleton, music_disc_clouds, S",
            "minecraft:chests/stronghold_corridor, music_disc_anti, music_disc_aether, 1",
            "minecraft:chests/desert_pyramid, music_disc_sand, 1",
            "minecraft:chests/igloo_chest, music_disc_chill, 1",
            "minecraft:chests/shipwreck_supply, music_disc_dive, 1",
    );
}
