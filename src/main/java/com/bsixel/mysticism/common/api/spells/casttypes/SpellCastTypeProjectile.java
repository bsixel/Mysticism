package com.bsixel.mysticism.common.api.spells.casttypes;

import com.bsixel.mysticism.MysticismMod;
import com.bsixel.mysticism.common.api.capability.mana.Force;
import com.bsixel.mysticism.common.api.spells.BaseSpellComponent;
import com.bsixel.mysticism.common.api.spells.SpellComponentInstance;
import com.bsixel.mysticism.common.api.spells.instances.SpellInstance;
import com.bsixel.mysticism.common.entities.projectiles.SpellProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TranslationTextComponent;

public class SpellCastTypeProjectile extends BaseSpellComponent implements ISpellCastType {
    private static final ResourceLocation location = new ResourceLocation(MysticismMod.MOD_ID, "spellcomponent.projectile");

    public SpellCastTypeProjectile() {}

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public TranslationTextComponent getName() {
        return new TranslationTextComponent("spell.component.projectile.name");
    }

    @Override
    public TranslationTextComponent getDescription() {
        return new TranslationTextComponent("spell.component.projectile.description");
    }

    @Override
    public ResourceLocation getResourceLocation() {
        return location;
    }

    @Override
    public ItemStack getIcon() {
        return new ItemStack(Items.ARROW); // TODO: Maybe a snowball? Something else?
    }

    @Override
    public double getAttenuationToForce(Force force) {
        return force == Force.AIR ? getCost() : 0;
    }

    @Override
    public double getCost() {
        return 15;
    }

    @Override
    public double getSustainedCost() {
        return 15;
    }

    @Override
    public boolean canTouchFluids() {
        return false;
    }

    @Override
    public boolean cast(SpellInstance spellInstance, SpellComponentInstance wrapper) {
        SpellProjectileEntity projectileEntity = new SpellProjectileEntity(spellInstance, wrapper);
        projectileEntity.launch();
        spellInstance.getCaster().world.addEntity(projectileEntity);
        return false;
    }

    @Override
    public boolean cast(BlockPos sourcePos, Vector3d lookVector, SpellInstance spellInstance, SpellComponentInstance wrapper) {
        return false;
    }

    // TODO: Color

}
