package dev.efekos.barricades.init;

import dev.efekos.barricades.Barricades;
import dev.efekos.barricades.entity.BarricadeEntity;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class BarricadesEntities {

    public static void run(){

    }

    public static final EntityType<BarricadeEntity> BARRICADE = Registry.register(Registries.ENTITY_TYPE, Barricades.id("barricade"),
            EntityType.Builder.create(BarricadeEntity::new,SpawnGroup.MISC).makeFireImmune().setDimensions(0,0).build("barricade")
            );

    public static final EntityModelLayer BARRICADE_MODEL_LAYER = new EntityModelLayer(Barricades.id("barricade"), "main");

}
