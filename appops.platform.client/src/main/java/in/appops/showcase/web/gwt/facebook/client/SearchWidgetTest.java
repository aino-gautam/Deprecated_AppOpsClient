/**
 * 
 */
package in.appops.showcase.web.gwt.facebook.client;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import in.appops.client.common.util.JsonToEntityConverter;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.JsonProperty;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.EntityList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com
 *
 */
public class SearchWidgetTest extends Composite{
	
	private VerticalPanel baseVp;
	private VerticalPanel resultDisplayVp ;
	private Button searchBtn;
	private final DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
	private final DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);
	
	
	
	public SearchWidgetTest(){
		baseVp = new VerticalPanel();
		resultDisplayVp = new VerticalPanel();
		searchBtn = new Button("Test");
		createUi();
		initWidget(baseVp);
		
		
	}
	
	private void createUi(){
		try{
			baseVp.add(searchBtn);
			baseVp.add(resultDisplayVp);
			
			searchBtn.addClickHandler(new ClickHandler() {
				
				@SuppressWarnings("unchecked")
				@Override
				public void onClick(ClickEvent event) {
					processAndDisplay(null);
					HashMap<String, Object> parMap = new HashMap<String, Object>();
					parMap.put("authorNameTemp", "Mahesh");
					
					Query appopsQuery = new Query();
					appopsQuery.setQueryName("fetchAllBookByAuthor");
					appopsQuery.setQueryParameterMap(parMap);
					appopsQuery.setStartIndex(0);
					appopsQuery.setListSize(5);
					
					Map map = new HashMap();
					map.put("query", appopsQuery);
					
					StandardAction action = new StandardAction(Entity.class, "search.SearchService.getSearchEntity", map);
					dispatch.execute(action, new AsyncCallback<Result<Entity>>() {
						
						
						public void onFailure(Throwable caught) {
							//Window.alert("operation failed ");
							caught.printStackTrace();
						}
						
						
						public void onSuccess(Result<Entity> result) {
							if(result!=null){
								Entity entity = (Entity) result.getOperationResult();
								if(entity!=null){
									processAndDisplay(entity);
								}
							}
						}
					});
				}
			});
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void processAndDisplay(Entity entity) {
		try{
			resultDisplayVp.clear();
			JsonToEntityConverter converter = new JsonToEntityConverter();
			HashMap<String, Property<? extends Serializable>> propMap = entity.getValue();
			for(String propName : propMap.keySet()){

				Property prop = entity.getProperty(propName);
				if(prop instanceof JsonProperty){
					JsonProperty propMain = (JsonProperty) prop;
					String jsonStr = propMain.getJsonString();
					Label jsonStrLbl = new Label(jsonStr);
					resultDisplayVp.add(jsonStrLbl);
					
					EntityList list = converter.getConvertedJsonToEntityList(jsonStr);
					Entity entityBook = list.get(0);
					String bookName = entityBook.getPropertyByName("bookName");
					Label authorNameLbl = new Label("BOOK NAME : "+bookName);
					resultDisplayVp.add(authorNameLbl);
					
					Label separator = new Label("***************");
					resultDisplayVp.add(separator);
				}
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


}
