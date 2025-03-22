package dev.efekos.barricades;

import dev.efekos.barricades.init.BarricadesEntities;
import dev.efekos.barricades.init.BarricadesItems;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class Barricades implements ModInitializer {

    public static final String ID = "barricades";

    public static Identifier id(String path){
        return Identifier.of(ID,path);
    }

    @Override
    public void onInitialize() {
        BarricadesEntities.run();
        BarricadesItems.run();
    }

}
