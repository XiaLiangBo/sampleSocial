package com.britesnow.samplesocial.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import com.britesnow.samplesocial.entity.User;
import com.britesnow.samplesocial.service.DropboxFileService;
import com.britesnow.snow.web.RequestContext;
import com.britesnow.snow.web.handler.annotation.WebResourceHandler;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.britesnow.snow.web.param.annotation.WebPath;
import com.britesnow.snow.web.param.annotation.WebUser;
import com.britesnow.snow.web.rest.annotation.WebGet;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DropboxFileHandler {
	
	@Inject
	private DropboxFileService dropboxFileService;
	
	@WebGet("/dropbox/getMetadata")
	public WebResponse getFileMetadata(@WebParam("path") String path,@WebUser User user){
		if(path==null)
			path="";
		dropboxFileService.getThumbnails(path, user.getId());
		return WebResponse.success(dropboxFileService.getMetadata(path, user.getId()));
	}
	
	@WebResourceHandler(matches="/dropbox/getFile/.*")
	public void getFile(@WebPath String path,@WebUser User user,RequestContext rc) throws IOException{
		path = path.substring("/dropbox/getFile".length());
		InputStream in = dropboxFileService.getFile(path, user.getId());
		HttpServletResponse res = rc.getRes();
		res.addHeader("Content-Disposition", "attachment;filename="+path.substring(path.lastIndexOf("/")+1));
		res.addHeader("Content-Length", "" + in.available());
		OutputStream out = res.getOutputStream();
		res.setContentType("application/octet-stream");
		int length = 0;
		byte[] data = new byte[10240];
		while((length=in.read(data))!=-1){
			out.write(data, 0, length);
		}
		in.close();
		out.close();
	}
	
	@WebResourceHandler(matches="/dropbox/thumbnails/.*")
	public void getThumbnails(@WebPath String path,@WebUser User user,RequestContext rc) throws IOException{
		path = path.substring("/dropbox/thumbnails".length());
		System.out.println(path);
		InputStream in = dropboxFileService.getThumbnails(path, user.getId());
		HttpServletResponse res = rc.getRes();
		//res.addHeader("Content-Disposition", "attachment;filename="+path.substring(path.lastIndexOf("/")+1));
		//res.addHeader("Content-Length", "" + in.available());
		OutputStream out = res.getOutputStream();
		res.setContentType("image/jpeg");
		int length = 0;
		byte[] data = new byte[10240];
		while((length=in.read(data))!=-1){
			out.write(data, 0, length);
		}
		in.close();
		out.close();
	}
}
