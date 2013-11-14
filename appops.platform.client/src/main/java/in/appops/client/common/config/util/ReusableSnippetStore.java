package in.appops.client.common.config.util;

import com.google.gwt.i18n.client.Dictionary;

public class ReusableSnippetStore {
	  private static Dictionary snippetStore;
	    
	    public static void loadSnippetDesc() {
	    	snippetStore = Dictionary.getDictionary("snippetStore");
	    }
	 	
		public static String getSnippetDesc(String type) {
			String snippetDesc = snippetStore.get(type);
			return snippetDesc;
		}
}
