package dev.efekos.barricades.client;

import dev.efekos.barricades.client.entity.BarricadeEntityRenderer;
import dev.efekos.barricades.client.entity.BarricadeModel;
import dev.efekos.barricades.init.BarricadesEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class BarricadesClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        EntityModelLayerRegistry.registerModelLayer(BarricadesEntities.BARRICADE_MODEL_LAYER, BarricadeModel::getTexturedModelData);

        EntityRendererRegistry.register(BarricadesEntities.BARRICADE, BarricadeEntityRenderer::new);

    }


}