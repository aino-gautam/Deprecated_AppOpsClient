package in.appops.client.common.config.field.querythought;

import in.appops.client.common.config.field.intellithought.IntelliThoughtField;
import in.appops.client.common.config.field.intellithought.IntelliThoughtSuggestion;
import in.appops.client.common.config.field.intellithought.IntelliThoughtUtil;
import in.appops.client.common.event.FieldEvent;
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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;


public class QueryThoughtField extends IntelliThoughtField implements FieldEventHandler{
	
	private Logger logger = Logger.getLogger(getClass().getName());
	private HashMap<Integer,Entity> levelVsEntityMap = null;
	private HashMap<String,EntityList> tableVsPropMap = null;
	private EntityList entityDefList = null;
	
	/** Levels used in query editor **/
	private final int FIRST = 1;
	private final int SECOND = 2;
		
	private int CURRENT_SUGGESTION_LEVEL = 1;
	
	private final String SEPERATOR = ".";
	
	/** Keywords used in query editor **/
	private final String FROM = "from";
	
	private final String SELECT = "select";
	
	/** Clause used in query editor **/
	private final String WHERE = "where";
	
	
	public QueryThoughtField() {
		super();
		
		entityDefList = new EntityList();
		tableVsPropMap = new HashMap<String, EntityList>();
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
				String entityName = getEntityNameFromTypedWord(suggestionLevel, wordBeingTyped);
				showSuggestionsForLevel(suggestionLevel,entityName);
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
			
			if(keyCode == 32 && event.getCtrlKey()){
				showSuggestionsForLevel(FIRST,null);
				return;
			}
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
						handleWordEnteredEvent(wordBeingTyped,textTillCaretPosition);
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
	
	
	private void handleWordEnteredEvent(String word , String texttillCaret) {
		try {
			if(word.equalsIgnoreCase(FROM)  || word.equalsIgnoreCase(SELECT) || word.equalsIgnoreCase("("+ SELECT)){
				showSuggestionsForLevel(FIRST,"");
			}else if(word.equalsIgnoreCase(WHERE) ){
				String entityName = getlastEntityTyped( texttillCaret);
				showSuggestionsForLevel(SECOND, entityName);
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
	private void showSuggestionsForLevel(int level,String entityName) {
		try {
			
			CURRENT_SUGGESTION_LEVEL = level;
			Long id = 0L;
			Entity parentEntity = levelVsEntityMap.get(level-1);
			
			if(parentEntity!=null){
				id = ((Key<Long>)parentEntity.getPropertyByName("id")).getKeyValue();
			}
			
			if (level == FIRST) {
				if(entityDefList!=null && !entityDefList.isEmpty()){
					showSuggestions(entityDefList,"name");
				}else{
					id = ((Key<Long>)getSchemaEntity().getPropertyByName("id")).getKeyValue();
					getSuggestionForLevel("ems.SchemaDefinitionManager.getEntityListForQueryEditor","getAllEntityDefForSchema", "schemaId", id, "name", entityName);
				}
			} else if (level == SECOND) {
				if(entityDefList.isEmpty()){
					id = ((Key<Long>)getSchemaEntity().getPropertyByName("id")).getKeyValue();
					getSuggestionForLevel("ems.SchemaDefinitionManager.getEntityListForQueryEditor","getAllEntityDefForSchema", "schemaId", id, "name", entityName);
				}else if(tableVsPropMap!=null && tableVsPropMap.get(entityName)!=null){
					showSuggestions(tableVsPropMap.get(entityName),"name");
				}else{
					getSuggestionForLevel("ems.SchemaDefinitionManager.getEntityListForQueryEditor","getAllPropertyDefForEntityDef", "entityId", id, "name",entityName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "[QueryThoughtField] ::Exception in showSuggestionsForLevel method :"+e);
		}
	}
	
	private void showSuggestions(EntityList linkedSuggestionList, String propertyToDisplay){
		try {
			String coordinates = IntelliThoughtUtil.getSelectionCoords();
			String [] cordin = coordinates.split("#");
						
			final int posx = Integer.parseInt(cordin[0]);
			final int posy = Integer.parseInt(cordin[1])+19;

			linkedSuggestion.createUi();
			
			if(linkedSuggestionList != null && !linkedSuggestionList.isEmpty()) {
				
				if(!linkedSuggestion.isShowing()){
					linkedSuggestion.show();
					linkedSuggestion.setPopupPosition(posx, posy);
				}
				
				linkedSuggestion.setEntityList(linkedSuggestionList);
				linkedSuggestion.setEntPropToDisplay(propertyToDisplay);
				linkedSuggestion.populateSuggestions();
				linkedSuggestion.setFirstSelection();
			} else {
				linkedSuggestion.hide();
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void getSuggestionForLevel(String opName, String queryName,String paramName, final Long paramValue, final String entityPropToDisplay, final String entityName) {

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
				
			String coordinates = IntelliThoughtUtil.getSelectionCoords();
			String [] cordin = coordinates.split("#");
						
			final int posx = Integer.parseInt(cordin[0]);
			final int posy = Integer.parseInt(cordin[1])+19;

			linkedSuggestion.createUi();
			
			//This is the Server Call to fetch the suggestions		
			StandardAction action = new StandardAction(EntityList.class, opName, map);
			dispatch.execute(action, new AsyncCallback<Result<EntityList>>() {
				
				public void onFailure(Throwable caught) {
					linkedSuggestion.hide();
					caught.printStackTrace();
				}
				
				public void onSuccess(Result<EntityList> result) {
					Boolean showSuggestion = true;
					EntityList linkedSuggestionList = result.getOperationResult();
					if(CURRENT_SUGGESTION_LEVEL==1){
						entityDefList = linkedSuggestionList;
					}else if(CURRENT_SUGGESTION_LEVEL==2 && entityDefList.isEmpty()){
						entityDefList = linkedSuggestionList;
						showSuggestion = false;
						Long entityId = getEntityIdFromName(entityName);
						getSuggestionForLevel("ems.SchemaDefinitionManager.getEntityListForQueryEditor","getAllPropertyDefForEntityDef", "entityId", entityId, "name",entityName);
						
					}else{
						String entityName= getEntityName(paramValue);
						tableVsPropMap.put(entityName, linkedSuggestionList);
					}
					
					if (showSuggestion) {
						if (linkedSuggestionList != null && !linkedSuggestionList.isEmpty()) {

							if (!linkedSuggestion.isShowing()) {
								linkedSuggestion.show();
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
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(Level.SEVERE, "[QueryThoughtField] ::Exception in handleThreeCharEnteredEvent method :"+e);
		}
		
	}
	
	private String getEntityName(Long parentId){
		
		try {
			for (int i = 0; i < entityDefList.size(); i++) {
				Entity entityDef = entityDefList.get(i);
				Long tableId = ((Key<Long>) entityDef.getPropertyByName("id")).getKeyValue();
				if (parentId == tableId) {
					return entityDef.getPropertyByName("name");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private Long getEntityIdFromName(String name){
		try {
			for (int i = 0; i < entityDefList.size(); i++) {
				Entity entityDef = entityDefList.get(i);
				String tableName =  entityDef.getPropertyByName("name");
				if (tableName .equals(name)) {
					return ((Key<Long>)entityDef.getPropertyByName("id")).getKeyValue();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	
	private String getEntityNameFromTypedWord(int currentLevel, String wordTyped) {
		String[] words = wordTyped.split("\\.");
		return words[currentLevel-2];
		
	}
	
	private String getlastEntityTyped(String texttillcaret) {
		
		try {
			String word = texttillcaret.substring(texttillcaret.lastIndexOf("from"), caretPosition);
			String[] words = word.split("\\s+");
			return words[1];
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
	
	/**
	 * Removes the invalid markers
	 */
	@Override
	public void clearInvalidMarkers() {
		try {
			logger.log(Level.INFO,"[QueryThoughtFieldQueryThoughtField]:: In clearInvalidMarkers  method ");
			super.clearInvalidMarkers();
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[QueryThoughtFieldQueryThoughtField]::Exception In clearInvalidMarkers  method :"+e);
		}
	}
	
	/**
	 * Event received are handled here.
	 */
	@Override
	public void onFieldEvent(FieldEvent event) {
		int eventType = event.getEventType();
				
		if (this.isVisible()) {
			if (eventType == FieldEvent.SUGGESTION_CLICKED) {
				if (linkedSuggestion.isShowing()) {
					IntelliThoughtSuggestion suggestion = linkedSuggestion.getCurrentSelection();
					Entity selectedEnt = suggestion.getEntity();
					levelVsEntityMap.put(CURRENT_SUGGESTION_LEVEL, selectedEnt);

					collectSelectedSuggestion(selectedEnt);
					
					linkSuggestionAfterSelection(suggestion.getDisplayText());
				}
			}
		}
	}
	
	protected void linkSuggestionAfterSelection(String text) {
		try {
			logger.log(Level.INFO,"[QueryThoughtField] ::In linkSuggestion method ");
			String elementValue = this.getText();
						
			String textTillCaretPosition = elementValue.substring(0, caretPosition);
			
			if(linkedSuggestion.isShowing()){
			Anchor tag = new Anchor(text);
			tag.setStylePrimaryName(getLinkedSuggestionPrimaryCss());
			String tagHtml = tag.getElement().getString();

			int start = IntelliThoughtUtil.checkPreviousWord(textTillCaretPosition, text);

			if (start == -1) {
				start = textTillCaretPosition.lastIndexOf(" ") + 1;
				if (textTillCaretPosition.lastIndexOf(" ") < textTillCaretPosition.trim().lastIndexOf(".")) {
					start = textTillCaretPosition.trim().lastIndexOf(".") + 1;
				}
			}
			
			System.out.println("caret === "+ IntelliThoughtUtil.getCaretPosition(getBaseFieldId()));
			try {
				IntelliThoughtUtil.setCaretPosition(intelliText, start,	caretPosition, false);
			} catch (Exception e) {
				intelliText.setInnerHTML(intelliText.getInnerHTML().replace("&nbsp;", "&nbsp;&nbsp;"));
				IntelliThoughtUtil.setCaretPosition(intelliText, start,	caretPosition, false);
			}
			
			
			IntelliThoughtUtil.insertNodeAtCaret(tagHtml);
			}
			linkedSuggestion.hide();

		} catch (Exception e) {
			logger.log(Level.SEVERE,"[QueryThoughtField] ::Exception in linkSuggestionAfterSelection method :"+ e);
			
		}
	}

	

	public interface QueryThoughtFieldConstant  extends IntelliThoughtFieldConstant{
		
		public static final String QRYTHOUGHT_SERVICE = "service";
		
		public static final String QRYTHOUGHT_SCHEMA = "schema";
	}
}
