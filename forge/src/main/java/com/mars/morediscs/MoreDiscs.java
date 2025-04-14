package com.mars.morediscs;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.*;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.function.Supplier;

import static com.mars.morediscs.CommonClass.MUSIC_DISCS_NAMES;
import static com.mars.morediscs.CommonClass.MUSIC_DISC_LENGTHS;
import static com.mars.morediscs.Constants.MOD_ID;

@Mod(Constants.MOD_ID)
public class MoreDiscs {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, MOD_ID);

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final HashMap<String, RegistryObject<RecordItem>> ITEM_LIST = new HashMap<>();
    public static final HashMap<String, RegistryObject<SoundEvent>> SOUND_EVENT_LIST = new HashMap<>();
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>>  GLOBAL_LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);

    public static final RegistryObject<Codec<? extends DiscAdder>> MY_LOOT_MODIFIER =
            GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("disc_adder", DiscAdder.CODEC::codec);
    public MoreDiscs() {
        CommonClass.init();
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MUSIC_DISCS_NAMES.forEach(MoreDiscs::registerSoundEvent);
        SOUND_EVENTS.register(eventBus);
        MUSIC_DISCS_NAMES.forEach(MoreDiscs::registerItem);
        ITEMS.register(eventBus);


        CREATIVE_MODE_TAB.register("music_disc_group", () -> CreativeModeTab.builder().title(Component.literal("More Music Discs"))
                .withTabsBefore(CreativeModeTabs.COMBAT)
                .icon(() -> ITEM_LIST.get("music_disc_test").get().getDefaultInstance())
                .displayItems(
                        (parameters, output) -> {
                            for (int i = 0; i < MUSIC_DISCS_NAMES.size() - 1; i++) {
                                output.accept(ITEM_LIST.get(MUSIC_DISCS_NAMES.get(i + 1)).get());
                            }
                        }
                ).build());
        CREATIVE_MODE_TAB.register(eventBus);

        GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }

    public static RegistryObject<SoundEvent> registerSoundEvent(String name){
        RegistryObject<SoundEvent> soundEvent = SOUND_EVENTS.register(name, () -> SoundEvent.createFixedRangeEvent(new ResourceLocation(MOD_ID, name + "_sound"), 75f));

        SOUND_EVENT_LIST.put(name, soundEvent);
        return soundEvent;
    }

    public static RegistryObject<RecordItem> registerItem(String name){
        RegistryObject<RecordItem> item = ITEMS.register(name,
                () -> new RecordItem(1, SOUND_EVENT_LIST.get(name), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), MUSIC_DISC_LENGTHS.get(name) * 20));

        ITEM_LIST.put(name, item);
        return item;
    }
}
