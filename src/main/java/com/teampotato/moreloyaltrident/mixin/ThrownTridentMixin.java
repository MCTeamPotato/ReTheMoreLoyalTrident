package com.teampotato.moreloyaltrident.mixin;

import com.teampotato.moreloyaltrident.TheMoreLoyalTrident;
import com.teampotato.moreloyaltrident.api.LootCarrier;
import com.teampotato.moreloyaltrident.api.LoyalChecker;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

@Mixin(ThrownTrident.class)
public abstract class ThrownTridentMixin extends AbstractArrow implements LoyalChecker, LootCarrier {
    @Shadow @Final private static EntityDataAccessor<Byte> ID_LOYALTY;

    @Unique private @Nullable Set<UUID> moreLoyalTrident$carriedItems = null;

    protected ThrownTridentMixin(EntityType<? extends AbstractArrow> arg, Level arg2) {
        super(arg, arg2);
    }

    @Override
    public boolean moreLoyalTrident$canCatchLoot() {
        return this.entityData.get(ID_LOYALTY) >= TheMoreLoyalTrident.LEVEL.get().byteValue();
    }

    @Override
    public void moreLoyalTrident$carryLoot(UUID loot) {
        if (this.moreLoyalTrident$carriedItems == null) this.moreLoyalTrident$carriedItems = new ConcurrentSet<>();
        this.moreLoyalTrident$carriedItems.add(loot);
    }

    @Override
    public Set<UUID> moreLoyalTrident$getCarriedLoots() {
        return this.moreLoyalTrident$carriedItems == null ? Collections.emptySet() : this.moreLoyalTrident$carriedItems;
    }

    @Override
    public void moreLoyalTrident$clearData() {
        this.moreLoyalTrident$carriedItems = null;
    }

    @Inject(method = "playerTouch", at = @At("TAIL"))
    private void moreLoyalTrident$carryItems(Player player, CallbackInfo ci) {
        if (!(this.level() instanceof ServerLevel)) return;
        this.moreLoyalTrident$getCarriedLoots().forEach(loot -> {
            Entity lootEntity = ((ServerLevel) this.level()).getEntity(loot);
            if (lootEntity != null) lootEntity.teleportTo(this.getX(), this.getY(), this.getZ());
        });
        this.moreLoyalTrident$clearData();
    }
}
