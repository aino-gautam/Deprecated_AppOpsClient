package in.appops.client.common.snippet;

import in.appops.client.common.fields.LabelField;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;

import com.google.gwt.user.client.ui.HorizontalPanel;

public class HomeSnippet extends Snippet {

	private HorizontalPanel basePanel = new HorizontalPanel();

	private LabelField titleLabel;

	public HomeSnippet() {
		initWidget(basePanel);
	}
	
	public HomeSnippet(Entity entity) {

		this.entity = entity;
		initWidget(basePanel);
		
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
		basePanel.setStylePrimaryName("homeSnippetBasePanel");
		basePanel.add(titleLabel);
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

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public HorizontalPanel getBasePanel() {
		return basePanel;
	}

	public void setBasePanel(HorizontalPanel basePanel) {
		this.basePanel = basePanel;
	}

	public LabelField getTitleLabel() {
		return titleLabel;
	}

	public void setTitleLabel(LabelField titleLabel) {
		this.titleLabel = titleLabel;
	}

}
