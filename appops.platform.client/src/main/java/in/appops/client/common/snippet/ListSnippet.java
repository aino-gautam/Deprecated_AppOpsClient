package in.appops.client.common.snippet;

import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.client.common.snippet.Snippet;
import in.appops.client.common.snippet.SnippetFactory;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
/**
 * 
 * @author pallavi@ensarm.com
 */

public class ListSnippet extends Snippet implements  Configurable{

	private VerticalPanel basePanel = new VerticalPanel();
	private ScrollPanel scrollPanel ;
	private VerticalPanel listPanel;
	private Configuration configuration;
	private EntityList entityList;
	private SnippetFactory snippetFactory;
	private final AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);

	public EntityList getEntityList() {
		return entityList;
	}


	public void setEntityList(EntityList entityList) {
		this.entityList = entityList;
	}

	public ListSnippet() {
		initWidget(basePanel);
	}

	public ListSnippet(EntityList list) {
		this.entityList = list;
		initWidget(basePanel);
	}


	@Override
	public void initialize(){
		SnippetFactory snippetFactory = injector.getSnippetFactory();
		
		listPanel = new VerticalPanel();
		scrollPanel = new ScrollPanel(listPanel);
		
		int height = Window.getClientHeight() - 120;
		int width = Window.getClientWidth() - 100;
		scrollPanel.setHeight(height + "px");
		scrollPanel.setWidth(width + "px");
		
		listPanel.setSpacing(5);
		basePanel.add(scrollPanel);
		
		listPanel.setStylePrimaryName("listComponentPanel");
		
		basePanel.setCellHorizontalAlignment(scrollPanel, HasAlignment.ALIGN_CENTER);
		
		basePanel.setStylePrimaryName("listComponentPanel");
		
		for(Entity entity :entityList){
			Snippet snippet = snippetFactory.getSnippetByEntityType(entity.getType(), null);
			snippet.setEntity(entity);
			snippet.initialize();
			listPanel.add(snippet);
		}
		
	}


	@Override
	public Configuration getConfiguration() {
		return configuration;
	}


	@Override
	public void setConfiguration(Configuration conf) {
		this.configuration = conf;
		
	}
	

}
