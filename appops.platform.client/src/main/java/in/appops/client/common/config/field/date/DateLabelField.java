package in.appops.client.common.config.field.date;

import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.config.field.LabelField.LabelFieldConstant;
import in.appops.platform.core.shared.Configuration;
import java.util.Date;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DockPanel;


/**
Field class to represent a {@link DateLabelField }
@author pallavi@ensarm.com

<p>
<h3>Configuration</h3>
<a href="DateLabelField.DateLabelFieldConstant.html">Available configurations</a>
</p>

<p>
<h3>Example</h3>

DateLabelField dateLabelField = new DateLabelField();<br>
Configuration configuration = new Configuration();<br>
configuration.setPropertyByName(DateLabelFieldConstant.DTLBL_DSPLY_FORM, DateLabelFieldConstant.LIVETIMESTAMP_DSPLY);<br>
configuration.setPropertyByName(DateLabelFieldConstant.DATETIME_FORMAT, "MMM dd ''yy 'at' HH:mm");<br>
configuration.setPropertyByName(DateLabelFieldConstant.DATETIME_TO_DISPLAY, new Date());<br>
dateLabelField.setConfiguration(configuration);<br>
dateLabelField.configure();<br>
dateLabelField.create();<br>

</p>*/

public class DateLabelField extends BaseField{
	
	private LabelField displayLblField ;
	
	public DateLabelField() {}
	
	/**
	 * Method creates the date label field.
	 */
	@Override
	public void create() {
		getBasePanel().add(displayLblField,DockPanel.CENTER);
	}
	
	/**
	 * Method configures the date label field.
	 */
	@Override
	public void configure() {
		
		displayLblField = new LabelField();
		displayLblField.setConfiguration(getDateLabelConfiguration());
		displayLblField.configure();
		displayLblField.create();
		
		String displayFormat = getDisplayFormat();
		
		if(displayFormat.equals(DateLabelFieldConstant.LIVETIMESTAMP_DSPLY)){
			setTimeToLabel();
		}else{
			displayLblField.setValue(getFormatedDate());
		}
	}
	
	/********************************************************************************************************/
	
	/**
	 * Method returns the date display format to use .Defaults to LIVETIMESTAMP_DSPLY.
	 * @return
	 */
	private String getDisplayFormat() {
		
		String displayFormat = DateLabelFieldConstant.LIVETIMESTAMP_DSPLY;
		if(getConfigurationValue(DateLabelFieldConstant.DTLBL_DSPLY_FORM) != null) {
			
			displayFormat = getConfigurationValue(DateLabelFieldConstant.DTLBL_DSPLY_FORM).toString();
		}
		return displayFormat;
	}
	
	
	/**
	 * Method returns the date time format to use to display the date. Defaults to "MMM dd''yy 'at' HH:mm a".
	 * @return
	 */
	private String getDateTimeFormat() {
		
		String displayFormat = "MMM dd''yy 'at' HH:mm a";
		if(getConfigurationValue(DateLabelFieldConstant.DATETIME_FORMAT) != null) {
			
			displayFormat = getConfigurationValue(DateLabelFieldConstant.DATETIME_FORMAT).toString();
		}
		return displayFormat;
	}
	
	/**
	 * Method returns the date to display in label.
	 * @return
	 */
	private Date getDateTimeToDisplay() {
		
		Date date = null;;
		if(getConfigurationValue(DateLabelFieldConstant.DATETIME_TO_DISPLAY) != null) {
			
			date = (Date) getConfigurationValue(DateLabelFieldConstant.DATETIME_TO_DISPLAY);
		}
		return date;
	}
		
	/**************************************************************************************************/
	
	private void setTimeToLabel(){
		Timer timer = new Timer() {
			public void run() {
				String timeAgo = calculateTimeAgo(getDateTimeToDisplay());
				displayLblField.setValue(timeAgo);
			}
		};
		timer.run();
		timer.scheduleRepeating(1000);
	}
	
	/**
	 * Method calculates the time.
	 * @param activitydate
	 * @return
	 */
	private String calculateTimeAgo(Date activitydate){
		Date currDate = new Date();
		long diffInSec = (currDate.getTime() - activitydate.getTime()) / 1000;
		long second = diffInSec % 60;
	    diffInSec/= 60;
	    long minute = diffInSec % 60;
	    diffInSec /= 60;
	    long hour = diffInSec % 24;
	    diffInSec /= 24;
	    long day = diffInSec;
	    
	    String str = "";
	    if(day != 0){
	    	str = day > 1 ? " days " : " day ";
	    	return Long.toString(day) + str + "ago";
	    } else if(hour != 0){
	    	str = hour > 1 ? " hours " : " hour ";
	    	return Long.toString(hour) + str + "ago";
	    } else if(minute != 0){
	    	str = minute > 1 ? " minutes " : " minute ";
	    	return Long.toString(minute) + str + "ago";
	    } else{
	    	str = second > 1 ? " secs " : " sec ";
	    	return Long.toString(second) + str + "ago";
	    }
	}
	
	/**
	 * Method get the configurations for date label , creates the configuration object for it and return.
	 * @return
	 */
	private Configuration getDateLabelConfiguration(){
		Configuration conf = new Configuration();
		conf.setPropertyByName(LabelFieldConstant.LBLFD_ISWORDWRAP, true);
		conf.setPropertyByName(LabelFieldConstant.BF_PCLS, getBaseFieldPrimCss());
		conf.setPropertyByName(LabelFieldConstant.BF_DCLS, getBaseFieldCss());
		return conf;
		
	}
	
	/**
	 * Method convert date from one format to other format and return.
	 * @return
	 */
	private String getFormatedDate(){
		
		String dateString = DateTimeFormat.getFormat(getDateTimeFormat()).format(getDateTimeToDisplay());
		
		return dateString;
	}
	
	/***********************************************************************************/
	
	public interface DateLabelFieldConstant extends BaseFieldConstant{
		
		/** Specifies the format to be used for displaying label */
		public static final String DTLBL_DSPLY_FORM = "DisplayFormat";
		
		/** Specifies the livetimestamp format. */
		public static final String LIVETIMESTAMP_DSPLY = "liveTimeStamp";
		
		/** Specifies the display format in DateTime . */
		public static final String DATETIME_DSPLY = "dateTimeDisplay";
		
		/** Specifies the date time format to be used for label. */
		public static final String DATETIME_FORMAT = "conversionFormat";
		
		/** Specifies the date time. */
		public static final String DATETIME_TO_DISPLAY = "dateTimeToDisplay";
		
	}

}
