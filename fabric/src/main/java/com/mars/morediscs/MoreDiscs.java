package com.mars.morediscs;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.HashMap;

import static com.mars.morediscs.CommonClass.MUSIC_DISCS_NAMES;
import static com.mars.morediscs.CommonClass.MUSIC_DISC_LENGTHS;
import static com.mars.morediscs.Constants.MOD_ID;
import static com.mars.morediscs.MoreDiscsConfig.discs_loot_list;
import static com.mars.morediscs.MoreDiscsConfig.enable_loot_modifiers;

public class MoreDiscs implements ModInitializer {
    public static final HashMap<String, Item> ITEM_LIST = new HashMap<>();
    public static final CreativeModeTab ITEM_GROUP = FabricItemGroupBuilder.build(
            new ResourceLocation(MOD_ID, "music_disc_group"), () -> new ItemStack(ITEM_LIST.get("music_disc_test")));

    @Override
    public void onInitialize() {
        CommonClass.init();

        MUSIC_DISCS_NAMES.forEach(this::registerItem);

        if(enable_loot_modifiers){
            for (String disc_loot : discs_loot_list) {
                String[] set = disc_loot.replaceAll("\\s", "").split(",");

                LootTableEvents.MODIFY.register((key, lootDataManager, id, tableBuilder, source) -> {
                    if(id.toString().equals(set[0])) {
                        LootPool.Builder poolBuilder = LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1));

                        for (int i = 0; i < set.length - 2; i++) {
                            poolBuilder.add(LootItem.lootTableItem(ITEM_LIST.get(set[i + 1])));
                        }

                        if(set[set.length - 1].equals("S")){
                            poolBuilder.when(LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.KILLER,
                                    EntityPredicate.Builder.entity().of(EntityTypeTags.SKELETONS)));
                        }
                        else{
                            float chance = 1 / Float.parseFloat(set[set.length - 1]);
                            LootItemCondition chanceCondition = LootItemRandomChanceCondition.randomChance(chance).build();
                            poolBuilder.conditionally(chanceCondition);
                        }

                        tableBuilder.pool(poolBuilder.build());
                    }
                });
            }
        }
    }

    public RecordItem registerItem(String name){
        SoundEvent soundEvent = registerJukeboxSong(name + "_sound");

        RecordItem item = Registry.register(Registry.ITEM, new ResourceLocation(MOD_ID, name), name.equals("music_disc_test") ?
                new RecordItem(1, soundEvent, new Item.Properties().rarity(Rarity.RARE).stacksTo(1), MUSIC_DISC_LENGTHS.get(name)) :
                new RecordItem(1, soundEvent, new Item.Properties().tab(ITEM_GROUP).rarity(Rarity.RARE).stacksTo(1), MUSIC_DISC_LENGTHS.get(name)));

        ITEM_LIST.put(name, item);
        return item;
    }

    public static SoundEvent registerJukeboxSong(String name) {
        ResourceLocation id = new ResourceLocation(MOD_ID, name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id, 75));
    }
}
