package com.github.backtolifemod.backtolife.client.model;

import net.ilexiconn.llibrary.client.model.tools.AdvancedModelBase;
import net.ilexiconn.llibrary.client.model.tools.AdvancedModelRenderer;

public class ModelTissueAnalyzer extends AdvancedModelBase {
    public AdvancedModelRenderer base;
    public AdvancedModelRenderer back;
    public AdvancedModelRenderer analysischamber;
    public AdvancedModelRenderer console;

    public ModelTissueAnalyzer() {
        this.textureWidth = 64;
        this.textureHeight = 128;
        this.analysischamber = new AdvancedModelRenderer(this, 0, 60);
        this.analysischamber.setRotationPoint(0.0F, -1.9F, 0.0F);
        this.analysischamber.addBox(-6.0F, -5.0F, -3.5F, 12, 5, 7, 0.0F);
        this.back = new AdvancedModelRenderer(this, 0, 20);
        this.back.setRotationPoint(0.0F, 0.0F, 7.5F);
        this.back.addBox(-7.5F, -15.0F, -3.5F, 15, 15, 7, 0.0F);
        this.base = new AdvancedModelRenderer(this, 0, 0);
        this.base.setRotationPoint(0.0F, 24.0F, -3.5F);
        this.base.addBox(-7.5F, -2.0F, -4.0F, 15, 2, 8, 0.0F);
        this.console = new AdvancedModelRenderer(this, 0, 45);
        this.console.setRotationPoint(0.0F, -11.0F, -6.0F);
        this.console.addBox(-7.0F, -3.5F, -2.5F, 14, 7, 5, 0.0F);
        this.base.addChild(this.back);
        this.back.addChild(this.console);
        this.base.addChild(this.analysischamber);
    }

    public void render(float f5) { 
        this.base.render(f5);
    }

}
