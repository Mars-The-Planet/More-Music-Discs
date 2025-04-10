package com.mars.morediscs;

import com.mars.deimos.config.DeimosConfig;
import com.google.common.collect.Lists;

import java.util.List;

public class MoreDiscsConfig extends DeimosConfig {
    @Entry public static boolean enable_loot_modifiers = true;
    @Entry public static List<String> discs_loot_list = Lists.newArrayList(
            "minecraft:chests/desert_pyramid, music_disc_tall, music_disc_42, 1",
            "minecraft:chests/end_city_treasure, music_disc_flight_of_the_voids_ship, music_disc_void, music_disc_justyhebeginning, music_disc_dreams, music_disc_avian, music_disc_tall, music_disc_chorus, music_disc_squiggles, music_disc_antiremake, music_disc_reloaded, music_disc_scopophobia, 1",
            "minecraft:chests/pillager_outpost, music_disc_omen, 1",
            "minecraft:chests/ancient_city, music_disc_silence, 1",
            "minecraft:entities/mooshroom, music_disc_shroom, 1",
            "minecraft:chests/nether_bridge, music_disc_soul, 1",
            "minecraft:entities/ender_dragon, music_disc_droopylovesjean, 1",
            "minecraft:chests/jungle_temple, music_disc_jungle, 1"
    );
}
