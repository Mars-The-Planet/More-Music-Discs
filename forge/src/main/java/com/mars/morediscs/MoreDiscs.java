package com.mars.morediscs;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
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
import static com.mars.morediscs.Constants.MOD_ID;

@Mod(Constants.MOD_ID)
public class MoreDiscs {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final HashMap<String, RegistryObject<Item>> ITEM_LIST = new HashMap<>();
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MOD_ID);

    public static final Supplier<MapCodec<DiscAdder>> MY_LOOT_MODIFIER =
            GLOBAL_LOOT_MODIFIER_SERIALIZERS.register("disc_adder", () -> DiscAdder.CODEC);
    public MoreDiscs() {
        CommonClass.init();
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

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

    public static RegistryObject<Item> registerItem(String name){
        RegistryObject<Item> item = ITEMS.register(name,
                () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(registerJukeboxSong(name + "_sound"))));

        ITEM_LIST.put(name, item);
        return item;
    }

    public static ResourceKey<JukeboxSong> registerJukeboxSong(String name){
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
    }
}
