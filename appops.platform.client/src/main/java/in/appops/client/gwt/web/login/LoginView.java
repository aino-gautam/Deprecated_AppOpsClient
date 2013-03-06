package in.appops.client.gwt.web.login;

import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;

import in.appops.client.common.fields.LabelField;
import in.appops.client.common.fields.TextField;
import in.appops.platform.core.shared.Configuration;

public class LoginView extends Composite implements ILoginView {
	
	private LoginPresenter	presenter;
	private AbsolutePanel basePanel;
	
	public LoginView(){
		basePanel = new AbsolutePanel();
		basePanel.setWidth("100%");
		basePanel.setHeight("100%");
		
		initWidget(basePanel);
	}
	
	/**
	 * creates the view UI
	 */
	public void createView(){
		System.out.println("LoginView createView() started");
		LabelField usernameLbl = new LabelField();
		usernameLbl.setConfiguration(getLabelFieldConfiguration(true, "labelField", null, null));
		
		LabelField passwordLbl = new LabelField();
		passwordLbl.setConfiguration(getLabelFieldConfiguration(true, "labelField", null, null));
		
		TextField usernameTb = new TextField();
		usernameTb.setConfiguration(getTextFieldConfiguration(1, false, TextField.TEXTFIELDTYPE_TEXTBOX, "textField", null, null));
		
		TextField passwordTb = new TextField();
		passwordTb.setConfiguration(getTextFieldConfiguration(1, false, TextField.TEXTFIELDTYPE_PASSWORDTEXTBOX, "textField", null, null));
		
	
		FlexTable flex = new FlexTable();
		flex.setWidget(0, 0, usernameLbl);
		flex.setWidget(1, 0, usernameTb);
		flex.setWidget(2, 0, passwordLbl);
		flex.setWidget(3, 0, passwordTb);
		
		basePanel.add(flex);
		System.out.println("LoginView createView() completed");
	}

	/**
	 * creates the configuration object for a {@link}LabelField
	 * @param allowWordWrap boolean true / false
	 * @param primaryCss String the primary css style name to be applied to the field as defined in the css file
	 * @param secondaryCss (optional) String the dependent css style name to be applied to the field as defined in the css file
	 * @param debugId (optional) String the debug id for the {@link}LabelField
	 * @return
	 */
	private Configuration getLabelFieldConfiguration(boolean allowWordWrap, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_WORDWRAP, allowWordWrap);
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return configuration;
	}
	
	/**
	 * creates the configuration object for a {@link}TextField
	 * @param visibleLines int - the number of visibles lines ( 1 if textbox / passwordtextbox. For textarea > 1)
	 * @param readOnly boolean - true / false
	 * @param textFieldType - type of the textField ( textbox / passwordtextbox / textarea )
	 * @param primaryCss - String the primary css style name to be applied to the field as defined in the css file
	 * @param secondaryCss - (optional) String the dependent css style name to be applied to the field as defined in the css file
	 * @param debugId - (optional) String the debug id for the {@link}TextField
	 * @return
	 */
	private Configuration getTextFieldConfiguration(int visibleLines, boolean readOnly, String textFieldType, String primaryCss, String secondaryCss, String debugId){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(TextField.TEXTFIELD_VISIBLELINES, visibleLines);
		configuration.setPropertyByName(TextField.TEXTFIELD_READONLY, readOnly);
		configuration.setPropertyByName(TextField.TEXTFIELD_TYPE, textFieldType);
		configuration.setPropertyByName(TextField.TEXTFIELD_PRIMARYCSS, primaryCss);
		configuration.setPropertyByName(TextField.TEXTFIELD_DEPENDENTCSS, secondaryCss);
		configuration.setPropertyByName(TextField.TEXTFIELD_DEBUGID, debugId);
		return configuration;
	}
	
	@Override
	public void setPresenter(LoginPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public LoginPresenter getPresenter() {
	    return this.presenter;
	}

}
