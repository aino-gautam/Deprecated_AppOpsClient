/**
 * 
 */
package in.appops.client.common.config.breadcrumb;

import in.appops.client.common.config.model.EntityListModel;
import in.appops.platform.core.shared.Configuration;

/**
 * @author mahesh@ensarm.com
 */
public class BreadcrumbPresenter {
	
	private Configuration breadCrumbConfig;
	private EntityListModel model;
	private BreadcrumbView view;

	public void setConfiguration(Configuration breadCrumbConfig) {
		this.breadCrumbConfig = breadCrumbConfig;
	}

	public void initialize() {
		try{
			model = new EntityListModel();
			view = new BreadcrumbView();
			view.setModel(model);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createUi() {
		try{
			view.setConfiguration(breadCrumbConfig);
			view.create();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public BreadcrumbView getView() {
		return view;
	}

}
