package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponent;
import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.field.FieldPresenter;
import in.appops.client.common.config.model.IsConfigurationModel;
import in.appops.client.common.config.model.PropertyModel;


public interface DynamicMVPFactory {
	String PAGE = "page";
	String HTMLSNIPPET = "htmlSnippet";
	String COMPONENT = "component";
	String SPINNERFIELD = "spinnerField";
	String ACTIONFIELD = "actionField";
	String LABELFIELD = "labelField";
	String HTMLFIELD = "htmlField";
	String TEXTFIELD = "textField";
	String HTMLEDTFIELD = "htmlEditorField";
	String DATELABELFIELD = "dateLabelField";
	String LISTBOXFIELD = "listBoxField";
	String BUTTONFIELD = "buttonField";
	String IMAGEFIELD = "imageField";
	String TOGGLEIMAGEFIELD = "toggleImageField";
	String FIELD = "FIELD";
	String LISTCOMPONENT = "listComponent";
	String CONFIGEDITORCOMPONENT = "configEditorComponent";

	PageSnippetPresenter requestPageSnippet();

	HTMLSnippetPresenter requestHTMLSnippet(String type, String instance);

	FieldPresenter requestField(String type, String instance, PropertyModel model);

	IsConfigurationModel requestModel(String type);

	BaseComponent requestView(String type);

	BaseComponentPresenter requestComponent(String type, String instance);
}
