package in.appops.client.common.config.dsnip;

import in.appops.platform.core.entity.Entity;

public class Context {
	private String contextPath;
	private Entity parentEntity;
	private final ApplicationContext applicationContext;

	public Context() {
		applicationContext = ApplicationContext.getInstance();
	}

	public String getContextPath() {
		return contextPath == null ? "" : contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public Entity getParentEntity() {
		return parentEntity;
	}

	public void setParentEntity(Entity parentEntity) {
		this.parentEntity = parentEntity;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
}
