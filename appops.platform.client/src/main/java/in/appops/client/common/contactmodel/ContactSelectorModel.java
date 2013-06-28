package in.appops.client.common.contactmodel;

import in.appops.client.common.core.EntityListModel;
import in.appops.client.common.event.AppUtils;
import in.appops.client.common.event.SelectionEvent;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ContactSelectorModel extends EntityListModel {
	
	private boolean isNearByContact;
	private boolean isYourContact;
	private boolean isContactKnown;
	private final DefaultExceptionHandler exceptionHandler = new DefaultExceptionHandler();
	private final DispatchAsync	dispatch = new StandardDispatchAsync(exceptionHandler);
	private EntityList entityList;
	
	public EntityList getEntityList() {
		return entityList;
	}

	public void setCurrentEntityList(EntityList entityList) {
		this.entityList = entityList;
	}

	public ContactSelectorModel(Query query, String operatioName, int noOfEntities) {
		setQueryToBind(query);
		setOperationNameToBind(operatioName);
		setNoOfEntities(noOfEntities);
	}
	
	public boolean isNearByContact() {
		return isNearByContact;
	}

	public void setNearByContact(boolean isNearByContact) {
		this.isNearByContact = isNearByContact;
	}

	public boolean isYourContact() {
		return isYourContact;
	}

	public void setYourContact(boolean isYourContact) {
		this.isYourContact = isYourContact;
	}

	public boolean isContactKnown() {
		return isContactKnown;
	}

	public void setContactKnown(boolean isContactKnown) {
		this.isContactKnown = isContactKnown;
	}
	
	public void fetchContactEntityList() {
		Query query = getQueryToBind();
		
		Map parameterMap = new HashMap();
		parameterMap.put("query", query);
		
		StandardAction action = new StandardAction(EntityList.class, "contact.ContactService.getEntityList", parameterMap);
		dispatch.execute(action, new AsyncCallback<Result>() {

			@Override
			public void onFailure(Throwable caught) {
				caught.printStackTrace();
			}

			@Override
			public void onSuccess(Result result) {
				EntityList contactList = (EntityList) result.getOperationResult();
				setCurrentEntityList(contactList);
				SelectionEvent selectionEvent = new SelectionEvent();
				selectionEvent.setEventType(SelectionEvent.DATARECEIVED);
				selectionEvent.setEventData(getEntityList());
				AppUtils.EVENT_BUS.fireEvent(selectionEvent);
			}
		});
	}
}
