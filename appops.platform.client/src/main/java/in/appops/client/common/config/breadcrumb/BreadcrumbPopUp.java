package in.appops.client.common.config.breadcrumb;

import in.appops.client.common.config.field.LinkField.LinkFieldConstant;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.FieldEvent;
import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.EntityList;

import java.util.ListIterator;

import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class BreadcrumbPopUp extends PopupPanel implements FieldEventHandler{

	private VerticalPanel basePanel;
	private HandlerRegistration fieldEventHandler= null;
	private EntityList entitylist;

	public BreadcrumbPopUp(EntityList childlist) {
		super(true);
		this.entitylist=childlist;
		if(fieldEventHandler ==null)
			fieldEventHandler = AppUtils.EVENT_BUS.addHandler(FieldEvent.TYPE, this);
	}

	public void createUI(){

		basePanel= new VerticalPanel();
		ListIterator<Entity> itr= entitylist.listIterator();
		while(itr.hasNext()){
			Entity entity=itr.next();
			PopUpEntry link= new PopUpEntry();
			link.setEntity(entity);
			link.setConfiguration(getLinkFieldConfiguration(entity.getPropertyByName("Name").toString()));
			link.configure();
			link.create();
			basePanel.add(link);
		}
		setWidget(basePanel);
	}

	@Override
	public void onFieldEvent(FieldEvent event) {

	}

	public Configuration getLinkFieldConfiguration(String value){
		Configuration configuration= new Configuration();
		configuration.setPropertyByName(LinkFieldConstant.LNK_TYPE, LinkFieldConstant.LNKTYPE_HYPERLINK);
		configuration.setPropertyByName(LinkFieldConstant.BF_PCLS,"appops-LinkField");
		configuration.setPropertyByName(LinkFieldConstant.LNK_DISPLAYTEXT, value);
		return configuration;
	}

}
