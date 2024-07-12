package com.teampotato.moreloyaltrident.api;

import java.util.Set;
import java.util.UUID;

public interface LootCarrier {
    void moreLoyalTrident$carryLoot(UUID loot);
    Set<UUID> moreLoyalTrident$getCarriedLoots();
    void moreLoyalTrident$clearData();
}
