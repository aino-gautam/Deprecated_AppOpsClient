var snippetStore = {
entityLinkSnippet: "<table cellspacing=\"5\" cellpadding=\"0\" style=\"width: 100%;\"><tbody><tr><td align=\"left\" style=\"vertical-align: top; \">" +
					"<span id=\"entityLink\" appopsField=\"true\" widgetType= \"actionField\" data-config=\"entityLink\"></span>" +
					"</td><td style=\"float: right;" + 
					"\">" + 
					"<span id=\"entityEditLink\" appopsField=\"true\" widgetType=\"actionField\" data-config=\"entityEditLink\"></span>" + 
					"</td></tr></tbody></table>",
entityContentSnippet:"		<header class=\"entry-header\">\n" + 
		"			<h1 class=\"entry-title\">\n" + 
		"				<span id=\"entityTitleLink\" appopsField=\"true\" widgetType= \"actionField\" data-config=\"entityLink\"></span>\n" + 
		"			</h1>\n" + 
		"		\n" + 
		//"			<div class=\"entry-meta\">\n" + 
		"				<table cellspacing=\"10\" cellpadding=\"0\">\n" + 
		"					<tbody>\n" + 
		"						<tr>\n" + 
		"							<td align=\"left\" style=\"vertical-align: top;\">\n" + 
		"								<span id=\"postedOnLbl\" appopsField=\"true\" widgetType= \"labelField\" data-config=\"postedOn\"></span>\n" + 
	    "							</td>\n" + 
    	"							<td align=\"left\" style=\"vertical-align: top;\">\n" + 
		"								<span id=\"postedDateLbl\" appopsField=\"true\" widgetType= \"dateTimeFieldField\" data-config=\"postedDate\"></span>\n" + 
		"							</td>\n" + 
		"						</tr>\n" + 
		"						<tr>\n" + 
		"							<td align=\"left\" style=\"vertical-align: top;\">\n" + 
		"								<span id=\"authorLblField\" appopsField=\"true\" widgetType= \"labelField\" data-config=\"authorLblFld\"></span>\n" + 
	    "							</td>\n" + 
    	"							<td align=\"left\" style=\"vertical-align: top;\">\n" + 
		"								<span id=\"authorNameLbl\" appopsField=\"true\" widgetType= \"labelField\" data-config=\"authorNameLbl\"></span>\n" + 
		"							</td>\n" + 
		"						</tr>\n" + 
		"					</tbody>\n" + 
		"				</table>\n" + 
	//	"			</div>\n" + 
		"		</header>\n" + 
		"		<div class=\"entry-content\">\n" + 
		"				<span id=\"contentHtmlField\" appopsField=\"true\" widgetType= \"htmlField\" data-config=\"content\"></span>\n" + 
		"		</div>",
entityEditCreateSnippet:"<table cellspacing=\"10\" cellpadding=\"0\">\n" + 
		"		<tbody>\n" + 
		"			<tr>\n" + 
		"				<td align=\"left\" style=\"vertical-align: top;\">\n" + 
		"					<span id= \"articleTitleField\" appopsfield=\"true\" widgetType= \"textField\" data-config=\"editCreateTFCfg\"> </span>\n" + 
		"				</td>\n" + 
		"			</tr>\n" + 
		"			<tr>\n" + 
		"				<td align=\"left\" style=\"vertical-align: top;\">\n" + 
		"					<span id= \"editCreateLbl\" appopsfield=\"true\" widgetType= \"labelField\" data-config=\"editCreateLblCfg\"> </span>\n" + 
		"				</td>\n" + 
		"			</tr>\n" + 
		"			<tr>\n" + 
		"				<td align=\"left\" style=\"vertical-align: top;\">\n" + 
		"					<span id= \"editCreateContEdt\" appopsfield=\"true\" widgetType= \"htmlEditorField\" data-config=\"editCreateContEdtCfg\"> </span>\n" + 
		"				</td>\n" + 
		"			</tr>\n" +
		"           <tr>\n" + 
		"				<td align=\"left\" style=\"vertical-align: top;\">\n" + 
		"					<span id= \"editCreateContEdt\" appopsfield=\"true\" widgetType= \"actionField\" data-config=\"editCreatePublishCfg\"> </span>\n" + 
		"				</td>\n" + 
		"			</tr>\n" + 
		"		</tbody>\n" + 
		"	</table>",
pageHeaderSnippet:"<table style=\"float: right;\" cellspacing=\"5\" cellpadding=\"0\"> \n" + 
		"				<tbody> \n" + 
		"					<tr>\n" + 
		"                       <td> \n" + 
		"							<span id= \"allArticleLink\" appopsfield=\"true\" widgetType= \"actionField\" data-config=\"allArticleLinkConfig\"> </span>\n" + 
		"						</td>\n" +
		"						<td> \n" + 
		"							<span id= \"blogHomeLink\" appopsfield=\"true\" widgetType= \"actionField\" data-config=\"blogHomeLinkConfig\"> </span>\n" + 
		"						</td>\n" + 
		"                       <td> \n" + 
		"							<span id= \"writeArticleActionLink\" appopsfield=\"true\" widgetType= \"actionField\" data-config=\"writeArticleLinkConfig\"> </span>\n" + 
		"						</td>" +
		"						<td>\n" + 
		"							<span id= \"authorHomeActionLink\" appopsfield=\"true\" widgetType= \"actionField\" data-config=\"authorHomeLinkConfig\"> </span>\n" + 
		"						</td>	\n" + 
		"					</tr>\n" + 
		"				</tbody>\n" + 
        "  		  </table>",		
blogArticleSnippet: "<div class=\"art-vmenublockheader\">"+ 
		"				<h3 class=\"t\">Blogs</h3>"+ 
		"			</div>" +
		"			<table cellspacing=\"1\" cellpadding=\"0\" style=\"width: 100%;\">" +
		"				<tbody>" +
		"					<tr>" +
		"						<td align=\"left\" style=\"vertical-align: top; \">" +
		"							<span id=\"blogListBoxField\" appopsField=\"true\" widgetType= \"listBoxField\" data-config=\"blogListBoxField\"></span>" +
		"						</td>" +
		"					</tr>" +
		"					<tr>" +
		"						<td align=\"left\" style=\"vertical-align: top; \">" +
		/*"							<span id=\"articleListTree\" appopsComponent=\"true\" widgetType= \"listTreeComponent\" data-config=\"articleListTree\"></span>" +*/
		"						</td>" +
		"					</tr>" +
		"				</tbody>" +
		"			</table>",
		
productEntitySnippet:"<table> \n" + 
		"				<tbody> \n" + 
		"					<tr>\n" + 
		"                       <td colspan=\"2\" align =\"center\"> \n" + 
		"							<span id= \"productImage\" appopsfield=\"true\" widgetType= \"imageField\" data-config=\"productImageConfig\"> </span>\n" + 
		"						</td>\n" +
		"                   </tr>\n" + 
		"		    		<tr>\n" + 
		"                    	<td colspan=\"2\" align =\"center\"> \n" + 
		"							<span id= \"productName\" appopsfield=\"true\" widgetType= \"labelField\" data-config=\"productNameConfig\"> </span>\n" + 
		"						</td>\n" + 
		"                   </tr>\n" + 
 		"					<tr>\n" + 
		"                    	<td> \n" + 
		"							<span id= \"price\" appopsfield=\"true\" widgetType= \"labelField\" data-config=\"priceConfig\"> </span>\n" + 
		"						</td>\n" + 
		"					 <td > \n" + 
		"							<span id= \"pinkHeartImage\" appopsfield=\"true\" widgetType= \"imageField\" data-config=\"pinkHeartImageConfig\"> </span>\n" + 
		"					 </td>\n" +
		" 						<td> \n" + 
		"							<span id= \"likes\" appopsfield=\"true\" widgetType= \"labelField\" data-config=\"noOfLikesLabelConfig\"> </span>\n" + 
		"						</td>\n" + 
		"                  </tr>\n" + 
		"					<tr>\n" + 
		"                    <td colspan=\"2\" align =\"center\"> \n" + 
		"							<span id= \"cartImage\" appopsfield=\"true\" widgetType= \"imageField\" data-config=\"cartImageConfig\"> </span>\n" + 
		"					 </td>\n" +
		"                  </tr>\n" + 
		"				</tbody>\n" + 
        "  	 		</table>",
}; 