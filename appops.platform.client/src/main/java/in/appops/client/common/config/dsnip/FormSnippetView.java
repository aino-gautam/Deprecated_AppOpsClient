package in.appops.client.common.config.dsnip;

import in.appops.client.common.config.dsnip.FormSnippetPresenter.FormSnippetConstant;
import in.appops.client.common.config.dsnip.HTMLSnippetPresenter.HTMLSnippetConstant;

public class FormSnippetView extends HTMLSnippetView{
	
	private boolean hasInlineEditing;
	
	/**
	 * Method configures the html snippet.
	 */
	@Override
	public void configure() {
		super.configure();
		
		if(getFormPrimCss() != null) {
			snippetPanel.setStylePrimaryName(getFormPrimCss());
		}
	
		if(getFormDependentCss() != null) {
			snippetPanel.addStyleName(getFormDependentCss());
		}
		
		if(hasInlineEditing() != null)
			hasInlineEditing = hasInlineEditing();
	}

	/**
	 * Returns the primary style to be applied to the component basepanel.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getFormPrimCss() {
		String primaryCss = null;
		try {
			if(viewConfiguration.getConfigurationValue(HTMLSnippetConstant.HS_PCLS) != null) {
				primaryCss = viewConfiguration.getConfigurationValue(HTMLSnippetConstant.HS_PCLS).toString();
			}
		} catch (Exception e) {
			
		}
		return primaryCss;
	}


	/**
	 * Returns the dependent style to be applied to the component basepanel.
	 * If the style is not provided through configuration default is returned
	 * @return
	 */
	protected String getFormDependentCss() {
		String depCss = null;
		try {
			if(viewConfiguration.getConfigurationValue(HTMLSnippetConstant.HS_DCLS) != null) {
				depCss = viewConfiguration.getConfigurationValue(HTMLSnippetConstant.HS_DCLS).toString();
			}
		} catch (Exception e) {

		}
		return depCss;
	}
	
	protected Boolean hasInlineEditing(){
		boolean inlineEditing = false;
		try{
			if(viewConfiguration.getConfigurationValue(FormSnippetConstant.HAS_INLINE_EDITING) != null)
				inlineEditing = (Boolean) viewConfiguration.getConfigurationValue(FormSnippetConstant.HAS_INLINE_EDITING);
		}catch (Exception e) {

		}
		return inlineEditing;
	}

}
