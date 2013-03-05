package in.appops.client.common.bound;

import in.appops.platform.core.entity.Entity;

public interface EntityBound extends Bound {

	public Entity getEntity();
	
	public void setEntity(Entity entity);
	
}
