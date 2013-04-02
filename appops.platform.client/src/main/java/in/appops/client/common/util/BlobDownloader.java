package in.appops.client.common.util;

import com.google.gwt.core.client.GWT;

public class BlobDownloader {
	
	 public String getImageDownloadURL(String blobId){
		return GWT.getHostPageBaseURL()+"download?blobId="+blobId;
		 
	 }
	 public String getIconDownloadURL(String blobId){
		 return GWT.getHostPageBaseURL()+"download?blobId=i_"+blobId;
		 
	 }

	 public String getThumbNailDownloadURL(String blobId){
		 return GWT.getHostPageBaseURL()+"download?blobId=t_"+blobId;
		 
	 }
}
