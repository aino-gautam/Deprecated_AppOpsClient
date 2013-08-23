package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.component.grid.GridComponentPresenter;
import in.appops.client.common.config.component.list.ListComponentPresenter;
import in.appops.client.common.config.component.tree.TreeComponentPresenter;
import in.appops.client.common.config.field.ActionField;
import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.field.HTMLField;
import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.date.DateLabelField;
import in.appops.client.common.config.field.spinner.SpinnerField;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.htmleditor.HtmlEditorField;

public class ComponentFactoryImpl implements ComponentFactory {
	private final String SPINNERFIELD = "spinnerField";
	private final String ACTIONFIELD = "actionField";
	private final String LABELFIELD = "labelField";
	private final String HTMLFIELD = "htmlField";
	private final String TEXTFIELD = "textField";
	private final String HTMLEDTFIELD = "htmlEditorField";
	private final String DATETIMEFIELD = "dateTimeFieldField";
	private final String LISTBOXFIELD = "listBoxField";
	private final String IMAGEFIELD = "imageField";
	
	@Override
	public BaseField getField(String type) {
		if(type.equalsIgnoreCase(ACTIONFIELD)) {
			return new ActionField();
		} else if(type.equalsIgnoreCase(SPINNERFIELD)) {
			return new SpinnerField();
		} else if(type.equalsIgnoreCase(LABELFIELD)) {
			return new LabelField();
		} else if(type.equalsIgnoreCase(HTMLFIELD)) {
			return new HTMLField();
		} else if(type.equalsIgnoreCase(TEXTFIELD)) {
			return new TextField();
		} else if(type.equalsIgnoreCase(HTMLEDTFIELD)) {
			return new HtmlEditorField();
		} else if(type.equalsIgnoreCase(DATETIMEFIELD)) {
			return new DateLabelField();
		} else if(type.equalsIgnoreCase(LISTBOXFIELD)) {
			return new ListBoxField();
		}else if(type.equalsIgnoreCase(IMAGEFIELD)) {
			return new ImageField();
		}
		return null;
	}

	private final String LISTCOMPONENT = "listComponent";
	private final String GRIDCOMPONENT = "gridComponent";
	private final String LISTTREECOMPONENT = "listTreeComponent";

	
	@Override
	public BaseComponentPresenter getComponent(String type) {
		if(type.equalsIgnoreCase(LISTCOMPONENT)) {
			return new ListComponentPresenter();
		} else if(type.equalsIgnoreCase(LISTTREECOMPONENT)) {
			return new TreeComponentPresenter();
		} else if(type.equalsIgnoreCase(GRIDCOMPONENT)) {
			return new GridComponentPresenter();
		}
		return null;
	}
}
