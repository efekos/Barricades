package dev.efekos.barricades.client.entity;

import dev.efekos.barricades.Barricades;
import dev.efekos.barricades.entity.BarricadeEntity;
import dev.efekos.barricades.init.BarricadesEntities;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class BarricadeEntityRenderer extends EntityRenderer<BarricadeEntity> {

    private final BarricadeModel model;

    public BarricadeEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        model = new BarricadeModel(ctx.getPart(BarricadesEntities.BARRICADE_MODEL_LAYER));
    }

    private static final Identifier[] TEXTURES = new Identifier[]{
            Barricades.id("textures/entity/barricade.png"),
            Barricades.id("textures/entity/barricade_damage1.png"),
            Barricades.id("textures/entity/barricade_damage2.png"),
            Barricades.id("textures/entity/barricade_damage3.png"),
            Barricades.id("textures/entity/barricade_damage4.png"),
            Barricades.id("textures/entity/barricade_damage4.png")
    };

    @Override
    public Identifier getTexture(BarricadeEntity entity) {
        return TEXTURES[entity.getDamage()];
    }

    @Override
    public void render(BarricadeEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.push();
        long l = entity.getWorld().getTime() - entity.getLastDamageTicks();
        if(l>=0&&l<2){
            float v = 1 + (l * 0.04f);
            matrices.scale(v,v,v);
        }
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-entity.getYaw()));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(entity.getPitch()));
        VertexConsumer buffer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(TEXTURES[entity.getDamage()]));
        model.render(matrices,buffer,light,OverlayTexture.packUv(0, 10),1f,1f,1f,1f);
        matrices.pop();
    }

}
