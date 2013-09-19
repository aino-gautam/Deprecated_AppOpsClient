package in.appops.showcase.web.gwt.componentconfiguration.client.page;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;
import in.appops.platform.core.entity.Entity;

import java.util.HashMap;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PageManager extends Composite implements ConfigEventHandler{
	
	private VerticalPanel basePanel;
	private PageConfiguration pageConfig;

	public PageManager() {
		init();
		initWidget(basePanel);
		AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
	}

	private void init() {
		basePanel = new VerticalPanel();
	}

	public void initialize() {
		basePanel.clear();
		PageCreation pageCreation = new PageCreation();
		basePanel.add(pageCreation);
		
		pageConfig = new PageConfiguration();
		basePanel.add(pageConfig);
		pageConfig.setVisible(false);
		basePanel.setStylePrimaryName("PageManagerBasePanel");
	}

	@Override
	public void onConfigEvent(ConfigEvent event) {
		try {
			int eventType = event.getEventType();
			switch (eventType) {
			case ConfigEvent.SHOWPAGECONFIGURATION: {
				if(event.getEventData() != null) {
					HashMap<String, Entity> entityMap = (HashMap<String, Entity>) event.getEventData();
					if(entityMap != null && !entityMap.isEmpty()) {
						Entity pageCompInstEnt = entityMap.get("pageCompInstEntity");
						Entity pageEnt = entityMap.get("pageEntity");
						if(pageCompInstEnt != null) {
							pageConfig.setVisible(true);
							pageConfig.setPageComponentInstEntity(pageCompInstEnt);
							pageConfig.setPageEntity(pageEnt);
						}
					}
					
				}
				break;
			}
			case ConfigEvent.HIDEPAGECONFIGURATION: {
				if(pageConfig != null) {
					pageConfig.setVisible(false);
				}
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
