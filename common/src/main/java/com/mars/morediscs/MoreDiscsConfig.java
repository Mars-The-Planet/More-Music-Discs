package com.mars.morediscs;

import com.mars.deimos.config.DeimosConfig;
import com.google.common.collect.Lists;

import java.util.List;

public class MoreDiscsConfig extends DeimosConfig {
    @Entry public static boolean enable_loot_modifiers = true;
    @Entry public static List<String> discs_loot_list = Lists.newArrayList(
            "minecraft:chests/desert_pyramid, music_disc_sand, music_disc_scorched, 1",
            "minecraft:chests/jungle_temple, music_disc_intothejungle, music_disc_jungle, music_disc_jungler, 1",
            "minecraft:chests/igloo_chest, music_disc_chill, music_disc_dropclouds, 1",
            "minecraft:chests/abandoned_mineshaft, music_disc_amethyzied, music_disc_mesa_depth, music_disc_before, 1",
            "minecraft:chests/stronghold_corridor, music_disc_anti, music_disc_aether, 1",

            "minecraft:chests/ancient_city, music_disc_wardensprize, music_disc_thedarkside, music_disc_silence, music_disc_sound, music_disc_warden, music_disc_left_shift, 1",
            "minecraft:chests/pillager_outpost, music_disc_omen, music_disc_raid, music_disc_ravage, 1",
            "minecraft:chests/simple_dungeon, music_disc_retri, 1",

            "minecraft:chests/nether_bridge, music_disc_thelostsoul, music_disc_warped_forest, music_disc_vengeful, music_disc_stridehop, music_disc_witherdance, music_disc_soul, music_disc_victory, 1",
            "minecraft:chests/bastion_other, music_disc_blazetrap, 1",
            "minecraft:chests/end_city_treasure, music_disc_flyingship, music_disc_lgm, music_disc_flight_of_the_voids_ship, music_disc_void, music_disc_justyhebeginning, music_disc_dreams, music_disc_avian, music_disc_tall, music_disc_chorus, music_disc_squiggles, music_disc_antiremake, music_disc_reloaded, music_disc_scopophobia, 1",

            "minecraft:chests/shipwreck_supply, music_disc_waves, music_disc_dive, music_disc_tide, music_disc_drowned_anthem, music_disc_spiral, 1",

            "minecraft:chests/elder_guardian, music_disc_dive, music_disc_tide, music_disc_submerge, 1",
            "minecraft:entities/ender_dragon, music_disc_droopylovesjean, 1",

            "minecraft:entities/mooshroom, music_disc_shroom, music_disc_mush_roam, S",
            "minecraft:entities/witch, music_disc_shallow, music_disc_potion_of_swiftness, music_disc_mangrove_swamp, S",
            "minecraft:entities/skeleton, music_disc_clouds, S",
            "minecraft:entities/pig, music_disc_technobladeneverdiesatleastinourhearts, S",


            "minecraft:chests/village/village_temple, music_disc_thebrightside, music_disc_forest, 1"


    );
}
