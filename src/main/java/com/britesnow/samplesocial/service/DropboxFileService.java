package com.britesnow.samplesocial.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Verb;

import com.britesnow.samplesocial.entity.SocialIdEntity;
import com.britesnow.snow.util.JsonUtil;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.DropboxAPI.Entry;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session.AccessType;
import com.dropbox.client2.session.WebAuthSession;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DropboxFileService {
	@Inject
	private DropboxAuthService dropboxAuthService;
	
	private static String METADATA = "https://api.dropbox.com/1/metadata/dropbox";
	private static String THUMBNAILS = "https://api-content.dropbox.com/1/thumbnails/dropbox";
	private static String FILES = "https://api-content.dropbox.com/1/files/dropbox";
	private static String CREATEFOLDER = "https://api.dropbox.com/1/fileops/create_folder";
	private static String DELETE = "https://api.dropbox.com/1/fileops/delete";
	@SuppressWarnings("unused")
	private static String FILESPUT = "https://api-content.dropbox.com/1/files_put/dropbox";
	private static String SHARE = "https://api.dropbox.com/1/shares/dropbox";
	public Map getMetadata(String path,Long userId,boolean includeDeleted,Locale locale){
		OAuthRequest request = new OAuthRequest(Verb.GET,METADATA+path);
		dropboxAuthService.setAuthorizationHeader(request, userId);
		request.addQuerystringParameter("locale", locale.toString());
		request.addQuerystringParameter("include_deleted", includeDeleted+"");
    	String metadata = request.send().getBody();
    	return JsonUtil.toMapAndList(metadata);
	}
	
	public InputStream getThumbnails(String path,Long userId){
		OAuthRequest request = new OAuthRequest(Verb.GET,THUMBNAILS+path);
		dropboxAuthService.setAuthorizationHeader(request, userId);
    	return request.send().getStream();
	}
	
	public InputStream getFile(String path,Long userId){
		OAuthRequest request = new OAuthRequest(Verb.GET,FILES+path);
		dropboxAuthService.setAuthorizationHeader(request, userId);
		return request.send().getStream();
	}
	
	public Map createFolder(String path,Long userId){
		OAuthRequest request = new OAuthRequest(Verb.POST,CREATEFOLDER);
		dropboxAuthService.setAuthorizationHeader(request, userId);
		request.addBodyParameter("path", path);
		request.addBodyParameter("root", "dropbox");
    	String metadata = request.send().getBody();
    	System.out.println(metadata);
    	return JsonUtil.toMapAndList(metadata);
	}
	
	public Map delete(String path,Long userId){
		OAuthRequest request = new OAuthRequest(Verb.POST,DELETE);
		dropboxAuthService.setAuthorizationHeader(request, userId);
		request.addBodyParameter("path", path);
		request.addBodyParameter("root", "dropbox");
    	String metadata = request.send().getBody();
    	System.out.println(metadata);
    	return JsonUtil.toMapAndList(metadata);
	}
	
	public Entry upload(FileItem item,String path,Long userId) throws IOException, DropboxException{
		WebAuthSession session = new WebAuthSession(new AppKeyPair("ulvnx4aushyzhe3", "jupanq7xdsht8md"),AccessType.DROPBOX);
	    SocialIdEntity soId = dropboxAuthService.getSocialIdEntity(userId);
	    AccessTokenPair accessPair = new AccessTokenPair(soId.getToken(),soId.getSecret());
	    session.setAccessTokenPair(accessPair);
	    DropboxAPI<WebAuthSession> dropboxApi= new DropboxAPI<WebAuthSession>(session);
    	return dropboxApi.putFile(path, item.getInputStream(),item.getSize(), null, null);
	}
	
	public Map share(String path,Long userId){
		OAuthRequest request = new OAuthRequest(Verb.POST,SHARE+path);
		dropboxAuthService.setAuthorizationHeader(request, userId);
    	String metadata = request.send().getBody();
    	System.out.println(metadata);
    	return JsonUtil.toMapAndList(metadata);
	}
}
