package com.mars.morediscs;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mars.morediscs.MoreDiscs.ITEM_LIST;
import static com.mars.morediscs.MoreDiscsConfig.discs_loot_list;
import static com.mars.morediscs.MoreDiscsConfig.enable_loot_modifiers;

public class DiscAdder extends LootModifier {
    protected DiscAdder(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @NotNull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext lootContext) {
        if(!enable_loot_modifiers)
            return generatedLoot;

        Random random = lootContext.getRandom();
        ResourceLocation currentTable = lootContext.getQueriedLootTableId();

        for (String disc_loot : discs_loot_list) {
            String[] set = disc_loot.replaceAll("\\s", "").split(",");
            ArrayList<ItemStack> itemList = new ArrayList<>();
            if(set[0].equals(currentTable.toString())){
                for (int i = 0; i < set.length - 2; i++) {
                    itemList.add(new ItemStack(ITEM_LIST.get(set[i + 1]).get()));
                }

                if(set[set.length - 1].equals("S")){
                    if(lootContext.getParamOrNull(LootContextParams.DAMAGE_SOURCE) != null &&
                            lootContext.getParamOrNull(LootContextParams.DAMAGE_SOURCE).getEntity() instanceof Skeleton)
                        generatedLoot.add(itemList.get(random.nextInt(0, itemList.size() - 1)));
                }
                else if(1 == random.nextInt(1, Integer.parseInt(set[set.length - 1])))
                    generatedLoot.add(itemList.get(random.nextInt(0, itemList.size() - 1)));
            }
        }

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<DiscAdder> {
        @Override
        public DiscAdder read(ResourceLocation location, JsonObject object,
                                     LootItemCondition[] ailootcondition) {
            return new DiscAdder(ailootcondition);
        }

        @Override
        public JsonObject write(DiscAdder instance) {
            return new JsonObject();
        }
    }
}
