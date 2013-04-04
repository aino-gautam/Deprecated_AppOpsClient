package in.appops.client.common.components;

import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.client.common.fields.IntelliThoughtField;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class IntelliThoughtWidget extends Composite implements Configurable, ClickHandler, FieldEventHandler{
	private FlexTable basePanel;
	private IntelliThoughtField intelliShareField;
	private MediaField attachMediaField;
	private SuggestionAction suggestionAction;
	private HorizontalPanel mediaServicePanel;
	private boolean isAttachedMediaField;

	private Label searchButton;
	private Label postButton;
	private Label messageButton;
	
	public static final String IS_INTELLISHAREFIELD = "isIntelliShareField";
	public static String IS_ATTACHMEDIAFIELD = "isAttachMediaField";
	public static String INTELLITHOUGHTOPTIONPANEL_PRIMARYSCSS = "intelliToughtOptionPanel";

	public IntelliThoughtWidget(){
		initialize();
		initWidget(basePanel);
	}

	private void initialize() {
		basePanel = new FlexTable();
		mediaServicePanel = new HorizontalPanel();
		intelliShareField = new IntelliThoughtField();
		suggestionAction = new SuggestionAction();
	}
	
	/**
	 * Creates the IntelliSharedComponent
	 * @param configuration
	 * @throws AppOpsException
	 */
	public void createComponent(Configuration configuration) throws AppOpsException{
			basePanel.setWidget(0, 0, mediaServicePanel);
			mediaServicePanel.setStylePrimaryName("intelliShareField");
			
			Boolean isIntelliShareField = 	configuration.getPropertyByName(IS_INTELLISHAREFIELD);

			createAttachMediaField();
			if(isIntelliShareField){
				createIntelliShareField();
			}
			addPredefinedOptions();
			basePanel.setWidget(5, 0, suggestionAction);
			
			intelliShareField.addFieldEventHandler(this);
			
	}
	
	private void addPredefinedOptions() {

		searchButton = new Label("Search");
		postButton = new Label("Post");
		messageButton = new Label("Message");

		searchButton.setStylePrimaryName("appops-intelliThought-Label");
		postButton.setStylePrimaryName("appops-intelliThought-Label");
		messageButton.setStylePrimaryName("appops-intelliThought-Label");
		
//		prominentOptionlPanel.add(searchButton);
//		prominentOptionlPanel.add(postButton);
//		prominentOptionlPanel.add(messageButton);

		basePanel.setWidget(1, 1, searchButton);
		basePanel.getCellFormatter().setHeight(1, 1, "20px");

		basePanel.setWidget(2, 0, messageButton);
		basePanel.getCellFormatter().setHeight(2, 0, "20px");

		basePanel.setWidget(3, 0, postButton);
		basePanel.getCellFormatter().setHeight(3, 0, "20px");
		
		searchButton.setVisible(false);
		postButton.setVisible(false);
		messageButton.setVisible(false);
	}

	private void createAttachMediaField() {
		attachMediaField = new MediaField();
		attachMediaField.isFadeUpEffect(true);
		attachMediaField.showMediaField();
		
		mediaServicePanel.add(attachMediaField);
		attachMediaField.setVisible(false);
		mediaServicePanel.setWidth("100%");
		mediaServicePanel.setCellHorizontalAlignment(attachMediaField, HasHorizontalAlignment.ALIGN_RIGHT);
		
		attachMediaField.getMedia().addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				attachMediaField.showMediaOption();
			}
		});
	}

	private void createIntelliShareField() throws AppOpsException {
		intelliShareField.createField();
		basePanel.getFlexCellFormatter().setRowSpan(1, 0, 4);

		basePanel.setWidget(1, 0, intelliShareField);
		basePanel.getFlexCellFormatter().setVerticalAlignment(1, 0, HasVerticalAlignment.ALIGN_TOP);
		basePanel.getFlexCellFormatter().getElement(1, 0).setClassName("intelliThoughtFieldCol");
		//fieldAndPredefineOptionPanel.add(intelliShareField);
		//intelliShareField.addDomHandler(this, KeyUpEvent.getType());
	}

	public void setIntelliShareFieldConfiguration(Configuration conf){
		intelliShareField.setConfiguration(conf);
	}

	@Override
	public Configuration getConfiguration() {
		return null;
	}
	
	@Override
	public void setConfiguration(Configuration conf) {
		
	}

	@Override
	public void onClick(ClickEvent event) {
		Widget source = (Widget) event.getSource();
		if(source.equals(attachMediaField.getMedia())){
			attachMediaField.showMediaOption();
		}
	}

//	@Override
//	public void onKeyUp(KeyUpEvent event) {
//		char enteredChar = getCharCode(event.getNativeEvent()) ;
//		if(event.getSource().equals(intelliShareField)){
//			wordBuffer.append(Character.toString(enteredChar));
//			
//			if( !intelliShareField.getText().trim().equals("") && (enteredChar == ' ' || event.getNativeKeyCode() == KeyCodes.KEY_ENTER)){
//				fireWordEnteredEvent(wordBuffer.toString());
//				wordBuffer = new StringBuffer();
//			}
//			if(!intelliShareField.getText().trim().equals("") && !isAttachedMediaField){
//				attachMediaField.setVisible(true);
//				setAttachedMediaField(true);
//			} else if(intelliShareField.getText().trim().equals("")){
//				attachMediaField.setVisible(false);
//				setAttachedMediaField(false);
//			}
//		}
//	}

	private void handleWordEnteredEvent(String string) {
		
		String intelliText  = intelliShareField.getText();
		String[] words = intelliText.split("\\s+");
		
		if(!searchButton.isVisible()){
			searchButton.addStyleName("fadeInLeft");
			searchButton.setVisible(true);
			messageButton.addStyleName("fadeInLeft");
			messageButton.setVisible(true);
		} 

		
		if(words.length == 2 && !postButton.isVisible()){
			postButton.addStyleName("fadeInLeft");
			postButton.setVisible(true);
		}

//		if(words.length == 5 && !messageButton.isVisible()){
//
//		}
		
		suggestionAction.showActionSuggestion(string);
		
	}

	public boolean isAttachedMediaField() {
		return isAttachedMediaField;
	}

	public void setAttachedMediaField(boolean isAttachedMediaField) {
		this.isAttachedMediaField = isAttachedMediaField;
	}

///*	private native char getCharCode(NativeEvent e)-{
//	    var code = e.keyCode ? e.keyCode : e.charCode ? e.charCode : e.which ? e.which : void 0;
//	    if( e.which ) {
//	        if( code && ( ! ( e.ctrlKey || e.altKey ) ) ){
//	            return code;
//	        }
//	    }
//	    return void 0;
//	}-;*/

	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
		String eventData = (String) event.getEventData();
		
		if(eventType == FieldEvent.EDITINITIATED) {
			if (!isAttachedMediaField) {
				attachMediaField.setVisible(true);
				setAttachedMediaField(true);
			} 
		} else if(eventType == FieldEvent.WORDENTERED) {
			handleWordEnteredEvent(eventData);
		}
	}

	
}