package com.github.backtolifemod.backtolife.enums;

import net.minecraft.tileentity.TileEntity;

public enum EnumPrehistoricTileEntityType {
	VELOCIRAPTOR(new Class[]{VelociraptorSkull.class, VelociraptorRibcage.class, VelociraptorFoot.class, VelociraptorLimb.class, VelociraptorTail.class}),
	PROTOCERATOPS(new Class[]{ProtoceratopsSkull.class, ProtoceratopsRibcage.class, ProtoceratopsFoot.class, ProtoceratopsLimb.class, ProtoceratopsTail.class}),
	TARBOSAURUS(new Class[]{TarbosaurusSkull.class, TarbosaurusRibcage.class, TarbosaurusFoot.class, TarbosaurusLimb.class, TarbosaurusTail.class});
	public class VelociraptorSkull extends TileEntity{}
	public class VelociraptorRibcage extends TileEntity{}
	public class VelociraptorFoot extends TileEntity{}
	public class VelociraptorLimb extends TileEntity{}
	public class VelociraptorTail extends TileEntity{}
	public class ProtoceratopsSkull extends TileEntity{}
	public class ProtoceratopsRibcage extends TileEntity{}
	public class ProtoceratopsFoot extends TileEntity{}
	public class ProtoceratopsLimb extends TileEntity{}
	public class ProtoceratopsTail extends TileEntity{}
	public class TarbosaurusSkull extends TileEntity{}
	public class TarbosaurusRibcage extends TileEntity{}
	public class TarbosaurusFoot extends TileEntity{}
	public class TarbosaurusLimb extends TileEntity{}
	public class TarbosaurusTail extends TileEntity{}
	
	public Class<? extends TileEntity>[] skullClasses;
	private EnumPrehistoricTileEntityType(Class<? extends TileEntity>[] skullClasses) {
		this.skullClasses = skullClasses;
	}
}
