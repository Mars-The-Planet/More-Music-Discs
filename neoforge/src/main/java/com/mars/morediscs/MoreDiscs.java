package com.mars.morediscs;


import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.CreativeModeTabRegistry;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.HashMap;
import java.util.function.Supplier;

import static com.mars.morediscs.CommonClass.MUSIC_DISCS_NAMES;
import static com.mars.morediscs.CommonClass.MUSIC_DISC_LENGTHS;
import static com.mars.morediscs.Constants.MOD_ID;

@Mod(MOD_ID)
public class MoreDiscs {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final HashMap<String, DeferredItem<RecordItem>> ITEM_LIST = new HashMap<>();
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);

    public static final Supplier<MapCodec<DiscAdder>> MY_LOOT_MODIFIER =
            GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("disc_adder", () -> DiscAdder.CODEC);

    public MoreDiscs(IEventBus eventBus) {
        CommonClass.init();

        MUSIC_DISCS_NAMES.forEach(MoreDiscs::registerItem);
        ITEMS.register(eventBus);

        CREATIVE_MODE_TAB.register("music_disc_group", () -> CreativeModeTab.builder().title(Component.literal("More Music Discs"))
                .withTabsBefore(CreativeModeTabs.COMBAT)
                .icon(() -> ITEM_LIST.get("music_disc_test").toStack())
                .displayItems(
                        (parameters, output) -> {
                            for (int i = 0; i < MUSIC_DISCS_NAMES.size() - 1; i++) {
                                output.accept(ITEM_LIST.get(MUSIC_DISCS_NAMES.get(i + 1)));
                            }
                        }
                ).build());
        CREATIVE_MODE_TAB.register(eventBus);

        GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }

    public static DeferredItem<RecordItem> registerItem(String name){
        DeferredItem<RecordItem> item = ITEMS.register(name,
                () -> new RecordItem(1, registerJukeboxSong(name + "_sound"), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), MUSIC_DISC_LENGTHS.get(name)));

        ITEM_LIST.put(name, item);
        return item;
    }

    public static SoundEvent registerJukeboxSong(String name){
        ResourceLocation id = new ResourceLocation(MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createFixedRangeEvent(id, 75));
    }
}
