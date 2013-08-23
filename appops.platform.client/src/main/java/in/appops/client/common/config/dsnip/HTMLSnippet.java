package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.component.base.BaseComponentView;
import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.field.BaseField.BaseFieldConstant;
import in.appops.client.common.config.util.Configurator;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class HTMLSnippet extends HTMLPanel {

	private Entity entity;
	private Configuration configuration;
	private Map<String, Widget> snippetElement = new HashMap<String, Widget>();
	/**
	 * Takes the html description and sets it to the root element of the html panel 
	 * @param htmlDesc
	 */
	public HTMLSnippet(String htmlDesc) {
		super(htmlDesc);
	}
	
	/**
	 * This method retrieves the root element i.e. the <span> for the form. Create a the form and replaces the <span>
	 * element. Further populates the form with all the fields.
	 */
	public void processSnippetDescription() {
		try {
			NodeList<Element> nodeList = null; 
			nodeList = getAllSpanNodes();
			if(nodeList != null) {
				
				int lengthOfNodes = nodeList.getLength();
				for (int i = lengthOfNodes - 1; i > -1; i--) { 
					Node node = nodeList.getItem(i);
					Element spanElement = Element.as(node); 
					if(spanElement != null) {
						AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
						ComponentFactory componentFactory = injector.getComponentFactory();
						if (spanElement.hasAttribute("appopsfield") && Boolean.valueOf(spanElement.getAttribute("appopsfield"))) {

							BaseField formField = componentFactory.getField(spanElement.getAttribute("widgetType"));
							if(formField != null) {
								
								String dataConfig = spanElement.getAttribute("data-config");
								if(dataConfig != null && !dataConfig.equals("")) {
								
									Configuration fieldConf = Configurator.getChildConfiguration(configuration, dataConfig);
									fieldConf.setPropertyByName(BaseFieldConstant.BF_ID, spanElement.getId());
									formField.setConfiguration(fieldConf);
								}
								
								formField.configure();
								formField.create();
								snippetElement.put(spanElement.getId(), formField);
								this.addAndReplaceElement(formField.asWidget(), spanElement);

/*								if(formField instanceof HtmlEditorField) {
									RootPanel.get().add(formField);
									
									HtmlEditorField htmlField = (HtmlEditorField)formField;
									htmlField.setValue("This");
									htmlField.setValue("is");
									htmlField.setValue("load");
								} else {
									this.addAndReplaceElement(formField.asWidget(), spanElement);
								}
*/							}
						} else if(spanElement.hasAttribute("appopsComponent") && Boolean.valueOf(spanElement.getAttribute("appopsComponent"))) {
							BaseComponentPresenter compPres = componentFactory.getComponent(spanElement.getAttribute("widgetType"));
							String dataConfig = spanElement.getAttribute("data-config");
							Configuration compConfig =  Configurator.getChildConfiguration(configuration, dataConfig);
							compPres.setConfiguration(compConfig);
							
							compPres.configure();
							BaseComponentView component = compPres.getView();
							snippetElement.put(spanElement.getId(), component);
							this.addAndReplaceElement(component, spanElement);
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
	private NodeList<Element> getAllSpanNodes() {
		NodeList<Element> nodeList = null;
		try {

			nodeList = this.getElement().getElementsByTagName("span");
			return nodeList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/*public void attach() {
		onAttach();
		RootPanel.detachOnWindowClose(this);
	}*/
	
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
	
	/**
	 * Method configures the html snippet. 
	 */
	public void configure() {
				
		if(getConfigurationValue(BaseFieldConstant.BF_PCLS) != null) {
			this.setStylePrimaryName(getConfigurationValue(BaseFieldConstant.BF_PCLS).toString());
		}
		
		if(getConfigurationValue(BaseFieldConstant.BF_DCLS) != null) {
			this.addStyleName(getConfigurationValue(BaseFieldConstant.BF_DCLS).toString());
		}
	}
	
	protected boolean hasConfiguration(String configKey) {
		if(configuration != null && configuration.getPropertyByName(configKey) != null) {
			return true;
		}
		return false;
	}
	
	protected Serializable getConfigurationValue(String configKey) {
		if(hasConfiguration(configKey)) {
			return configuration.getPropertyByName(configKey);
		}
		return null;
	}
	
	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public Map<String, Widget> getSnippetElement() {
		return snippetElement;
	}

	public void setSnippetElement(Map<String, Widget> snippetElement) {
		this.snippetElement = snippetElement;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
