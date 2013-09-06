package in.appops.showcase.web.gwt.componentconfiguration.client.page;

import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;

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
		basePanel.setWidth("100%");
	}

	@Override
	public void onConfigEvent(ConfigEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			case ConfigEvent.SHOWPAGECONFIGURATION: {
				if(pageConfig != null) {
					pageConfig.setVisible(true);
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
