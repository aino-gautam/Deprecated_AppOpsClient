package in.appops.client.common.config.field.date;

import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.fields.Field;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import java.util.Date;

import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ShowRangeEvent;
import com.google.gwt.event.logical.shared.ShowRangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.CalendarUtil;

/**
 * Old Raw Code for test. Code to be committed.
 * @author nitish@ensatm.com 
 *
 */
public class DatePickerField extends Composite implements Field, ClickHandler {
	
	public interface DatePickerConstant {

	}
	
	private HorizontalPanel dtPickFieldBase;
	private VerticalPanel vp;
	private VerticalPanel dtPickPopupBase;
	private PopupPanel dtPickPopup;
	private TextBox dtPickBox;
	private ToggleButton dtPickTrigger;
	private AppopsDatePicker dtPicker;
	private Date start;
	private Date end;
	Label invalidFormat;
	Date startDt = null;//DateTimeFormat.getFormat("dd/MM/yyyy").parse("09/08/2013");
	Date endDt = null;//DateTimeFormat.getFormat("dd/MM/yyyy").parse("22/08/2013");
	public DatePickerField(){
		initialize();
		
		
		
		/*		textbox=new TextBox();
		textbox.setStylePrimaryName("appops-TextField-default");
		textbox.addFocusHandler(this);
*/		//initWidget(dtPickBox);
	}
	
	private void initialize() {
		dtPickFieldBase = new HorizontalPanel();
		dtPickBox=new TextBox();
		dtPickBox.getElement().setAttribute("placeholder", "DD/MM/YYYY");
		dtPickTrigger = new ToggleButton();
		dtPickPopupBase = new VerticalPanel();
		dtPickPopup = new PopupPanel(true);
		dtPickPopup.setWidth("142px");
		dtPicker = new AppopsDatePicker();
		vp = new VerticalPanel();
	}
	
	public void configure() {
		
	}
	
	@Override
	public void create() throws AppOpsException {
		Date dateString = DateTimeFormat.getFormat("dd/MM/yyyy").parse("12/08/2013");
        dtPickBox.setText( DateTimeFormat.getFormat("dd/MM/yyyy").format(dateString));
        vp.add(dtPickFieldBase);
		dtPickFieldBase.setStylePrimaryName(getDatePickFieldPrimCss());
		dtPickBox.setStylePrimaryName(getDatePickBoxPrimCss());
		dtPickFieldBase.add(dtPickBox);
		
		dtPickTrigger.setStylePrimaryName("appops-dtPickTrigger");
		dtPickFieldBase.add(dtPickTrigger);
		invalidFormat = new Label("Invalid format");
		invalidFormat.setWidth("150px");
		invalidFormat.setHeight("20px");
		invalidFormat.setStylePrimaryName("appops-ErrorText");
		vp.add(invalidFormat);
		dtPickTrigger.setDown(false);
		invalidFormat.setVisible(false);
		NodeList<com.google.gwt.dom.client.Element> nodeList = dtPickFieldBase.getElement().getElementsByTagName("td");
	    Node td1Node = nodeList.getItem(0);
	    Element td1Element = (Element) Element.as(td1Node);
	    td1Element.setClassName("appops-dtPicker-border-box");

	    Node td2Node = nodeList.getItem(1);
	    Element td2Element = (Element) Element.as(td2Node);
	    td2Element.setClassName("appops-dtPicker-border-box");
		
	    dtPicker.setWidth("100%");
	    dtPickPopup.setVisible(false);
	    dtPickPopup.setAnimationEnabled(true);
	    dtPickPopup.setWidget(dtPicker);
	    
		initWidget(vp);
		dtPickTrigger.addClickHandler(this);
		dtPicker.addValueChangeHandler(new ValueChangeHandler(){
			@Override
			public void onValueChange(ValueChangeEvent event) {
				java.util.Date date = (java.util.Date) event.getValue();
		        String dateString = DateTimeFormat.getFormat("dd/MM/yyyy").format(date);
		        dtPickBox.setText(dateString);
		        dtPickPopup.hide();
		       
		     }});
		
		dtPickPopup.addCloseHandler(new CloseHandler<PopupPanel>() {
			
			@Override
			public void onClose(CloseEvent<PopupPanel> event) {
				dtPickTrigger.setDown(false);
				
			}
		});
		
		dtPicker.addShowRangeHandler(new ShowRangeHandler<Date>() {
	         public void onShowRange(ShowRangeEvent<Date> dateShowRangeEvent) {
	             setValidDates(dateShowRangeEvent);
	         }
	    });
		dtPickBox.addBlurHandler(new BlurHandler() {
			
			@Override
			public void onBlur(BlurEvent event) {
				p();
				
			}
		});
	}

	private void setValidDates(ShowRangeEvent<Date> dateShowRangeEvent) {
        start = dateShowRangeEvent.getStart();
        end = dateShowRangeEvent.getEnd();

        Integer daysBetween = CalendarUtil.getDaysBetween(start, end);

        for (int i = 0; i < daysBetween; i++) {
            Date date = new Date(start.getTime());
            CalendarUtil.addDaysToDate(date, i);
            setDatePickable(date);
        }
    }
    private void setDatePickable(Date date) {
        Boolean enabled = true;
        Date todaysDate = new Date();
//        String dateString = DateTimeFormat.getFormat("dd/MM/yyyy").format(todaysDate);
//        Date parsedDate = DateTimeFormat.getFormat("dd/MM/yyyy").parse(dateString);
//        Date endDate = DateTimeFormat.getFormat("dd/MM/yyyy").parse("22/08/2013");
        if (startDt != null && date.before(startDt)) {
            enabled = false;
        } else if (endDt != null && date.after(endDt)) {
            enabled = false;
        }
        dtPicker.getCalendarView().setEnabledOnDate(enabled, date);
    }
	
	
	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConfiguration(Configuration conf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFieldValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Returns the primary style to be applied to the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getDatePickFieldPrimCss() {
		String primaryCss = "appops-dtPickFieldPrim";
//		if(getConfigurationValue(SpinnerConfigurationConstant.SPINNER_PRIMARYCSS) != null) {
//			primaryCss = getConfigurationValue(SpinnerConfigurationConstant.SPINNER_PRIMARYCSS).toString();
//		}
		return primaryCss;
	}

	/**
	 * Returns the dependent style to be applied to the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getDatePickFieldDepCss() {
		String depCss = "appops-dtPickFieldDep";
//		if(getConfigurationValue(SpinnerConfigurationConstant.SPINNER_DEPENDENTCSS) != null) {
//			depCss = getConfigurationValue(SpinnerConfigurationConstant.SPINNER_DEPENDENTCSS).toString();
//		}
		return depCss;
	}

	/**
	 * Returns the primary style to be applied to the textbox of the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getDatePickBoxPrimCss() {
		String primaryCss = "appops-dtPickBoxPrim";
//		if(getConfigurationValue(SpinnerConfigurationConstant.BOX_PRIMARYCSS) != null) {
//			primaryCss = getConfigurationValue(SpinnerConfigurationConstant.BOX_PRIMARYCSS).toString();
//		}
		return primaryCss;
	}
	
	/**
	 * Returns the dependent style to be applied to the textbox of the spinner field.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getDatePickBoxDepCss() {
		String dependentCss = "appops-dtPickBoxDep";
//		if(getConfigurationValue(SpinnerConfigurationConstant.BOX_DEPENDENTCSS) != null) {
//			dependentCss = getConfigurationValue(SpinnerConfigurationConstant.BOX_DEPENDENTCSS).toString();
//		}
		return dependentCss;
	}

	@Override
	public void onClick(ClickEvent event) {
		if(event.getSource().equals(dtPickTrigger)) {
			if(dtPickTrigger.isDown()) {
/*				final String[] formats = { "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd'T'HH:mm:ssZ", "yyyy-MM-dd'T'HH:mm:ss",
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "yyyy-MM-dd HH:mm:ss", "MM/dd/yyyy HH:mm:ss",
                    "MM/dd/yyyy'T'HH:mm:ss.SSS'Z'", "MM/dd/yyyy'T'HH:mm:ss.SSSZ", "MM/dd/yyyy'T'HH:mm:ss.SSS", "MM/dd/yyyy'T'HH:mm:ssZ",
                    "MM/dd/yyyy'T'HH:mm:ss", "yyyy:MM:dd HH:mm:ss", "yyyyMMdd"};
*/				p();
			} else if(!dtPickTrigger.isDown()) {

			}
			
		}
		
	}
	
	public void p () {
		final String[] formats = { "dd.MM.yyyy", "dd/MM/yyyy", "dd-MM-yyyy"};
		String regex = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$";
		Date endDate = null;
		boolean parsed = false;
		if(!dtPickBox.getText().equals("")) {
			for(String parse : formats) {
				try {
					if(!parsed) { 
						endDate = DateTimeFormat.getFormat(parse).parse(dtPickBox.getText());
						if(endDate != null)
							parsed = true;
					}
				} catch(IllegalArgumentException e) {
					System.out.println("sdfsd");
				}
			}
			if(parsed) {
				if(dtPickBox.getText().matches(regex)) {
					if(startDt != null && endDt != null) {
						if(endDate.before(endDt) && endDate.after(startDt)) {
							dtPicker.setCurrentMonth(endDate);
							dtPicker.setValue(endDate);
							dtPickPopup.showRelativeTo(dtPickBox);
							
							invalidFormat.setVisible(false);
						} else {
							invalidFormat.setText("Date should be between max and min");
							invalidFormat.setVisible(true);
							dtPickTrigger.setDown(false);
						}
					} else {
						dtPicker.setCurrentMonth(endDate);
						dtPicker.setValue(endDate);
						dtPickPopup.showRelativeTo(dtPickBox);
						invalidFormat.setVisible(false);
					}
				} else {
					invalidFormat.setText("Invalid date");
					invalidFormat.setVisible(true);
					dtPickTrigger.setDown(false);
				}
			} else {
				invalidFormat.setText("Invalid format");
				invalidFormat.setVisible(true);
				dtPickTrigger.setDown(false);
			}
		} else {
			Date dateString = DateTimeFormat.getFormat("dd/MM/yyyy").parse("12/08/2013");
			dtPicker.setCurrentMonth(endDate);

			dtPicker.setValue(dateString);
			dtPickPopup.showRelativeTo(dtPickBox);
			
			invalidFormat.setVisible(false);
		}
	}
	
}
