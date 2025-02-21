package net.dorianpb.cem.external.renderers;

import net.dorianpb.cem.external.models.CemZombieModel;
import net.dorianpb.cem.internal.api.CemRenderer;
import net.dorianpb.cem.internal.models.CemArmorModel;
import net.dorianpb.cem.internal.models.CemModelRegistry;
import net.dorianpb.cem.internal.util.CemRegistryManager;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.util.Identifier;

public class CemZombieRenderer extends ZombieEntityRenderer implements CemRenderer{
	private final CemModelRegistry registry;
	
	public CemZombieRenderer(EntityRendererFactory.Context context){
		super(context);
		this.registry = CemRegistryManager.getRegistry(getType());
		try{
			this.model = new CemZombieModel(this.registry);
			if(this.registry.hasShadowRadius()){
				this.shadowRadius = this.registry.getShadowRadius();
			}
			this.features.replaceAll((feature) -> {
				if(feature instanceof ArmorFeatureRenderer){
					return new ArmorFeatureRenderer<>(this,
					                                  new CemArmorModel<>((CemZombieModel) this.model, 0.5F),
					                                  new CemArmorModel<>((CemZombieModel) this.model, 1.0F),
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
		return EntityType.ZOMBIE;
	}
	
	@Override
	public String getId(){
		return getType().toString();
	}
	
	@Override
	public Identifier getTexture(ZombieEntity entity){
		if(this.registry != null && this.registry.hasTexture()){
			return this.registry.getTexture();
		}
		return super.getTexture(entity);
	}
}