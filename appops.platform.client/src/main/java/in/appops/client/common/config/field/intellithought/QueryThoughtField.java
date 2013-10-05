package in.appops.client.common.config.field.intellithought;

import in.appops.client.common.event.handlers.FieldEventHandler;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.query.Query;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.EntityList;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;


public class QueryThoughtField extends IntelliThoughtField implements FieldEventHandler{
	
	private Logger logger = Logger.getLogger(getClass().getName());
	private HashMap<Integer,Entity> levelVsEntityMap = null;
	
	/** Levels used in query editor **/
	private final int FIRST = 1;
	private final int SECOND = 2;
	private final int THIRD = 3;
	private final int FOURTH = 4;
	
	private int CURRENT_SUGGESTION_LEVEL = 1;
	
	private final String SEPERATOR = ".";
	
	/** Keywords used in query editor **/
	private final String FROM = "from";
	
	private final String SELECT = "select";
	
	/** Clause used in query editor **/
	private final String WHERE = "where";
	
	
	public QueryThoughtField() {
		super();
		levelVsEntityMap= new HashMap<Integer, Entity>();
	}
	
	/**
	 * Method handles key up event.
	 * @param event
	 */
	@Override
	protected void handleOnKeyUpEvent(Event event){
		try {
			logger.log(Level.INFO, "[QueryThoughtField] ::In handleOnKeyUpEvent method ");
			int keyCode = event.getKeyCode();

			String elementValue = this.getText();
			caretPosition = IntelliThoughtUtil.getCaretPosition(getBaseFieldId());
			String textTillCaretPosition = elementValue.substring(0, caretPosition);
			String wordBeingTyped = IntelliThoughtUtil.getWordBeingTyped(textTillCaretPosition, caretPosition);
			
			if(keyCode == 190){
				int suggestionLevel = getSuggestionLevel(wordBeingTyped);
				showSuggestionsForLevel(suggestionLevel);
				return;
			}
			
			if(keyCode == KeyCodes.KEY_DOWN || keyCode == KeyCodes.KEY_UP || keyCode == KeyCodes.KEY_ENTER || keyCode == KeyCodes.KEY_ESCAPE || keyCode == KeyCodes.KEY_ENTER){
				if(linkedSuggestion.isShowing() && keyCode == KeyCodes.KEY_ESCAPE){
					linkedSuggestion.hide();
				}
				return;
			}
			if(keyCode == KeyCodes.KEY_BACKSPACE){
				if(wordBeingTyped.length() <=  2){
					if(linkedSuggestion.isShowing()){
						linkedSuggestion.hide();
					}
				}
			}
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[QueryThoughtField] ::Exception in handleOnKeyUpEvent method :"+e);
		}
	}
	
	/**
	 * Method handles key down event.
	 * @param event
	 */
	@Override
	public void handleOnKeyDownEvent(Event event) {
		try {
			logger.log(Level.INFO, "[QueryThoughtField] ::" +	"In handleOnKeyDownEvent method ");
			int keyCode = event.getKeyCode();
			String elementValue = this.getText();
			caretPosition = IntelliThoughtUtil.getCaretPosition(getBaseFieldId());
			String textTillCaretPosition = elementValue.substring(0, caretPosition);
			String wordBeingTyped = IntelliThoughtUtil.getWordBeingTyped(textTillCaretPosition, caretPosition);
			
			if(keyCode == KeyCodes.KEY_DOWN || keyCode == KeyCodes.KEY_UP){
				if(linkedSuggestion.isShowing()){
					event.preventDefault();
					linkedSuggestion.doSelection(keyCode);
				}
				
				return;
			}
			if(keyCode == KeyCodes.KEY_ENTER){
				/** This needs to be removed and handling of enter needs to be fixed**/
				event.preventDefault();
				if(linkedSuggestion.isShowing()){
					IntelliThoughtSuggestion suggestion = linkedSuggestion.getCurrentSelection();
					Entity selectedEnt = suggestion.getEntity();
					levelVsEntityMap.put(CURRENT_SUGGESTION_LEVEL, selectedEnt);
					
					collectSelectedSuggestion(selectedEnt);

					linkSuggestion(suggestion.getDisplayText());
					linkedSuggestion.hide();
				}
				return;
			}
			if(!elementValue.trim().equals("") && (keyCode == 32)) {
				if(linkedSuggestion.isShowing()){
					linkedSuggestion.hide();
				}
				if(!wordBeingTyped.trim().equals("")) {
					if(isFireWordEnteredEvent()){
						handleWordEnteredEvent(wordBeingTyped);
					}
				}
				return;
			}
			if(keyCode == KeyCodes.KEY_BACKSPACE){
				int start = textTillCaretPosition.trim().lastIndexOf(" ") + 1;
				if(start<textTillCaretPosition.trim().lastIndexOf(".")+1){
					start = textTillCaretPosition.trim().lastIndexOf(".") + 1;
				}
				String nodeType = IntelliThoughtUtil.getNodeType(intelliText, start, caretPosition);
				if(nodeType.equalsIgnoreCase("a")){
					IntelliThoughtUtil.setCaretPosition(intelliText, start+1, caretPosition, true);
				}
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[QueryThoughtField] ::Exception in handleOnKeyDownEvent method :"+e);
		}
		
	}
	
	private void handleWordEnteredEvent(String word) {
		try {
			if(word.equalsIgnoreCase(FROM) || word.equalsIgnoreCase(SELECT)){
				showSuggestionsForLevel(FIRST);
			}else if(word.equalsIgnoreCase(WHERE)){
				showSuggestionsForLevel(SECOND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "[QueryThoughtField] ::Exception in handleWordEnteredEvent method :"+e);
		}
	}
	
	@Override
	public Object getValue() {
		try {
			return getText();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Entity getServiceEntity() {
		Entity serviceEnt = null;
		try {
			logger.log(Level.INFO, "[QueryThoughtField] ::In getServiceEntity method ");
			if (getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_MAXCHARLEN) != null) {
				serviceEnt = (Entity) getConfigurationValue(QueryThoughtFieldConstant.QRYTHOUGHT_SERVICE);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[QueryThoughtField] ::Exception in getServiceEntity method :"+e);
		}
		return serviceEnt;
	}
	
	private Entity getSchemaEntity() {
		Entity schemaEnt = null;
		try {
			logger.log(Level.INFO, "[QueryThoughtField] ::In getSchemaEntity method ");
			if (getConfigurationValue(IntelliThoughtFieldConstant.INTLTHT_MAXCHARLEN) != null) {
				schemaEnt = (Entity) getConfigurationValue(QueryThoughtFieldConstant.QRYTHOUGHT_SCHEMA);
			}
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[QueryThoughtField] ::Exception in getSchemaEntity method :"+e);
		}
		return schemaEnt;
	}
	
	@SuppressWarnings("unchecked")
	private void showSuggestionsForLevel(int level) {
		try {
			
			CURRENT_SUGGESTION_LEVEL = level;
			Long id = 0L;
			Entity parentEntity = levelVsEntityMap.get(level-1);
			
			if(parentEntity!=null){
				id = ((Key<Long>)parentEntity.getPropertyByName("id")).getKeyValue();
			}
			/*if (level == FIRST) {
				getSuggestionForLevel("ems.SchemaDefinitionManager.getAllServiceList","getAllServicesNames", null, null, "name");
			} else if (level == FIRST) {
				id = ((Key<Long>)getServiceEntity().getPropertyByName("id")).getKeyValue();
				getSuggestionForLevel("ems.SchemaDefinitionManager.getEntityListForQueryEditor", "getAllSchemasForService", "serviceId", id, "name");
			} */
			if (level == FIRST) {
				id = ((Key<Long>)getSchemaEntity().getPropertyByName("id")).getKeyValue();
				getSuggestionForLevel("ems.SchemaDefinitionManager.getEntityListForQueryEditor","getAllEntityDefForSchema", "schemaId", id, "name");
			} else if (level == SECOND) {
				getSuggestionForLevel("ems.SchemaDefinitionManager.getEntityListForQueryEditor","getAllPropertyDefForEntityDef", "entityId", id, "name");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "[QueryThoughtField] ::Exception in showSuggestionsForLevel method :"+e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getSuggestionForLevel(String opName, String queryName,String paramName, Long paramValue, final String entityPropToDisplay) {

		final DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
		final DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);

		try {
			logger.log(Level.INFO, "[QueryThoughtField] ::In handleThreeCharEnteredEvent method ");
			
			Query queryToBeExecute = new Query();
			queryToBeExecute.setQueryName(queryName);
			queryToBeExecute.setListSize(50);
				
			if(paramName != null){
				HashMap<String, Object> queryParam = new HashMap<String, Object>();
				queryParam.put(paramName, paramValue);
				queryToBeExecute.setQueryParameterMap( queryParam);
			}
			
			Map map = new HashMap();
			map.put("query", queryToBeExecute);
				
			final int posy = basePanel.getElement().getAbsoluteTop() + basePanel.getElement().getOffsetHeight();
			final int posx = basePanel.getElement().getAbsoluteLeft();

			linkedSuggestion.createUi();
			
			
			//This is the Server Call to fetch the suggestions		
			StandardAction action = new StandardAction(EntityList.class, opName, map);
			dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {
				
				public void onFailure(Throwable caught) {
					linkedSuggestion.hide();
					caught.printStackTrace();
				}
				
				public void onSuccess(Result<EntityList> result) {
					EntityList linkedSuggestionList = result.getOperationResult();
					if(linkedSuggestionList != null && !linkedSuggestionList.isEmpty()) {
						
						if(!linkedSuggestion.isShowing()){
							linkedSuggestion.show();
							linkedSuggestion.setWidth(basePanel.getElement().getOffsetWidth() - 10 + "px");

							linkedSuggestion.setPopupPosition(posx, posy);
						}
						
						linkedSuggestion.setEntityList(linkedSuggestionList);
						linkedSuggestion.setEntPropToDisplay(entityPropToDisplay);
						linkedSuggestion.populateSuggestions();
						linkedSuggestion.setFirstSelection();
					} else {
						linkedSuggestion.hide();
					}
				}
			});
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[QueryThoughtField] ::Exception in handleThreeCharEnteredEvent method :"+e);
		}
		
	}

	private int getSuggestionLevel(String wordBeingTyped) {
		int currentLevel = 1;
		int index = wordBeingTyped.indexOf(SEPERATOR);
		while (index >= 0) {
			currentLevel++;
			index = wordBeingTyped.indexOf(SEPERATOR, index + 1);
		}
		System.out.println(currentLevel);
		return currentLevel;
	}
	
	@Override
	protected void linkSuggestion(String text) {
		try {
			logger.log(Level.INFO, "[QueryThoughtField] ::In linkSuggestion method ");
			String elementValue = this.getText();
			String textTillCaretPosition = elementValue.substring(0, caretPosition);
			
			if(linkedSuggestion.isShowing()){
				Anchor tag = new Anchor(text);
				tag.setStylePrimaryName(getLinkedSuggestionPrimaryCss());
				String tagHtml = tag.getElement().getString();
				
				int start = IntelliThoughtUtil.checkPreviousWord(textTillCaretPosition, text);
				
				if(start == -1){
					start = textTillCaretPosition.lastIndexOf(" ")+1;
					if(textTillCaretPosition.lastIndexOf(" ")  < textTillCaretPosition.trim().lastIndexOf(".") ){
						start = textTillCaretPosition.trim().lastIndexOf(".") + 1;
					}
				}
				if(start != caretPosition){
					IntelliThoughtUtil.setCaretPosition(intelliText, start, caretPosition, false);
				}
				
				IntelliThoughtUtil.insertNodeAtCaret(tagHtml); 
				}
			linkedSuggestion.hide();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[QueryThoughtField] ::Exception in linkSuggestion method :"+e);
		}
	}
	

	public interface QueryThoughtFieldConstant  extends IntelliThoughtFieldConstant{
		
		public static final String QRYTHOUGHT_SERVICE = "service";
		
		public static final String QRYTHOUGHT_SCHEMA = "schema";
	}

}
