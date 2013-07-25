package in.appops.client.common.fields.htmleditor;

import in.appops.client.common.config.field.BaseField;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;

public class HtmlEditorField extends BaseField implements ValueChangeHandler<String> {

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
	}
	
	private String fieldMode;
	//private CKConfig config;
	private HTML content;
	//private CKEditor editor;
	private String fieldConfigMode;
	private String editedHtmlContent;
	
	public HtmlEditorField() {
		super();
	}
	
	/*public Toolbar createToolbar() {
		try {
			ToolbarLine toolbarLine = new ToolbarLine();
			TOOLBAR_OPTIONS[] options = { TOOLBAR_OPTIONS.Bold, TOOLBAR_OPTIONS.Italic, TOOLBAR_OPTIONS.Font, TOOLBAR_OPTIONS.FontSize, TOOLBAR_OPTIONS.BulletedList, TOOLBAR_OPTIONS.Link};
			toolbarLine.addAll(options);
			Toolbar toolbar = new Toolbar();
			toolbar.add(toolbarLine);
			return toolbar;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
	
	@Override
	public void configure() {
		super.configure();
		
		/*fieldConfigMode = getFieldConfigMode();
		if(fieldConfigMode != null) {
			if(fieldConfigMode.equals(HtmlEditorFieldConstant.FIELD_CONFIG_FULL_MODE)) {
				config = CKConfig.full;
			} else {
				config = CKConfig.basic;
			}
		} else {
			config = CKConfig.basic;
		}
		
		fieldMode = getFieldMode();
		String height = getFieldHeight();
		config.setHeight(height);
		String width = getFieldWidth();
		config.setWidth(width);
		boolean isEnable = getFieldResizeEnable();
		if(isEnable) {
			int maxResizeHeight = getMaxResizeHeight();
			config.setResizeMaxHeight(maxResizeHeight);
			
			int minResizeHeight = getMinResizeHeight();
			config.setResizeMinHeight(minResizeHeight);
			
			int maxResizeWidth = getMaxResizeWidth();
			config.setResizeMaxWidth(maxResizeWidth);
			
			int minResizeWidth = getMinResizeWidth();
			config.setResizeMinWidth(minResizeWidth);
		}
		editor = new CKEditor(config);*/
		//editor.addValueChangeHandler(this);
	}
	
	@Override
	protected String getBaseFieldPrimCss() {
		String primaryCss = super.getBaseFieldPrimCss();
		
		if(primaryCss == null) {
			return "appops-HtmlEditorFieldPrimary";
		}
		return primaryCss;
	}

	protected String getBaseFieldCss() {
		String depCss = super.getBaseFieldCss();
		if(depCss == null) {
			return "appops-HtmlEditorFieldDependent";
		}	
		return depCss;
	}
	
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
			if(fieldMode != null) {
				if(fieldMode.equals(HtmlEditorFieldConstant.FIELD_EDIT_MODE)) {
					createEditModeUI();
				} else {
					createViewModeUI();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void createViewModeUI() {
		content = new HTML();
		content.setSize("100%", "100%");
		basePanel.add(content,DockPanel.CENTER);
	}

	private void createEditModeUI() {
		/*if(fieldConfigMode != null) {
			if(fieldConfigMode.equals(HtmlEditorFieldConstant.FIELD_CONFIG_CUSTOM_MODE)) {
				Toolbar toolbar = createToolbar();
				config.setToolbar(toolbar);
			}
		}
		basePanel.add(editor,DockPanel.CENTER);*/
	}
	
	public void setHtmlContent(String html) {
		if(content != null) {
			content.setHTML(html);
		}
	}
	
	public String getHtmlContent() {
		String html = null;
		if(content != null) {
			html = content.getText();
		}
		return html;
	}
	
	public String getEditedContent() {
		String html = null;
		/*if(editor != null) {
			html = editor.getHTML();
		}*/
		return html;
	}
	
	public void changeToViewMode() {
		editedHtmlContent = getEditedContent();

		basePanel.clear();
		if(content == null) {
			createViewModeUI();
		} else {
			basePanel.add(content,DockPanel.CENTER);
		}
		
		if(editedHtmlContent == null || editedHtmlContent.equals("")) {
			setHtmlContent("-");
		} else {
			setHtmlContent(editedHtmlContent);
		}
	}
	
	public void changeToEditMode() {
		/*basePanel.clear();
		if(editor == null) {
			createEditModeUI();
		} else {
			basePanel.add(editor,DockPanel.CENTER);
		}
		
		String htmlContent = getHtmlContent();
		if(htmlContent != null) {
			editor.setHTML(htmlContent);
		} else {
			editor.setHTML("");
		}*/
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		editedHtmlContent = event.getValue();
	}
	
	public void setContentToEditor(String content) {
		/*try {
			if(editor != null) {
				editor.setHTML(content);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
}