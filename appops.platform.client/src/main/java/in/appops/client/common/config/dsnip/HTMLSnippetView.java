package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.component.base.BaseComponentView;
import in.appops.client.common.config.dsnip.HTMLSnippetPresenter.HTMLSnippetConstant;
import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.field.BaseField.BaseFieldConstant;
import in.appops.platform.core.shared.Configuration;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class HTMLSnippetView extends BaseComponentView {
	protected HTMLPanel snippetPanel;
	private Map<String, Widget> snippetElementMap = new HashMap<String, Widget>();
	
	private HTMLSnippetModel model;

	
	@Override
	protected void initialize() {
		super.initialize();
		snippetPanel = new HTMLPanel("") {
			
			@Override
			public void addAndReplaceElement(Widget widget, com.google.gwt.user.client.Element toReplace) {
				addAndReplaceElement(widget, toReplace);
				
				if (toReplace == widget.getElement()) {
					return;
				}
				widget.removeFromParent();
				Widget toRemove = null;
				Iterator<Widget> children = getChildren().iterator();
				while (children.hasNext()) {
					Widget next = children.next();
					if (toReplace.isOrHasChild(next.getElement())) {
						if (next.getElement() == toReplace) {
							toRemove = next;
							break;
						}
						children.remove();
					}
				}
				getChildren().add(widget);
				if (toRemove == null) {
					toReplace.getParentNode().replaceChild(widget.getElement(),	toReplace);
				} else {
					toReplace.getParentNode().insertBefore(widget.getElement(),	toReplace);
					remove(toRemove);
				}

				adopt(widget);
			}
		};
	}
	
	protected void processSnippetDescription() {
		try {
			
			//TODO
			NodeList<Element> nodeList =  getAllSpanNodes(); 

			if(nodeList != null) {
				
				int lengthOfNodes = nodeList.getLength();
				for (int i = lengthOfNodes - 1; i > -1; i--) { 
					Node node = nodeList.getItem(i);
					Element spanElement = (Element) Element.as(node); 
					if(spanElement != null) {
						ComponentFactory componentFactory = injector.getComponentFactory();
						
						if (spanElement.hasAttribute("appopsfield") && Boolean.valueOf(spanElement.getAttribute("appopsfield"))) {

							BaseField formField = componentFactory.getField(spanElement.getAttribute("widgetType"));
							if(formField != null) {
								
								String dataConfig = spanElement.getAttribute("data-config");
								if(dataConfig != null && !dataConfig.equals("")) {
								
									if(configuration.getConfigurationValue(dataConfig) != null) {
										Configuration fieldConf = (Configuration) configuration.getConfigurationValue(dataConfig);
										
											fieldConf.setPropertyByName(BaseFieldConstant.BF_ID, spanElement.getId());
											formField.setConfiguration(fieldConf);
											formField.configure();
											formField.create();
											snippetElementMap.put(spanElement.getId(), formField);
											snippetPanel.addAndReplaceElement(formField.asWidget(), spanElement);
										
									}
								}
							}
						} else if(spanElement.hasAttribute("appopsComponent") && Boolean.valueOf(spanElement.getAttribute("appopsComponent"))) {
							BaseComponentPresenter compPres = componentFactory.getComponent(spanElement.getAttribute("widgetType"));
							String dataConfig = spanElement.getAttribute("data-config");
							
							if(configuration.getConfigurationValue(dataConfig) != null) {
								Configuration compConfig = (Configuration) configuration.getConfigurationValue(dataConfig);
								compPres.setConfiguration(compConfig);
								compPres.initialize();

								compPres.configure();
								BaseComponentView component = compPres.getView();
								snippetElementMap.put(spanElement.getId(), component);
								snippetPanel.addAndReplaceElement(component, spanElement);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns <span> elements. 
	 */
	protected NodeList<com.google.gwt.dom.client.Element> getAllSpanNodes() {
		try {
			NodeList<com.google.gwt.dom.client.Element> nodeList = null;
			nodeList = snippetPanel.getElement().getElementsByTagName("span");
			return nodeList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	/**
	 * Method configures the html snippet. 
	 */
	public void configure() {
				
		if(configuration.getConfigurationValue(HTMLSnippetConstant.HS_PCLS) != null) {
			this.setStylePrimaryName(configuration.getConfigurationValue(HTMLSnippetConstant.HS_PCLS).toString());
		}
		
		if(configuration.getConfigurationValue(HTMLSnippetConstant.HS_DCLS) != null) {
			this.addStyleName(configuration.getConfigurationValue(HTMLSnippetConstant.HS_DCLS).toString());
		}
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public Map<String, Widget> getSnippetElementMap() {
		return snippetElementMap;
	}

	public void setSnippetElementMap(Map<String, Widget> snippetElement) {
		this.snippetElementMap = snippetElement;
	}



	public HTMLSnippetModel getModel() {
		return model;
	}

	public void setModel(HTMLSnippetModel model) {
		this.model = model;
	}

	public void setSnippetDescription(String snippetDescription) {
		snippetPanel.getElement().setInnerHTML(snippetDescription);
	}
	
	
}
