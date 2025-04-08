package com.mars.morediscs;

import com.mars.deimos.config.DeimosConfig;
import com.google.common.collect.Lists;

import java.util.List;

public class MoreDiscsConfig extends DeimosConfig {
    @Entry public static boolean enable_loot_modifiers = true;
    @Entry public static List<String> discs_loot_list = Lists.newArrayList("minecraft:chests/desert_pyramid, music_disc_tall, music_disc_42, 1");
}
