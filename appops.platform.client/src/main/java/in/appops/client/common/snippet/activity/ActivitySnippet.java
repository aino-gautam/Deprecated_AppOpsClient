/**
 * 
 */
package in.appops.client.common.snippet.activity;

import in.appops.client.common.config.field.LabelField;
import in.appops.client.common.fields.ImageField;
import in.appops.client.common.snippet.RowSnippet;
import in.appops.client.common.util.BlobDownloader;
import in.appops.client.common.util.PostContentParser;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.entity.Property;
import in.appops.platform.core.shared.Configuration;
import in.appops.platform.core.util.AppOpsException;
import in.appops.platform.server.core.services.activity.constant.ActivityConstant;
import in.appops.platform.server.core.services.social.constant.PostConstant;

import java.io.Serializable;
import java.util.Date;

import com.google.gwt.event.dom.client.ErrorEvent;
import com.google.gwt.event.dom.client.ErrorHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author mahesh@ensarm.com
 *
 */
public class ActivitySnippet extends RowSnippet {

	private DockPanel postSnippetPanel;
	private LabelField timeLbl;
	private VerticalPanel postContentPanel;
	private HorizontalPanel spaceIconPlusTimePanel ;
	private String mode;
	
	public static String MODE_UPDATE = "modeUpdate";
	public static String MODE_ACTIVITY = "modeActivity";
	public static final String ACTIVITY_SNIPPET = "activitySnippnet";
	public static final String POST_TIME_LABEL = "postTimeLabel";
	public static final String POST_USER_IMAGE = "postUserImage";
	public static final String POST_CONTEXT_LABEL = "postContextLabel";
	public static final String CURSOR_CSS = "opptin-cursor";

	private Entity userEntity;

	public ActivitySnippet() {
		setHeight("100%");
	}

	@Override
	public void initialize(){

		postSnippetPanel = new DockPanel();
		createUi();
	}

	@SuppressWarnings("unchecked")
	public void createUi(){
		userEntity = null;
		String blobUrl = null;

		HorizontalPanel imagePanel = new HorizontalPanel();
		postContentPanel = new VerticalPanel();
		spaceIconPlusTimePanel = new HorizontalPanel();
		final ImageField imageField = new ImageField();

		BlobDownloader blobDownloader = new BlobDownloader();
		Property<Serializable> property=(Property<Serializable>) getEntity().getProperty(ActivityConstant.USERID);
		if(property!=null){
			if(property.getValue() instanceof Long){
				try{
					blobUrl=blobDownloader.getIconDownloadURL(userEntity.getPropertyByName("imgBlobId").toString());
				}
				catch (Exception e) {
					blobUrl	="images/default_userIcon.png";
				}
			} else{
				userEntity=(Entity) property.getValue();
				if(userEntity.getPropertyByName("imgBlobId") != null)
					blobUrl=blobDownloader.getIconDownloadURL(userEntity.getPropertyByName("imgBlobId").toString());
				else
					blobUrl	="images/default_userIcon.png";
			}
		}


		imageField.setConfiguration(getImageFieldConfiguration(blobUrl));
		try {
			imageField.create();
		} catch (AppOpsException e) {
			e.printStackTrace();
		}

		imagePanel.add(imageField);
		imagePanel.setBorderWidth(1);
		postSnippetPanel.add(imagePanel, DockPanel.WEST);
		postSnippetPanel.setCellWidth(imagePanel, "15%");

		imagePanel.setCellVerticalAlignment(imageField, HasVerticalAlignment.ALIGN_MIDDLE);

		imageField.addMouseOverHandler(new MouseOverHandler() {

			@Override
			public void onMouseOver(MouseOverEvent event) {
				if(userEntity == null){
					imageField.setAltText("Image not available");
				} else{
					imageField.setAltText(userEntity.getPropertyByName("username").toString());
				}

			}
		});

		imageField.addErrorHandler(new ErrorHandler() {

			@Override
			public void onError(ErrorEvent event) {
				imageField.setUrl("images/default_Icon.png");
			}
		});

		String xmlContent = getEntity().getPropertyByName(PostConstant.CONTENT).toString();
		PostContentParser postContentParser = new PostContentParser();
		FlowPanel postContentFlowPanel;
		if(mode.equals(MODE_ACTIVITY)) {
			postContentFlowPanel = postContentParser.getSelfMsgComponent(xmlContent);
		} else {
			postContentFlowPanel = postContentParser.getGeneralMsgComponent(xmlContent);
		}

		postContentPanel.add(spaceIconPlusTimePanel);
		postContentPanel.setCellWidth(spaceIconPlusTimePanel, "10%");

		postContentPanel.add(postContentFlowPanel);
		postContentPanel.setCellVerticalAlignment(postContentFlowPanel, HasVerticalAlignment.ALIGN_MIDDLE);
		postContentPanel.setCellHorizontalAlignment(postContentFlowPanel, HasHorizontalAlignment.ALIGN_JUSTIFY);
		postContentPanel.setHeight("100%");
		postContentPanel.setWidth("90%");
		spaceIconPlusTimePanel.setWidth("100%");
		postSnippetPanel.add(postContentPanel, DockPanel.CENTER);
		postSnippetPanel.setCellWidth(postContentPanel, "90%");

		DOM.setStyleAttribute(postContentFlowPanel.getElement(), "margin", "5px");
		DOM.setStyleAttribute(imagePanel.getElement(), "margin", "7px");

		setTime(getEntity());

		add(postSnippetPanel,DockPanel.CENTER);

		postSnippetPanel.setStylePrimaryName(ACTIVITY_SNIPPET);
		imagePanel.setStylePrimaryName(POST_USER_IMAGE);
		postContentFlowPanel.setStylePrimaryName(POST_CONTEXT_LABEL);

		imageField.setStylePrimaryName("activityUserImageIcon");
	}

	private Configuration getImageFieldConfiguration(String url) {
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(ImageField.IMAGEFIELD_BLOBID,url);
		return configuration;
	}

	private void setTime(Entity ent) {
		timeLbl = new LabelField();
		timeLbl.setConfiguration(createConfiguration(true));
		timeLbl.create();
		final Date timeStamp = (Date) ent.getProperty(PostConstant.CREATEDON).getValue();

		Timer timer = new Timer() {


			public void run() {
				String timeAgo = calculateTimeAgo(timeStamp);
				timeLbl.setValue(timeAgo);

			}
		};
		timer.run();
		timer.scheduleRepeating(5000);

		DateTimeFormat dateFormat = DateTimeFormat.getFormat("dd MMM yyyy h:mm");	
		String date = dateFormat.format(timeStamp);



		timeLbl.setTitle(date);
		spaceIconPlusTimePanel.add(timeLbl);
		postContentPanel.add(spaceIconPlusTimePanel);
		spaceIconPlusTimePanel.setCellWidth(timeLbl, "30%");

		DOM.setStyleAttribute(timeLbl.getElement(), "margin", "5px");
		timeLbl.setStylePrimaryName(POST_TIME_LABEL);
	}

	private String calculateTimeAgo(Date activitydate){
		Date currDate = new Date();
		long diffInSec = (currDate.getTime() - activitydate.getTime()) / 1000;
		long second = diffInSec % 60;
		diffInSec/= 60;
		long minute = diffInSec % 60;
		diffInSec /= 60;
		long hour = diffInSec % 24;
		diffInSec /= 24;
		long day = diffInSec;

		String str = "";
		if(day != 0){
			str = day > 1 ? " days " : " day ";
			return Long.toString(day) + str + "ago";
		} else if(hour != 0){
			str = hour > 1 ? " hours " : " hour ";
			return Long.toString(hour) + str + "ago";
		} else if(minute != 0){
			str = minute > 1 ? " minutes " : " minute ";
			return Long.toString(minute) + str + "ago";
		} else{
			str = second > 1 ? " secs " : " sec ";
			return Long.toString(second) + str + "ago";
		}
	}


	public Configuration createConfiguration(boolean wordWrap){
		Configuration configuration = new Configuration();
		configuration.setPropertyByName(LabelField.LabelFieldConstant.LBLFD_ISWORDWRAP, wordWrap);
		return configuration;

	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}