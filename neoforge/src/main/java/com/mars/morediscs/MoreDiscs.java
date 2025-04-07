package com.mars.morediscs;


import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;

import static com.mars.morediscs.CommonClass.MUSIC_DISCS;
import static com.mars.morediscs.Constants.MOD_ID;

@Mod(MOD_ID)
public class MoreDiscs {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final ArrayList<DeferredItem<Item>> ITEM_LIST = new ArrayList<>();

    public MoreDiscs(IEventBus eventBus) {
        CommonClass.init();
        MUSIC_DISCS.forEach((name) -> registerItem(name));
        ITEMS.register(eventBus);

        CREATIVE_MODE_TAB.register("music_disc_group", () -> CreativeModeTab.builder().title(Component.literal("More Music Discs"))
                .withTabsBefore(CreativeModeTabs.COMBAT)
                .icon(() -> ITEM_LIST.getFirst().toStack())
                .displayItems(
                        (parameters, output) -> ITEM_LIST.forEach((item) -> {
                            if (!item.equals(ITEM_LIST.getFirst()))
                                output.accept(item);
                        })
                ).build());
        CREATIVE_MODE_TAB.register(eventBus);
    }

    public static DeferredItem<Item> registerItem(String name){
        DeferredItem<Item> item = ITEMS.register(name,
                () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.RARE).jukeboxPlayable(registerJukeboxSong(name + "_sound"))));
        ITEM_LIST.add(item);

        return item;
    }

    public static ResourceKey<JukeboxSong> registerJukeboxSong(String name){
        return ResourceKey.create(Registries.JUKEBOX_SONG, ResourceLocation.fromNamespaceAndPath(MOD_ID, name));
    }
}
