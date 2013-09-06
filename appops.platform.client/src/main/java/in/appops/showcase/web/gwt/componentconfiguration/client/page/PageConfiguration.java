package in.appops.showcase.web.gwt.componentconfiguration.client.page;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.ConfigEvent;
import in.appops.client.common.event.handlers.ConfigEventHandler;
import in.appops.platform.core.shared.Configuration;

import java.util.ArrayList;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class PageConfiguration extends Composite implements ConfigEventHandler {
	
	private VerticalPanel basePanel;
	private VerticalPanel addConfigPanel;
	private static final String SPAN_LISTBOX_ID = "spanListBoxFieldId";
	
	private static String CONFIG_TITLE_LABEL_CSS = "configTitleLabel";
	private static String SPAN_SELECTION_LABEL_CSS = "spanSelectionLabel";
	private static String SPAN_SELECTION_PANEL_CSS = "spanSelectionPanel";
	private static String PAGE_CONFIGURATION_BASEPANEL_CSS = "pageConfigurationBasePanel";
	
	private ListBoxField spanListbox;
	
	public PageConfiguration() {
		initialize();
		createUI();
		initWidget(basePanel);
		AppUtils.EVENT_BUS.addHandler(ConfigEvent.TYPE, this);
	}

	private void initialize() {
		basePanel = new VerticalPanel();
		addConfigPanel = new VerticalPanel();
	}
	
	public void createUI() {
		try {
			basePanel.clear();
			
			LabelField titleLabelField = new LabelField();
			titleLabelField.setConfiguration(getConfigTitleLabelConfig());
			titleLabelField.configure();
			titleLabelField.create();
			
			basePanel.add(titleLabelField);
			
			HorizontalPanel spanSelectionPanel = createSpanSelectionPanel();
			basePanel.add(spanSelectionPanel);
			
			basePanel.add(addConfigPanel);
			basePanel.setStylePrimaryName(PAGE_CONFIGURATION_BASEPANEL_CSS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private HorizontalPanel createSpanSelectionPanel() {
		try {
			HorizontalPanel spanSelectionPanel = new HorizontalPanel();
			
			LabelField selectSpanLabelField = new LabelField();
			selectSpanLabelField.setConfiguration(getSelectSpanLabelConfig());
			selectSpanLabelField.configure();
			selectSpanLabelField.create();
			spanSelectionPanel.add(selectSpanLabelField);
			
			spanListbox = new ListBoxField();
			spanListbox.setConfiguration(getSpanListBoxConfiguration(null));
			spanListbox.configure();
			spanListbox.create();
			spanSelectionPanel.add(spanListbox);
			
			spanSelectionPanel.setCellWidth(selectSpanLabelField, "20%");
			spanSelectionPanel.setCellVerticalAlignment(selectSpanLabelField, HasVerticalAlignment.ALIGN_MIDDLE);
			spanSelectionPanel.setStylePrimaryName(SPAN_SELECTION_PANEL_CSS);
			return spanSelectionPanel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates the select span Label configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getSelectSpanLabelConfig() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Select a span element");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, SPAN_SELECTION_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates the Config Title Label configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getConfigTitleLabelConfig() {
		try {
			Configuration configuration = null;	
			try{
				configuration = new Configuration();
				configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, "Page Configurations");
				configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, CONFIG_TITLE_LABEL_CSS);
			}
			catch(Exception e){
				e.printStackTrace();
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Creates the Span Listbox configuration object and return.
	 * @return Configuration instance
	 */
	private Configuration getSpanListBoxConfiguration(ArrayList<String> configList) {
		try {
			Configuration configuration = new Configuration();
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,SPAN_LISTBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"--Select span--");
			if(configList != null) {
				configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ITEMS,configList);
			}
			return configuration;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setAddConfigPanel(Widget widget) {
		try {
			addConfigPanel.clear();
			addConfigPanel.add(widget);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onConfigEvent(ConfigEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			case ConfigEvent.POPULATESPANS: {
				if (eventSource instanceof PageCreation) {
					ArrayList<Element> appopsFieldList =  (ArrayList<Element>) event.getEventData();
					populateSpansListBox(appopsFieldList);
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
	
	private void populateSpansListBox(ArrayList<Element> spansList){
		ArrayList<String> configList = new ArrayList<String>();
		for(Element element : spansList){
			String dataConfig = element.getAttribute("data-config");
			configList.add(dataConfig);
		}
		spanListbox.setConfiguration(getSpanListBoxConfiguration(configList));
		spanListbox.configure();
		spanListbox.create();
	}
}
