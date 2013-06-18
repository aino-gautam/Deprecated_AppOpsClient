package in.appops.client.common.fields;


import in.appops.client.common.event.FieldEvent;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

/**
 * Field class to represent a {@link Hyperlink} or {@link Anchor}
 * @author nairutee
 *
 */
public class LinkField extends Composite implements Field {

	private Configuration configuration;
	private String fieldValue;
	private Anchor anchor;
	private Hyperlink hyperLink;
	
	public static final String LINKFIELD_TYPE = "LinkType";
	public static final String LINKFIELDTYPE_HYPERLINK = "Hyperlink";
	public static final String LINKFIELDTYPE_ANCHOR = "Anchor";
	
	public static final String LINKFIELD_PRIMARYCSS = "labelFieldPrimaryCss";
	public static final String LINKFIELD_DEPENDENTCSS = "labelFieldDependentCss";
	public static final String LINKFIELD_DEBUGID = "labelFieldDebugId";
	
	public LinkField(){
		
	}
	
	/**
	 * creates the field UI
	 * @throws AppOpsException 
	 */
	@Override
	public void create() throws AppOpsException{
		if(getConfiguration() == null)
			throw new AppOpsException("LinkField configuration unavailable");
		
		
		if(getConfiguration().getPropertyByName(LINKFIELD_TYPE).toString().equalsIgnoreCase(LINKFIELDTYPE_HYPERLINK)){
			hyperLink = new Hyperlink();
			hyperLink.setText(getFieldValue());
			if(getConfiguration().getPropertyByName(LINKFIELD_PRIMARYCSS) != null)
				hyperLink.setStylePrimaryName(getConfiguration().getPropertyByName(LINKFIELD_PRIMARYCSS).toString());
			if(getConfiguration().getPropertyByName(LINKFIELD_DEPENDENTCSS) != null)
				hyperLink.addStyleName(getConfiguration().getPropertyByName(LINKFIELD_DEPENDENTCSS).toString());
			if(getConfiguration().getPropertyByName(LINKFIELD_DEBUGID) != null)
				hyperLink.ensureDebugId(getConfiguration().getPropertyByName(LINKFIELD_DEBUGID).toString());
			
			initWidget(hyperLink);
		}else if(getConfiguration().getPropertyByName(LINKFIELD_TYPE).toString().equalsIgnoreCase(LINKFIELDTYPE_ANCHOR)){
			anchor = new Anchor();
			anchor.setText(getFieldValue());
			if(getConfiguration().getPropertyByName(LINKFIELD_PRIMARYCSS) != null)
				anchor.setStylePrimaryName(getConfiguration().getPropertyByName(LINKFIELD_PRIMARYCSS).toString());
			if(getConfiguration().getPropertyByName(LINKFIELD_DEPENDENTCSS) != null)
				anchor.addStyleName(getConfiguration().getPropertyByName(LINKFIELD_DEPENDENTCSS).toString());
			if(getConfiguration().getPropertyByName(LINKFIELD_DEBUGID) != null)
				anchor.ensureDebugId(getConfiguration().getPropertyByName(LINKFIELD_DEBUGID).toString());
			
			initWidget(anchor);
		}
	}

	/**
	 * clears the field if it has any values
	 */
	@Override
	public void clear() {
		if(getConfiguration().getPropertyByName(LINKFIELD_TYPE).toString().equalsIgnoreCase(LINKFIELDTYPE_HYPERLINK))
			hyperLink.setText("");
		else if(getConfiguration().getPropertyByName(LINKFIELD_TYPE).toString().equalsIgnoreCase(LINKFIELDTYPE_ANCHOR))
			anchor.setText("");
	}

	/**
	 * resets the field to the original value that has been set via setFieldValue()
	 */
	@Override
	public void reset() {
		if(getConfiguration().getPropertyByName(LINKFIELD_TYPE).toString().equalsIgnoreCase(LINKFIELDTYPE_HYPERLINK))
			hyperLink.setText(getFieldValue());
		else if(getConfiguration().getPropertyByName(LINKFIELD_TYPE).toString().equalsIgnoreCase(LINKFIELDTYPE_ANCHOR))
			anchor.setText(getFieldValue());
	}
	
	@Override
	public Configuration getConfiguration() {
		return this.configuration;
	}
	

	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	@Override
	public String getFieldValue() {
		return this.fieldValue;
	}

	@Override
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public Widget getWidget() {
		if(getConfiguration().getPropertyByName(LINKFIELD_TYPE).toString().equalsIgnoreCase(LINKFIELDTYPE_HYPERLINK)) {
			return hyperLink;
		} else if(getConfiguration().getPropertyByName(LINKFIELD_TYPE).toString().equalsIgnoreCase(LINKFIELDTYPE_ANCHOR)) {
			return anchor;
		}
		return null;
	}

	@Override
	public void configure() {
		// TODO Auto-generated method stub
		
	}
}
