package in.appops.client.common.fields.htmleditor.tool.editor.tinymce.client.code;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.TextArea;

public class TinyMCEEditor extends TextArea {

    private String id;

    public TinyMCEEditor() {
        super();
        id = HTMLPanel.createUniqueId();
        DOM.setElementAttribute(getElement(), "id", id);
    }

    /**
     * getID() -
     *
     * @return the MCE element's ID
     */
    public String getID() {
        return id;
    }

    public static native String getEditorContents(
        String elementId) /*-{
        return $wnd.tinyMCE.get(elementId).getContent();
    }-*/;

    public native void setEditorContents(String html) /*-{
       $wnd.tinyMCE.activeEditor.setContent(html);
    }-*/;

    public String getText() {
        return getEditorContents(id);
    }

    public void setEnabled(boolean enabled) {
        setEnabled(enabled);
    }

    /**
     * @see com.google.gwt.user.client.ui.Widget#onLoad()
     */
    @Override
    protected void onLoad() {
        super.onLoad();
        Scheduler.get().scheduleDeferred(new ScheduledCommand() {    
        	  @Override
        	  public void execute() {
      	          setTextAreaToTinyMCE(id);
      			  loadEditor(id);
                  focusMCE(id);
        	  }
    	});
    }
    
    public  static native void loadEditor(String id) /*-{
	 
	 	var conf = {
	    
		  theme : "modern",
	    	skin : "lightgray",
	    	plugins: [
	    	          "advlist autolink lists link image charmap print preview hr anchor pagebreak",
	    	          "searchreplace wordcount visualblocks visualchars code fullscreen",
	    	          "insertdatetime media nonbreaking save table contextmenu directionality",
	    	          "emoticons template paste textcolor"
	    	      ],
	  	      toolbar1: "insertfile undo redo | styleselect fontselect fontsizeselect | bold italic | alignleft aligncenter alignright alignjustify ",
	  	      toolbar2: "bullist numlist outdent indent | link image | print preview media | forecolor backcolor emoticons",
	  	      image_advtab: true,
	    };
	    $wnd.tinyMCE.init(conf);
	}-*/;
    
    
    
    

    /**
     * focusMCE() -
     *
     * Use this to set the focus to the MCE area
     * @param id - the element's ID
     */
    protected native void focusMCE(String id) /*-{
        $wnd.tinyMCE.execCommand('mceFocus', true, id);
    }-*/;

    /**
     * resetMCE() -
     *
     * Use this if reusing the same MCE element, but losing focus
     */
    public native void resetMCE() /*-{
        $wnd.tinyMCE.execCommand('mceResetDesignMode', true);
    }-*/;

    /**
     * unload() -
     *
     * Unload this MCE editor instance from active memory.
     * I use this in the onHide function of the containing widget. This helps
     * to avoid problems, especially when using tabs.
     */
    public void unload() {
        unloadMCE(id);
    }

    /**
     * unloadMCE() -
     *
     * @param id - The element's ID
     * JSNI method to implement unloading the MCE editor instance from memory
     */
    protected native void unloadMCE(String id) /*-{
        $wnd.tinyMCE.execCommand('mceFocus', false, id);
        $wnd.tinyMCE.execCommand('mceRemoveEditor', false, id);
    }-*/;

    /**
     * updateContent() -
     *
     * Update the internal referenced content. Use this if you programatically change
     * the original text area's content (eg. do a clear)
     * @param id - the ID of the text area that contains the content you wish to copy
     */
    protected native void updateContent(String id) /*-{
        $wnd.tinyMCE.activeEditor = $wnd.tinyMCE.get(id);
        $wnd.tinyMCE.activeEditor.setContent($wnd.document.getElementById(id).value);
    }-*/;

    /**
     * getTextArea() -
     *
     */
    protected native void getTextData(String id) /*-{
        $wnd.tinyMCE.activeEditor = $wnd.tinyMCE.get(id);
        $wnd.tinyMCE.activeEditor.save();
        $wnd.tinyMCE.triggerSave();
    }-*/;

    /**
     * encodeURIComponent() -
     *
     * Wrapper for the native URL encoding methods
     * @param text - the text to encode
     * @return the encoded text
     */
    protected native String encodeURIComponent(String text) /*-{
        return encodeURIComponent(text);
    }-*/;

    /**
     * setTextAreaToTinyMCE() -
     *
     * Change a text area to a tiny MCE editing field
     * @param id - the text area's ID
     */
    protected native void setTextAreaToTinyMCE(String id) /*-{
        $wnd.tinyMCE.execCommand('mceAddEditor', true, id);
    }-*/;

    /**
     * removeMCE() -
     *
     * Remove a tiny MCE editing field from a text area
     * @param id - the text area's ID
     */
    public native void removeMCE(String id) /*-{
        $wnd.tinyMCE.execCommand('mceRemoveEditor', true, id);
    }-*/;
    public interface OnChangeListener {
		
		public void onChange();
		
		public void onEvent(NativeEvent event);
	}
}