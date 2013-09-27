package in.appops.client.common.config.breadcrumb;


import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.util.EntityList;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class BreadcrumbComponent extends Composite{

	FlowPanel fPanel;
	Entity entity1= new Entity();
	public Logger logger = Logger.getLogger(getClass().getName());

	public BreadcrumbComponent(){
		
	}
	
	public void configure() {
		
		fPanel= new FlowPanel();
		EntityList list= new EntityList();
		
		Entity entity2= new Entity();
		entity2.setPropertyByName("Name","Gujarat");
		Entity entity3= new Entity();
		entity3.setPropertyByName("Name","Kerala");
		Entity entity4= new Entity();
		entity4.setPropertyByName("Name","Goa");
		list.add(entity2);
		list.add(entity3);
		list.add(entity4);
		
		
		entity1.setPropertyByName("Name","India");
		entity1.setPropertyByName("ChildList", list);
				
		initWidget(fPanel);
	}
 
	public void create(){
		try {
			logger.log(Level.INFO, "[BreadcrumbComponent] ::In create method ");
			BreadcrumbSnippetField component = new BreadcrumbSnippetField(entity1.getPropertyByName("Name").toString());
			component.setActionEntity(entity1);
			HorizontalPanel hPanel= component.create();
				
			fPanel.add(hPanel);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[BreadcrumbComponent] ::Exception In create method "+e);

		}
	}

}
