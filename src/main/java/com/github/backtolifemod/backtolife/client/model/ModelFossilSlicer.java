package com.github.backtolifemod.backtolife.client.model;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;

public class ModelFossilSlicer extends AdvancedModelBase {
    public AdvancedModelRenderer slicerglasscase;
    public AdvancedModelRenderer table;
    public AdvancedModelRenderer slicercover;

    public ModelFossilSlicer() {
        this.textureWidth = 100;
        this.textureHeight = 100;
        this.table = new AdvancedModelRenderer(this, 0, 0);
        this.table.setRotationPoint(0.0F, 24.0F, 0.0F);
        this.table.addBox(-7.5F, -8.0F, -7.5F, 15, 8, 15, 0.0F);
        this.slicercover = new AdvancedModelRenderer(this, 0, 30);
        this.slicercover.setRotationPoint(0.0F, 16.0F, 0.0F);
        this.slicercover.addBox(-5.5F, -3.5F, -2.5F, 11, 4, 5, 0.0F);
        this.slicerglasscase = new AdvancedModelRenderer(this, 0, 45);
        this.slicerglasscase.setRotationPoint(0.0F, 16.5F, 0.0F);
        this.slicerglasscase.addBox(-7.0F, -7.0F, -7.0F, 14, 7, 14, 0.0F);
    }

    public void render(float f5) { 
        this.table.render(f5);
        this.slicercover.render(f5);
        this.slicerglasscase.render(f5);
    }

}
