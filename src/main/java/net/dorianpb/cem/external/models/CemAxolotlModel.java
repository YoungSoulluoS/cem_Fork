package net.dorianpb.cem.external.models;	

import net.dorianpb.cem.internal.api.CemModel;	
import net.dorianpb.cem.internal.models.CemModelRegistry;	
import net.dorianpb.cem.internal.models.CemModelRegistry.CemPrepRootPartParamsBuilder;
import net.minecraft.client.render.entity.model.AxolotlEntityModel;	
import net.minecraft.entity.passive.AxolotlEntity;	

import java.util.*;

public class CemAxolotlModel extends AxolotlEntityModel<AxolotlEntity> implements CemModel {	
    private static final Map<String, String> partNames  = new HashMap<>();	
    private static final Map<String, List<String>> familyTree = new LinkedHashMap<>();	
    private final CemModelRegistry registry;	

    static{	
        partNames.put("leg1", "right_hind_leg");	
        partNames.put("leg2", "left_hind_leg");	
        partNames.put("leg3", "right_front_leg");	
        partNames.put("leg4", "left_front_leg");	
    }	

    static{	
        familyTree.put("body", Arrays.asList("leg1", "leg2", "leg3", "leg4","head","tail","top_gills", "left_gills", "right_gills"));	
    }	

    public CemAxolotlModel(CemModelRegistry registry){	
        super(registry.prepRootPart((new CemPrepRootPartParamsBuilder()).setPartNameMap(partNames)	
                                                                                        .setFamilyTree(familyTree)	
                                                                                        .setVanillaReferenceModelFactory(() -> getTexturedModelData().createModel())	
                                                                                        .create()));	
        this.registry = registry;	
    }	

    @Override	
    public void setAngles(AxolotlEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch){	
        super.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);	
        this.registry.applyAnimations(limbAngle, limbDistance, animationProgress, headYaw, headPitch, entity);	
    }	
}	

