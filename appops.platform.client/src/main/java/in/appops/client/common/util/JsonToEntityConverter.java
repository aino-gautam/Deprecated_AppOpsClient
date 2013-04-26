/**
 * 
 */
package in.appops.client.common.util;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.GeoLocation;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.MetaType;
import in.appops.platform.core.entity.type.Type;
import in.appops.platform.core.operation.InitiateActionContext;
import in.appops.platform.core.operation.IntelliThought;
import in.appops.platform.core.util.EntityList;

import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;


/**
 * @author mahesh@ensarm.com
 * 
 * This will converted the json object into entitylist
 *
 */
public class JsonToEntityConverter {

	Logger logger = Logger.getLogger("JsonToEntityConverter");
	
	public EntityList getConvertedJsonToEntityList(String jsonObjectStr){
		EntityList entityList = null;
		try {
			entityList = new EntityList();
			JSONValue jsonVal = JSONParser.parseLenient(jsonObjectStr);
			
			JSONObject jsonObj = new JSONObject(jsonVal.isObject().getJavaScriptObject());
			for (String strId : jsonObj.keySet()) {
				JSONObject json = (JSONObject) jsonObj.get(strId);
				Entity entity = getConvertedEntity(json);
				entityList.add(entity);
			}
			
		} catch (Exception e) {
			logger.log(Level.SEVERE, "[JsonToEntityConverter] :: [getConvertedJsonToEntity] :: Exception", e);
		}
		return entityList;
	}

	public Entity getConvertedEntity(JSONObject json) {
		Entity entity = null;
		try{
			String mainKey = json.keySet().iterator().next();
			JSONObject childJson = (JSONObject) json.get(mainKey).isObject();
			String[] splitter = mainKey.split("##");
			
			String mainType = splitter[0];
		//	mainType = mainType.replace(".", "#");
		//	String[] typeSplitter = mainType.split("#");
			
			//String typeName = typeSplitter[typeSplitter.length-1];
			
			if(mainType.contains("ActionContext")){
				entity = new InitiateActionContext();
			} else {
				entity = new Entity();
			}
			
			Type type = new  MetaType(mainType);
			
			entity.setType(type);
			
			for(String propName : childJson.keySet()){
				JSONValue propJsonValue = childJson.get(propName);
				JSONObject propJsonObject = null;
				JSONArray propJsonArray = null;
				if((propJsonObject = propJsonValue.isObject()) != null){
					entity = addProperty(propJsonObject, propName, entity);
				} else if((propJsonArray = propJsonValue.isArray()) != null){
					entity = addProperty(propJsonArray, propName, entity);
				}
				
				
			}
			
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "[JsonToEntityConverter] :: [getConvertedEntity] :: Exception", e);
		}
		return entity;
	}

	private Entity addProperty(JSONObject propValueJson, String propName,Entity entity) {
		try{
			String primitiveTypeName = propValueJson.keySet().iterator().next();
			if(!primitiveTypeName.contains("##")){
				if(primitiveTypeName.equals("Date")){
					Property<Date> dateProp = new Property<Date>();
					dateProp.setName(propName);
					String valStr = propValueJson.get(primitiveTypeName).toString();
					valStr = valStr.replace("\"", "");
					Long val = Long.parseLong(valStr);
					Date date = new Date(val);
					dateProp.setValue(date);
					entity.setProperty(propName, dateProp);
				}
				else if (primitiveTypeName.equals("Key")) {
					JSONObject actulalValJson = (JSONObject) propValueJson.get(primitiveTypeName);
					String valueType  = actulalValJson.keySet().iterator().next();
					String valueStr = actulalValJson.get(valueType).toString();
					valueStr = valueStr.replace("\"", "");
					Long value = Long.parseLong(valueStr);
					Key<Long> key = new Key<Long>(value);
					Property<Key<Long>> keyProperty = new Property<Key<Long>>(key);
					entity.setProperty(propName, keyProperty);
				}
				else if(primitiveTypeName.equals("Double")){
					Property<Double> doubleProp = new Property<Double>();
					doubleProp.setName(propName);
					String valStr = propValueJson.get(primitiveTypeName).toString();
					valStr = valStr.replace("\"", "");
					Double val = Double.parseDouble(valStr);
					doubleProp.setValue(val);
					entity.setProperty(propName, doubleProp);
				}
				else if(primitiveTypeName.equals("String")){
					Property<String> stringProp = new Property<String>();
					stringProp.setName(propName);
					String val = propValueJson.get(primitiveTypeName).toString();
					val = val.replace("\"", "");
					stringProp.setValue(val);
					entity.setProperty(propName, stringProp);
				}
				else if(primitiveTypeName.equals("Integer")){
					Property<Integer> integerProp = new Property<Integer>();
					integerProp.setName(propName);
					String valStr = propValueJson.get(primitiveTypeName).toString();
					valStr = valStr.replace("\"", "");
					Integer val = Integer.parseInt(valStr);
					integerProp.setValue(val);
					entity.setProperty(propName, integerProp);
				}
				else if(primitiveTypeName.equals("Float")){
					Property<Float> floatProp = new Property<Float>();
					floatProp.setName(propName);
					String val = propValueJson.get(primitiveTypeName).toString();
					val = val.replace("\"", "");
					Float floatValue = Float.parseFloat(val);
					floatProp.setValue(floatValue);
					entity.setProperty(propName, floatProp);
				}
				else if(primitiveTypeName.equals("Boolean")){
					Property<Boolean> booleanProp = new Property<Boolean>();
					booleanProp.setName(propName);
					String valStr = propValueJson.get(primitiveTypeName).toString();
					valStr = valStr.replace("\"", "");
					Boolean val = Boolean.parseBoolean(valStr);
					booleanProp.setValue(val);
					entity.setProperty(propName, booleanProp);
				}
				else if(primitiveTypeName.equals("Byte")){
					Property<Byte> byteProp = new Property<Byte>();
					byteProp.setName(propName);
					String val = propValueJson.get(primitiveTypeName).toString();
					Byte byteVal = new Byte(val);
					byteProp.setValue(byteVal);
					entity.setProperty(propName, byteProp);
				}
				else if(primitiveTypeName.equals("Long")){
					Property<Long> longProp = new Property<Long>();
					longProp.setName(propName);
					String valStr = propValueJson.get(primitiveTypeName).toString();
					valStr = valStr.replace("\"", "");
					Long val = Long.parseLong(valStr);
					longProp.setValue(val);
					entity.setProperty(propName, longProp);
				}
				else if(primitiveTypeName.equals("GeoLocation")){
					
					JSONObject geoLocJson = propValueJson.get(primitiveTypeName).isObject();
					Double lat,lng;
					JSONObject latJson = (JSONObject) geoLocJson.get("latitude");
					JSONObject lngJson = (JSONObject) geoLocJson.get("longitude");
					
					String latStr = latJson.get("Double").toString();
					latStr = latStr.replace("\"", "");
					String lngStr = lngJson.get("Double").toString();
					lngStr = lngStr.replace("\"", "");
					lat = Double.parseDouble(latStr);
					lng = Double.parseDouble(lngStr);
					GeoLocation geoLoc = new GeoLocation();
					geoLoc.setLatitude(lat);
					geoLoc.setLongitude(lng);
					
					Property<GeoLocation> geoProp = new Property<GeoLocation>();
					geoProp.setName(propName);
					geoProp.setValue(geoLoc);
					
					entity.setProperty(propName, geoProp);
				} else if(primitiveTypeName.equals("intelliThought")){
					
					JSONObject geoLocJson = propValueJson.get(primitiveTypeName).isObject();
					Double lat,lng;
					JSONObject intellitextJson = (JSONObject) geoLocJson.get("intellitext");
					JSONObject intellihtmlJson = (JSONObject) geoLocJson.get("intellihtml");
					JSONArray intelliLinkedEntities = (JSONArray) geoLocJson.get("linkedEntities");
					
					String intellitextStr = intellitextJson.get("String").toString();
					String intellihtmlStr = intellihtmlJson.get("String").toString();
					IntelliThought intelliThought = new IntelliThought();
					intelliThought.setIntelliText(intellitextStr);
					intelliThought.setIntelliHtml(intellihtmlStr);
					
					intelliThought.setLinkedEntities((ArrayList<Entity>)decodeJsonArray(intelliLinkedEntities));

					entity.setPropertyByName(propName, intelliThought);
				}
				else{
					Entity childEntity = getConvertedEntity(propValueJson);
					entity.setProperty(propName, childEntity);
				}
			}
			else{
				Entity childEntity = getConvertedEntity(propValueJson);
				entity.setProperty(propName, childEntity);
			}
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "[JsonToEntityConverter] :: [addProperty] :: Exception", e);

		}
		return entity;
	}
	
	private Entity addProperty(JSONArray propJsonArray, String propName,Entity entity) {
		try{
			ArrayList<Object> list = new ArrayList<Object>();
		    for (int i = 0; i < propJsonArray.size(); i++) {
		      JSONValue v = propJsonArray.get(i);
		      if (v.isString() != null) {
		        list.add(v.isString().stringValue());
		      }
		    }

		    entity.setPropertyByName(propName, list);
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "[JsonToEntityConverter] :: [addProperty] :: Exception", e);

		}
		return entity;
	}

	private ArrayList<Entity> decodeJsonArray(JSONArray jsonArray){
		ArrayList<Entity> list = null;
		try{
			list = new ArrayList<Entity>();
			for (int i = 0; i < jsonArray.size(); i++) {
				JSONValue v = jsonArray.get(i);
				if(v.isObject() != null){
					Entity entity =  getConvertedEntity(v.isObject());
					list.add(entity);
				}
			}
			
		}
		catch (Exception e) {
			logger.log(Level.SEVERE, "[JsonToEntityConverter] :: [decodeJsonArray] :: Exception", e);
		}
		return list;
		
	}
	
	public Entity convertjsonStringToEntity(String jsonObjectStr){
		logger.log(Level.INFO,"[JsonToEntityConverter] :: In convertjsonStringToEntity() ");

		Entity convertedEntity = null;
		try {

			JSONValue jsonVal = JSONParser.parseLenient(jsonObjectStr);

			JSONObject jsonObj = new JSONObject(jsonVal.isObject().getJavaScriptObject());

			convertedEntity = getConvertedEntity(jsonObj);
		} catch (Exception e) {
			logger.log(Level.SEVERE,"[JsonToEntityConverter] :: Exception in convertjsonStringToEntity()",e);
		}

		return convertedEntity;
	}

	
}