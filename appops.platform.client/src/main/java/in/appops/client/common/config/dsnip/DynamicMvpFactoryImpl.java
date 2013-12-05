package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.component.base.BaseComponent;
import in.appops.client.common.config.component.base.BaseComponentPresenter;
import in.appops.client.common.config.component.editor.ConfigEditorComponentPresenter;
import in.appops.client.common.config.component.editor.ConfigEditorComponentView;
import in.appops.client.common.config.component.list.ListComponentPresenter;
import in.appops.client.common.config.component.list.ListComponentView;
import in.appops.client.common.config.field.ActionField;
import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.FieldPresenter;
import in.appops.client.common.config.field.HTMLField;
import in.appops.client.common.config.field.ImageField;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LinkField;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.StateField;
import in.appops.client.common.config.field.ToggleImageField;
import in.appops.client.common.config.field.date.DateLabelField;
import in.appops.client.common.config.field.rangeslider.RangeSliderField;
import in.appops.client.common.config.field.spinner.SpinnerField;
import in.appops.client.common.config.field.textfield.TextField;
import in.appops.client.common.config.model.ConfigurationListModel;
import in.appops.client.common.config.model.ConfigurationModel;
import in.appops.client.common.config.model.IsConfigurationModel;
import in.appops.client.common.config.model.PropertyModel;
import in.appops.client.common.fields.htmleditor.HtmlEditorField;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * @author nitish@ensarm.com
 * Class that will be responsible for return proper instance {@link HTMLSnippetPresenter}
 * Depending on the type of the html.
 */
public class DynamicMvpFactoryImpl implements DynamicMvpFactory {

	@Override
	public HTMLSnippetPresenter requestHTMLSnippet(String type, String instance) {
		try{
			HTMLSnippetPresenter snippetPresenter = null;
			if(type.equalsIgnoreCase(PAGE)) {
				snippetPresenter = new PageSnippetPresenter(type, instance);
			}   else {
				snippetPresenter = new HTMLSnippetPresenter(type, instance);
			}
			return snippetPresenter;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public FormSnippetPresenter requestFormSnippet(String type, String instance) {
		FormSnippetPresenter snippetPresenter = new FormSnippetPresenter(type, instance);
		return snippetPresenter;
	}

	@Override
	public PageSnippetPresenter requestPageSnippet() {
		final Element pageSpan = RootPanel.get(PAGE).getElement();

		PageSnippetPresenter pageSnippetPresenter = (PageSnippetPresenter)requestHTMLSnippet(PAGE, pageSpan.getAttribute("data-config"));
		return pageSnippetPresenter;
	}

	@Override
	public IsConfigurationModel requestModel(String type) {
		if(type.equals(PAGE)) {
			return new PageSnippetModel();
		} else if(type.equals(FORMSNIPPET)) {
			return new FormSnippetModel();
		} else if(type.equals(HTMLSNIPPET)) {
			return new HTMLSnippetModel();
		} else if(type.equals(LISTCOMPONENT)) {
			return new ConfigurationListModel();
		} else if(type.equals(CONFIGEDITORCOMPONENT)) {
			return new ConfigurationModel();
		}
		return null;
	}

	@Override
	public BaseComponent requestView(String type) {
		if(type.equals(PAGE)) {
			return new PageSnippetView();
		} else if(type.equals(FORMSNIPPET)) {
			return new FormSnippetView();
		} else if(type.equals(HTMLSNIPPET)) {
			return new HTMLSnippetView();
		} else if(type.equalsIgnoreCase(ACTIONFIELD)) {
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
		} else if(type.equalsIgnoreCase(DATELABELFIELD)) {
			return new DateLabelField();
		} else if(type.equalsIgnoreCase(LISTBOXFIELD)) {
			return new ListBoxField();
		} else if(type.equalsIgnoreCase(BUTTONFIELD)) {
			return new ButtonField();
		}else if(type.equalsIgnoreCase(IMAGEFIELD)) {
			return new ImageField();
		}else if(type.equalsIgnoreCase(TOGGLEIMAGEFIELD)) {
			return new ToggleImageField();
		}else if(type.equalsIgnoreCase(LISTCOMPONENT)) {
			return new ListComponentView();
		}else if(type.equalsIgnoreCase(CONFIGEDITORCOMPONENT)) {
			return new ConfigEditorComponentView();
		}else if(type.equalsIgnoreCase(RANGESLIDERFIELD)){
			return new RangeSliderField();
		}else if(type.equalsIgnoreCase(LINKFIELD)) {
			return new LinkField();
		}else if(type.equalsIgnoreCase(STATEFIELD)) {
			return new StateField();
		}
		return null;
	}

	@Override
	public FieldPresenter requestField(String type, String instance, PropertyModel model) {
		try{
			FieldPresenter fieldPresenter = new FieldPresenter(type, instance, model);
			return fieldPresenter;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public BaseComponentPresenter requestComponent(String type, String instance) {
		try{
			if(type.equalsIgnoreCase(LISTCOMPONENT)) {
				return new ListComponentPresenter(type, instance);
			}else if(type.equalsIgnoreCase(CONFIGEDITORCOMPONENT)) {
				return new ConfigEditorComponentPresenter(type, instance);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}


}
