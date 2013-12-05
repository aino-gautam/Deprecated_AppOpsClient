var snippetStore = {
		componentCategoriesSnippet: " <table width=\"225px\">" + 
				"						<tr>" + 
				"					 		<td> <span id=\"componentCategoriesList\" componentType=\"appopsComponent\" type= \"listComponent\" data-config=\"componentCategoriesListConfig\"></span> </td>" + 
				"						</tr>" + 
				"					  </table>",
				
		componentConfigEditorSnippet: " <table width=\"400px\">" + 
				"						<tr>" + 
				"					 		<td> <span id=\"componentConfigEditor\" componentType=\"appopsComponent\" type= \"configEditorComponent\" data-config=\"componentConfigEditorConfig\"></span> </td>" + 
				"						</tr>" + 
				"					  </table>",

		disclosureSnippet: " <div>" + 
				"				<div style=\"padding-left: 0px;\" class=\"disclosureSnippetUpper1 disclosureSnippetUpper2\">" + 
				"					<div style=\"padding-left: 16px;position:relative;\" class=\"disclosureSnippetUpper2 disclosureSnippetUpper3\">" + 
				"			 			<span id=\"disclosureCategoryToggleImage\" componentType=\"appopsField\" type= \"toggleImageField\" data-config=\"disclosureImageFieldConfig\"></span>" + 
				"						<span id=\"disclosureCategoryLabel\" componentType=\"appopsField\" type= \"labelField\" data-config=\"disclosureLabelFieldConfig\"></span>" + 
				"					</div>" + 
				"				</div>" + 
				"				<span id=\"disclosureComponentList\" componentType=\"appopsComponent\" type= \"listComponent\" data-config=\"disclosureComponentListConfig\"></span>" + 
				"			</div>",

		componentSnippet: " <div>" + 
				"				<div style=\"padding-left: 16px;position:relative;\" class=\"componentSnippet1 \">" + 
				"					<span id=\"componentLabel\" componentType=\"appopsField\" type= \"actionField\" data-config=\"componentLabelConfig\"></span>" + 
				"				</div>" + 
				"			</div>",
				
		componentPreviewSnippet: " <div>" + 
				"						<div style=\"padding-left: 16px;position:relative;\" >" + 
				"							<span id=\"defaultPreviewSnippet\" componentType=\"htmlSnippet\" type= \"defaultPreviewSnippet\" data-config=\"defaultPreviewSnippetConfig\"></span>" + 
				"						</div>" + 
				" 				   </div>",
		
		defaultPreviewSnippet: " <div>" + 
				"					  <span id=\"defaultlabelPreview\" componentType=\"appopsField\" type= \"labelField\" data-config=\"defaultLabelPreviewConfig\"></span>" + 
				"			     </div>",

		labelFieldPreviewSnippet: " <div>" + 
				"						 <span id=\"labelPreview\" componentType=\"appopsField\" type= \"labelField\" data-config=\"labelFieldPreviewConfig\"></span>" + 
				"			        </div>",

		textFieldPreviewSnippet: " <div>" + 
				"						<span id=\"textFieldPreview\" componentType=\"appopsField\" type= \"textField\" data-config=\"textFieldPreviewConfig\"></span>" + 
				"			   	   </div>",
		
		basicImageFieldPreviewSnippet: " <div>" + 
				"							  <span id=\"basicImagePreview\" componentType=\"appopsField\" type= \"imageField\" data-config=\"basicImageFieldPreviewConfig\"></span>" + 
				"			   			</div>",

		toggleImageFieldPreviewSnippet: " <div>" + 
				"							   <span id=\"toggleImagePreview\" componentType=\"appopsField\" type= \"toggleImageField\" data-config=\"toggleImageFieldPreviewConfig\"></span>" + 
				"			   			 </div>",
		
		numericTextFieldPreviewSnippet: " <div>" + 
				"								<span id=\"numericTextFieldPreview\" componentType=\"appopsField\" type= \"textField\" data-config=\"numericTextFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",

		numericSpinnerFieldPreviewSnippet: " <div>" + 
				"								<span id=\"numericSpinnerFieldPreview\" componentType=\"appopsField\" type= \"spinnerField\" data-config=\"numericSpinnerFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",

		listSpinnerFieldPreviewSnippet: " <div>" + 
				"								<span id=\"listSpinnerFieldPreview\" componentType=\"appopsField\" type= \"spinnerField\" data-config=\"listSpinnerFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",
				
		buttonFieldPreviewSnippet: " <div>" + 
				"								<span id=\"buttonFieldPreview\" componentType=\"appopsField\" type= \"buttonField\" data-config=\"buttonFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",
				
		hyperLinkFieldPreviewSnippet: " <div>" + 
				"								<span id=\"hyperLinkFieldPreview\" componentType=\"appopsField\" type= \"linkField\" data-config=\"hyperLinkFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",
		anchorFieldPreviewSnippet: " <div>" + 
				"								<span id=\"anchorFieldPreview\" componentType=\"appopsField\" type= \"linkField\" data-config=\"anchorFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",
		textAreaFieldPreviewSnippet: " <div>" + 
				"								<span id=\"textAreaFieldPreview\" componentType=\"appopsField\" type= \"textField\" data-config=\"textAreaFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",
		passwordTextFieldPreviewSnippet: " <div>" + 
				"								<span id=\"passwordTextFieldPreview\" componentType=\"appopsField\" type= \"textField\" data-config=\"passwordTextFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",
		emailTextFieldPreviewSnippet: " <div>" + 
				"								<span id=\"emailTextFieldPreview\" componentType=\"appopsField\" type= \"textField\" data-config=\"emailTextFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",
		stateStaticFieldPreviewSnippet: " <div>" + 
				"								<span id=\"stateStaticFieldPreview\" componentType=\"appopsField\" type= \"stateField\" data-config=\"stateStaticFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",
		stateQueryFieldPreviewSnippet: " <div>" + 
				"								<span id=\"stateQueryFieldPreview\" componentType=\"appopsField\" type= \"stateField\" data-config=\"stateQueryFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",
		dateLabelTimeStampFieldPreviewSnippet: " <div>" + 
				"								<span id=\"dateLabelTimeStampFieldPreview\" componentType=\"appopsField\" type= \"dateLabelField\" data-config=\"dateLabelTimeStampFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",
				
		dateLabelDateTimeFieldPreviewSnippet: " <div>" + 
				"								<span id=\"dateLabelDateTimeFieldPreview\" componentType=\"appopsField\" type= \"dateLabelField\" data-config=\"dateLabelDateTimeFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",
				
		listBoxStaticFieldPreviewSnippet: " <div>" + 
				"								<span id=\"listBoxStaticFieldPreview\" componentType=\"appopsField\" type= \"listBoxField\" data-config=\"listBoxStaticFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",

		listBoxQueryFieldPreviewSnippet: " <div>" + 
				"								<span id=\"listBoxQueryFieldPreview\" componentType=\"appopsField\" type= \"listBoxField\" data-config=\"listBoxQueryFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",
		numericRangeSliderFieldPreviewSnippet: " <div>" + 
				"								<span id=\"numericRangeSliderFieldPreview\" componentType=\"appopsField\" type= \"rangeSliderField\" data-config=\"numericRangeSliderFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",
				
		stringRangeSliderFieldPreviewSnippet: " <div>" + 
				"								<span id=\"stringRangeSliderFieldPreview\" componentType=\"appopsField\" type= \"rangeSliderField\" data-config=\"stringRangeSliderFieldPreviewConfig\"></span>" + 
				"			   	   		</div>",
				
		/**        Snippet Name             -    SnippetConfigurationInstance
		 *  passwordTextFieldPreviewSnippet - passwordTextFieldPreviewSnippetConfig
		 *  emailTextFieldPreviewSnippet - emailTextFieldPreviewSnippetConfig
		 *  textAreaFieldPreviewSnippet - textAreaFieldPreviewSnippetConfig
		 *  groupCheckBoxFieldPreviewSnippet - groupCheckBoxFieldPreviewSnippetConfig
		 *  groupRadioButtonFieldPreviewSnippet - groupRadioButtonFieldPreviewSnippetConfig
		 *  hyperLinkFieldPreviewSnippet - hyperLinkFieldPreviewSnippetConfig
		 *  anchorFieldPreviewSnippet - anchorFieldPreviewSnippetConfig
		 *  locationSelectorFieldPreviewSnippet - locationSelectorFieldPreviewSnippetConfig
		 *  numericRangeSliderFieldPreviewSnippet - numericRangeSliderFieldPreviewSnippetConfig
		 *  stringRangeSliderFieldPreviewSnippet - stringRangeSliderFieldPreviewSnippetConfig
		 *  listBoxStaticFieldPreviewSnippet - listBoxStaticFieldPreviewSnippetConfig
		 *  listBoxQueryFieldPreviewSnippet - listBoxQueryFieldPreviewSnippetConfig
		 *  stateStaticFieldPreviewSnippet - stateStaticFieldPreviewSnippetConfig
		 *  stateQueryFieldPreviewSnippet - stateQueryFieldPreviewSnippetConfig
		 *  dateLabelTimeStampFieldPreviewSnippet - dateLabelTimeStampFieldPreviewSnippetConfig
		 *  dateLabelDateTimeFieldPreviewSnippet - dateLabelDateTimeFieldPreviewSnippetConfig
		 *  intelliThoughtFieldPreviewSnippet - intelliThoughtFieldPreviewSnippetConfig
		 *  queryThoughtFieldPreviewSnippet - queryThoughtFieldPreviewSnippetConfig
		 *  
		 *   
		 *  
		 */
			
		formSnippet : "<table>" + 
		"        	<tr>" + 
		"				<td><span id=\"userNameLabel\" componentType=\"appopsField\" type= \"labelField\" data-config=\"userNameLabelConfig\"></span></td>" + 
		"				<td><span id=\"userNameBox\" componentType=\"appopsField\" type=\"textField\" data-config=\"userNameBoxConfig\"></span></td>" + 
		"			</tr>" + 
		"			<tr>" + 
		"				<td><span id=\"passwordLabel\" componentType=\"appopsField\" type=\"labelField\" data-config=\"passwordLabelConfig\"> </span></td>" + 
		"				<td><span id=\"passwordLabelBox\" componentType=\"appopsField\" type=\"textField\" data-config=\"passwordBoxConfig\"></td>" + 
		"			</tr>" + 
		"			<tr>" + 
		"				<td><span id=\"loginButton\" componentType=\"appopsField\" type= \"buttonField\" data-config=\"loginButtonConfig\"></span></td>" + 
		"				<td><span id=\"clearButton\" componentType=\"appopsField\" type= \"buttonField\" data-config=\"clearButtonConfig\"></span></td>" + 
		"			</tr>" + 
		"		</table> ",
		
		listComponentPreviewSnippet: " <table>" + 
		"						<tr>" + 
		"					 		<td> <span id=\"listComponentPreview\" componentType=\"appopsComponent\" type= \"listComponent\" data-config=\"listComponentPreviewConfig\"></span> </td>" + 
		"						</tr>" + 
		"					  </table>",
		
		contactSnippet: " <div>" + 
		"				<div style=\"padding-left: 0px;\" class=\"disclosureSnippetUpper1 disclosureSnippetUpper2\">" + 
		"					<div style=\"padding-left: 16px;position:relative;height: 50px;\" class=\"disclosureSnippetUpper2 disclosureSnippetUpper3\">" + 
		"			 			<span id=\"contactImage\" componentType=\"appopsField\" type= \"imageField\" data-config=\"contactImageFieldConfig\"></span>" + 
		"						<span id=\"contactNameLabel\" componentType=\"appopsField\" type= \"labelField\" data-config=\"contactNameLabelFieldConfig\"></span>" + 
		"					</div>" + 
		"				</div>" + 
		"			</div>",

		storySelectSnippet:	"<table cellspacing=\"1\" cellpadding=\"0\" style=\"width: 100%;\">" +
		"				<tbody>" +
		"					<tr>" +
		"						<td align=\"left\" style=\"vertical-align: top; \">" +
		"							<span id=\"storyListBoxField\" componentType=\"appopsField\" type= \"listBoxField\" data-config=\"storyListBoxFieldConfig\"></span>" +
		"						</td>" +
		"					</tr>" +
		"					<tr>" +
		"						<td align=\"left\" style=\"vertical-align: top; \">" +
		"							<span id=\"storySnippet\" componentType=\"htmlSnippet\" type= \"storySnippet\" data-config=\"storySnippetConfig\"></span>" +
		"						</td>" +
		"					</tr>" +
		"				</tbody>" +
		"			</table>",
		
		storySnippet : "	<table cellspacing=\"0\" cellpadding=\"0\"	style=\"height: 100%; width: 100%; margin-top: 10px;\">\n" + 
				"		<tbody>\n" + 
				"			<tr>\n" + 
				"				<td align=\"left\" style=\"vertical-align: top;\" width=\"60%\">\n" +
				" 					<table cellspacing=\"0\" cellpadding=\"0\">\n " +
				"						<tbody> \n"+ 
				"							<tr> \n"+
				"								<td align=\"left\" style=\"vertical-align: top;\"> \n"+
				"									<span id=\"storyTitleLabel\" componentType=\"appopsField\" type= \"labelField\" data-config=\"storyTitleLabelConfig\"></span>\n" + 
				"								</td>\n" +
				"							</tr>\n" + 
"										</tbody>\n" + 
"									</table>\n" + 
				"				</td>\n" + 
				"			</tr>\n" + 
				"			<tr>\n" + 
				" 					<table cellspacing=\"0\" cellpadding=\"0\">\n " +
				"						<tbody> \n"+ 
				"							<tr> \n"+
				"								<td align=\"left\" style=\"vertical-align: top;\"> \n"+
				"									<span id=\"storyEpicLabel\" componentType=\"appopsField\" type= \"labelField\" data-config=\"storyEpicLabelConfig\"></span>\n" + 
				"								</td>\n" + 
				"								<td align=\"left\" style=\"vertical-align: top;\">\n" + 
				"									<span id=\"epicTitleLabel\" componentType=\"appopsField\" type= \"labelField\" data-config=\"epicTitleLabelConfig\"></span>\n" + 
				"								</td>\n" + 
				"							</tr>\n" + 
				"										</tbody>\n" + 
				"									</table>\n" + 
				"			</tr>\n" + 
				"			<tr>\n" + 
				"				<td align=\"left\" style=\"vertical-align: top;\">\n" + 
				"					<table cellspacing=\"0\" cellpadding=\"0\" style=\"width: 100%;\">\n" + 
				"						<tbody>\n" + 
				"							<tr>\n" + 
				"								<td align=\"left\" style=\"vertical-align: middle;\" width=\"40%\">\n" + 
				"									<table cellspacing=\"0\" cellpadding=\"0\">\n" + 
				"										<tbody>\n" + 
				"											<tr>\n" + 
				"												<td align=\"left\" style=\"vertical-align: top;\">\n" + 
				"													<span id=\"modifiedOnLabel\" componentType=\"appopsField\" type= \"labelField\" data-config=\"modifiedOnLabelConfig\"></span>\n" + 
				"												</td>\n" + 
				"												<td align=\"left\" style=\"vertical-align: top;\">\n" + 
				"												    <span id=\"modifiedOnDateLabel\" componentType=\"appopsField\" type= \"dateLabelField\" data-config=\"modifiedOnDateConfig\"></span>\n" +
				"												</td>\n" + 
				"											</tr>\n" + 
				"										</tbody>\n" + 
				"									</table></td>\n" + 
				"								<td align=\"left\" style=\"vertical-align: middle;\" width=\"60%\">\n" + 
				"									<table cellspacing=\"0\" cellpadding=\"0\">\n" + 
				"										<tbody>\n" + 
				"											<tr>\n" + 
				"												<td align=\"left\" style=\"vertical-align: top;\">\n" + 
				"													<span id=\"modifiedByLabel\" componentType=\"appopsField\" type= \"labelField\" data-config=\"modifiedByLabelConfig\"></span>\n" + 
				"												</td>\n" + 
				"												<td align=\"left\" style=\"vertical-align: top;\">\n" + 
				"													<span id=\"modifiedByNameLabel\" componentType=\"appopsField\" type= \"labelField\" data-config=\"modifiedByNameLabelConfig\"></span>\n" + 
				"												</td>\n" + 
				"											</tr>\n" + 
				"										</tbody>\n" + 
				"									</table>\n" + 
				"								</td>\n" + 
				"							</tr>\n" + 
				"						</tbody>\n" + 
				"					</table>\n" + 
				"				</td>\n" + 
				"			</tr>\n" + 
				"			<tr>\n" + 
				"				<td align=\"left\" style=\"vertical-align: top;\">\n" + 
				"					<table cellspacing=\"0\" cellpadding=\"0\"  style=\"width: 100%;\">\n" + 
				"						<tbody>\n" + 
				"							<tr>\n" + 
				"								<td align=\"left\" style=\"vertical-align: middle;\" width=\"40%\">\n" + 
				"									<table cellspacing=\"0\" cellpadding=\"0\">\n" + 
				"										<tbody>\n" + 
				"											<tr>\n" + 
				"												<td align=\"left\" style=\"vertical-align: middle;\">\n" + 
				"													<span id=\"statusLabel\" componentType=\"appopsField\" type= \"labelField\" data-config=\"statusLabelConfig\"></span>\n" + 
				"												</td>\n" + 
				"												<td align=\"left\" style=\"vertical-align: middle;\" width=\"78%\">\n" + 
				"													<span id=\"statusNameLabel\" componentType=\"appopsField\" type= \"labelField\" data-config=\"statusNameLabelConfig\"></span>\n" + 
				"												</td>\n" + 
				"											</tr>\n" + 
				"										</tbody>\n" + 
				"									</table></td>\n" + 
				"								<td align=\"left\" style=\"vertical-align: middle;\" width=\"60%\">\n" + 
				"									<table cellspacing=\"0\" cellpadding=\"0\">\n" + 
				"										<tbody>\n" + 
				"											<tr>\n" + 
				"												<td align=\"left\" style=\"vertical-align: middle;\">\n" + 
				"													<span id=\"ownerLabel\" componentType=\"appopsField\" type= \"labelField\" data-config=\"ownerLabelConfig\"></span>\n" + 
				"												</td>\n" + 
				"												<td align=\"left\" style=\"vertical-align: middle;\" width=\"78%\">\n" + 
				"													<span id=\"ownerNameLabel\" componentType=\"appopsField\" type= \"labelField\" data-config=\"ownerNameLabelConfig\"></span>\n" + 
				"												</td>\n" + 
				"											</tr>\n" + 
				"										</tbody>\n" + 
				"									</table>\n" + 
				"								</td>\n" + 
				"							</tr>\n" + 
				"						</tbody>\n" + 
				"					</table>",
				
				loginUserSnippet:
					" 	<table>" + 
					"   <span id=\"loginSnippet\" componentType=\"htmlSnippet\" type=\"loginSnippet\" data-config=loginSnippetConfig />"+
					"	</table> ",
				
				loginSnippet:			
				" 	<table>" + 
				"   <span id=\"loginFormSnippet\" componentType=\"formSnippet\" type=\"loginFormSnippet\" data-config=loginFormSnippetConfig />"+
				"	</table> ",
				
				loginFormSnippet : "<table>" + 
				"        	<tr>" + 
				"				<td><span id=\"emailLabel\" componentType=\"appopsField\" type= \"labelField\" data-config=\"emailLabelConfig\"></span></td>" + 
				"				<td><span id=\"emailBox\" componentType=\"appopsField\" type=\"textField\" data-config=\"emailBoxConfig\"></span></td>" + 
				"			</tr>" + 
				"			<tr>" + 
				"				<td><span id=\"pswLabel\" componentType=\"appopsField\" type=\"labelField\" data-config=\"pswLabelConfig\"> </span></td>" + 
				"				<td><span id=\"pswLabelBox\" componentType=\"appopsField\" type=\"textField\" data-config=\"pswBoxConfig\"></td>" + 
				"			</tr>" + 
				"			<tr>" + 
				"				<td><span id=\"loginFormButton\" componentType=\"appopsField\" type= \"buttonField\" data-config=\"loginFormButtonConfig\"></span></td>" + 
				"				<td><span id=\"clearFormButton\" componentType=\"appopsField\" type= \"buttonField\" data-config=\"clearFormButtonConfig\"></span></td>" + 
				"				<td><span id=\"resetFormButton\" componentType=\"appopsField\" type= \"buttonField\" data-config=\"resetFormButtonConfig\"></span></td>" + 
				"			</tr>" + 
				"		</table> ",
};