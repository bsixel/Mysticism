package com.bsixel.mysticism.common.utilities;

import com.bsixel.mysticism.common.init.registries.ItemRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.IItemTier;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.crafting.Ingredient;

public class ItemHelper {

    public static IItemTier generateItemTier(int harvestLevel) { // If you want an actual tier please make one and don't use this, it's basically just for spell helpers
        return new IItemTier() {
            @Override
            public int getMaxUses() {
                return harvestLevel * 1000;
            }

            @Override
            public float getEfficiency() {
                return harvestLevel * 2;
            }

            @Override
            public float getAttackDamage() {
                return harvestLevel * 2;
            }

            @Override
            public int getHarvestLevel() {
                return harvestLevel;
            }

            @Override
            public int getEnchantability() {
                return harvestLevel * 7;
            }

            @Override
            public Ingredient getRepairMaterial() {
                return null;
            }
        };
    }

    public static ItemStack generateMockPick(int miningLevel) {
        return new ItemStack(() -> new PickaxeItem(generateItemTier(miningLevel), miningLevel * 2, miningLevel, ItemRegistry.getDefaultProperties()));
    }

    public static ItemStack getToolOrMock(Entity user, BlockState hitState, int minMiningLevel) {
        ItemStack returnTool = generateMockPick(minMiningLevel);
        if (user instanceof PlayerEntity) { // We're some sorta player, see if we can grab a valid stack to use
            PlayerEntity playerCaster = (PlayerEntity) user;
            ItemStack mainhand = playerCaster.getHeldItemMainhand();
            ItemStack offhand = playerCaster.getHeldItemOffhand();
            if (isToolItem(mainhand)) { // Default to using bonus properties of the mainhand tool
                returnTool = generateMockPick(mainhand.getHarvestLevel(mainhand.getToolTypes().stream().findFirst().get(), null, null));
                EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(mainhand), returnTool);
            } else if (isToolItem(offhand)) {
                returnTool = generateMockPick(offhand.getHarvestLevel(offhand.getToolTypes().stream().findFirst().get(), null, null));
                EnchantmentHelper.setEnchantments(EnchantmentHelper.getEnchantments(offhand), returnTool);
            }
        }
        return returnTool;
    }

    public static boolean isToolItem(ItemStack stack) {
        return stack.getToolTypes().size() > 0;
    }

}
