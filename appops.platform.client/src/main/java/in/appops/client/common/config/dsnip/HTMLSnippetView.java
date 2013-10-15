package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponent;
import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.field.FieldPresenter;
import in.appops.client.common.config.model.ConfigurationModel;
import in.appops.client.common.config.model.PropertyModel;
import in.appops.client.common.core.EntityReceiver;
import in.appops.platform.core.entity.Entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.DockPanel.DockLayoutConstant;

public class HTMLSnippetView extends BaseComponent implements EntityReceiver {
	protected HTMLPanel snippetPanel;
	protected Map<String, BaseComponentPresenter> elementMap = new HashMap<String, BaseComponentPresenter>();
	
	private String snippetType;
	private final String SPAN = "span";
	private final String APPOPS_FIELD = "appopsField";
	private final String APPOPS_COMPONENT = "appopsComponent";
	protected final String HTMLSNIPPET = "htmlSnippet";
	protected final String COMPONENT_TYPE = "componentType";
	protected final String TYPE = "type";
	protected final String DATA_CONFIG = "data-config";
	
	@Override
	public void initialize() {
		super.initialize();
		String snippetDescription = ((HTMLSnippetModel) model).getDescription(snippetType);
		//((HTMLSnippetModel) model).setReceiver(this);
		snippetPanel = new HTMLPanel(snippetDescription) {
			
			@Override
			public void addAndReplaceElement(Widget widget, com.google.gwt.user.client.Element toReplace) {
				
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
		basePanel.add(snippetPanel, DockPanel.CENTER);
	}
	
	@Override
	public void create() {
		try {
			
			NodeList<Element> nodeList =  getAllSpanNodes(); 

			if(nodeList != null) {
				
				int lengthOfNodes = nodeList.getLength();
				for (int i = lengthOfNodes - 1; i > -1; i--) { 
					Node node = nodeList.getItem(i);
					Element spanElement = (Element) Element.as(node); 
					if(spanElement != null) {
						
						if (spanElement.hasAttribute(COMPONENT_TYPE)) {
							if(spanElement.getAttribute(COMPONENT_TYPE).equalsIgnoreCase(APPOPS_FIELD)) {
								String dataConfig = spanElement.getAttribute(DATA_CONFIG);
	
								PropertyModel propertyModel = ((HTMLSnippetModel) model).getPropertyModel();
								FieldPresenter fieldPresenter = mvpFactory.requestField(spanElement.getAttribute(TYPE), dataConfig, propertyModel);
								if(fieldPresenter != null) {
									fieldPresenter.create();
									elementMap.put(dataConfig, fieldPresenter);
									snippetPanel.addAndReplaceElement(fieldPresenter.getView().asWidget(), spanElement);
								}
							} else if(spanElement.getAttribute(COMPONENT_TYPE).equalsIgnoreCase(APPOPS_COMPONENT)) {
							/*BaseComponentPresenter compPres = componentFactory.getComponent(spanElement.getAttribute("widgetType"));
							String dataConfig = spanElement.getAttribute("data-config");
							
							if(viewConfiguration.getConfigurationValue(dataConfig) != null) {
								Configuration compConfig = (Configuration) viewConfiguration.getConfigurationValue(dataConfig);
								compPres.setConfiguration(compConfig);
								compPres.initialize();

								compPres.configure();
								BaseComponentView component = compPres.getView();
								snippetElementMap.put(spanElement.getId(), component);
								snippetPanel.addAndReplaceElement(component, spanElement);
							}*/
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
			nodeList = snippetPanel.getElement().getElementsByTagName(SPAN);
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
		super.configure();
		
		/*
		 * TODO set configurations for snippet panel if any
		 */
		/*if(viewConfiguration.getConfigurationValue(HTMLSnippetConstant.HS_PCLS) != null) {
			this.setStylePrimaryName(viewConfiguration.getConfigurationValue(HTMLSnippetConstant.HS_PCLS).toString());
		}
		
		if(viewConfiguration.getConfigurationValue(HTMLSnippetConstant.HS_DCLS) != null) {
			this.addStyleName(viewConfiguration.getConfigurationValue(HTMLSnippetConstant.HS_DCLS).toString());
		}*/
	}

	public Map<String, BaseComponentPresenter> getElementMap() {
		return elementMap;
	}

	public void setElementMap(Map<String, BaseComponentPresenter> snippetElement) {
		this.elementMap = snippetElement;
	}

	@Override
	public void noMoreData() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityReceived(Entity entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEntityUpdated(Entity entity) {
		// TODO Auto-generated method stub
		
	}
	
	public void addAndReplaceElement(Widget widget, Element toReplace) {
		snippetPanel.addAndReplaceElement(widget, toReplace);
	}

	public void setSnippetType(String snippetType) {
		this.snippetType = snippetType;
	}
}
