package dev.efekos.barricades.init;

import dev.efekos.barricades.Barricades;
import dev.efekos.barricades.item.BarricadeItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BarricadesItems {

    private static <T extends Item> T register(String id,T item){
        return Registry.register(Registries.ITEM, Barricades.id(id), item);
    }

    public static final BarricadeItem BARRICADE = register("barricade",new BarricadeItem(new Item.Settings()));

    public static void run() {

    }

}