package com.mars.morediscs;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.HashMap;

import static com.mars.morediscs.CommonClass.MUSIC_DISCS_NAMES;
import static com.mars.morediscs.Constants.MOD_ID;
import static com.mars.morediscs.MoreDiscsConfig.discs_loot_list;
import static com.mars.morediscs.MoreDiscsConfig.enable_loot_modifiers;

public class MoreDiscs implements ModInitializer {
    public static final HashMap<String, Item> ITEM_LIST = new HashMap<>();
    public static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), ResourceLocation.fromNamespaceAndPath(MOD_ID, "music_disc_group"));

    @Override
    public void onInitialize() {
        CommonClass.init();

        MUSIC_DISCS_NAMES.forEach(this::registerItem);

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, ITEM_GROUP, FabricItemGroup.builder()
                .title(Component.translatable("itemgroup.morediscs.music_disc_group"))
                .icon(() -> new ItemStack(ITEM_LIST.get("music_disc_test")))
                .build());

        if(enable_loot_modifiers){
            for (String disc_loot : discs_loot_list) {
                String[] set = disc_loot.replaceAll("\\s", "").split(",");

                LootTableEvents.MODIFY.register((key, tableBuilder, source, registries) -> {
                    for (int i = 0; i < set.length - 2; i++) {
                        if(key.location().toString().equals(set[0])){
                            float chance = 1 / Float.parseFloat(set[set.length - 1]);
                            LootItemCondition chanceCondition = LootItemRandomChanceCondition.randomChance(chance).build();

                            LootPool poolBuilder = LootPool.lootPool()
                                    .setRolls(ConstantValue.exactly(1))
                                    .conditionally(chanceCondition)
                                    .add(LootItem.lootTableItem(ITEM_LIST.get(set[i + 1])))
                                    .build();
                            tableBuilder.pool(poolBuilder);
                        }
                    }
                });
            }
        }
    }

    public Item registerItem(String name){
        Item item = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(MOD_ID, name),
                new Item(new Item.Properties().rarity(Rarity.RARE).jukeboxPlayable(registerJukeboxSong(name + "_sound")).stacksTo(1)));

        if(!name.equals("music_disc_test"))
            ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(entries -> entries.accept(item));

        ITEM_LIST.put(name, item);
        return item;
    }

    public static ResourceKey<JukeboxSong> registerJukeboxSong(String name) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
    }
}
