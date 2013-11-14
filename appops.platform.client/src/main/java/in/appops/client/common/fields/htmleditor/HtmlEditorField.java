package in.appops.client.common.fields.htmleditor;

import in.appops.client.common.config.field.BaseField;
import in.appops.client.common.fields.htmleditor.tool.editor.tinymce.client.code.TinyMCEEditor;

import com.google.gwt.user.client.ui.DockPanel;

public class HtmlEditorField extends BaseField  {

	/**
	 *	HtmlEditor Configuration Property Constants 
	 */
	public interface HtmlEditorFieldConstant extends BaseFieldConstant {
		
		public static final String FIELD_HEIGHT = "FieldHeight";
		public static final String FIELD_WIDTH = "FieldWidth";
		public static final String FIELD_RESIZE_ENABLE = "FieldResizeEnable";
		public static final String FIELD_RESIZE_MIN_HEIGHT = "FieldResizeMinHeight";
		public static final String FIELD_RESIZE_MAX_HEIGHT = "FieldResizeMaxHeight";
		public static final String FIELD_RESIZE_MIN_WIDTH = "FieldResizeMinWidth";
		public static final String FIELD_RESIZE_MAX_WIDTH = "FieldResizeMaxWidth";
		public static final String FIELD_MODE = "FieldMode";
		public static final String FIELD_EDIT_MODE = "FieldEditMode";
		public static final String FIELD_VIEW_MODE = "FieldViewMode";
		public static final String FIELD_CONFIG_MODE = "FieldConfigMode";
		public static final String FIELD_CONFIG_BASIC_MODE = "FieldConfigBasicMode";
		public static final String FIELD_CONFIG_FULL_MODE = "FieldConfigFullMode";
		public static final String FIELD_CONFIG_CUSTOM_MODE = "FieldConfigCustomMode";
		public static final String HF_EDTCLS = "htmlEditorBoxCss";
	}
	
	private TinyMCEEditor mceEditor;
	
	public HtmlEditorField() {
		super();
	}
	

	
	@Override
	public void configure() {
		super.configure();
		mceEditor = new TinyMCEEditor();
		
		String height = getFieldHeight();
		mceEditor.setHeight(height);
		String width = getFieldWidth();
		mceEditor.setWidth(width);
	}
	
//	private String getEditorPrimaryCls() {
//		String css = "appops-SpinnerBoxPrimary";
//		if(getConfigurationValue(HtmlEditorFieldConstant.HF_EDTCLS) != null) {
//			css = getConfigurationValue(HtmlEditorFieldConstant.HF_EDTCLS).toString();
//		}
//		return css;
//	}

	@Override
	protected String getBaseFieldPrimCss() {
		String primaryCss = super.getBaseFieldPrimCss();
		
		if(primaryCss == null) {
			return "appops-HtmlEditorFieldPrimary";
		}
		return primaryCss;
	}

	/*protected String getBaseFieldCss() {
		String depCss = super.getBaseFieldCss();
		if(depCss == null) {
			return "appops-HtmlEditorFieldDependent";
		}	
		return depCss;
	}*/
	
	private String getFieldConfigMode() {
		String mode = null;
		if(getConfigurationValue(HtmlEditorFieldConstant.FIELD_CONFIG_MODE) != null) {
			 mode = getConfigurationValue(HtmlEditorFieldConstant.FIELD_CONFIG_MODE).toString();
		}
		return mode;
	}

	private boolean getFieldResizeEnable() {
		boolean isEnable = false;
		if(getConfigurationValue(HtmlEditorFieldConstant.FIELD_RESIZE_ENABLE) != null) {
			isEnable = (Boolean) getConfigurationValue(HtmlEditorFieldConstant.FIELD_RESIZE_ENABLE);
		}
		return isEnable;
	}

	private int getMinResizeWidth() {
		int minWidth = 0;
		if(getConfigurationValue(HtmlEditorFieldConstant.FIELD_RESIZE_MIN_WIDTH) != null) {
			String minimum = getConfigurationValue(HtmlEditorFieldConstant.FIELD_RESIZE_MIN_WIDTH).toString();
			if(minimum.contains("px")) {
				minimum = minimum.replace("px", "");
			}
			minWidth = Integer.parseInt(minimum);
		}
		return minWidth;
	}

	private int getMaxResizeWidth() {
		int maxWidth = 0;
		if(getConfigurationValue(HtmlEditorFieldConstant.FIELD_RESIZE_MAX_WIDTH) != null) {
			String maximum = getConfigurationValue(HtmlEditorFieldConstant.FIELD_RESIZE_MAX_WIDTH).toString();
			if(maximum.contains("px")) {
				maximum = maximum.replace("px", "");
			}
			maxWidth = Integer.parseInt(maximum);
		}
		return maxWidth;
	}

	private int getMinResizeHeight() {
		int minHeight = 0;
		if(getConfigurationValue(HtmlEditorFieldConstant.FIELD_RESIZE_MIN_HEIGHT) != null) {
			String minimum = getConfigurationValue(HtmlEditorFieldConstant.FIELD_RESIZE_MIN_HEIGHT).toString();
			if(minimum.contains("px")) {
				minimum = minimum.replace("px", "");
			}
			minHeight = Integer.parseInt(minimum);
		}
		return minHeight;
	}

	private int getMaxResizeHeight() {
		int maxHeight = 0;
		if(getConfigurationValue(HtmlEditorFieldConstant.FIELD_RESIZE_MAX_HEIGHT) != null) {
			String maximum = getConfigurationValue(HtmlEditorFieldConstant.FIELD_RESIZE_MAX_HEIGHT).toString();
			if(maximum.contains("px")) {
				maximum = maximum.replace("px", "");
			}
			maxHeight = Integer.parseInt(maximum);
		}
		return maxHeight;
	}

	private String getFieldWidth() {
		String width = null;
		if(getConfigurationValue(HtmlEditorFieldConstant.FIELD_WIDTH) != null) {
			 width = getConfigurationValue(HtmlEditorFieldConstant.FIELD_WIDTH).toString();
		}
		return width;
	}
	
	private String getFieldHeight() {
		String height = null;
		if(getConfigurationValue(HtmlEditorFieldConstant.FIELD_HEIGHT) != null) {
			 height = getConfigurationValue(HtmlEditorFieldConstant.FIELD_HEIGHT).toString();
		}
		return height;
	}

	private String getFieldMode() {
		String mode = null;
		if(getConfigurationValue(HtmlEditorFieldConstant.FIELD_MODE) != null) {
			 mode = getConfigurationValue(HtmlEditorFieldConstant.FIELD_MODE).toString();
		}
		return mode;
	}

	@Override
	public void create() {
		try {
			super.create();
			basePanel.add(mceEditor, DockPanel.CENTER);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setValue(Object value) {
		
		try {
			super.setValue(value);
			mceEditor.setEditorContents(value.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Object getValue() {
		String html = null;
		if(mceEditor != null) {
			html = mceEditor.getText();
		}
		return html;
	}
}