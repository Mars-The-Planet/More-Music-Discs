package com.mars.morediscs;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

import java.util.ArrayList;

import static com.mars.morediscs.MoreDiscs.ITEM_LIST;
import static com.mars.morediscs.MoreDiscsConfig.discs_loot_list;
import static com.mars.morediscs.MoreDiscsConfig.enable_loot_modifiers;

public class DiscAdder extends LootModifier {
    public static final MapCodec<DiscAdder> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst).apply(inst, DiscAdder::new));
    protected DiscAdder(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext lootContext) {
        if(!enable_loot_modifiers)
            return generatedLoot;

        RandomSource random = lootContext.getRandom();
        ResourceLocation currentTable = lootContext.getQueriedLootTableId();

        for (String disc_loot : discs_loot_list) {
            String[] set = disc_loot.replaceAll("\\s", "").split(",");
            ArrayList<ItemStack> itemList = new ArrayList<>();
            if(set[0].equals(currentTable.toString())){
                for (int i = 0; i < set.length - 2; i++) {
                    itemList.add(new ItemStack(ITEM_LIST.get(set[i + 1]).asItem()));
                }

                if(set[set.length - 1].equals("S")){
                    if(lootContext.getOptionalParameter(LootContextParams.DAMAGE_SOURCE) != null &&
                            lootContext.getOptionalParameter(LootContextParams.DAMAGE_SOURCE).getEntity() instanceof Skeleton)
                        generatedLoot.add(itemList.get(random.nextIntBetweenInclusive(0, itemList.size() - 1)));
                }
                else if(1 == random.nextIntBetweenInclusive(1, Integer.parseInt(set[set.length - 1])))
                    generatedLoot.add(itemList.get(random.nextIntBetweenInclusive(0, itemList.size() - 1)));
            }
        }

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return CODEC;
    }
}
