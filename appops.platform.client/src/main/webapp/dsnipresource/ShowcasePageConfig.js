var pageconfig = {
		pageConfig :"{\"config\":{}}",
		
		componentCategoriesSnippetConfig : "{\"config\":{\"view\":{\"config\":{}},\"componentCategoriesListConfig\" : {\"config\":{\"view\":{\"config\":{\"listScrollCss\":{\"String\":\"dynamicListScroll\"}}}, \"model\":{\"config\":{\"queryname\":{\"String\":\"getAllCategories\"},\"operationname\":{\"String\":\"blogging.ShowcaseService.getAllCategories\"}, \"queryParam\":{\"config\":{}}}}, \"snippetInstance\":{\"String\":\"disclosureSnippetConfig\"}, \"snippetType\":{\"String\":\"disclosureSnippet\"}}}}}",
		
		disclosureSnippetConfig : "{\"config\":{\"view\":{\"config\":{}}, \"disclosureImageFieldConfig\":{\"config\":{\"view\":{\"config\":{\"stateImgPrimaryCss\":{\"String\":\"disclosureImage\"}, \"upStateUrl\":{\"String\":\"images/disclosureRightArrow.png\"}, \"downStateUrl\":{\"String\":\"images/disclosureDownArrow.png\"}, \"visible\":{\"Boolean\":\"true\"}}}}}, \"disclosureLabelFieldConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"disclosureLabel\"}, \"bindProperty\":{\"String\":\"title\"},\"visible\":{\"Boolean\":\"true\"}}}}}, \"disclosureComponentListConfig\" : {\"config\":{\"view\":{\"config\":{\"visible\":{\"Boolean\":\"false\"}}}, \"model\":{\"config\":{\"queryname\":{\"String\":\"getAllComponents\"},\"operationname\":{\"String\":\"blogging.ShowcaseService.getAllComponents\"}, \"queryParam\":{\"config\":{}}}}, \"snippetInstance\":{\"String\":\"componentSnippetConfig\"}, \"snippetType\":{\"String\":\"componentSnippet\"},\"eventActionRuleMap\":{\"eventActionRuleMap\":{\"41##disclosureImageFieldConfig\":{\"eventActionRuleList\":[{\"updateConfigurationRule\":{\"Has_update_configuration\":{\"Boolean\":\"true\"}, \"Configuration_to_update\":{\"config\":{\"view.visible\":{\"Boolean\":\"true\"}, \"model.queryParam.category\":{\"String\":\"@parententity.id\"}}}}}]}, \"40##disclosureImageFieldConfig\":{\"eventActionRuleList\":[{\"updateConfigurationRule\":{\"Has_update_configuration\":{\"Boolean\":\"true\"}, \"Configuration_to_update\":{\"config\":{\"view.visible\":{\"Boolean\":\"false\"}}}}}]}}}}}}}",
		
		componentSnippetConfig : "{\"config\":{\"view\":{\"config\":{}}, \"componentLabelConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"componentLabel\"}, \"bindProperty\":{\"String\":\"title\"},\"visible\":{\"Boolean\":\"true\"}, \"tokenValue\":{\"String\":\"@parententity.name\"}, \"widgetType\":{\"Integer\":\"3\"}}}}}}}",

		componentPreviewSnippetConfig : "{\"config\":{\"view\":{\"config\":{}}, \"eventActionRuleMap\":{\"eventActionRuleMap\":{\"labelField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"labelFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"labelFieldPreviewSnippetConfig\"}}}]}, \"textField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"textFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"textFieldPreviewSnippetConfig\"}}}]}, \"basicImageField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"basicImageFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"basicImageFieldPreviewSnippetConfig\"}}}]}, \"toggleImageField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"toggleImageFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"toggleImageFieldPreviewSnippetConfig\"}}}]}, \"numericTextField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"numericTextFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"numericTextFieldPreviewSnippetConfig\"}}}]}, \"numericSpinnerField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"numericSpinnerFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"numericSpinnerFieldPreviewSnippetConfig\"}}}]}, \"listSpinnerField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"listSpinnerFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"listSpinnerFieldPreviewSnippetConfig\"}}}]}, \"buttonField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"buttonFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"buttonFieldPreviewSnippetConfig\"}}}]}, \"passwordTextField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"passwordTextFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"passwordTextFieldPreviewSnippetConfig\"}}}]}, \"emailTextField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"emailTextFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"emailTextFieldPreviewSnippetConfig\"}}}]}, \"textAreaField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"textAreaFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"textAreaFieldPreviewSnippetConfig\"}}}]}, \"groupCheckBoxField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"groupCheckBoxFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"groupCheckBoxFieldPreviewSnippetConfig\"}}}]}, \"groupRadioButtonField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"groupRadioButtonFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"groupRadioButtonFieldPreviewSnippetConfig\"}}}]}, \"hyperLinkField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"hyperLinkFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"hyperLinkFieldPreviewSnippetConfig\"}}}]}, \"anchorField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"anchorFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"anchorFieldPreviewSnippetConfig\"}}}]}, \"locationSelectorField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"locationSelectorFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"locationSelectorFieldPreviewSnippetConfig\"}}}]}, \"numericRangeSliderField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"numericRangeSliderFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"numericRangeSliderFieldPreviewSnippetConfig\"}}}]}, \"stringRangeSliderField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"stringRangeSliderFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"stringRangeSliderFieldPreviewSnippetConfig\"}}}]}, \"listBoxStaticField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"listBoxStaticFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"listBoxStaticFieldPreviewSnippetConfig\"}}}]}, \"listBoxQueryField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"listBoxQueryFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"listBoxQueryFieldPreviewSnippetConfig\"}}}]}, \"stateStaticField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"stateStaticFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"stateStaticFieldPreviewSnippetConfig\"}}}]}, \"stateQueryField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"stateQueryFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"stateQueryFieldPreviewSnippetConfig\"}}}]}, \"dateLabelTimeStampField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"dateLabelTimeStampFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"dateLabelTimeStampFieldPreviewSnippetConfig\"}}}]}, \"dateLabelDateTimeField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"dateLabelDateTimeFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"dateLabelDateTimeFieldPreviewSnippetConfig\"}}}]}, \"intelliThoughtField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"intelliThoughtFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"intelliThoughtFieldPreviewSnippetConfig\"}}}]}, \"queryThoughtField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"queryThoughtFieldPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"queryThoughtFieldPreviewSnippetConfig\"}}}]}, \"listComponent\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"listComponentPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"listComponentPreviewSnippetConfig\"}}}]}, \"gridComponent\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"listComponentPreviewSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"listComponentPreviewSnippetConfig\"}}}]}, \"entitySnippet\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"storySelectSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"storySelectSnippetConfig\"}}}]}, \"entityForm\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"Transform_to_snippet\":{\"String\":\"formSnippet\"}, \"Has_transformation\":{\"Boolean\":\"true\"}, \"Transform_to_snippet_instance\":{\"String\":\"formSnippetConfig\"}}}]}}},  \"defaultPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"defaultLabelPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"disclosureLabel\"}, \"defaultValue\":{\"String\":\"To preview a component first expand a category and then choose a component\"},\"visible\":{\"Boolean\":\"true\"}}}}}}}, \"labelFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"labelFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"labelFieldPreview\"}, \"defaultValue\":{\"String\":\"I am a Label Field\"},\"visible\":{\"Boolean\":\"true\"}}}}}}}, \"textFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"textFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"textFieldPreview\"},\"visible\":{\"Boolean\":\"true\"}, \"suggestionText\":{\"String\":\"Enter text here \"}, \"suggestionPosition\":{\"String\":\"suggestionInline\"}, \"readOnly\":{\"Boolean\":\"false\"}, \"visibleLines\":{\"Integer\":\"1\"},\"fieldType\":{\"String\":\"txtbox\"}, \"valueType\":{\"StringValueType\":{\"maxlength\":{\"Integer\":\"50\"}, \"defaultValue\":{\"String\":\"This is a text box\"}}}}}}}}}, \"basicImageFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"basicImageFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"basicImageFieldPreview\"}, \"blobId\":{\"String\":\"images/userIcon.jpg\"},\"visible\":{\"Boolean\":\"true\"}}}}}}}, \"toggleImageFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"toggleImageFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"stateImgPrimaryCss\":{\"String\":\"toggleImageFieldPreview\"}, \"upStateUrl\":{\"String\":\"images/disclosureRightArrow.png\"}, \"downStateUrl\":{\"String\":\"images/disclosureDownArrow.png\"}, \"visible\":{\"Boolean\":\"true\"}}}}}}}, \"numericTextFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"numericTextFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"textFieldPreview\"},\"visible\":{\"Boolean\":\"true\"}, \"suggestionText\":{\"String\":\"Enter Number \"}, \"suggestionPosition\":{\"String\":\"suggestionInline\"}, \"readOnly\":{\"Boolean\":\"false\"}, \"visibleLines\":{\"Integer\":\"1\"},\"fieldType\":{\"String\":\"numeric\"}, \"errorPosition\":{\"String\":\"top\"}, \"validateField\":{\"Boolean\":\"true\"}, \"valueType\":{\"IntegerValueType\":{\"maxValue\":{\"Integer\":\"50\"}, \"minValue\":{\"Integer\":\"15\"}, \"defaultValue\":{\"String\":\"25\"}}}}}}}}}, \"numericSpinnerFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"numericSpinnerFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"visible\":{\"Boolean\":\"true\"}, \"step\":{\"Integer\":\"3\"}, \"unit\":{\"String\":\"km\"}, \"circular\":{\"Boolean\":\"true\"}, \"spinnerType\":{\"Integer\":\"1\"}, \"errorPosition\":{\"String\":\"bottom\"}, \"validateOnChange\":{\"Boolean\":\"true\"}, \"valueType\":{\"DecimalValueType\":{\"maxValue\":{\"Float\":\"50\"}, \"minValue\":{\"Float\":\"15\"}, \"defaultValue\":{\"String\":\"25\"}}}}}}}}}, \"listSpinnerFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"listSpinnerFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"visible\":{\"Boolean\":\"true\"}, \"valueListIndex\":{\"Integer\":\"0\"}, \"circular\":{\"Boolean\":\"true\"}, \"spinnerType\":{\"Integer\":\"2\"}, \"valueList\":{\"arrayList\": [\"Sunday\", \"Monday\", \"Tuesday\", \"Wednesday\", \"Thursday\", \"Friday\", \"Saturday\"]}}}}}}}, \"listComponentPreviewSnippetConfig\": {\"config\":{\"view\":{\"config\":{}},\"listComponentPreviewConfig\" : {\"config\":{\"view\":{\"config\":{\"listScrollCss\":{\"String\":\"dynamicListScroll\"}}}, \"model\":{\"config\":{\"queryname\":{\"String\":\"getContactList\"},\"operationname\":{\"String\":\"contact.ContactService.getEntityList\"}, \"queryParam\":{\"config\":{}}}}, \"snippetInstance\":{\"String\":\"contactSnippetConfig\"}, \"snippetType\":{\"String\":\"contactSnippet\"}}}}}, \"formSnippetConfig\": {\"config\":{\"view\":{\"config\":{\"formPrimaryCss\":{\"String\":\"formCss\"}}},\"userNameLabelConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"appops-LabelField\"}, \"defaultValue\":{\"String\":\"UserName\"},\"visible\":{\"Boolean\":\"true\"}, \"title\":{\"String\":\"UserName\"}}}}}, \"userNameBoxConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"appops-TextField\"}, \"defaultValue\":{\"String\":\"Enter user name\"},\"visible\":{\"Boolean\":\"true\"}, \"fieldType\":{\"String\":\"emailbox\"}}}}}, \"passwordLabelConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"appops-LabelField\"}, \"defaultValue\":{\"String\":\"Password\"},\"visible\":{\"Boolean\":\"true\"}, \"title\":{\"String\":\"Password\"}}}}}, \"passwordBoxConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"appops-TextField\"}, \"defaultValue\":{\"String\":\"Enter password\"},\"visible\":{\"Boolean\":\"true\"}, \"fieldType\":{\"String\":\"passowrdTxtbox\"}}}}}, \"clearButtonConfig\": {\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"postenDateLabelField\"}, \"title\":{\"String\":\"Click to clear\"}, \"displayText\":{\"String\":\"Clear\"}}}}}, \"loginButtonConfig\": {\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"postenDateLabelField\"}, \"title\":{\"String\":\"Click to login\"}, \"displayText\":{\"String\":\"Login\"}}}}}}}, \"storySelectSnippetConfig\": {\"config\":{\"view\":{\"config\":{}},\"storyListBoxFieldConfig\" : {\"config\":{\"view\":{\"config\":{\"operation\":{\"String\":\"engile.EngileService.getAllStories\"}, \"queryName\":{\"String\":\"getAllStories\"},\"defaultValue\":{\"String\":\"-- Select Story --\"},\"propertyToDisplay\":{\"String\":\"title\"}}}}}, \"storySnippetConfig\" : {\"config\":{\"view\":{\"config\":{\"visible\":{\"Boolean\":\"false\"}}}, \"model\":{\"config\":{\"queryname\":{\"String\":\"getStory\"},\"operationname\":{\"String\":\"engile.EngileService.getStory\"}, \"queryParam\":{\"config\":{}}}}, \"eventActionRuleMap\":{\"eventActionRuleMap\":{\"17##storyListBoxFieldConfig\":{\"eventActionRuleList\":[{\"updateConfigurationRule\":{\"Has_update_configuration\":{\"Boolean\":\"true\"}, \"Configuration_to_update\":{\"config\":{\"view.visible\":{\"Boolean\":\"true\"}, \"model.queryParam.story\":{\"String\":\"@eventdata.id\"}}}}}]}}},\"storyTitleLabelConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"storyDetailsLabel\"}, \"bindProperty\":{\"String\":\"title\"},\"visible\":{\"Boolean\":\"true\"}}}}}, \"storyEpicLabelConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"storyPropertyDetails\"}, \"defaultValue\":{\"String\":\"Epic\"},\"visible\":{\"Boolean\":\"true\"}}}}}, \"epicTitleLabelConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"storyDetailsLabel\"}, \"bindProperty\":{\"String\":\"epic.title\"},\"visible\":{\"Boolean\":\"true\"}}}}}, \"modifiedOnLabelConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"storyPropertyDetails\"}, \"defaultValue\":{\"String\":\"Modified On\"},\"visible\":{\"Boolean\":\"true\"}}}}}, \"modifiedOnDateConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"storyDetailsLabel\"}, \"bindProperty\":{\"String\":\"epic.title\"},\"visible\":{\"Boolean\":\"true\"}}}}}, \"modifiedByLabelConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"storyPropertyDetails\"}, \"defaultValue\":{\"String\":\"By\"},\"visible\":{\"Boolean\":\"true\"}}}}}, \"modifiedByNameLabelConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"storyDetailsLabel\"}, \"bindProperty\":{\"String\":\"modifiedBy.name\"},\"visible\":{\"Boolean\":\"true\"}}}}}, \"statusLabelConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"storyPropertyDetails\"}, \"defaultValue\":{\"String\":\"Status\"},\"visible\":{\"Boolean\":\"true\"}}}}}, \"statusNameLabelConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"storyDetailsLabel\"}, \"bindProperty\":{\"String\":\"status.name\"},\"visible\":{\"Boolean\":\"true\"}}}}}, \"ownerLabelConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"storyPropertyDetails\"}, \"defaultValue\":{\"String\":\"Owner\"},\"visible\":{\"Boolean\":\"true\"}}}}}, \"ownerNameLabelConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"storyDetailsLabel\"}, \"bindProperty\":{\"String\":\"createdBy.name\"},\"visible\":{\"Boolean\":\"true\"}}}}} }}}}, \"hyperLinkFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"hyperLinkFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"LinkType\":{\"String\":\"Hyperlink\"}, \"baseFieldPrimaryCss\":{\"String\":\"appops-LinkField\"}, \"displayText\":{\"String\":\"Hyperlink\"}, \"historyToken\":{\"String\":\"historyToken\"}}}}}}}, \"anchorFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"anchorFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"LinkType\":{\"String\":\"Anchor\"}, \"baseFieldPrimaryCss\":{\"String\":\"appops-LinkField\"}, \"displayText\":{\"String\":\"Anchor\"}, \"title\":{\"String\":\"Anchor with configurations\"}}}}}}}, \"textAreaFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"textAreaFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"visibleLines\":{\"Integer\":\"10\"},\"readOnly\":{\"Boolean\":\"false\"}, \"fieldType\":{\"String\":\"txtarea\"}, \"baseFieldPrimaryCss\":{\"String\":\"textAreaFieldPreview\"}, \"baseFieldDependentCss\":{\"String\":\"textAreaFieldPreviewBase\"}, \"suggestionPosition\":{\"String\":\"suggestionInline\"},\"suggestionText\":{\"String\":\"Enter field value\"},\"validateOnChange\":{\"Boolean\":\"true\"}, \"errorPosition\":{\"String\":\"bottom\"}, \"charWidth\":{\"Integer\":\"70\"}, \"validateField\":{\"Boolean\":\"false\"}, \"valueType\":{\"StringValueType\":{\"defaultValue\":{\"String\":\"This is a text area\"}}}}}}}}}, \"passwordTextFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"passwordTextFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"visibleLines\":{\"Integer\":\"1\"},\"readOnly\":{\"Boolean\":\"false\"}, \"fieldType\":{\"String\":\"passowrdTxtbox\"}, \"baseFieldPrimaryCss\":{\"String\":\"passwordTextFieldPreview\"}, \"baseFieldDependentCss\":{\"String\":\"passwordTextFieldPreviewBase\"}, \"suggestionPosition\":{\"String\":\"suggestionInline\"},\"suggestionText\":{\"String\":\"Enter field value\"},\"validateOnChange\":{\"Boolean\":\"true\"}, \"errorPosition\":{\"String\":\"bottom\"}, \"validateField\":{\"Boolean\":\"true\"}, \"valueType\":{\"StringValueType\":{\"maxlength\":{\"Integer\":\"20\"}, \"defaultValue\":{\"String\":\"Password\"}}}}}}}}}, \"emailTextFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"emailTextFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"visibleLines\":{\"Integer\":\"1\"},\"readOnly\":{\"Boolean\":\"false\"}, \"fieldType\":{\"String\":\"emailbox\"}, \"baseFieldPrimaryCss\":{\"String\":\"emailTextFieldPreview\"}, \"baseFieldDependentCss\":{\"String\":\"emailTextFieldPreviewBase\"}, \"suggestionPosition\":{\"String\":\"suggestionInline\"},\"suggestionText\":{\"String\":\"Enter email\"},\"validateOnChange\":{\"Boolean\":\"true\"}, \"errorPosition\":{\"String\":\"bottom\"}, \"validateField\":{\"Boolean\":\"true\"}, \"showValidField\":{\"Boolean\":\"true\"}, \"invalidEmailText\":{\"String\":\"Invalid email\"}, \"valueType\":{\"StringValueType\":{\"maxlength\":{\"Integer\":\"50\"}, \"defaultValue\":{\"String\":\"Enter email\"}, \"regexValidator\":{\"String\":\"^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$\"}}}}}}}}}, \"stateStaticFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"stateStaticFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"isStaticBox\":{\"Boolean\":\"true\"}, \"suggestionText\":{\"String\":\"Enter a day\"}, \"itemsList\":{\"arrayList\": [\"Sunday\", \"Monday\", \"Tuesday\", \"Wednesday\", \"Thursday\", \"Friday\", \"Saturday\"]}}}}}}}, \"stateQueryFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"stateQueryFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"isStaticBox\":{\"Boolean\":\"false\"}, \"suggestionText\":{\"String\":\"Enter a service name\"}, \"operation\":{\"String\":\"appdefinition.AppDefinitionService.getAllServiceList\"}, \"queryName\":{\"String\":\"getServiceSuggestionsForAugs\"}, \"propertyToDisplay\":{\"String\":\"name\"}, \"queryMaxresult\":{\"Integer\":\"5\"}, \"isAutoSuggestion\":{\"Boolean\":\"true\"}}}}}}}, \"dateLabelTimeStampFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"dateLabelTimeStampFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"dateTimelabel\"}, \"DisplayFormat\":{\"String\":\"liveTimeStamp\"},\"conversionFormat\":{\"String\":\"MMM dd \'\'yy \'at\' HH:mm\"}}}}}}},\"dateLabelDateTimeFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"dateLabelDateTimeFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"dateTimelabel\"}, \"DisplayFormat\":{\"String\":\"dateTimeDisplay\"},\"conversionFormat\":{\"String\":\"MMM dd \'\'yy \'at\' HH:mm\"}}}}}}},\"listBoxStaticFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}}, \"listBoxStaticFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"listOfItems\":{\"arrayList\":[\"Private access\",\"Public\",\"Restricted\",\"Me\"]}, \"defaultValue\":{\"String\":\"Public\"}}}}}}}, \"numericRangeSliderFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"numericRangeSliderFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"stepval\":{\"Double\":\"50.0\"}, \"maxVal\":{\"Double\":\"200.0\"}, \"baseFieldPrimaryCss\":{\"String\":\"sliderPanel\"}, \"minVal\":{\"Double\":\"100.0\"}, \"sliderMode\":{\"String\":\"numericSlider\"}}}}}}}, \"buttonFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},buttonFieldPreviewConfig:{\"config\":{\"view\":{\"config\":{\"displayText\":{\"String\":\"Configure\"},\"title\":{\"String\":\"Configure\"},\"baseFieldPrimaryCss\":{\"String\":\"appops-Button\"}}}}}}}, \"stringRangeSliderFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"stringRangeSliderFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"itemsList\":{\"arrayList\":[\"Me\",\"Public\",\"Private\",\"Restricted\"]}, \"baseFieldPrimaryCss\":{\"String\":\"sliderPanel\"}, \"sliderMode\":{\"String\":\"stringSlider\"}}}}}}}, \"listBoxQueryFieldPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"listBoxQueryFieldPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"defaultValue\":{\"String\":\"--Select service--\"}, \"operation\":{\"String\":\"appdefinition.AppDefinitionService.getAllServiceList\"}, \"queryName\":{\"String\":\"getServicesForAugs\"}, \"propertyToDisplay\":{\"String\":\"name\"}, \"baseFieldPrimaryCss\":{\"String\":\"dynamicUIListBox\"}, \"loaderImgPrimarycss\":{\"String\":\"dynamicUIListBoxLoader\"}}}}}}}  }}",

		contactSnippetConfig : "{\"config\":{\"view\":{\"config\":{}}, \"contactImageFieldConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"basicImageFieldPreview\"}, \"bindProperty\":{\"String\":\"imgBlobId\"},\"visible\":{\"Boolean\":\"true\"}, \"defaultValue\":{\"String\":\"images/default_userIcon.png\"}}}}}, \"contactNameLabelFieldConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"contactLabel\"}, \"bindProperty\":{\"String\":\"name\"},\"visible\":{\"Boolean\":\"true\"}}}}}}}",
		
		//disclosureSnippetConfig : "{\"config\":{\"view\":{\"config\":{\"disclosureImageFieldConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"disclosureImage\"}, \"blobId\":{\"String\":\"images/disclosureRightArrow.png\"},\"visible\":{\"Boolean\":\"true\"}}}}}, \"disclosureLabelFieldConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"disclosureLabel\"}, \"bindProperty\":{\"String\":\"title\"},\"visible\":{\"Boolean\":\"true\"}}}}}}}}}",
		
		//componentCategoriesListConfig : "{\"config\":{\"view\":{\"config\":{}}, \"model\":{\"config\":{\"queryname\":{\"String\":\"getAllCategories\"},\"operationname\":{\"String\":\"blogging.ShowcaseService.getAllCategories\"}, \"queryParam\":{\"config\":{}}}}, \"snippetInstance\":{\"String\":\"disclosureSnippetConfig\"}, \"snippetType\":{\"String\":\"disclosureSnippet\"}}}",
};