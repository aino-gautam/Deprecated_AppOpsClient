package in.appops.showcase.web.gwt.componentconfiguration.client.page;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.server.core.services.configuration.constant.ConfigTypeConstant;

public class EntityInstanceProvider {

	public Entity getConfiginstanceEntity(String instanceName, String configKeyName, String instanceValue, Entity parent, Long configTypeId) {
		try{
			Entity configInstEntity = new Entity();
			configInstEntity.setType(new MetaType("Configinstance"));
			configInstEntity.setPropertyByName("instancename", instanceName);
			configInstEntity.setPropertyByName("configkeyname", configKeyName);
			if(instanceValue != null) {
				configInstEntity.setPropertyByName("instancevalue", instanceValue);
			}
			if(parent != null) {
				configInstEntity.setProperty("configinstance", parent);
			}
			if(configTypeId != null) {
				configInstEntity.setProperty("configtype", getConfigType(configTypeId));
			}
			return configInstEntity;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//TODO : Hardcoded entity has been set
	public Entity getConfigType(Long configTypeId) {
		Entity configTypeEntity = new Entity();
		configTypeEntity.setType(new MetaType("Configtype"));
		Key<Long> key = new Key<Long>(configTypeId);
		Property<Key<Long>> keyProp = new Property<Key<Long>>(key);
		configTypeEntity.setProperty(ConfigTypeConstant.ID, keyProp);
		return configTypeEntity;
	}
	
	//TODO : Hardcoded entity has been set
	public Entity getCompoDefEnt() {
		try{
			Entity compoDefEntity = new Entity();
			compoDefEntity.setType(new MetaType("Componentdefinition"));
			Key<Long> key = new Key<Long>(78L);
			Property<Key<Long>> keyProp = new Property<Key<Long>>(key);
			compoDefEntity.setProperty("id", keyProp);
			compoDefEntity.setPropertyByName("name", "Container");
			compoDefEntity.setPropertyByName("typeId", 189L);
			
			//TODO : hardcoded entity
			compoDefEntity.setPropertyByName("configtypeId", 134L);
			compoDefEntity.setPropertyByName("isMvp", 0);
			return compoDefEntity;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
