package dev.efekos.barricades.item;

import dev.efekos.barricades.entity.BarricadeEntity;
import dev.efekos.barricades.init.BarricadesEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

import java.util.Random;

public class BarricadeItem extends Item {

    public BarricadeItem(Settings settings) {
        super(settings);
    }

    private static final Random random = new Random();

    public static boolean canPlace(HitResult hit, Vec3d base) {
        return hit!=null&&hit.getPos()!=null&&hit.getPos().distanceTo(base)<=5;
    }

    public static boolean canPlace(BlockHitResult hit, Vec3d base){
        return hit!=null&&hit.getPos()!=null&&hit.getPos().distanceTo(base)<=5&&hit.getSide()!=Direction.UP&&hit.getSide()!=Direction.DOWN&&hit.getType()== HitResult.Type.BLOCK;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        BlockHitResult result = raycast(world, user, RaycastContext.FluidHandling.NONE);
        if (!canPlace(result,user.getPos()))return TypedActionResult.pass(stack);
        if(!world.isClient){
            BarricadeEntity entity = new BarricadeEntity(BarricadesEntities.BARRICADE, world);
            Direction side = result.getSide();
            if(side.getAxis()==Direction.Axis.X)entity.setRotateHitbox(true);
            entity.setYaw(side.asRotation()+random.nextFloat()*10f-5f);
            entity.setPitch(random.nextFloat()*10f-5f);
            entity.setPosition(result.getPos().add(side.getOffsetX()*0.01f,0,side.getOffsetZ()*0.01f));
            world.spawnEntity(entity);
        }
        return TypedActionResult.success(user.isCreative()?stack:stack.copyWithCount(stack.getCount()-1),true);
    }

}
