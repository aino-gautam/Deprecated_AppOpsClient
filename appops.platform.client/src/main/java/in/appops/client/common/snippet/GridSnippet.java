package in.appops.client.common.snippet;

import in.appops.client.common.gin.AppOpsGinjector;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configurable;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class GridSnippet extends Snippet implements Configurable {

	private HorizontalPanel basePanel = new HorizontalPanel();
	private Grid gridPanel;
	private Configuration configuration;
	private EntityList entityList;
	private final AppOpsGinjector injector = GWT.create(AppOpsGinjector.class);
	private int noOfRows = 0;
	private int noOfCols = 3;
	

	public EntityList getEntityList() {
		return entityList;
	}

	public void setEntityList(EntityList entityList) {
		this.entityList = entityList;
	}

	public GridSnippet() {
		initWidget(basePanel);
	}

	public GridSnippet(EntityList list) {
		this.entityList = list;
		initWidget(basePanel);
	}

	@Override
	public void initialize(){
		SnippetFactory snippetFactory = injector.getSnippetFactory();

		int height = Window.getClientHeight() - 120;
		int width = Window.getClientWidth() - 100;

		noOfRows = (entityList.size() / 3) + 1;
		gridPanel = new Grid(noOfRows, 3);
		gridPanel.setSize("100%", "100%");

		gridPanel.setCellSpacing(10);
		gridPanel.setCellPadding(2);

		int index = 0;
		for (int row = 0; row < noOfRows; row++) {

			for (int col = 0; col < noOfCols; col++) {
				if (index < entityList.size()) {
					Entity entity = entityList.get(index);
					Snippet snippet = snippetFactory.getSnippetByEntityType(entity.getType(), null);
					snippet.setEntity(entity);
					snippet.initialize();
					gridPanel.setWidget(row, col, snippet);
					index++;
				} else {
					break;
				}
			}

		}

		basePanel.add(gridPanel);
		
		basePanel.setStylePrimaryName("serviceListPanel");
				
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
