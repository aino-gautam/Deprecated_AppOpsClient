/*package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.component.base.BaseComponentView;
import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.util.Configurator;
import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class DynamicSnippet extends HTMLPanel {

	private Entity entity;
	private Map<String, Container> placeholders = new HashMap<String, Container>();
	private String instance;
	*//**
	 * Takes the html description and sets it to the root element of the html panel 
	 * @param htmlDesc
	 *//*
	public DynamicSnippet(String htmlDesc) {
		super(htmlDesc);
	}
	
	*//**
	 * This method retrieves the root element i.e. the <span> for the form. Create a the form and replaces the <span>
	 * element. Further populates the form with all the fields.
	 *//*
	public void processSnippetDescription() {
		try {
			// The root <span> element i.e. <span id="bookATableForm" widgetContainerType="form"/> 
			
			
			NodeList<Element> nodeList = this.getElement().getElementsByTagName("span");
			int lengthOfNodes = nodeList.getLength();
			for (int i = lengthOfNodes - 1; i > -1; i--) { // Iterating through the <span> elements
				Node node = nodeList.getItem(i);
				Element rootFormElement = Element.as(node);
				rootFormElement.setId(rootFormElement.getId());
				if (rootFormElement != null) {
					if (rootFormElement.hasAttribute("widgetContainerType") && rootFormElement.getAttribute("widgetContainerType").equals("form")) {
						Container snippetForm = new Container();
						snippetForm.create(rootFormElement);
						
						// Replace the <span> element for form with the actual form.
						this.addAndReplaceElement(snippetForm, rootFormElement);
						placeholders.put(snippetForm.getId(), snippetForm);
						populateFormFields(snippetForm);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	*//**
	 * This method retrieves all the component related <span>, gets the corresponding appops component, configures it  and replaces 
	 * the <span> with the same.
	 *//*
	private void populateFormFields(Container snippetForm)	{
		try {
			NodeList<Element> nodeList = null; // Node list containing the <span> elements
			nodeList = getAllChildrenSpanNodes(snippetForm.getId());
			if(nodeList != null) {
				int lengthOfNodes = nodeList.getLength();
	
				for (int i = lengthOfNodes - 1; i > -1; i--) { // Iterating through the <span> elements
					Node node = nodeList.getItem(i);
					Element spanElement = Element.as(node); // e.g. <span id="apw_nop" appopsfield="true" widgetType="spinnerField"></span>
					if(spanElement != null) {
						AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
						ComponentFactory componentFactory = injector.getComponentFactory();
						// Process further only if the <span> has attribute "appopsfield", whose value is true
						if (spanElement.hasAttribute("appopsfield") && Boolean.valueOf(spanElement.getAttribute("appopsfield"))) {

							// Request a the corresponding field to the widgetType.
							BaseField formField = componentFactory.getField(spanElement.getAttribute("widgetType"));
							if(formField != null) {
								
								// Gets the configuration data for the field which resides in JSON format in an attribute in the <span> 
								String dataConfig = spanElement.getAttribute("data-config");
								if(dataConfig != null && !dataConfig.equals("")) {
									
									// If configuration data is present create the corresponding configuration entity and set it to the field
									Configuration configuration = null;
									if(instance == null) {
										configuration = Configurator.getConfiguration(dataConfig);
									} else {
										configuration = Configurator.getChildConfiguration(instance, dataConfig);
									}
									
									formField.setConfiguration(configuration);
									
									// TODO The field will have a configure() which will apply the configuration properties to the field
									// Currently the createField() applies the configuration to the field.
								}
								formField.configure();
								formField.create();
								
								//Replace the <span> with the appops component
								HTMLPanel htmlPanel = (HTMLPanel)((SimplePanel)snippetForm).getWidget();
								htmlPanel.addAndReplaceElement(formField.asWidget(), spanElement);
								
								snippetForm.addAndReplaceFormFieldElement(formField.asWidget(), spanElement);

							}
						} else if(spanElement.hasAttribute("appopsComponent") && Boolean.valueOf(spanElement.getAttribute("appopsComponent"))) {
							BaseComponentPresenter compPres = componentFactory.getComponent(spanElement.getAttribute("widgetType"));
							String dataConfig = spanElement.getAttribute("data-config");
							Configuration compConfig = Configurator.getConfiguration(dataConfig);
							compPres.setConfiguration(compConfig);
							
							compPres.init();
							BaseComponentView component = compPres.getView();
							//HTMLPanel htmlPanel = (HTMLPanel)((SimplePanel)snippetForm).getWidget();
							snippetForm.addAndReplaceFormFieldElement(component, spanElement);
							compPres.load();
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	*//**
	 * Returns <span> elements. 
	 *//*
	private NodeList<Element> getAllChildrenSpanNodes(String id) {
		NodeList<Element> nodeList = null;
		try {
			if (id != null) {
				nodeList = this.getElementById(id).getElementsByTagName("span");
			} 
			return nodeList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void attach() {
		
		 * Widget.onAttach() is protected
		 
		onAttach();

		
		 * mandatory for all widgets without parent widget
		 
		RootPanel.detachOnWindowClose(this);
	}
	
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
	
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}
	
	public void populateFields() {
		for (Map.Entry<String, Container> formEntry : placeholders.entrySet()) {
		    Container form = formEntry.getValue();
		    Map<String, Widget> widgetMap = form.getRenderedFieldMap();
		    
		    for (Map.Entry<String, Widget> widgetEntry : widgetMap.entrySet()) {
		    	Widget formWidget = widgetEntry.getValue();
		    	
		    	if(formWidget instanceof BaseField) {
		    		BaseField baseField = (BaseField)formWidget;
		    		
	    			Key key = (Key)entity.getPropertyByName("id");
	    			Long id = (Long)key.getKeyValue();
	    			baseField.setBindId(id);
		    		
	    			if(baseField.isFieldVisible()) {
	    			
			    		String bindProp = baseField.getBindProperty();
			    		if(bindProp != null) {
			    			Serializable bindValue = entity.getPropertyByName(bindProp);
			    			baseField.setValue(bindValue);
			    			
	
			    		}
	    			}
		    	}
		    }
		}
		
	}

}
*/