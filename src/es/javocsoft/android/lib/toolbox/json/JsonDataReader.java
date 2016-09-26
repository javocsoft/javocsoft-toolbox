package es.javocsoft.android.lib.toolbox.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import es.javocsoft.android.lib.toolbox.ToolBox;
import es.javocsoft.android.lib.toolbox.json.exception.JsonDataException;

/**
 * A class that loads and process the JSON information. 
 * Allows to get easily a property of the JSON.
 * 
 * @author JavocSoft 2015
 * @since  2016
 */
public class JsonDataReader {
	
	private JsonObject jsonObject = null;
	private List<Entry<String, JsonElement>> jsonObjectProperties = new ArrayList<Entry<String, JsonElement>>();
	private Gson gsonProcessor = null;
	private String json = null;
	private final static String TAG = "ToolBox->JsonDataReader";
	
	
	/**
	 * Creates a JsonDataReader object that contains the information of the specified JSON string.
	 * It will create an instance of Gson processor, see {@link com.google.gson.Gson}.
	 * 
	 * @param json	The JSON to load.
	 * @throws JsonDataException
	 */
	public JsonDataReader(String json) throws JsonDataException {
		this.json = json;
		this.gsonProcessor = new Gson();
		loadAndProcessJSON();
	}
	
	/**
	 * Creates a JsonDataReader object that contains the information of the specified JSON string.
	 * It gets a JSON GSON processor, {@link es.javocsoft.android.lib.toolbox.json.GsonProcessor} 
	 * , of the desired type.
	 * 
	 * @param gsonprocessorType	See {@link es.javocsoft.android.lib.toolbox.json.GsonProcessor.GSON_PROCESSOR_TYPE} 
	 * @param json The JSON to process.
	 * @throws JsonDataException
	 */
	public JsonDataReader(GsonProcessor.GSON_PROCESSOR_TYPE gsonprocessorType, String json) throws JsonDataException {
		this.json = json;
		this.gsonProcessor = GsonProcessor.getInstance().getGson(gsonprocessorType);
		loadAndProcessJSON();
	}

	/**
	 * Creates a JsonDataReader object that contains the information of the specified JSON string.
	 * It will use to process the data the specified JSON Gson processor, see {@link com.google.gson.Gson}.
	 * 
	 * @param gsonProcessor A valid instance of {@link com.google.gson.Gson}
	 * @param json	The JSON to load.
	 * @throws JsonDataException
	 */
	public JsonDataReader(Gson gsonProcessor, String json) throws JsonDataException {
		this.json = json;
		this.gsonProcessor = gsonProcessor;
		loadAndProcessJSON();
	}
	
	
	/**
	 * Gets the JsonObject for the specified element name. The desired object
	 * could be a valid json object or an object in string format inside the JSON.
	 * 
	 * @param elementName
	 * @return	The JsonObject or null if element does not exists or is not a
	 * 			JsonObject.
	 */
	public JsonObject getJsonObject(String name) throws JsonDataException {
		if(!isNull(name)){			
			try{
				for(Entry<String, JsonElement> entry:jsonObjectProperties){
					if(entry.getKey().equals(name)){ //Found the element name in the list
						if(entry.getValue() instanceof JsonObject) {
							//is an object
							return entry.getValue().getAsJsonObject();					
						}else{
							//not an object
							if (entry.getValue().isJsonPrimitive()) {
								//The object data could be in a string encoded format
								if (entry.getValue().getAsJsonPrimitive().isString()){
									//We try to get the object data
									String jsonStrE = entry.getValue().getAsString();
									JsonElement e = new Gson().fromJson (jsonStrE, JsonElement.class);
									if(e instanceof JsonObject){
										//Is an object in a string format
										return e.getAsJsonObject();
									}
								}
							}
						}					
					}
				}				
			}catch(Exception e){
				if(ToolBox.LOG_ENABLE)
					Log.e(TAG, "Json Object data could not be get for property: '" + name + "' (" + e.getMessage() + ")",e);
				throw new JsonDataException("JsonObject data could not be get for property: '" + name + "' (" + e.getMessage() + ")", e);
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the JsonElement for the specified element name. 
	 * 
	 * @param elementName
	 * @return
	 */
	public JsonElement getJsonElement(String name) throws JsonDataException {
		if(!isNull(name)){
			try{
				for(Entry<String, JsonElement> entry:jsonObjectProperties){
					if(entry.getKey().equals(name)){ //Found the element name in the list
						return entry.getValue();
					}
				}
			}catch(Exception e){
				if(ToolBox.LOG_ENABLE)
					Log.e(TAG, "Json Element data could not be get for property: '" + name + "' (" + e.getMessage() + ")",e);
				throw new JsonDataException("JsonElement data could not be get for property: '" + name + "' (" + e.getMessage() + ")", e);
			}
		}
		
		return null;						
	}	
	
	/**
	 * Get the JsonArray from the JSON data and converts to List.
	 * 
	 * @param name	The name of the JsonElement that contains the JsonArray.
	 * @param itemClass The object type (class) of the list.
	 * @return A list of item objects.
	 */
	public List<?> getListFromJsonArrayElement(String name, Object itemClass) throws JsonDataException {
		List<?> res = null;
		
		JsonElement e = getJsonElement(name);
		if(e!=null){
			Type listType = new TypeToken<List<?>>() {}.getType();
			res = new Gson().fromJson(e, listType);
		}
		
		return res;
	}
	
	/**
	 * Gets the list of all properties of the JSON.
	 * 
	 * @return
	 */
	public List<Entry<String, JsonElement>> getJsonProperties(){
		return jsonObjectProperties;
	}
	
	
	
	
	
	//AUXILIAR
	
	private void loadAndProcessJSON() throws JsonDataException {
		try{						
			jsonObject = gsonProcessor.fromJson(json, JsonObject.class);
			listProperties();			
		}catch(JsonSyntaxException e){
			if(ToolBox.LOG_ENABLE)
				Log.e(TAG, "Error parsing JSON string (" + e.getMessage() + ")", e);
			throw new JsonDataException("Error parsing JSON string (" + e.getMessage() + ")", e);
		}catch(Exception e){
			if(ToolBox.LOG_ENABLE)
				Log.e(TAG, "JSON data could not be parsed/processed (" + e.getMessage() + ").",e);
			throw new JsonDataException("JSON data could not be parsed/processed (" + e.getMessage() + ").", e);
		}
	}
	
	/**
	 * Gets the list of properties of the JSON object in a list format.
	 * 
	 */
	private void listProperties() throws Exception {
		
		try{
			Set<Entry<String, JsonElement>> properties = jsonObject.entrySet();
			Iterator<Entry<String, JsonElement>> it = properties.iterator();
			while( it.hasNext() ){
	        	lookIntoEntry(it.next(), jsonObjectProperties);        	
	        }
		}catch(Exception e){
			throw new Exception("Error getting JSON object properties list (" + e.getMessage() + "). JSON String: " + json, e);
		}
	}
	
	/**
	 * This is a recursive function that looks into a entry JsonElement adding it to the
	 * list of entries and looking in it for any JsonElement child that is a JsonObject.
	 *  
	 * @param entry	An entry of the JSON.
	 * @param entries	The list of entries that the JSON has.
	 */
	private void lookIntoEntry(Entry<String, JsonElement> entry, List<Entry<String, JsonElement>> entries) throws Exception {
		//Add the entry
		entries.add(entry);
		
		//Recursive
		if(entry.getValue() instanceof JsonObject) {
			Set<Entry<String, JsonElement>> properties = entry.getValue().getAsJsonObject().entrySet();
			Iterator<Entry<String, JsonElement>> it = properties.iterator();
			while( it.hasNext() ){
				lookIntoEntry(it.next(), entries);
			}
		}
	}
	
	/**
	 * Checks if the element name exists or not in the property list
	 * of the JSON.
	 * 
	 * @param elementName
	 * @return
	 */
	private boolean isNull(String elementName) {
		
		for(Entry<String, JsonElement> entry:jsonObjectProperties){
			if(entry.getKey().equals(elementName) && !(entry.getValue() instanceof JsonNull)){ //Found the element name in the list
				return false;
			}
		}
		
		return true;
	}
	
}
