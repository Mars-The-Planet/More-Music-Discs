package com.mars.morediscs;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
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
    public static final ResourceKey<CreativeModeTab> ITEM_GROUP = ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), new ResourceLocation(MOD_ID, "music_disc_group"));

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

        RecordItem item = Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(MOD_ID, name),
                new RecordItem(1, soundEvent, new Item.Properties().rarity(Rarity.RARE).stacksTo(1), MUSIC_DISC_LENGTHS.get(name)));

        if(!name.equals("music_disc_test"))
            ItemGroupEvents.modifyEntriesEvent(ITEM_GROUP).register(entries -> entries.accept(item));

        ITEM_LIST.put(name, item);
        return item;
    }

    public static SoundEvent registerJukeboxSong(String name) {
        ResourceLocation id = new ResourceLocation(MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createFixedRangeEvent(id, 75));
    }
}
