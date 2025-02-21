package net.dorianpb.cem.external.renderers;

import net.dorianpb.cem.external.models.CemSkeletonModel;
import net.dorianpb.cem.internal.api.CemRenderer;
import net.dorianpb.cem.internal.models.CemArmorModelSkeleton;
import net.dorianpb.cem.internal.models.CemModelRegistry;
import net.dorianpb.cem.internal.util.CemRegistryManager;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.WitherSkeletonEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.util.Identifier;

public class CemWitherSkeletonRenderer extends WitherSkeletonEntityRenderer implements CemRenderer{
	private final CemModelRegistry registry;
	
	public CemWitherSkeletonRenderer(EntityRendererFactory.Context context){
		super(context);
		this.registry = CemRegistryManager.getRegistry(getType());
		try{
			this.model = new CemSkeletonModel(this.registry);
			if(this.registry.hasShadowRadius()){
				this.shadowRadius = this.registry.getShadowRadius();
			}
			this.features.replaceAll((feature) -> {
				if(feature instanceof ArmorFeatureRenderer){
					return new ArmorFeatureRenderer<>(this,
					                                  new CemArmorModelSkeleton<>((CemSkeletonModel) this.model, 0.5F),
					                                  new CemArmorModelSkeleton<>((CemSkeletonModel) this.model, 1.0F),
					                                  context.getModelManager()
					);
				}
				else{
					return feature;
				}
			});
		} catch(Exception e){
			this.modelError(e);
		}
	}
	
	private static EntityType<? extends Entity> getType(){
		return EntityType.WITHER_SKELETON;
	}
	
	@Override
	public String getId(){
		return getType().toString();
	}
	
	@Override
	public Identifier getTexture(AbstractSkeletonEntity entity){
		if(this.registry != null && this.registry.hasTexture()){
			return this.registry.getTexture();
		}
		return super.getTexture(entity);
	}
}