var pageconfig = {
  recentArticleListConfig: "{\"config\":{\"view\":{\"config\":{\"listSnippetInstanceType\":{\"String\":\"entityArticleLink\"}, \"listSnippetType\":{\"String\":\"entityLinkSnippet\"}, \"compponent.view.headervalue\":{\"String\":\"Recent Posts\"}, \"compponent.view.header\":{\"Boolean\":\"true\"}}}, \"interestedEvents\":{\"map\":{\"onLoad\":{\"config\":{\"operation\":{\"String\":\"blogging.BloggingService.getLatestUpdates\"}, \"type\":{\"Integer\":\"2\"}}}}}}}",
  recentAuthorArticleListConfig: "{\"config\":{\"view\":{\"config\":{\"listSnippetInstanceType\":{\"String\":\"entityArticleLinkAuthor\"}, \"listSnippetType\":{\"String\":\"entityLinkSnippet\"}, \"compponent.view.headervalue\":{\"String\":\"Recent Posts\"}, \"compponent.view.header\":{\"Boolean\":\"true\"}}}, \"interestedEvents\":{\"map\":{\"onLoad\":{\"config\":{\"operation\":{\"String\":\"blogging.BloggingService.getLatestUpdates\"}, \"type\":{\"Integer\":\"2\"}}},\"onAfterSubmit\":{\"config\":{\"operation\":{\"String\":\"blogging.BloggingService.getLatestUpdates\"}, \"type\":{\"Integer\":\"2\"}}}}}}}",

  articleSnippetListConfig: "{\"config\":{\"view\":{\"config\":{\"listSnippetInstanceType\":{\"String\":\"articleSummaryContentSnippet\"}, \"listSnippetType\":{\"String\":\"entityContentSnippet\"}, \"compponent.view.headervalue\":{\"String\":\"Recent Posts\"}, \"compponent.view.header\":{\"Boolean\":\"true\"}}}, \"model\":{\"config\":{\"queryname\":{\"String\":\"getArticlesForBlog\"}, \"queryParam\":{\"arrayList\":[{\"config\":{\"paramName\":{\"String\":\"blogId\"}, \"entityProp\":{\"String\":\"blog.id\"}, \"paramType\":{\"String\":\"contextParam\"}}}]},\"operationname\":{\"String\":\"blogging.BloggingService.getArticles\"}}}}}",
  articleContentSnippet: "{\"config\":{\"authorNameLbl\":{\"config\":{\"labelfieldcss\":{\"String\":\"postenDateLabelField\"}, \"bindProperty\":{\"String\":\"authorName\"},\"visible\":{\"Boolean\":\"false\"}, \"baseFieldPrimaryCss\":{\"String\":\"postenOnLabelFieldBase\"}}}, \"authorLblFld\":{\"config\":{\"labelfieldcss\":{\"String\":\"postenOnLabelField\"}, \"baseFieldPrimaryCss\":{\"String\":\"postenOnLabelFieldBase\"},\"visible\":{\"Boolean\":\"false\"}, \"displayTxt\":{\"String\":\"Author\"}}}, \"content\":{\"config\":{\"bindProperty\":{\"String\":\"contentBlobId\"}, \"baseFieldPrimaryCss\":{\"String\":\"article-SummaryHTMLFieldBase\"}, \"htmlfieldcss\":{\"String\":\"article-summaryHTMLField\"}}}, \"postedDate\":{\"config\":{\"labelfieldcss\":{\"String\":\"postenDateLabelField\"}, \"bindProperty\":{\"String\":\"createdOn\"}, \"baseFieldPrimaryCss\":{\"String\":\"postenOnLabelFieldBase\"}}}, \"postedOn\":{\"config\":{\"labelfieldcss\":{\"String\":\"postenOnLabelField\"}, \"baseFieldPrimaryCss\":{\"String\":\"postenOnLabelFieldBase\"}, \"displayTxt\":{\"String\":\"Posted On\"}}}, \"entityLink\":{\"config\":{\"bindProperty\":{\"String\":\"title\"}, \"baseFieldPrimaryCss\":{\"String\":\"article-titleLinkFieldBase\"}, \"widgetType\":{\"Integer\":\"1\"},\"acss\":{\"String\":\"article-titleLinkField\"}, \"tokenValue\":{\"String\":\"eventName=viewArticle&&eventOperation=blogging.BloggingService.getArticle&&snippetType=entityContentSnippet&&instanceType=articleViewContentSnippet&&entityId=\"}}}}}",
  authorHomeLinkConfig: "{\"config\":{\"defaultValue\":{\"String\":\"Author Home\"}, \"acss\":{\"String\":\"recentPost-articleLinkField\"}, \"widgetType\":{\"Integer\":\"1\"},\"mode\":{\"String\":\"2\"}, \"page\":{\"String\":\"authorhome.html\"}}}",
  articleEditCreateSnippet: "{\"config\":{\"editCreateLblCfg\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"appops-LabelField\"}, \"isWordWrap\":{\"Boolean\":\"true\"}, \"displayTxt\":{\"String\":\"Enter Article Content Below\"}}}, \"editCreateTFCfg\":{\"config\":{\"suggestionText\":{\"String\":\"Enter Title \"}, \"maxlength\":{\"Integer\":\"20\"},\"bindProperty\":{\"String\":\"title\"}, \"baseFieldPrimaryCss\":{\"String\":\"appops-SpinnerBoxPrimary\"}, \"readOnly\":{\"Boolean\":\"false\"}, \"suggestionPosition\":{\"String\":\"suggestionInline\"}, \"visibleLines\":{\"Integer\":\"1\"}, \"fieldType\":{\"String\":\"txtbox\"}, \"validateOnChange\":{\"Boolean\":\"true\"}, \"errorPosition\":{\"String\":\"bottom\"}}}, \"editCreateContEdtCfg\":{\"config\":{\"FieldMode\":{\"String\":\"FieldEditMode\"}, \"FieldHeight\":{\"String\":\"250px\"}, \"FieldResizeEnable\":{\"Boolean\":\"false\"},\"bindProperty\":{\"String\":\"contentBlobId\"}, \"FieldWidth\":{\"String\":\"500px\"}}}}}",
  articleViewContentSnippet: "{\"config\":{\"authorNameLbl\":{\"config\":{\"labelfieldcss\":{\"String\":\"postenDateLabelField\"}, \"bindProperty\":{\"String\":\"authorName\"},\"visible\":{\"Boolean\":\"true\"}, \"baseFieldPrimaryCss\":{\"String\":\"postenOnLabelFieldBase\"}}}, \"authorLblFld\":{\"config\":{\"labelfieldcss\":{\"String\":\"postenOnLabelField\"}, \"baseFieldPrimaryCss\":{\"String\":\"postenOnLabelFieldBase\"},\"visible\":{\"Boolean\":\"true\"}, \"displayTxt\":{\"String\":\"Author\"}}}, \"content\":{\"config\":{\"bindProperty\":{\"String\":\"contentBlobId\"}, \"baseFieldPrimaryCss\":{\"String\":\"article-SummaryHTMLFieldBase\"}, \"htmlfieldcss\":{\"String\":\"article-summaryHTMLField\"}}}, \"postedDate\":{\"config\":{\"labelfieldcss\":{\"String\":\"postenDateLabelField\"}, \"bindProperty\":{\"String\":\"createdOn\"}, \"baseFieldPrimaryCss\":{\"String\":\"postenOnLabelFieldBase\"}}}, \"postedOn\":{\"config\":{\"labelfieldcss\":{\"String\":\"postenOnLabelField\"}, \"baseFieldPrimaryCss\":{\"String\":\"postenOnLabelFieldBase\"}, \"displayTxt\":{\"String\":\"Posted On\"}}}, \"entityLink\":{\"config\":{\"bindProperty\":{\"String\":\"title\"}, \"baseFieldPrimaryCss\":{\"String\":\"article-titleLinkFieldBase\"}, \"widgetType\":{\"Integer\":\"1\"}, \"acss\":{\"String\":\"article-titleLinkField\"}, \"tokenValue\":{\"String\":\"eventName=viewArticle&&eventOperation=blogging.BloggingService.getArticle&&snippetType=entityContentSnippet&&instanceType=articleViewContentSnippet&&entityId=\"}}}}}",
  writeArticleLinkConfig: "{\"config\":{\"defaultValue\":{\"String\":\"Write Article\"}, \"acss\":{\"String\":\"recentPost-articleLinkField\"}, \"widgetType\":{\"Integer\":\"1\"}, \"page\":{\"String\":\"viewArticle\"}}}",
  blogHomeLinkConfig: "{\"config\":{\"defaultValue\":{\"String\":\"Blog Home\"}, \"acss\":{\"String\":\"recentPost-articleLinkField\"}, \"widgetType\":{\"Integer\":\"1\"}, \"mode\":{\"String\":\"2\"}, \"page\":{\"String\":\"bloghome.html\"}}}",
  
  blogHeaderPlaceHonder:"{\"config\":{\"interestedEvents\":{\"map\":{\"onLoad\":{\"config\":{\"isTransformWidget\":{\"Boolean\":\"true\"}, \"transformInstance\":{\"String\":\"blogHomeheaderConfig\"}, \"transformType\":{\"Integer\":\"1\"}, \"transformTo\":{\"String\":\"pageHeaderSnippet\"}, \"type\":{\"Integer\":\"1\"}}}}}}}",
  authorHeaderPlaceHonder:"{\"config\":{\"interestedEvents\":{\"map\":{\"onLoad\":{\"config\":{\"isTransformWidget\":{\"Boolean\":\"true\"}, \"transformInstance\":{\"String\":\"authorHomeheaderConfig\"}, \"transformType\":{\"Integer\":\"1\"}, \"transformTo\":{\"String\":\"pageHeaderSnippet\"}, \"type\":{\"Integer\":\"1\"}}}}}}}",
  recentPostPlaceHonder:"{\"config\":{\"interestedEvents\":{\"map\":{\"onLoad\":{\"config\":{\"transformInstance\":{\"String\":\"recentArticleListConfig\"}, \"transformType\":{\"Integer\":\"2\"}, \"transformTo\":{\"String\":\"listComponent\"}, \"type\":{\"Integer\":\"1\"}}}}}}}",
  authorRecentPostPlaceHonder:"{\"config\":{\"interestedEvents\":{\"map\":{\"onLoad\":{\"config\":{\"transformInstance\":{\"String\":\"recentAuthorArticleListConfig\"}, \"transformType\":{\"Integer\":\"2\"}, \"transformTo\":{\"String\":\"listComponent\"}, \"type\":{\"Integer\":\"1\"}}}}}}}",
//  articleSnippetPlaceHolder:"{\"config\":{\"interestedEvents\":{\"map\":{\"onLoad\":{\"config\":{\"transformInstance\":{\"String\":\"articleSnippetListConfig\"}, \"transformType\":{\"Integer\":\"2\"}, \"transformTo\":{\"String\":\"listComponent\"}, \"type\":{\"Integer\":\"1\"}}},\"onCreateArticle\":{\"config\":{\"transformInstance\":{\"String\":\"onArticleCreateSnippet\"}, \"transformType\":{\"Integer\":\"1\"}, \"transformTo\":{\"String\":\"entityEditCreateSnippet\"}, \"type\":{\"Integer\":\"1\"}}}, \"onEditArticle\":{\"config\":{\"transformInstance\":{\"String\":\"onArticleEditSnippet\"}, \"transformType\":{\"Integer\":\"1\"}, \"transformTo\":{\"String\":\"entityEditCreateSnippet\"}, \"type\":{\"Integer\":\"1\"}}}, \"onAfterSubmit\":{\"config\":{\"transformInstance\":{\"String\":\"articleSummaryContentSnippet\"}, \"transformType\":{\"Integer\":\"1\"}, \"transformTo\":{\"String\":\"entityContentSnippet\"}, \"type\":{\"Integer\":\"1\"}}}, \"onViewArticle\":{\"config\":{\"transformInstance\":{\"String\":\"articleDetailsContentSnippet\"}, \"transformType\":{\"Integer\":\"1\"}, \"transformTo\":{\"String\":\"entityContentSnippet\"}, \"type\":{\"Integer\":\"1\"}}}}}}}",
  articleSummaryContentSnippet: "{\"config\":{\"view\":{\"config\":{\"authorNameLbl\":{\"config\":{\"labelfieldcss\":{\"String\":\"postenDateLabelField\"}, \"bindProperty\":{\"String\":\"authorName\"},\"visible\":{\"Boolean\":\"false\"}, \"baseFieldPrimaryCss\":{\"String\":\"postenOnLabelFieldBase\"}}}, \"authorLblFld\":{\"config\":{\"labelfieldcss\":{\"String\":\"postenOnLabelField\"}, \"baseFieldPrimaryCss\":{\"String\":\"postenOnLabelFieldBase\"},\"visible\":{\"Boolean\":\"false\"}, \"displayTxt\":{\"String\":\"Author\"}}}, \"content\":{\"config\":{\"bindProperty\":{\"String\":\"contentBlobId\"}, \"baseFieldPrimaryCss\":{\"String\":\"article-SummaryHTMLFieldBase\"}, \"htmlfieldcss\":{\"String\":\"article-summaryHTMLField\"}}}, \"postedDate\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"postenOnLabelFieldBase\"}, \"bindProperty\":{\"String\":\"createdOn\"},\"conversionFormat\":{\"String\":\"MMM dd \'\'yy \'at\' HH:mm\"}, \"DisplayFormat\":{\"String\":\"dateTimeDisplay\"}}}, \"postedOn\":{\"config\":{\"labelfieldcss\":{\"String\":\"postenOnLabelField\"}, \"baseFieldPrimaryCss\":{\"String\":\"postenOnLabelFieldBase\"}, \"displayTxt\":{\"String\":\"Posted On\"}}}, \"entityLink\":{\"config\":{\"bindProperty\":{\"String\":\"title\"}, \"baseFieldPrimaryCss\":{\"String\":\"article-titleLinkFieldBase\"}, \"widgetType\":{\"Integer\":\"1\"}, \"acss\":{\"String\":\"article-titleLinkField\"}, \"tokenValue\":{\"String\":\"eventName=onViewArticle&&entityId=\"}}}}}}}",
  articleDetailsContentSnippet: "{\"config\":{\"view\":{\"config\":{\"authorNameLbl\":{\"config\":{\"labelfieldcss\":{\"String\":\"postenDateLabelField\"}, \"bindProperty\":{\"String\":\"authorName\"},\"visible\":{\"Boolean\":\"true\"}, \"baseFieldPrimaryCss\":{\"String\":\"postenOnLabelFieldBase\"}}}, \"authorLblFld\":{\"config\":{\"labelfieldcss\":{\"String\":\"postenOnLabelField\"}, \"baseFieldPrimaryCss\":{\"String\":\"postenOnLabelFieldBase\"},\"visible\":{\"Boolean\":\"true\"}, \"displayTxt\":{\"String\":\"Author\"}}}, \"content\":{\"config\":{\"bindProperty\":{\"String\":\"contentBlobId\"}, \"baseFieldPrimaryCss\":{\"String\":\"article-SummaryHTMLFieldBase\"}, \"htmlfieldcss\":{\"String\":\"article-summaryHTMLField\"}}}, \"postedDate\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"postenOnLabelFieldBase\"}, \"bindProperty\":{\"String\":\"createdOn\"},\"conversionFormat\":{\"String\":\"MMM dd \'\'yy \'at\' HH:mm\"}, \"DisplayFormat\":{\"String\":\"dateTimeDisplay\"}}}, \"postedOn\":{\"config\":{\"labelfieldcss\":{\"String\":\"postenOnLabelField\"}, \"baseFieldPrimaryCss\":{\"String\":\"postenOnLabelFieldBase\"}, \"displayTxt\":{\"String\":\"Posted On\"}}}, \"entityLink\":{\"config\":{\"bindProperty\":{\"String\":\"title\"}, \"baseFieldPrimaryCss\":{\"String\":\"article-titleLinkFieldBase\"}, \"widgetType\":{\"Integer\":\"1\"}, \"acss\":{\"String\":\"article-titleLinkField\"}, \"tokenValue\":{\"String\":\"eventName=onViewArticle&&entityId=\"}}}}}, \"model\":{\"config\":{\"queryname\":{\"String\":\"getArticleFromId\"},\"operationname\":{\"String\":\"blogging.BloggingService.getArticle\"}, \"queryParam\":{\"config\":{}}}}}}",
  onArticleCreateSnippet: "{\"config\":{\"view\":{\"config\":{\"editCreateLblCfg\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"appops-LabelField\"}, \"isWordWrap\":{\"Boolean\":\"true\"}, \"displayTxt\":{\"String\":\"Enter Article Content Below\"}}}, \"editCreatePublishCfg\":{\"config\":{\"visible\":{\"Boolean\":\"true\"}, \"widgetType\":{\"Integer\":\"2\"},\"acss\":{\"String\":\"recentPost-articleLinkField\"}, \"tokenValue\":{\"String\":\"eventName=onSubmit\"}, \"defaultValue\":{\"String\":\"Publish\"}}},\"editCreateTFCfg\":{\"config\":{\"suggestionText\":{\"String\":\"Enter Title \"}, \"maxlength\":{\"Integer\":\"50\"},\"bindProperty\":{\"String\":\"title\"}, \"baseFieldPrimaryCss\":{\"String\":\"appops-SpinnerBoxPrimary\"}, \"readOnly\":{\"Boolean\":\"false\"}, \"suggestionPosition\":{\"String\":\"suggestionInline\"}, \"visibleLines\":{\"Integer\":\"1\"}, \"fieldType\":{\"String\":\"txtbox\"}, \"validateOnChange\":{\"Boolean\":\"true\"}, \"errorPosition\":{\"String\":\"bottom\"}}}, \"editCreateContEdtCfg\":{\"config\":{\"FieldMode\":{\"String\":\"FieldEditMode\"}, \"FieldHeight\":{\"String\":\"250px\"}, \"FieldResizeEnable\":{\"Boolean\":\"false\"},\"bindProperty\":{\"String\":\"contentBlobId\"}, \"FieldWidth\":{\"String\":\"500px\"}}}}}, \"interestedEvents\":{\"map\":{\"onLoad\":{\"config\":{\"operation\":{\"String\":\"blogging.BloggingService.getArticle\"}, \"type\":{\"Integer\":\"2\"}}}, \"onSubmit\":{\"config\":{\"operation\":{\"String\":\"blogging.BloggingService.createArticle\"}, \"entityType\":{\"String\":\"Article\"}, \"afterEvent\":{\"String\":\"eventName=onAfterSubmit&&entityId=\"},\"type\":{\"Integer\":\"2\"}}}}}}}",
  onArticleEditSnippet: "{\"config\":{\"view\":{\"config\":{\"editCreateLblCfg\":{\"config\":{\"baseFieldPrimaryCss\":{\"String\":\"appops-LabelField\"}, \"isWordWrap\":{\"Boolean\":\"true\"}, \"displayTxt\":{\"String\":\"Enter Article Content Below\"}}}, \"editCreatePublishCfg\":{\"config\":{\"visible\":{\"Boolean\":\"true\"}, \"widgetType\":{\"Integer\":\"2\"},\"acss\":{\"String\":\"recentPost-articleLinkField\"}, \"tokenValue\":{\"String\":\"eventName=onSubmit\"}, \"defaultValue\":{\"String\":\"Publish\"}}},\"editCreateTFCfg\":{\"config\":{\"suggestionText\":{\"String\":\"Enter Title \"}, \"maxlength\":{\"Integer\":\"50\"},\"bindProperty\":{\"String\":\"title\"}, \"baseFieldPrimaryCss\":{\"String\":\"appops-SpinnerBoxPrimary\"}, \"readOnly\":{\"Boolean\":\"false\"}, \"suggestionPosition\":{\"String\":\"suggestionInline\"}, \"visibleLines\":{\"Integer\":\"1\"}, \"fieldType\":{\"String\":\"txtbox\"}, \"validateOnChange\":{\"Boolean\":\"true\"}, \"errorPosition\":{\"String\":\"bottom\"}}}, \"editCreateContEdtCfg\":{\"config\":{\"FieldHeight\":{\"String\":\"250px\"}, \"bindProperty\":{\"String\":\"contentBlobId\"}, \"FieldWidth\":{\"String\":\"682px\"}}}}}, \"model\":{\"config\":{\"queryname\":{\"String\":\"getArticleFromId\"},\"operationname\":{\"String\":\"blogging.BloggingService.getArticle\"}, \"queryParam\":{\"config\":{}}}}}}",

  entityArticleLink: "{\"config\":{\"view\":{\"config\":{\"entityEditLink\":{\"config\":{\"visible\":{\"Boolean\":\"false\"}, \"widgetType\":{\"Integer\":\"1\"},\"acss\":{\"String\":\"recentPost-articleLinkField\"}, \"tokenValue\":{\"String\":\"onEditArticle\"}, \"defaultValue\":{\"String\":\"Edit\"}}}, \"entityLink\":{\"config\":{\"bindProperty\":{\"String\":\"title\"}, \"widgetType\":{\"Integer\":\"1\"}, \"acss\":{\"String\":\"recentPost-articleLinkField\"}, \"tokenValue\":{\"String\":\"onViewArticle\"}}}}}, \"model\":{\"config\":{\"operationname\":{\"String\":\"blogging.BloggingService.getArticle\"}}}}}",
  entityArticleLinkAuthor: "{\"config\":{\"view\":{\"config\":{\"entityEditLink\":{\"config\":{\"visible\":{\"Boolean\":\"true\"}, \"widgetType\":{\"Integer\":\"1\"},\"acss\":{\"String\":\"recentPost-articleLinkField\"}, \"tokenValue\":{\"String\":\"onEditArticle\"}, \"defaultValue\":{\"String\":\"Edit\"}}}, \"entityLink\":{\"config\":{\"bindProperty\":{\"String\":\"title\"}, \"widgetType\":{\"Integer\":\"1\"},\"acss\":{\"String\":\"recentPost-articleLinkField\"}, \"tokenValue\":{\"String\":\"onViewArticle\"}}}}}, \"interestedEvents\":{\"map\":{\"onLoad\":{\"config\":{\"operation\":{\"String\":\"blogging.BloggingService.getArticle\"}, \"type\":{\"Integer\":\"2\"}}}}}}}",
  blogHomeheaderConfig: "{\"config\":{\"view\":{\"config\":{\"authorHomeLinkConfig\":{\"config\":{\"visible\":{\"Boolean\":\"true\"}, \"widgetType\":{\"Integer\":\"1\"}, \"acss\":{\"String\":\"recentPost-articleLinkField\"}, \"page\":{\"String\":\"authorhome.html\"}, \"defaultValue\":{\"String\":\"Author Home\"}, \"mode\":{\"Integer\":\"2\"}}}, \"allArticleLinkConfig\":{\"config\":{\"visible\":{\"Boolean\":\"true\"}, \"widgetType\":{\"Integer\":\"1\"},\"acss\":{\"String\":\"recentPost-articleLinkField\"},  \"tokenValue\":{\"String\":\"eventName=onViewAllArticle\"}, \"defaultValue\":{\"String\":\"All Articles\"}, \"mode\":{\"Integer\":\"1\"}}}, \"writeArticleLinkConfig\":{\"config\":{\"visible\":{\"Boolean\":\"false\"}, \"widgetType\":{\"Integer\":\"1\"},\"acss\":{\"String\":\"recentPost-articleLinkField\"},  \"tokenValue\":{\"String\":\"eventName=onCreateArticle\"}, \"defaultValue\":{\"String\":\"Write Article\"}, \"mode\":{\"Integer\":\"1\"}}}, \"blogHomeLinkConfig\":{\"config\":{\"visible\":{\"Boolean\":\"true\"}, \"widgetType\":{\"Integer\":\"1\"},\"acss\":{\"String\":\"recentPost-articleLinkField\"}, \"page\":{\"String\":\"dynamicsnippet.html\"}, \"defaultValue\":{\"String\":\"Blog Home\"}, \"mode\":{\"Integer\":\"2\"}}}}}}}",
  authorHomeheaderConfig: "{\"config\":{\"view\":{\"config\":{\"authorHomeLinkConfig\":{\"config\":{\"visible\":{\"Boolean\":\"true\"}, \"widgetType\":{\"Integer\":\"1\"}, \"acss\":{\"String\":\"recentPost-articleLinkField\"}, \"page\":{\"String\":\"authorhome.html\"}, \"defaultValue\":{\"String\":\"Author Home\"}, \"mode\":{\"Integer\":\"2\"}}}, \"writeArticleLinkConfig\":{\"config\":{\"visible\":{\"Boolean\":\"true\"}, \"widgetType\":{\"Integer\":\"1\"},\"acss\":{\"String\":\"recentPost-articleLinkField\"},  \"tokenValue\":{\"String\":\"eventName=onCreateArticle\"}, \"defaultValue\":{\"String\":\"Write Article\"}, \"mode\":{\"Integer\":\"1\"}}}, \"blogHomeLinkConfig\":{\"config\":{\"visible\":{\"Boolean\":\"true\"}, \"widgetType\":{\"Integer\":\"1\"},\"acss\":{\"String\":\"recentPost-articleLinkField\"}, \"page\":{\"String\":\"dynamicsnippet.html\"}, \"defaultValue\":{\"String\":\"Blog Home\"}, \"mode\":{\"Integer\":\"2\"}}}}}}}",

  blogArticleListPlaceHolder: "{\"config\":{\"interestedEvents\":{\"map\":{\"onLoad\":{\"config\":{\"isTransformWidget\":{\"Boolean\":\"true\"}, \"transformInstance\":{\"String\":\"blogArticleListSnippet\"}, \"transformType\":{\"Integer\":\"1\"}, \"transformTo\":{\"String\":\"blogArticleSnippet\"}, \"type\":{\"Integer\":\"1\"}}}}}, \"listBoxField\":{\"config\":{\"operation\":{\"String\":\"blogging.BloggingService.getAllBlogs\"}, \"propertyToDisplay\":{\"String\":\"title\"}, \"listboxDefaultValue\":{\"String\":\"Blog\"}}}}}",
//  blogArticleListSnippet: "{\"config\":{\"view\":{\"config\":{\"blogListBoxField\":{\"config\":{\"operation\":{\"String\":\"blogging.BloggingService.getAllBlogs\"}, \"propertyToDisplay\":{\"String\":\"title\"}, \"listboxDefaultValue\":{\"String\":\"All Blog\"}}}, \"articleListTree\":{\"config\":{\"view\":{\"config\":{\"listSnippetInstanceType\":{\"String\":\"entityArticleLink\"}, \"listSnippetType\":{\"String\":\"entityLinkSnippet\"}, \"compponent.view.headervalue\":{\"String\":\"Recent Posts\"}, \"compponent.view.header\":{\"Boolean\":\"true\"}}}, \"model\":{\"config\":{\"depthConfigList\":{\"arrayList\":[{\"config\":{\"treeDepthQuery\":{\"String\":\"getArticlesForBlog\"}, \"treeDepthParam\":{\"arrayList\":[{\"config\":{\"value\":{\"Long\":\"0\"}, \"paramName\":{\"String\":\"parentId\"}}},{\"config\":{\"paramName\":{\"String\":\"blogId\"}, \"entityProp\":{\"String\":\"id\"}, \"paramType\":{\"String\":\"contextParam\"}}}]}}}]}, \"treeOperation\":{\"String\":\"blogging.BloggingService.getArticles\"}}}, \"currentRequestedDepth\":{\"Integer\":\"-1\"},\"interestedEvents\":{\"map\":{ \"onBlogSelected\":{\"config\":{\"query\":{\"String\":\"getArticlesForBlog\"}, \"operation\":{\"String\":\"blogging.BloggingService.getArticles\"},\"eventParam\":{\"String\":\"blogId\"}, \"type\":{\"Integer\":\"3\"}}}}}}}}}, \"interestedFieldEvents\":{\"map\":{\"29##blogListBoxField\":{\"config\":{\"appEvent\":{\"String\":\"onBlogSelected\"},\"eventDataId\":{\"String\":\"blog\"}, \"type\":{\"Integer\":\"1\"}}}}}}}",
  blogArticleListSnippet: "{\"config\":{\"view\":{\"config\":{\"blogListBoxField\":{\"config\":{\"operation\":{\"String\":\"blogging.BloggingService.getAllBlogs\"}, \"propertyToDisplay\":{\"String\":\"title\"}, \"listboxDefaultValue\":{\"String\":\"All Blog\"}}}, \"articleListTree\":{\"config\":{\"view\":{\"config\":{\"treeSnippetInstanceType\":{\"String\":\"entityArticleLink\"}, \"treeSnippetType\":{\"String\":\"entityLinkSnippet\"}, \"compponent.view.headervalue\":{\"String\":\"Recent Posts\"}, \"compponent.view.header\":{\"Boolean\":\"true\"}}}, \"model\":{\"config\":{\"depthConfigList\":{\"map\":{\"0\":{\"config\":{\"treeDepthQuery\":{\"String\":\"getArticleFromParentId\"}, \"treeDepthParam\":{\"arrayList\":[{\"config\":{\"value\":{\"Long\":\"0\"}, \"paramName\":{\"String\":\"parentId\"}}},{\"config\":{\"paramName\":{\"String\":\"blogId\"}, \"entityProp\":{\"String\":\"blog.id\"}, \"paramType\":{\"String\":\"contextParam\"}}}]}}}, \"1\":{\"config\":{\"treeDepthQuery\":{\"String\":\"getArticleFromParentId\"}, \"treeDepthParam\":{\"arrayList\":[{\"config\":{\"paramName\":{\"String\":\"parentId\"}, \"entityProp\":{\"String\":\"id\"}, \"paramType\":{\"String\":\"entityParam\"}}},{\"config\":{\"paramName\":{\"String\":\"blogId\"}, \"entityProp\":{\"String\":\"blog.id\"}, \"paramType\":{\"String\":\"contextParam\"}}}]}}}}}, \"treeOperation\":{\"String\":\"blogging.BloggingService.getArticles\"}}}, \"currentRequestedDepth\":{\"Integer\":\"-1\"},\"interestedEvents\":{\"map\":{ \"onBlogSelected\":{\"config\":{\"updateConfiguration\":{\"config\":{\"currentRequestedDepth\":{\"Integer\":\"0\"}}}}}}}}}}}, \"interestedFieldEvents\":{\"map\":{\"29##blogListBoxField\":{\"config\":{\"appEvent\":{\"String\":\"onBlogSelected\"},\"updateAppContextProp\":{\"String\":\"blog\"}}}}}}}",
  applicationContext: "{\"config\":{\"contextparam\":{\"arrayList\":[\"blog\"]}}}",
  
  
  
  
  /*****************************/
  articleSnippetListConfig: "{\"config\":{\"view\":{\"config\":{\"listSnippetInstanceType\":{\"String\":\"articleSummaryContentSnippet\"}, \"listSnippetType\":{\"String\":\"entityContentSnippet\"}, \"compponent.view.headervalue\":{\"String\":\"Recent Posts\"}, \"compponent.view.header\":{\"Boolean\":\"true\"}}}, \"model\":{\"config\":{\"queryname\":{\"String\":\"getArticlesForBlog\"},\"operationname\":{\"String\":\"blogging.BloggingService.getArticles\"}, \"queryParam\":{\"config\":{}}}}}}",
  articleSnippetPlaceHolder:"{\"config\":{\"interestedEvents\":{\"map\":{\"onBlogSelected\":{\"config\":{\"isUpdateConfiguration\":{\"Boolean\":\"true\"}, \"updateConfiguration\":{\"config\":{\"model.queryParam.blogId\":{\"String\":\"ac.blog.id\"}}}, \"isTransformWidget\":{\"Boolean\":\"true\"}, \"transformInstance\":{\"String\":\"articleSnippetListConfig\"}, \"transformType\":{\"Integer\":\"2\"}, \"transformTo\":{\"String\":\"listComponent\"}}}, \"onViewArticle\":{\"config\":{\"isUpdateConfiguration\":{\"Boolean\":\"true\"}, \"updateConfiguration\":{\"config\":{\"model.queryParam.id\":{\"String\":\"evt\"}}}, \"isTransformWidget\":{\"Boolean\":\"true\"}, \"transformInstance\":{\"String\":\"articleDetailsContentSnippet\"}, \"transformType\":{\"Integer\":\"1\"}, \"transformTo\":{\"String\":\"entityContentSnippet\"}, \"type\":{\"Integer\":\"1\"}}}, \"onEditArticle\":{\"config\":{\"isUpdateConfiguration\":{\"Boolean\":\"true\"}, \"updateConfiguration\":{\"config\":{\"model.queryParam.id\":{\"String\":\"evt\"}}}, \"isTransformWidget\":{\"Boolean\":\"true\"}, \"transformInstance\":{\"String\":\"onArticleEditSnippet\"}, \"transformType\":{\"Integer\":\"1\"}, \"transformTo\":{\"String\":\"entityEditCreateSnippet\"}, \"type\":{\"Integer\":\"1\"}}}}}}}",
  blogArticleListTreePlaceHolder: "{\"config\":{\"interestedEvents\":{\"map\":{\"onBlogSelected\":{\"config\":{\"isUpdateConfiguration\":{\"Boolean\":\"true\"}, \"updateConfiguration\":{\"config\":{\"currentRequestedDepth\":{\"Integer\":\"0\"}, \"model.0.queryParam.parentId\":{\"Long\":\"0\"}, \"model.0.queryParam.blogId\":{\"String\":\"ac.blog.id\"}}}, \"isTransformWidget\":{\"Boolean\":\"true\"}, \"transformInstance\":{\"String\":\"articleListTree\"}, \"transformType\":{\"Integer\":\"2\"}, \"transformTo\":{\"String\":\"listTreeComponent\"}}}}}}}",
  articleListTree:"{\"config\":{\"view\":{\"config\":{\"treeSnippetInstanceType\":{\"String\":\"entityArticleLink\"}, \"treeSnippetType\":{\"String\":\"entityLinkSnippet\"}, \"compponent.view.headervalue\":{\"String\":\"Recent Posts\"}, \"compponent.view.header\":{\"Boolean\":\"true\"}}}, \"model\":{\"config\":{\"0\":{\"config\":{\"treeDepthQuery\":{\"String\":\"getArticleFromParentId\"}, \"queryParam\":{\"config\":{}}}}, \"1\":{\"config\":{\"treeDepthQuery\":{\"String\":\"getArticleFromParentId\"}, \"queryParam\":{\"config\":{}}}}, \"treeOperation\":{\"String\":\"blogging.BloggingService.getArticles\"}}}, \"currentRequestedDepth\":{\"Integer\":\"-1\"},\"interestedEvents\":{\"map\":{ \"onBlogSelected\":{\"config\":{\"updateConfiguration\":{\"config\":{\"currentRequestedDepth\":{\"Integer\":\"0\"}}}}}}}}}",
  
  
  authorBlogArticleListPlaceHolder: "{\"config\":{\"interestedEvents\":{\"map\":{\"onLoad\":{\"config\":{\"isUpdateConfiguration\":{\"Boolean\":\"false\"}, \"isTransformWidget\":{\"Boolean\":\"true\"}, \"transformInstance\":{\"String\":\"authorblogArticleListSnippet\"}, \"transformType\":{\"Integer\":\"1\"}, \"transformTo\":{\"String\":\"blogArticleSnippet\"}, \"type\":{\"Integer\":\"1\"}}}}}, \"listBoxField\":{\"config\":{\"operation\":{\"String\":\"blogging.BloggingService.getAllBlogs\"}, \"propertyToDisplay\":{\"String\":\"title\"}, \"listboxDefaultValue\":{\"String\":\"Blog\"}}}}}",
  authorblogArticleListSnippet: "{\"config\":{\"view\":{\"config\":{\"blogListBoxField\":{\"config\":{\"operation\":{\"String\":\"blogging.BloggingService.getAllBlogs\"}, \"propertyToDisplay\":{\"String\":\"title\"}, \"listboxDefaultValue\":{\"String\":\"All Blog\"}}}, \"articleListTree\":{\"config\":{\"view\":{\"config\":{\"treeSnippetInstanceType\":{\"String\":\"entityArticleLink\"}, \"treeSnippetType\":{\"String\":\"entityLinkSnippet\"}, \"compponent.view.headervalue\":{\"String\":\"Recent Posts\"}, \"compponent.view.header\":{\"Boolean\":\"true\"}}}, \"model\":{\"config\":{\"depthConfigList\":{\"map\":{\"0\":{\"config\":{\"treeDepthQuery\":{\"String\":\"getArticleFromParentId\"}, \"treeDepthParam\":{\"arrayList\":[{\"config\":{\"value\":{\"Long\":\"0\"}, \"paramName\":{\"String\":\"parentId\"}}},{\"config\":{\"paramName\":{\"String\":\"blogId\"}, \"entityProp\":{\"String\":\"blog.id\"}, \"paramType\":{\"String\":\"contextParam\"}}}]}}}, \"1\":{\"config\":{\"treeDepthQuery\":{\"String\":\"getArticleFromParentId\"}, \"treeDepthParam\":{\"arrayList\":[{\"config\":{\"paramName\":{\"String\":\"parentId\"}, \"entityProp\":{\"String\":\"id\"}, \"paramType\":{\"String\":\"entityParam\"}}},{\"config\":{\"paramName\":{\"String\":\"blogId\"}, \"entityProp\":{\"String\":\"blog.id\"}, \"paramType\":{\"String\":\"contextParam\"}}}]}}}}}, \"treeOperation\":{\"String\":\"blogging.BloggingService.getArticles\"}}}, \"currentRequestedDepth\":{\"Integer\":\"-1\"},\"interestedEvents\":{\"map\":{ \"onBlogSelected\":{\"config\":{\"updateConfiguration\":{\"config\":{\"currentRequestedDepth\":{\"Integer\":\"0\"}}}}}}}}}}}, \"interestedFieldEvents\":{\"map\":{\"29##blogListBoxField\":{\"config\":{\"appEvent\":{\"String\":\"onBlogSelected\"},\"updateAppContextProp\":{\"String\":\"blog\"}}}}}}}",
  authorBlogArticleListTreePlaceHolder: "{\"config\":{\"interestedEvents\":{\"map\":{\"onBlogSelected\":{\"config\":{\"isUpdateConfiguration\":{\"Boolean\":\"true\"}, \"updateConfiguration\":{\"config\":{\"currentRequestedDepth\":{\"Integer\":\"0\"}, \"model.0.queryParam.parentId\":{\"Long\":\"0\"}, \"model.0.queryParam.blogId\":{\"String\":\"ac.blog.id\"}}}, \"isTransformWidget\":{\"Boolean\":\"true\"}, \"transformInstance\":{\"String\":\"authorArticleListTree\"}, \"transformType\":{\"Integer\":\"2\"}, \"transformTo\":{\"String\":\"listTreeComponent\"}}}}}}}",
  authorArticleListTree:"{\"config\":{\"view\":{\"config\":{\"treeSnippetInstanceType\":{\"String\":\"entityArticleLinkAuthor\"}, \"treeSnippetType\":{\"String\":\"entityLinkSnippet\"}, \"compponent.view.headervalue\":{\"String\":\"Recent Posts\"}, \"compponent.view.header\":{\"Boolean\":\"true\"}}}, \"model\":{\"config\":{\"0\":{\"config\":{\"treeDepthQuery\":{\"String\":\"getArticleFromParentId\"}, \"queryParam\":{\"config\":{}}}}, \"1\":{\"config\":{\"treeDepthQuery\":{\"String\":\"getArticleFromParentId\"}, \"queryParam\":{\"config\":{}}}}, \"treeOperation\":{\"String\":\"blogging.BloggingService.getArticles\"}}}, \"currentRequestedDepth\":{\"Integer\":\"-1\"},\"interestedEvents\":{\"map\":{ \"onBlogSelected\":{\"config\":{\"updateConfiguration\":{\"config\":{\"currentRequestedDepth\":{\"Integer\":\"0\"}}}}}}}}}",

};
