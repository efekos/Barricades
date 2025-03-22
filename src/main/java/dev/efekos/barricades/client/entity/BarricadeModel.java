package dev.efekos.barricades.client.entity;

import dev.efekos.barricades.entity.BarricadeEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class BarricadeModel extends EntityModel<BarricadeEntity> {

    private final ModelPart bb_main;

    public BarricadeModel(ModelPart root) {
        this.bb_main = root.getChild("bb_main");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-18.0F, -4.0F, -1.0F, 36.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 2.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(BarricadeEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.bb_main.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
