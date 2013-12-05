package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.component.base.BaseComponent.BaseComponentConstant;
import in.appops.client.common.config.dsnip.FormSnippetPresenter.FormSnippetConstant;
import in.appops.client.common.config.dsnip.HTMLSnippetPresenter.HTMLSnippetConstant;
import in.appops.client.common.config.dsnip.event.form.ValidationRule;
import in.appops.client.common.config.field.FieldPresenter;
import in.appops.client.common.config.field.BaseField.BaseFieldConstant;
import in.appops.client.common.config.model.ConfigurationModel;
import in.appops.client.common.config.model.IsConfigurationModel;
import in.appops.client.common.config.model.PropertyModel;
import in.appops.platform.core.entity.Entity;

import java.util.Iterator;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;

public class FormSnippetView extends HTMLSnippetView{
	
	private boolean hasInlineEditing;
	
	/**
	 * Method configures the html snippet.
	 */
	@Override
	public void configure() {
		super.configure();
		
		if(getFormPrimCss() != null) {
			snippetPanel.setStylePrimaryName(getFormPrimCss());
		}
	
		if(getFormDependentCss() != null) {
			snippetPanel.addStyleName(getFormDependentCss());
		}
		
		if(hasInlineEditing() != null)
			hasInlineEditing = hasInlineEditing();
	}
	
	@Override
	public void create() {
		try {

			NodeList<Element> nodeList =  getAllSpanNodes();

			if(nodeList != null) {

				int lengthOfNodes = nodeList.getLength();
				for (int i = lengthOfNodes - 1; i > -1; i--) {
					Node node = nodeList.getItem(i);
					Element spanElement = Element.as(node);
					if(spanElement != null) {
						String dataConfig = spanElement.getAttribute(DATA_CONFIG);

						if (spanElement.hasAttribute(COMPONENT_TYPE)) {
							BaseComponentPresenter componentPresenter = null;
							if(spanElement.getAttribute(COMPONENT_TYPE).equalsIgnoreCase(APPOPS_FIELD)) {
								PropertyModel propertyModel = ((HTMLSnippetModel) model).getPropertyModel();
								componentPresenter = mvpFactory.requestField(spanElement.getAttribute(TYPE), dataConfig, propertyModel);
							} else if(spanElement.getAttribute(COMPONENT_TYPE).equalsIgnoreCase(APPOPS_COMPONENT)) {
								componentPresenter = mvpFactory.requestComponent(spanElement.getAttribute(TYPE), dataConfig);
							} else if(spanElement.getAttribute(COMPONENT_TYPE).equalsIgnoreCase(HTMLSNIPPET)) {
								componentPresenter = mvpFactory.requestHTMLSnippet(spanElement.getAttribute(TYPE), dataConfig);
							}else if(spanElement.getAttribute(COMPONENT_TYPE).equalsIgnoreCase(FORMSNIPPET)) {
								componentPresenter = mvpFactory.requestFormSnippet(spanElement.getAttribute(TYPE), dataConfig);
							}
							if(componentPresenter != null) {
								componentPresenter.getView().setLocalEventBus(localEventBus);

								Context componentContext = new Context();
								componentContext.setParentEntity(((ConfigurationModel)model).getEntity());
								String componentContextPath = !model.getContext().getContextPath().equals("") ?
										model.getContext().getContextPath() + IsConfigurationModel.SEPARATOR + model.getInstance() : model.getInstance();
								componentContext.setContextPath(componentContextPath);
								componentPresenter.getModel().setContext(componentContext);

								componentPresenter.configure();
								ValidationRule validationRule = getValidationRule();
								//Just for inline and onfocus validation.
								if(getValidationRule()!=null && getValidationRule().isValidate()){
									configureFormField(componentPresenter);
								}
								componentPresenter.create();
								elementMap.put(dataConfig, componentPresenter);
								snippetPanel.addAndReplaceElement(componentPresenter.getView().asWidget(), spanElement);
							}

						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void configureFormField(BaseComponentPresenter fieldPresenter) {
		try {
			if (fieldPresenter instanceof FieldPresenter) {
				IsConfigurationModel model = fieldPresenter.getModel();
				if (model != null) {
					if (model.getViewConfiguration() != null) {
						if (model.getViewConfiguration().hasConfiguration(
								BaseComponentConstant.BC_ISINPUTFIELD)) {
							Boolean isInputField = (Boolean) model.getViewConfiguration().getPropertyByName(BaseComponentConstant.BC_ISINPUTFIELD);
							if (isInputField) {
								String validationMode = getValidationRule().getValidationMode();
								model.getViewConfiguration().setPropertyByName(BaseFieldConstant.BF_VALIDATEFIELD,true);
								if (validationMode.equals(ValidationRule.INLINEVALIDATION)) {
									model.getViewConfiguration().setPropertyByName(BaseFieldConstant.BF_VALIDATEONCHANGE,true);
								} else if (validationMode.equals(ValidationRule.FIELDFOCUSVALIDATION)) {
									model.getViewConfiguration().setPropertyByName(BaseFieldConstant.BF_VALIDATEONBLUR,	true);
								}
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
	 * Returns the primary style to be applied to the component basepanel.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getFormPrimCss() {
		String primaryCss = null;
		try {
			if(viewConfiguration.getConfigurationValue(HTMLSnippetConstant.HS_PCLS) != null) {
				primaryCss = viewConfiguration.getConfigurationValue(HTMLSnippetConstant.HS_PCLS).toString();
			}
		} catch (Exception e) {
			
		}
		return primaryCss;
	}


	/**
	 * Returns the dependent style to be applied to the component basepanel.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getFormDependentCss() {
		String depCss = null;
		try {
			if(viewConfiguration.getConfigurationValue(HTMLSnippetConstant.HS_DCLS) != null) {
				depCss = viewConfiguration.getConfigurationValue(HTMLSnippetConstant.HS_DCLS).toString();
			}
		} catch (Exception e) {

		}
		return depCss;
	}
	
	protected Boolean hasInlineEditing(){
		boolean inlineEditing = false;
		try{
			if(viewConfiguration.getConfigurationValue(FormSnippetConstant.HAS_INLINE_EDITING) != null)
				inlineEditing = (Boolean) viewConfiguration.getConfigurationValue(FormSnippetConstant.HAS_INLINE_EDITING);
		}catch (Exception e) {

		}
		return inlineEditing;
	}
	
	protected ValidationRule getValidationRule(){
		try{
			if(viewConfiguration.getConfigurationValue("validationRule") != null)
				return  (ValidationRule) viewConfiguration.getConfigurationValue("validationRule");
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	

	
	protected String getEntityType(){
		String type = null;
		try{
			if(viewConfiguration.getConfigurationValue(FormSnippetConstant.ENTITYTYPE) != null)
				type =  viewConfiguration.getConfigurationValue(FormSnippetConstant.ENTITYTYPE).toString();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return type;
	}
	
	@Override
	public void onEntityReceived(Entity entity) {
		for(Map.Entry<String, BaseComponentPresenter> componentEntry :  elementMap.entrySet()) {
			if(componentEntry.getValue() instanceof FieldPresenter){
				FieldPresenter fieldPresenter = (FieldPresenter) componentEntry.getValue();
				IsConfigurationModel model = fieldPresenter.getModel();
		    	if(model!=null){
		    		if(model.getViewConfiguration()!=null){
			    		if(model.getViewConfiguration().hasConfiguration(BaseComponentConstant.BC_ISINPUTFIELD)){
				    		Boolean isInputField = (Boolean)model.getViewConfiguration().getPropertyByName(BaseComponentConstant.BC_ISINPUTFIELD);
				    		if(isInputField)
				    			fieldPresenter.configure();
				    	}
			    	}
		    	}
			}
		}
	}

}
