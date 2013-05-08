package in.appops.client.common.fields;

import com.google.gwt.user.client.Element;

public class IntelliThoughtUtil {
	
	/**
	 * This will return the word latest word being typed. The word is from index of last space encountered
	 * in the text till the current caret position till the caret  position 
	 * @param textTillCaretPosition
	 * @param caretPosition
	 * @return
	 */
	public static String getWordBeingTyped(String textTillCaretPosition, int caretPosition){
		String word = null;
		word = textTillCaretPosition.substring(textTillCaretPosition.lastIndexOf(" ") + 1, caretPosition);
		return word;
	}
	
	public static String getWordBeingTypedForContextField(String textTillCaretPosition, int caretPosition){
		String word = null;
		word = textTillCaretPosition.substring(textTillCaretPosition.lastIndexOf(",") + 1, caretPosition);
		word = word.trim();
		return word;
	}
	
	public static String getTextTillCaretPosition(String elementValue, int caretPosition){
		return elementValue.substring(0, caretPosition);
	}
	
	/**
	 * This returns the current caret positon in the element.
	 * @param id - The element id;
	 * @return
	 */
	public static native char getCaretPosition(String id)/*-{
		var element = $doc.getElementById(id);
	    var caretOffset = 0;
	    if (typeof $wnd.getSelection != "undefined") {
	        var range = $wnd.getSelection().getRangeAt(0);
	        var preCaretRange = range.cloneRange();
	        preCaretRange.selectNodeContents(element);
	        preCaretRange.setEnd(range.endContainer, range.endOffset);
	        caretOffset = preCaretRange.toString().length;
	    } else if (typeof $doc.selection != "undefined" && $doc.selection.type != "Control") {
	        var textRange = $doc.selection.createRange();
	        var preCaretTextRange = $doc.body.createTextRange();
	        preCaretTextRange.moveToElementText(element);
	        preCaretTextRange.setEndPoint("EndToEnd", textRange);
	        caretOffset = preCaretTextRange.text.length;
	    }
	    return caretOffset;
	}-*/;

	/**
	 * Accept html string creates and inserts an HTML element at the Current Caret Position.
	 * @param html
	 * @return
	 */
	public static native Element insertNodeAtCaret(String html)/*-{
		var sel, range, expandedSelRange, node;
		if (typeof $wnd.getSelection != "undefined") {
	        sel = $wnd.getSelection();
	        if (sel.rangeCount) {
	            range = $wnd.getSelection().getRangeAt(0);
	            expandedSelRange = range.cloneRange();
	            range.collapse(false);

	            var el = document.createElement("div");
	            el.innerHTML = html;
	            var frag = document.createDocumentFragment(), node, lastNode;
	            while ( (node = el.firstChild) ) {
	                lastNode = frag.appendChild(node);
	            }
	            range.insertNode(frag);
	
	            if (lastNode) {
	                expandedSelRange.setEndAfter(lastNode);
                    expandedSelRange.collapse(false);
	                sel.removeAllRanges();
	                sel.addRange(expandedSelRange);
	            }
	        }
	    } 
	}-*/;
	
	/**
	 * When adding a tag, this would delete the all the characters of word typed and set the caret position to where the word being typed started from. 
	 * @param elementId
	 * @param start
	 * @param end
	 * @param isBackSpace
	 */
	public static native void setCaretPosition(Element elementId, int start, int end, boolean isBackSpace)/*-{
	    var document = $doc;
	    var window = $wnd;
	    var textNodes = getTextNodesIn(elementId);
	    var range = document.createRange();
	    var sel = window.getSelection();
	    var foundStart = false;
	    var charCount = 0, endCharCount, node;
	    
	    for (var i = 0, textNode; textNode = textNodes[i++]; ) {
	        endCharCount = charCount + textNode.length;
	        if (!foundStart && start >= charCount && (start < endCharCount || (start == endCharCount && i < textNodes.length))) {
	            range.setStart(textNode, start - charCount);
	            foundStart = true;
	            node = textNode;
	        }
	        if (foundStart && end <= endCharCount) {
	            range.setEnd(textNode, end - charCount);
	            break;
	        }
	        charCount = endCharCount;
	    }
	    
	    range.deleteContents();
	    
	    // This is a temporary fix. Issue being that when user selects a link tag hitting enter, the previous node being a text node, the the
	    // space character in  between is deleted and the text node and link tag are attached. So over here i am just inserting a element containing space
	    
	    if(!isBackSpace){
		    var el = document.createElement("div");
	        el.innerHTML = "&nbsp;";
	        var frag = document.createDocumentFragment(), node, lastNode;
	        while ( (node = el.firstChild) ) {
	            lastNode = frag.appendChild(node);
	        }
	        range.insertNode(frag);
	    }
	    
	    
	    
	    range.collapse(true);
	    sel.removeAllRanges();
	    sel.addRange(range);
	    
		function getTextNodesIn(node) {
		    var textNodes = [];
		    if (node.nodeType == 3) {
		        textNodes.push(node);
		    } else {
		        var children = node.childNodes;
		        for (var i = 0, len = children.length; i < len; ++i) {
		            textNodes.push.apply(textNodes, getTextNodesIn(children[i]));
		        }
		    }
		    return textNodes;
		}
	}-*/;
	
	public static int  checkPreviousWord(String textValue, String tagText){
		int start = -1;
		
		String firstWord = tagText.split("\\s+")[0];
		
		int lastindex = textValue.toLowerCase().lastIndexOf(firstWord.toLowerCase());
		if(lastindex != -1){
			String strTillEnd = textValue.substring(lastindex, textValue.length());
			
			boolean isStartWith = tagText.toLowerCase().startsWith(strTillEnd.toLowerCase());
			if(isStartWith){
				start = lastindex;
			}
		}
		return start;
	}

	public static int  checkPreviousWordForContactBoxField(String textValue, String tagText){
		int start = -1;
		if(tagText.contains(",")){
		int nextWord = tagText.lastIndexOf(",")+1;
		
		int lastindex = textValue.toLowerCase().lastIndexOf(tagText.substring(nextWord).toLowerCase());
		if(lastindex != -1){
			String strTillEnd = textValue.substring(lastindex, textValue.length());
			strTillEnd=strTillEnd.trim();
			boolean isStartWith = tagText.toLowerCase().startsWith(strTillEnd.toLowerCase());
			if(isStartWith){
				start = lastindex;
			}
		}
		}else{
			String firstWord = tagText.split("\\s+")[0];
			
			int lastindex = textValue.toLowerCase().lastIndexOf(firstWord.toLowerCase());
			if(lastindex != -1){
				String strTillEnd = textValue.substring(lastindex, textValue.length());
				
				boolean isStartWith = tagText.toLowerCase().startsWith(strTillEnd.toLowerCase());
				if(isStartWith){
					start = lastindex;
				}
			}
		}
		return start;
	}
	
	/**
	 * This would return the node at the specified range.
	 * @param elementId
	 * @param start
	 * @param end
	 * @return
	 */
	public static native String getNodeType(Element elementId, int start, int end)/*-{
	    var document = $doc;
	    var window = $wnd;
	    var textNodes = getTextNodesIn(elementId);
	    var range = document.createRange();
	    var sel = window.getSelection();
	    var foundStart = false;
	    var charCount = 0, endCharCount, node;
	    
	    for (var i = 0, textNode; textNode = textNodes[i++]; ) {
	        endCharCount = charCount + textNode.length;
	        if (!foundStart && start >= charCount && (start < endCharCount || (start == endCharCount && i < textNodes.length))) {
	            range.setStart(textNode, start - charCount);
	            foundStart = true;
	            node = textNode;
	        }
	        if (foundStart && end <= endCharCount) {
	                range.setEnd(textNode, end - charCount);
	                return textNode.parentElement.localName;
	        }
	        charCount = endCharCount;
	    }
	    
		function getTextNodesIn(node) {
		    var textNodes = [];
		    if (node.nodeType == 3) {
		        textNodes.push(node);
		    } else {
		        var children = node.childNodes;
		        for (var i = 0, len = children.length; i < len; ++i) {
		            textNodes.push.apply(textNodes, getTextNodesIn(children[i]));
		        }
		    }
		    return textNodes;
		}
	}-*/;

	


}
