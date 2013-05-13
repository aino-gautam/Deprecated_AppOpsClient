package in.appops.client.gwt.web.ui;

import in.appops.client.common.util.AppEnviornment;
import in.appops.client.common.util.BlobDownloader;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.DispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardAction;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.StandardDispatchAsync;
import in.appops.platform.bindings.web.gwt.dispatch.client.action.exception.DefaultExceptionHandler;
import in.appops.platform.core.entity.Entity;
import in.appops.platform.core.operation.Result;
import in.appops.platform.core.util.EntityList;
import in.appops.platform.server.core.services.media.constant.MediaConstant;
import in.appops.platform.server.core.services.media.constant.TagConstant;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;


public class MediaViewer extends VerticalPanel implements WheelWidgetProvider {
	private final DefaultExceptionHandler	exceptionHandler	= new DefaultExceptionHandler();
	private final DispatchAsync				dispatch			= new StandardDispatchAsync(exceptionHandler);
	private HashMap<Entity, EntityList> photoAlbumMap ;
	private HashMap<String, Row> rowPerAlbum;
	private BlobDownloader blobDownloader ;
	private EntityList photoList;
	private int startIndex = 0;
	private int endIndex = 9;
	
	
	public MediaViewer() {
		
	}
	
	public void initialize(){
		getAllPhotosByAlbum();
	}
	
	/**
	 * Method used to get photos by album.
	 */
	
	@SuppressWarnings("unchecked")
	public void getAllPhotosByAlbum(){
		
		HashMap<String, Object> qparam = new HashMap<String, Object>();
		qparam.put("tagType", "album");
		qparam.put("user", AppEnviornment.getCurrentUser());
			
		StandardAction action = new StandardAction(HashMap.class, "media.MediaService.getAllPhotosByTags", qparam);
		dispatch.execute(action, new AsyncCallback<Result>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Operation failed");
			}

			@Override
			public void onSuccess(Result result) {
				photoAlbumMap = (HashMap<Entity, EntityList>) result.getOperationResult();
				rowPerAlbum =  createRowsPerAlbum(photoAlbumMap);
				initializeWheel();
				
			}
		});
		
	}
	
	public void initializeWheel() {

		Cylinder cylinder = new Cylinder();
		cylinder.setName("cylinder1");
		cylinder.setOrder(0);
		cylinder.setHeight(500);
		cylinder.setRadius(200);
		cylinder.setCoordinates(300, 0);
		cylinder.setSkewMode(false);

		HashMap<String, Row> rowsPerAlbumMap = getRowPerAlbum();

		int index = 0;
		for (String rowName : rowsPerAlbumMap.keySet()) {
			Row row = rowsPerAlbumMap.get(rowName);

			if (index % 2 == 0) {
				row.setIndependent(true);
			} else
				row.setIndependent(false);

			cylinder.addRow(row);

			index++;
		}

		DragonWheelNew wheel = new DragonWheelNew();

		wheel.addCylinder(cylinder);

		wheel.initWidgetPositions(this);

		wheel.setStylePrimaryName("dragonWheel");

		add(wheel);

	}


	
	@SuppressWarnings("unchecked")
	public EntityList getNextPhotosOfAlbum(Entity album){
			
		Map parameters = new HashMap();
		parameters.put("tagName", album.getProperty(TagConstant.NAME).getValue().toString());
			
		StandardAction action = new StandardAction(Entity.class, "media.MediaService.getAllPhotosByTagName", parameters);
		dispatch.execute(action, new AsyncCallback<Result>() {

			@Override
			public void onFailure(Throwable caught) {
				System.out.println("Operation failed");
			}

			@Override
			public void onSuccess(Result result) {
				photoList = (EntityList) result.getOperationResult();
			}
		});
		return photoList;
	}
	
	
	public LinkedHashSet<Widget> getImageWidgetSet(EntityList photoList){
		LinkedHashSet<Widget> imageWidgetSet  = new LinkedHashSet<Widget>();
		
		endIndex = startIndex+9;
		
		for(int index =startIndex ;index<photoList.size() ;index++){
			
			if(index>endIndex)
				break;
			
			Entity photoEnt = photoList.get(index);
			String blobId  = photoEnt.getProperty(MediaConstant.BLOBID).getValue().toString();
			
			ImageWidget imageWidget =new ImageWidget(blobDownloader.getThumbNailDownloadURL(blobId));
			imageWidget.setEntity(photoEnt);
			imageWidgetSet.add(imageWidget);
			imageWidget.setStylePrimaryName("imageWidget");
		}
		
		return imageWidgetSet;
	}
		
	
	/**
	 * For each album of user create row/layer with 10 photos.
	 * @param map
	 * @return
	 */

	public HashMap<String, Row> createRowsPerAlbum(HashMap<Entity, EntityList> map){
		
		HashMap<String, Row> rowMap = new  HashMap<String, Row>();
				
		for (Map.Entry<Entity, EntityList> e : map.entrySet()) {
			
			Entity albumEnt = e.getKey();
			EntityList photList  = e.getValue();
			String name = albumEnt.getProperty(TagConstant.NAME).getValue().toString();
			if(photList.size()!=0){
				Row row =new Row(name);
				row.setIndependent(true);
				row.setEntity(albumEnt);
				row.setStylePrimaryName("rowPanel");
				rowMap.put(name, row);
			}
		}
				
		return rowMap;
	}
	
	
	@Override
	public LinkedHashSet<Widget> getNextWidgetSet(Row row) {
		if(blobDownloader == null)
			blobDownloader = new BlobDownloader();
		
		EntityList nextPhotoList = photoAlbumMap.get(row.getEntity());
		
		LinkedHashSet<Widget> newWidgetSetForRow = getImageWidgetSet(nextPhotoList);
		
		LinkedHashSet<Widget> existingWidgetSetForRow  = (LinkedHashSet<Widget>) row.getWidgetSetForRow();
					
		Object[] existingWidgetArray = existingWidgetSetForRow.toArray();
		
		Object[] newWidgetArray = newWidgetSetForRow.toArray();
					
		if(newWidgetSetForRow.size() == existingWidgetSetForRow.size()){
			return newWidgetSetForRow;
		}else {
			for(int i= 0;i < existingWidgetArray.length;i++){
				if(i > newWidgetArray.length){
					Widget widget = (Widget) existingWidgetArray[i];
					newWidgetSetForRow.add(widget);
				}
			}
		}
			
		return newWidgetSetForRow;
		
	}
		
	@Override
	public Map<Row, LinkedHashSet<Widget>> getNextWidgetSet(Cylinder cyl) {
		if(blobDownloader == null)
			blobDownloader = new BlobDownloader();
		
		Map<Row, LinkedHashSet<Widget>> rowVsWidgetSet =new HashMap<Row, LinkedHashSet<Widget>>();
		
		Map<String, Row> rowmap = cyl.getRowMap();
		
		for (String rowName : rowmap.keySet()){
			Row row =rowmap.get(rowName);
			
			EntityList photoList = photoAlbumMap.get(row.getEntity());
			
			LinkedHashSet<Widget> imageWidgetSet  = getImageWidgetSet(photoList);
										
			rowVsWidgetSet.put(row, imageWidgetSet);
		}
		
		return rowVsWidgetSet;
	}

	public HashMap<String, Row> getRowPerAlbum() {
		return rowPerAlbum;
	}

	public void setRowPerAlbum(HashMap<String, Row> rowPerAlbum) {
		this.rowPerAlbum = rowPerAlbum;
	}
	
	public int getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}


}