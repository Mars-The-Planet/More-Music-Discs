package com.mars.morediscs;

import com.mojang.serialization.Codec;
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

import static com.mars.morediscs.CommonClass.MUSIC_DISCS_NAMES;
import static com.mars.morediscs.CommonClass.MUSIC_DISC_LENGTHS;
import static com.mars.morediscs.Constants.MOD_ID;

@Mod(Constants.MOD_ID)
public class MoreDiscs {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MOD_ID);
    public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab ("music_disc_group") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ITEM_LIST.get("music_disc_test").get());
        }

        @Override
        public Component getDisplayName(){
            return Component.translatable("itemGroup.morediscs.music_disc_group");
        }
    };
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

        GLOBAL_LOOT_MODIFIER_SERIALIZERS.register(eventBus);
    }

    public static RegistryObject<SoundEvent> registerSoundEvent(String name){
        RegistryObject<SoundEvent> soundEvent = SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(MOD_ID, name + "_sound"), 75f));

        SOUND_EVENT_LIST.put(name, soundEvent);
        return soundEvent;
    }

    public static RegistryObject<RecordItem> registerItem(String name){
        RegistryObject<RecordItem> item = ITEMS.register(name, () -> name.equals("music_disc_test") ?
                new RecordItem(1, SOUND_EVENT_LIST.get(name), new Item.Properties().stacksTo(1).rarity(Rarity.RARE), MUSIC_DISC_LENGTHS.get(name) * 20) :
                new RecordItem(1, SOUND_EVENT_LIST.get(name), new Item.Properties().tab(ITEM_GROUP).stacksTo(1).rarity(Rarity.RARE), MUSIC_DISC_LENGTHS.get(name) * 20));

        ITEM_LIST.put(name, item);
        return item;
    }
}
