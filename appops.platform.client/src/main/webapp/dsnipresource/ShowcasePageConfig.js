var pageconfig = {
		pageConfig :"{\"config\":{}}",
		
		componentCategoriesSnippetConfig : "{\"config\":{\"view\":{\"config\":{}},\"componentCategoriesListConfig\" : {\"config\":{\"view\":{\"config\":{}}, \"model\":{\"config\":{\"queryname\":{\"String\":\"getAllCategories\"},\"operationname\":{\"String\":\"blogging.ShowcaseService.getAllCategories\"}, \"queryParam\":{\"config\":{}}}}, \"snippetInstance\":{\"String\":\"disclosureSnippetConfig\"}, \"snippetType\":{\"String\":\"disclosureSnippet\"}}}}}",
		
		disclosureSnippetConfig : "{\"config\":{\"view\":{\"config\":{}}, \"disclosureImageFieldConfig\":{\"config\":{\"view\":{\"config\":{\"stateImgPrimaryCss\":{\"String\":\"disclosureImage\"}, \"upStateUrl\":{\"String\":\"images/disclosureRightArrow.png\"}, \"downStateUrl\":{\"String\":\"images/disclosureDownArrow.png\"}, \"visible\":{\"Boolean\":\"true\"}}}}}, \"disclosureLabelFieldConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"disclosureLabel\"}, \"bindProperty\":{\"String\":\"title\"},\"visible\":{\"Boolean\":\"true\"}}}}}, \"disclosureComponentListConfig\" : {\"config\":{\"view\":{\"config\":{\"visible\":{\"Boolean\":\"false\"}}}, \"model\":{\"config\":{\"queryname\":{\"String\":\"getAllComponents\"},\"operationname\":{\"String\":\"blogging.ShowcaseService.getAllComponents\"}, \"queryParam\":{\"config\":{}}}}, \"snippetInstance\":{\"String\":\"componentSnippetConfig\"}, \"snippetType\":{\"String\":\"componentSnippet\"},\"eventActionRuleMap\":{\"eventActionRuleMap\":{\"41##disclosureImageFieldConfig\":{\"eventActionRuleList\":[{\"updateConfigurationRule\":{\"HasUpdateConfiguration\":{\"Boolean\":\"true\"}, \"ConfigurationToUpdate\":{\"config\":{\"view.visible\":{\"Boolean\":\"true\"}, \"model.queryParam.category\":{\"String\":\"@parententity.id\"}}}}}]}, \"40##disclosureImageFieldConfig\":{\"eventActionRuleList\":[{\"updateConfigurationRule\":{\"HasUpdateConfiguration\":{\"Boolean\":\"true\"}, \"ConfigurationToUpdate\":{\"config\":{\"view.visible\":{\"Boolean\":\"false\"}}}}}]}}}}}}}",
		
		componentSnippetConfig : "{\"config\":{\"view\":{\"config\":{}}, \"componentLabelConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"disclosureLabel\"}, \"bindProperty\":{\"String\":\"title\"},\"visible\":{\"Boolean\":\"true\"}, \"tokenValue\":{\"String\":\"@parententity.name\"}, \"widgetType\":{\"Integer\":\"3\"}}}}}}}",

		componentPreviewSnippetConfig : "{\"config\":{\"view\":{\"config\":{}}, \"eventActionRuleMap\":{\"eventActionRuleMap\":{\"labelField\":{\"eventActionRuleList\":[{\"snippetControllerRule\":{ \"TransformToSnippet\":{\"String\":\"labelPreviewSnippet\"}, \"HasTransformation\":{\"Boolean\":\"true\"}, \"TransformToSnippetInstance\":{\"String\":\"labelPreviewSnippetConfig\"}}}]}}}, \"defaultPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"defaultLabelPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"disclosureLabel\"}, \"defaultValue\":{\"String\":\"To preview a component first expand a category and the choose on a component\"},\"visible\":{\"Boolean\":\"true\"}}}}}}}, \"labelPreviewSnippetConfig\" : {\"config\":{\"view\":{\"config\":{}},\"labelPreviewConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"disclosureLabel\"}, \"defaultValue\":{\"String\":\"I am a Label Field\"},\"visible\":{\"Boolean\":\"true\"}}}}}}} }}",

		componentConfigEditorConfig:"{}",
		
		//disclosureSnippetConfig : "{\"config\":{\"view\":{\"config\":{\"disclosureImageFieldConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"disclosureImage\"}, \"blobId\":{\"String\":\"images/disclosureRightArrow.png\"},\"visible\":{\"Boolean\":\"true\"}}}}}, \"disclosureLabelFieldConfig\":{\"config\":{\"view\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"disclosureLabel\"}, \"bindProperty\":{\"String\":\"title\"},\"visible\":{\"Boolean\":\"true\"}}}}}}}}}",
		
		//componentCategoriesListConfig : "{\"config\":{\"view\":{\"config\":{}}, \"model\":{\"config\":{\"queryname\":{\"String\":\"getAllCategories\"},\"operationname\":{\"String\":\"blogging.ShowcaseService.getAllCategories\"}, \"queryParam\":{\"config\":{}}}}, \"snippetInstance\":{\"String\":\"disclosureSnippetConfig\"}, \"snippetType\":{\"String\":\"disclosureSnippet\"}}}",
};