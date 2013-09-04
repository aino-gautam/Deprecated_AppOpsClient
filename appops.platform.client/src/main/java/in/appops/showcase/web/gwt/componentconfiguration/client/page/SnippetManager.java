package in.appops.showcase.web.gwt.componentconfiguration.client.page;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import in.appops.client.common.config.dsnip.HTMLProcessor;
import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.field.ButtonField;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.ListBoxField;
import in.appops.client.common.config.field.BaseField.BaseFieldConstant;
import in.appops.client.common.config.field.ButtonField.ButtonFieldConstant;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.client.common.config.field.ListBoxField.ListBoxFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.TextField;
import in.appops.client.common.fields.TextField.TextFieldConstant;
import in.appops.platform.core.shared.Configuration;
import in.appops.showcase.web.gwt.componentconfiguration.client.library.ConfigurationEditor;
import in.appops.showcase.web.gwt.componentconfiguration.client.library.LibraryComponentManager;

import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * User for creating html snippets and processing them and managing their configurations.
 * @author nairutee
 *
 */
public class SnippetManager extends Composite implements FieldEventHandler {
	
	private VerticalPanel basePanel;
	private TextField snippetNameTextBox ;
	private TextField snippetHtmlTextArea ;
	private ButtonField saveAndProcessButton;
	private ConfigurationEditor configurationEditor;
	private ListBoxField libraryBox;
	private ListBoxField spanListBox;
	
	private Logger logger = Logger.getLogger("SnippetManager");
	
	private final String SAVECOMP_BTN_PCLS = "saveCompBtnCss";
	private final String HEADERLBL_CSS = "headerLabel";
	private final String SUBHEADERLBL_CSS =	 "subHeaderLabel";
	private final String REGULARLBL_CSS = "regularLabel";
	private final String TEXTFIELD_CSS = "textField";
	
	/** Field id**/
	public static final String LIBRARYLISTBOX_ID = "libraryListBoxId";
	private static String SAVECOMPONENT_BTN_ID = "saveCompBtnId";
	
	/**
	 * Constructor
	 */
	public SnippetManager() {
		AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}

	/**
	 * initializes the widget
	 */
	public void initialize() {
		basePanel = new VerticalPanel();
		basePanel.setWidth("100%");
		initWidget(basePanel);
		createSnippetRegistrationUI();
	}
	
	/**
	 * Constructs the UI
	 */
	private void createSnippetRegistrationUI(){
		FlexTable containerTable = new FlexTable();
		
		/* list box for library selection */
		libraryBox = new ListBoxField();
		libraryBox.setConfiguration(getLibraryListBoxConfiguration());
		libraryBox.configure();
		libraryBox.create();
		
		/* text box for entering component name*/
		snippetNameTextBox = new TextField();
		Configuration nameTfConfig = getSnippetNameTextBoxConfig();
		snippetNameTextBox.setConfiguration(nameTfConfig);
		snippetNameTextBox.configure();
		snippetNameTextBox.create();
		
		/* heading label for snippet html text area */
		LabelField headerLbl = new LabelField();
		Configuration headerLblConfig = getHeaderLblConfig("Enter the snippet html in the text area below: ");
		headerLbl.setConfiguration(headerLblConfig);
		headerLbl.configure();
		headerLbl.create();
		
		/* text area for entering component html */
		snippetHtmlTextArea = new TextField();
		Configuration descTfConfig = getSnippetHtmlTextAreaConfig();
		snippetHtmlTextArea.setConfiguration(descTfConfig);
		snippetHtmlTextArea.configure();
		snippetHtmlTextArea.create();
		
		/* button to process & save the component */
		saveAndProcessButton = new ButtonField();
		Configuration savebTnConfig = getSaveAndProcessBtnConfig();
		saveAndProcessButton.setConfiguration(savebTnConfig);
		saveAndProcessButton.configure();
		saveAndProcessButton.create();
		
		
		containerTable.setWidget(0, 0, libraryBox);
		containerTable.setWidget(1, 0, snippetNameTextBox);
		containerTable.setWidget(2, 0, headerLbl);
		containerTable.setWidget(3, 0, snippetHtmlTextArea);
		containerTable.setWidget(4, 0, saveAndProcessButton);
		
		basePanel.add(containerTable);
	}

	/**
	 * initializes the configuration editing component and lists the span elements available.
	 */
	public void createConfigurationEditorUI(){
		VerticalPanel vp = new VerticalPanel();
		
		HTML html = new HTML("<hr style=\"border-bottom : 1px dotted gray;\" \"width:100%;\"/>");
		
		LabelField headerLbl = new LabelField();
		Configuration headerLblConfig = getHeaderLblConfig(" Snippet Configurations ");
		headerLbl.setConfiguration(headerLblConfig);
		headerLbl.configure();
		headerLbl.create();
		
		
		HorizontalPanel hpSpanSelection = new HorizontalPanel();
		
		LabelField subHeaderLbl = new LabelField();
		Configuration subHeaderLblConfig = getLblConfig(" Select a span element ");
		subHeaderLbl.setConfiguration(subHeaderLblConfig);
		subHeaderLbl.configure();
		subHeaderLbl.create();
		
		spanListBox = new ListBoxField();
		spanListBox.setConfiguration(getSpansListBoxConfiguration());
		spanListBox.configure();
		spanListBox.create();
		
		hpSpanSelection.add(subHeaderLbl);
		hpSpanSelection.add(spanListBox);
		
		vp.add(html);
		vp.add(headerLbl);
		vp.add(hpSpanSelection);
		
		configurationEditor = new ConfigurationEditor();
		
		vp.add(configurationEditor);
		
		basePanel.add(vp);
		
	}
	
	private void validateHTML(){
		String htmlStr = snippetHtmlTextArea.getFieldText();
		NodeList<Element> spans = HTMLProcessor.getSpanElementsFromHTML(htmlStr);
		if(spans != null){
			ArrayList<Element> appopsFields = HTMLProcessor.getAppopsFieldElements(spans);
			if(appopsFields != null){
				populateSpansListBox(appopsFields);
			}
		}else{
			
		}
	}
	
	private void populateSpansListBox(ArrayList<Element> spansList){
		for(Element ele : spansList){
			// populate listbox
		}
	}
	/**
	 * method for setting configuration properties for the library listbox
	 * @return
	 */
	private Configuration getLibraryListBoxConfiguration() {
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,LIBRARYLISTBOX_ID);
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_OPRTION,"appdefinition.AppDefinitionService.getLibraries");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_QUERYNAME,"getAllLibraries");
			configuration.setPropertyByName(ListBoxFieldConstant.LSTFD_ENTPROP,"name");
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"--- Please select a library---");
		} catch (Exception e) {
			logger.log(Level.SEVERE, "LibraryComponentManager :: getLibraryListBoxConfiguration :: Exception", e);
		}
		return configuration;
	}
	
	/**
	 * method for setting configuration properties for the snippet name text box
	 * @return
	 */
	private Configuration getSnippetNameTextBoxConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTYPE_TXTBOX);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, TEXTFIELD_CSS);
			configuration.setPropertyByName(TextFieldConstant.VALIDATEFIELD, false);
			configuration.setPropertyByName(TextFieldConstant.TF_CHARWIDTH, 500);
			configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL, "Enter component name");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}
	
	/**
	 * method for setting configuration properties for the snippet html text area
	 * @return
	 */
	private Configuration getSnippetHtmlTextAreaConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(TextFieldConstant.TF_TYPE, TextFieldConstant.TFTTYPE_TXTAREA);
			configuration.setPropertyByName(TextFieldConstant.BF_PCLS, TEXTFIELD_CSS);
			configuration.setPropertyByName(TextFieldConstant.VALIDATEFIELD, false);
			configuration.setPropertyByName(TextFieldConstant.TF_VISLINES, 10);
			configuration.setPropertyByName(TextFieldConstant.TF_CHARWIDTH, 120);
			configuration.setPropertyByName(TextFieldConstant.BF_ERRPOS, BaseFieldConstant.BF_BOTTOM);
			configuration.setPropertyByName(TextFieldConstant.BF_INVLDMSG,"Invalid HTML format. No span elements found!");
			configuration.setPropertyByName(TextFieldConstant.BF_DEFVAL, "Enter snippet html here");

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}
	
	private Configuration getSaveAndProcessBtnConfig() {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(ButtonFieldConstant.BTNFD_DISPLAYTEXT, "Process and Save Snippet");
			configuration.setPropertyByName(ButtonFieldConstant.BF_PCLS,SAVECOMP_BTN_PCLS);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ENABLED, true);
			configuration.setPropertyByName(ButtonFieldConstant.BF_ID, SAVECOMPONENT_BTN_ID);

		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}

	/**
	 * configuration settings for labels that are used as headers
	 * @param labelText
	 * @return
	 */
	private Configuration getHeaderLblConfig(String labelText) {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, labelText);
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, HEADERLBL_CSS);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}
	
	/**
	 * settings for configuration of label fields used to display normal text
	 * @param labelText
	 * @return
	 */
	private Configuration getLblConfig(String labelText) {
		Configuration configuration = null;	
		try{
			configuration = new Configuration();
			configuration.setPropertyByName(LabelFieldConstant.LBLFD_DISPLAYTXT, labelText);
			configuration.setPropertyByName(LabelFieldConstant.BF_PCLS, REGULARLBL_CSS);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return configuration;
	}

	/**
	 * method for configuration settings of the list box displaying the span elements fetched after parsing the snippet html
	 * @return
	 */
	private Configuration getSpansListBoxConfiguration() {
		Configuration configuration = new Configuration();
		try {
			configuration.setPropertyByName(ListBoxFieldConstant.BF_ID,"spanListBoxField");
			configuration.setPropertyByName(ListBoxFieldConstant.BF_DEFVAL,"Select a span element");
		} catch (Exception e) {
			
		}
		return configuration;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		try {
			int eventType = event.getEventType();
			Object eventSource = event.getEventSource();
			switch (eventType) {
			case FieldEvent.CLICKED: {
				if (eventSource instanceof ButtonField) {
					ButtonField btnField = (ButtonField) eventSource;
					if (btnField.getBaseFieldId().equals(SAVECOMPONENT_BTN_ID)) {
						// TODO process the html
						// make a server call to save the component
						validateHTML();
						createConfigurationEditorUI();
					}
				}
				break;
			}
			default:
				break;
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "ConfigurationManagerHome :: onFieldEvent :: Exception", e);
		}
	}
}
