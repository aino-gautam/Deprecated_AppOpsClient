package in.appops.client.common.snippet;

import in.appops.client.common.fields.LabelField;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.ActionContext;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class HomeSnippet extends HorizontalPanel implements Snippet {

	private LabelField titleLabel;
	private Entity entity;
	private String type;

	public HomeSnippet() {
	}
	
	public HomeSnippet(Entity entity) {
		this.entity = entity;
	}
	
	@Override 
	public void initialize(){
		createView();
	}

	public void createView() {

		titleLabel = new LabelField();
		//String name = entity.getProperty("name").getValue().toString();
		titleLabel.setFieldValue("Default home for service");
		titleLabel.setConfiguration(getLabelFieldConfiguration(true,"homeSnippetTitleLabel", null, null));
		try {
			titleLabel.createField();
		} catch (AppOpsException e) {

			e.printStackTrace();
		}
		setStylePrimaryName("homeSnippetBasePanel");
		add(titleLabel);
	}

	private Configuration getLabelFieldConfiguration(boolean allowWordWrap,
			String primaryCss, String secondaryCss, String debugId) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LABELFIELD_WORDWRAP,allowWordWrap);
		configuration.setPropertyByName(LabelField.LABELFIELD_PRIMARYCSS,primaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEPENDENTCSS,secondaryCss);
		configuration.setPropertyByName(LabelField.LABELFIELD_DEBUGID, debugId);
		return configuration;
	}


	public LabelField getTitleLabel() {
		return titleLabel;
	}

	public void setTitleLabel(LabelField titleLabel) {
		this.titleLabel = titleLabel;
	}

	@Override
	public Entity getEntity() {
		return entity;
	}

	@Override
	public void setEntity(Entity entity) {
		this.entity = entity;
		
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public void setType(String type) {
		this.type = type;
		
	}

	@Override
	public void setConfiguration(Configuration configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Configuration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActionContext getActionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setActionContext(ActionContext actionContext) {
		// TODO Auto-generated method stub
		
	}

}
