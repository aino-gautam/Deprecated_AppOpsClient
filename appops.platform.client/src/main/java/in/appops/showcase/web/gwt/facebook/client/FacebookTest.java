/**
 * 
 */
package in.appops.showcase.web.gwt.facebook.client;

import in.appops.client.gwt.web.ui.messaging.MessagingComponent;
import in.appops.client.gwt.web.ui.messaging.chatuserlistcomponent.MainUserListingComponent;
import in.appops.client.gwt.web.ui.messaging.chatuserlistcomponent.UserListWidget;
import in.appops.client.gwt.web.ui.messaging.spacelistcomponent.SpaceListModel;
import in.appops.client.gwt.web.ui.messaging.spacelistcomponent.SpaceListWidget;
import in.appops.platform.core.constants.typeconstants.TypeConstants;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.server.core.services.contact.constant.ContactConstant;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author mahesh@ensarm.com
 *
 */
public class FacebookTest implements EntryPoint {

	@Override
	public void onModuleLoad() {
		try{
			
		/*	String clientId = "478704968863629";
			String serviceId = "11";
			AppopsFacebookWidget widget = new AppopsFacebookWidget(clientId,serviceId);
			RootPanel.get().add(widget);*/
			
	/*		SearchWidgetTest searchWidget = new SearchWidgetTest();
			RootPanel.get().add(searchWidget);*/
			
			

		/*	SpaceListModel spaceListModel  = new SpaceListModel();
			SpaceListWidget spaceListWidget = new SpaceListWidget(spaceListModel);
			*/
			
			Entity userEnt = createDummyEnt();
			
			MessagingComponent messaginComponent = new MessagingComponent();
			messaginComponent.setUserEntity(userEnt);
			
		//	ChatUserListWidget widget = new ChatUserListWidget();
			RootPanel.get().add(messaginComponent);
			
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Entity createDummyEnt() {
		Entity entity = new Entity();
		entity.setType(new MetaType(TypeConstants.CONTACT));
		
		Property<Key<Long>> keyPropbase = new Property<Key<Long>>();
		Key<Long> key1 = new Key<Long>(60L);
		keyPropbase.setValue(key1);
		entity.setProperty(ContactConstant.ID, keyPropbase);
		
		Property<String> nameProp = new Property<String>("Mahesh More");
		entity.setProperty(ContactConstant.NAME, nameProp);
		
		Property<String> emailProp = new Property<String>("mahesh@ensarm.com");
		entity.setProperty(ContactConstant.EMAILID, emailProp);
		
		Property<String> aliasProp = new Property<String>("mahesh");
		entity.setProperty(ContactConstant.ALIAS, aliasProp);
		
		return entity;
	}

}
