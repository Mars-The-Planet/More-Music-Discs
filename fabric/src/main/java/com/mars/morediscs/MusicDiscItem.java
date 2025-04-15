package com.mars.morediscs;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.RecordItem;

import static com.mars.morediscs.MoreDiscs.ITEM_GROUP;

public class MusicDiscItem extends RecordItem {
    protected MusicDiscItem(SoundEvent soundEvent, String name) {
        super(1, soundEvent, name.equals("music_disc_test") ?
                new Item.Properties().rarity(Rarity.RARE).stacksTo(1) :
                new Item.Properties().tab(ITEM_GROUP).rarity(Rarity.RARE).stacksTo(1));
    }
}
