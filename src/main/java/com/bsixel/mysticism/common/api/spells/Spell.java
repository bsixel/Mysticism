package com.bsixel.mysticism.common.api.spells;

import com.bsixel.mysticism.common.api.spells.casttypes.ISpellCastType;
import javafx.scene.paint.Color;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.UUID;

public class Spell { // NOTE: This hold the state of a spell, but doesn't represent the cast spell.
    public SpellComponentInstance root; // Spells are trees that start with one SpellComponentInstance node, basically

    // Some additional that may be needed depending on what components we have in the spell
    private final String creatorUUID; // Who made this spell? Spells can be copied, maybe we need the original creator TODO: We need a fake player+UUID
    private final String creationDimension;
    private final BlockPos creationPosition;

    public Spell(String creatorUUID, BlockPos pos, String creationDimension) {
        this.creatorUUID = creatorUUID;
        this.creationPosition = pos;
        this.creationDimension = creationDimension;
    }

    /**
     *
     * @param creator Who created it
     * @param pos The initial block position at the time of creation of this spell
     * @param creationDimension dimension.getDimensionKey().getRegistryName().toString()
     */
    public Spell(LivingEntity creator, BlockPos pos, String creationDimension) { // Living entity should be safe, FakePlayer is a distant inheritor
        this.creatorUUID = creator.getUniqueID().toString();
        this.creationDimension = creationDimension;
        this.creationPosition = pos;
    }

    public Spell(LivingEntity creator) {
        this(creator, creator.getPosition(), creator.world.getDimensionKey().getRegistryName().toString());
    }

    public Spell(LivingEntity creator, SpellComponentInstance root) {
        this(creator);
        this.root = root;
    }

    public static CompoundNBT serialize(Spell spell) {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putString("creator", spell.creatorUUID);
        nbt.putString("dim", spell.creationDimension);
        nbt.putInt("xPos", spell.creationPosition.getX());
        nbt.putInt("yPos", spell.creationPosition.getY());
        nbt.putInt("zPos", spell.creationPosition.getZ());
        nbt.put("tree", spell.root.serialize());
        return nbt;
    }

    public static Spell deserialize(World world, CompoundNBT nbt) {
        String creator = nbt.getString("creator");
        String dim = nbt.getString("dim");
        int xPos = nbt.getInt("xPos");
        int yPos = nbt.getInt("yPos");
        int zPos = nbt.getInt("zPos");

        CompoundNBT rootNbt = (CompoundNBT) nbt.get("tree");
        Spell spell = new Spell(Objects.requireNonNull(world.getPlayerByUuid(UUID.fromString(creator))), new BlockPos(xPos, yPos, zPos), dim);
        if (rootNbt != null) {
            spell.root = SpellComponentInstance.deserialize(spell, rootNbt);
        }
        return spell;
    }

    // Normal casters like players and justiciars/enforcers
    public void cast(@Nonnull LivingEntity caster) {
        if (root != null) {
            ((ISpellCastType)root.getComponent()).cast(caster, root);
        }
    }

    // Artificial caster. Not sure what all this will include but probably some sorta block that casts. Magic traps? Might be needed for anchors? I don't think so.
    public void cast(@Nonnull BlockPos sourcePos, @Nonnull Vector3d lookVector) {
        if (root != null) {
            ((ISpellCastType)root.getComponent()).cast(sourcePos, lookVector, root);
        }
    }

    public Color getPrimaryColor() {
        return root.getComponent().getPrimaryColor();
    }

    public String getCreatorUUID() {
        return creatorUUID;
    }

    public String getCreationDimension() {
        return creationDimension;
    }

    public BlockPos getCreationPosition() {
        return creationPosition;
    }

    public double getCost() {
        return this.root.getCost();
    }

}
