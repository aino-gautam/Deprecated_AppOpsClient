package in.appops.client.common.fields;

import java.sql.Time;
import java.util.Date;

import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DomEvent;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class DateTimePicker extends Composite implements FocusHandler{

	private VerticalPanel   vpPanel = new VerticalPanel();
	private ListBox			lstHours				= new ListBox();
	private ListBox			lstMinutes				= new ListBox();
	private ListBox			lstSeconds				= new ListBox();
	private DatePicker		datePicker              = new DatePicker();
	private HorizontalPanel timeFieldPanel			= new HorizontalPanel();
	private HorizontalPanel timePicker      		= new HorizontalPanel();
	private Time 			currentTimeValue;
	private Date			currentDate;
	private PopupPanel popupPanel;
	private TextBox textbox;
	private Button btDone = new Button("Done");
	private String currentDateTimeString;
	private VerticalPanel   vpBase = new VerticalPanel();
	
	public DateTimePicker(){
		textbox=new TextBox();
		textbox.addFocusHandler(this);
		popupPanel=new PopupPanel(true);
		popupPanel.setVisible(false);
		popupPanel.setAnimationEnabled(true);
		popupPanel.setAutoHideEnabled(true);
		popupPanel.setWidget(createDateTimePicker());
		popupPanel.addAutoHidePartner(textbox.getElement());
		initWidget(textbox);
		DOM.setStyleAttribute(textbox.getElement(), "width", "215px");
	}
	
	
	
	public VerticalPanel createDateTimePicker(){
		
		
		
		datePicker.addValueChangeHandler(new ValueChangeHandler(){

			@Override
			public void onValueChange(ValueChangeEvent event) {
				java.util.Date date = (java.util.Date) event.getValue();
		        String dateString = DateTimeFormat.getFormat("dd-MM-yyyy").format(date);
		        textbox.setText(dateString);
		        currentDate =  date;
		        currentDateTimeString = dateString;
		        
		     }});
		
		vpBase.add(textbox);
		vpPanel.add(datePicker);
		datePicker.setWidth("100%");
		timePicker.add(createTimeComponent());
		vpPanel.add(timePicker);
		vpPanel.add(btDone);
		//popupPanel.add(vpPanel);
		//vpBase.add(popupPanel);
		//popupPanel.show();
		btDone.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				String[] splitDate=textbox.getText().split(" ");
				
				String dateString = splitDate[0]+" "+lstHours.getValue(lstHours.getSelectedIndex())+":"+lstMinutes.getValue(lstMinutes.getSelectedIndex())+":"+lstSeconds.getValue(lstSeconds.getSelectedIndex());
				textbox.setText(dateString);
				currentDateTimeString = dateString;
			    DomEvent.fireNativeEvent(Document.get().createChangeEvent(),textbox);
			    popupPanel.hide();
			    
			 }
			
		});
		vpPanel.setCellHorizontalAlignment(btDone, HasHorizontalAlignment.ALIGN_CENTER);
		vpPanel.setStylePrimaryName("timeFieldPanel");
		return vpPanel;
	}
	
	private Widget createTimeComponent() {
		timeFieldPanel.clear();
		
		Date date = new Date( );
		String[] time=date.toString().split(" ");
		String[] item=time[3].split(":");
		
		populateTimeListbox(lstHours,23,item[0]);
		populateTimeListbox(lstMinutes,59,item[1]);
		populateTimeListbox(lstSeconds, 59,item[2]);
		timeFieldPanel.add(lstHours);
		Label lblHour= new Label("(hh):");
		lblHour.setStylePrimaryName("timePanelFont");
		timeFieldPanel.add(lblHour);
		//timeFieldPanel.setCellVerticalAlignment(lblHour, HasVerticalAlignment.ALIGN_BOTTOM);
		
		timeFieldPanel.add(lstMinutes);
		Label lblMinute= new Label("(mm)");
		lblMinute.setStylePrimaryName("timePanelFont");
		timeFieldPanel.add(lblMinute);
		//timeFieldPanel.setCellVerticalAlignment(lblMinute, HasVerticalAlignment.ALIGN_BOTTOM);
		
		timeFieldPanel.add(lstSeconds);
		Label lblSecond= new Label("(ss)");
		lblMinute.setStylePrimaryName("timePanelFont");
		timeFieldPanel.add(lblSecond);
		//timeFieldPanel.setCellVerticalAlignment(lblSecond, HasVerticalAlignment.ALIGN_BOTTOM);
		
		
		DOM.setStyleAttribute(timeFieldPanel.getElement(), "marginTop", "5px");
		DOM.setStyleAttribute(timeFieldPanel.getElement(), "marginBottom", "5px");
		return timeFieldPanel;
	}
	
	 private void populateTimeListbox(ListBox listBox,Integer lastNumber, String item) {
			
			listBox.clear();
			for(Integer i=0;i<=lastNumber;i++){
				
				if(i<10)
					listBox.addItem("0"+i.toString(), "0"+i.toString());
				else
					listBox.addItem(i.toString(), i.toString());
			}
			listBox.setSelectedIndex(Integer.parseInt(item));
		}

		
	public TextBox getTextbox() {
		return textbox;
	}

	public void setTextbox(TextBox textbox) {
		this.textbox = textbox;
	}

	@Override
	public void onFocus(FocusEvent event) {
		Date today = new Date();
        String todayString  = DateTimeFormat.getFormat("dd-MM-yyyy").format(today);
        textbox.setText(todayString);
        
		String[] time=today.toString().split(" ");
		String[] item=time[3].split(":");
		lstHours.setSelectedIndex(Integer.parseInt(item[0]));
		 lstMinutes.setSelectedIndex(Integer.parseInt(item[1]));
		 lstSeconds.setSelectedIndex(Integer.parseInt(item[2]));
		//popupPanel.setVisible(true);
		popupPanel.showRelativeTo(this);
		
		
	}

}
