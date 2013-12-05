package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponent;
import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.dsnip.HTMLSnippetPresenter.HTMLSnippetConstant;
import in.appops.client.common.config.model.ConfigurationModel;
import in.appops.client.common.config.model.IsConfigurationModel;
import in.appops.client.common.config.model.PropertyModel;
import in.appops.client.common.core.EntityReceiver;
import in.appops.platform.core.entity.Entity;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Widget;

public class HTMLSnippetView extends BaseComponent implements EntityReceiver {
	protected HTMLPanel snippetPanel;
	protected Map<String, BaseComponentPresenter> elementMap = new HashMap<String, BaseComponentPresenter>();

	private String snippetType;
	private final String SPAN = "span";
	protected final String APPOPS_FIELD = "appopsField";
	protected final String APPOPS_COMPONENT = "appopsComponent";
	protected final String FORMSNIPPET = "formSnippet";
	protected final String HTMLSNIPPET = "htmlSnippet";
	protected final String COMPONENT_TYPE = "componentType";
	protected final String TYPE = "type";
	protected final String DATA_CONFIG = "data-config";

	@Override
	public void initialize() {
		super.initialize();
		String snippetDescription = ((HTMLSnippetModel) model).getDescription(snippetType);
		((HTMLSnippetModel) model).setReceiver(this);
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
	@Override
	public void configure() {
		super.configure();
		
		if(viewConfiguration != null){
			if(getFormPrimCss() != null) {
				snippetPanel.setStylePrimaryName(getFormPrimCss());
			}
	
			if(getFormDependentCss() != null) {
				snippetPanel.addStyleName(getFormDependentCss());
			}
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
			e.printStackTrace();
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

	
	public Map<String, BaseComponentPresenter> getElementMap() {
		return elementMap;
	}

	@Override
	public void noMoreData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEntityReceived(Entity entity) {
		for(Map.Entry<String, BaseComponentPresenter> componentEntry :  elementMap.entrySet()) {
			componentEntry.getValue().configure();
		}

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
