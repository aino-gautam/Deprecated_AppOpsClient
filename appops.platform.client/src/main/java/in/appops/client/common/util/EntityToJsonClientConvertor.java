package in.appops.client.common.util;

import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.GeoLocation;
import in.appops.platform.core.entity.Key;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.entity.type.Type;
import in.appops.platform.core.operation.IntelliThought;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;


public class EntityToJsonClientConvertor {
	
	// Need to clean up the code. Would review it later.
	
	protected static <T> JSONArray encodeList(ArrayList<T> data) {
		JSONArray jsona = new JSONArray();
		for (int i = 0; i < data.size(); i++) {
			Object val = data.get(i);
			if(val instanceof String || val instanceof Integer || val instanceof Long) {
				jsona.set(i, new JSONString(val.toString()));
			} else if(val instanceof Entity){
				jsona.set(i, createJsonFromEntity((Entity)val));
			} 
		}
		return jsona;
	}
	
	 protected static JSONObject encodeMap(HashMap<String, Object> data) {
		    JSONObject jsobj = new JSONObject();
		    for (String key : data.keySet()) {
		    	Object val = data.get(key);
		      if (val instanceof String) {
		        jsobj.put(key, new JSONString(val.toString()));
		      } else if(val instanceof Entity){
		    	  JSONObject entJsonObj = createJsonFromEntity((Entity)val);
		    	  jsobj.put(key, entJsonObj);
		      } else if(val  instanceof ArrayList) {
		    	  ArrayList arrayList = (ArrayList)val;
		    	  JSONArray jsonArray = encodeList(arrayList);
		    	  jsobj.put(key, jsonArray);
		      }
		    }
		    return jsobj;
	  }
	
	
	public static JSONObject createJsonFromEntity(Entity entity) {
		JSONObject mainJson = null;
		JSONObject childJson=null;
		try{
			childJson = new JSONObject();
			HashMap<String, Property<? extends Serializable>> propMap = entity.getValue();
			for(String propName : propMap.keySet()){

				Property prop = entity.getProperty(propName);
				if(prop instanceof Entity){
					Entity childEnt = (Entity) prop;
					JSONObject subChildJson = createJsonFromEntity(childEnt);
					childJson.put(propName, subChildJson);
				}
				else{
					Object value = prop.getValue();
					if(value instanceof Key){

						value = ((Key) value).getKeyValue();
						JSONString num = new JSONString(value.toString());

						JSONObject actualJsonVal= new JSONObject();
						actualJsonVal.put("Long", num);

						JSONObject keyJson = new JSONObject();
						keyJson.put("Key", actualJsonVal);

						childJson.put(propName, keyJson);
					}
					else if(value instanceof GeoLocation){
						GeoLocation geoLoc = (GeoLocation) value;
						Double latitude = geoLoc.getLatitude();
						Double longitude = geoLoc.getLongitude();
						
						JSONString lat = new JSONString(latitude.toString());
						JSONString longi = new JSONString(longitude.toString());

						JSONObject actualLatJson = new JSONObject();
						actualLatJson.put("Double", lat);

						JSONObject actualLngJson = new JSONObject();
						actualLngJson.put("Double", longi);

						JSONObject geoJson = new JSONObject();
						geoJson.put("latitude", actualLatJson);
						geoJson.put("longitude", actualLngJson);

						JSONObject geoLocJson = new JSONObject();
						geoLocJson.put("GeoLocation", geoJson);
						
						childJson.put(propName, geoLocJson);
					}
					else if(value instanceof Integer){
						JSONString num = new JSONString(value.toString());
						JSONObject intJson = new JSONObject();
						intJson.put("Integer", num);

						childJson.put(propName, intJson);
					}
					else if(value instanceof Double){
						JSONString num = new JSONString(value.toString());

						JSONObject doubleJson = new JSONObject();
						doubleJson.put("Double", num);

						childJson.put(propName, doubleJson);
					}
					else if(value instanceof Long){
						JSONString num = new JSONString(value.toString());

						JSONObject longJson = new JSONObject();
						longJson.put("Long", num);

						childJson.put(propName, longJson);
					}
					else if(value instanceof Float){
						JSONString num = new JSONString(value.toString());

						JSONObject floatJson = new JSONObject();
						floatJson.put("Float", num);

						childJson.put(propName, floatJson);
					}
					else if(value instanceof Date || value instanceof Timestamp || value instanceof java.sql.Date){
						Long time = 0L;
						JSONObject dateJson = new JSONObject();
						JSONValue timeval = JSONParser.parseStrict(time.toString());

						if(value instanceof Date){
							time = ((Date)value).getTime();
							dateJson.put("Date", timeval);
						}
						else if(value instanceof Timestamp){
							time = ((Timestamp)value).getTime();
							dateJson.put("Timestamp", timeval);
						}
						else if(value instanceof java.sql.Date){
							time = ((java.sql.Date)value).getTime();
							dateJson.put("java.sql.Date", timeval);
						}
						childJson.put(propName, dateJson);
					}
					else if(value instanceof String){
						JSONString num = new JSONString(value.toString());

						JSONObject strJson = new JSONObject();
						strJson.put("String", num);

						childJson.put(propName, strJson);
					}
					else if(value instanceof Boolean){
						JSONString num = new JSONString(value.toString());

						JSONObject booleanJson = new JSONObject();
						booleanJson.put("Boolean", num);

						childJson.put(propName, booleanJson);
					}
					else if(value instanceof Byte){
						JSONString num = new JSONString(value.toString());

						JSONObject byteJson = new JSONObject();
						byteJson.put("Byte", num);

						childJson.put(propName, byteJson);
					} else if(value instanceof IntelliThought){
						IntelliThought intelliThought = (IntelliThought)value;
						
						JSONString text = new JSONString(intelliThought.getIntelliText());
						JSONString html = new JSONString(intelliThought.getIntelliHtml());

						JSONObject textJson = new JSONObject();
						textJson.put("String", text);

						JSONObject htmlJson = new JSONObject();
						htmlJson.put("String", html);


						JSONObject intelliThoughtJson = new JSONObject();
						intelliThoughtJson.put("intellitext", textJson);
						intelliThoughtJson.put("intellihtml", htmlJson);
						
						ArrayList<Entity> linkedEntities = intelliThought.getLinkedEntities();
						ArrayList<Entity> linkedSpaces = intelliThought.getLinkedSpaces();
						ArrayList<Entity> linkedUsers = intelliThought.getLinkedUsers();
						
						if(linkedEntities != null){
							JSONArray ja = encodeList(linkedEntities);
							intelliThoughtJson.put("linkedEntities", ja);
						} 
						
						
						
						JSONObject j = new JSONObject();
						j.put("intelliThought", intelliThoughtJson);

						childJson.put("intelliThought", j);
					} else if(value instanceof ArrayList) {
						JSONArray ja = encodeList((ArrayList<Object>)value);
						
						JSONObject listJson = new JSONObject();
						listJson.put("arrayList", ja);
						
						childJson.put(propName, listJson);
					} else if(value instanceof HashMap) {
						JSONObject jsonMapVal = encodeMap((HashMap<String, Object>)value);
						
						JSONObject mapJson = new JSONObject();
						mapJson.put("map", jsonMapVal);
						
						childJson.put(propName, mapJson);
					} 
				}
			}
			
//			Key<Long> value = entity.getPropertyByName("id");
//			String val =  value.getKeyValue().toString();

			Type type = entity.getType();

			String key = type.getTypeName();
            mainJson = new JSONObject();
            mainJson.put(key, childJson);

		}
		catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}
		return mainJson;
	}
	
}
