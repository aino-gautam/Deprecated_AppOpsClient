package in.appops.client.common.fields;

import in.appops.client.common.event.FieldEvent;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.user.client.ui.Image;

public class ImageField extends Image implements Field {

	private Configuration configuration;
	private String fieldValue;
	
	public static final String IMAGEFIELD_DISPLAYTEXT = "imageFieldFieldDisplayText";
	public static final String IMAGEFIELD_BLOBID = "imageFieldFieldBlobId";
	public static final String IMAGEFIELD_PRIMARYCSS = "imageFieldPrimaryCss";
	public static final String IMAGEFIELD_DEPENDENTCSS = "imageFieldDependentCss";
	public static final String IMAGEFIELD_DEBUGID = "imageFieldDebugId";
	
	public ImageField(){
		super();
	}
	
	/**
	 * get the {@link Configuration} object
	 */
	@Override
	public Configuration getConfiguration() {
		return configuration;
	}

	/**
	 * set the {@link Configuration} object
	 */
	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
	}

	/**
	 * Handles specific {@link FieldEvent}
	 */
	@Override
	public void onFieldEvent(FieldEvent event) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * creates the field UI
	 */
	@Override
	public void create() throws AppOpsException {
		if(getConfiguration() == null)
			throw new AppOpsException("Imagefield configuration unavailable");
		
		String blobId = getConfiguration().getPropertyByName(IMAGEFIELD_BLOBID);
		this.setUrl(blobId);
		if(getConfiguration().getPropertyByName(IMAGEFIELD_PRIMARYCSS) != null)
			this.setStylePrimaryName(getConfiguration().getPropertyByName(IMAGEFIELD_PRIMARYCSS).toString());
		if(getConfiguration().getPropertyByName(IMAGEFIELD_DEPENDENTCSS) != null)
			this.addStyleName(getConfiguration().getPropertyByName(IMAGEFIELD_DEPENDENTCSS).toString());
		if(getConfiguration().getPropertyByName(IMAGEFIELD_DEBUGID) != null)
			this.ensureDebugId(getConfiguration().getPropertyByName(IMAGEFIELD_DEBUGID).toString());
		if(getConfiguration().getPropertyByName(IMAGEFIELD_DISPLAYTEXT) != null)
			this.setTitle(getConfiguration().getPropertyByName(IMAGEFIELD_DISPLAYTEXT).toString());
	}

	/**
	 * clears the field
	 */
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * resets the field
	 */
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
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
	public void configure() {
		// TODO Auto-generated method stub
		
	}

}
