package com.github.backtolifemod.backtolife.client.model;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;

public class ModelFertilizationMachine extends AdvancedModelBase {
    public AdvancedModelRenderer Base;
    public AdvancedModelRenderer shape2;
    public AdvancedModelRenderer shape3;
    public AdvancedModelRenderer EggBase;

    public ModelFertilizationMachine() {
        this.textureWidth = 128;
        this.textureHeight = 128;
        this.EggBase = new AdvancedModelRenderer(this, 74, 0);
        this.EggBase.setRotationPoint(0.0F, -2.0F, 0.0F);
        this.EggBase.addBox(-2.5F, -3.0F, -2.5F, 5, 2, 5, 0.0F);
        this.shape2 = new AdvancedModelRenderer(this, 0, 26);
        this.shape2.setRotationPoint(0.0F, -3.0F, 0.0F);
        this.shape2.addBox(-6.0F, -8.0F, 3.5F, 12, 8, 7, 0.0F);
        this.shape3 = new AdvancedModelRenderer(this, 0, 0);
        this.shape3.setRotationPoint(0.0F, -12.0F, 0.0F);
        this.shape3.addBox(-7.5F, -4.0F, -3.0F, 15, 5, 14, 0.0F);
        this.Base = new AdvancedModelRenderer(this, 0, 47);
        this.Base.setRotationPoint(0.0F, 24.0F, -3.5F);
        this.Base.addBox(-7.5F, -3.0F, -4.0F, 15, 3, 15, 0.0F);
        this.Base.addChild(this.EggBase);
        this.Base.addChild(this.shape2);
        this.Base.addChild(this.shape3);
    }

    public void render(float f5) { 
        this.Base.render(f5);
    }
}
