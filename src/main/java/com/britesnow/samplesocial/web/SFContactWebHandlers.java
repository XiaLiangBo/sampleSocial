package com.britesnow.samplesocial.web;

import java.util.Map;

import net.sf.json.JSONObject;

import com.britesnow.samplesocial.manager.OAuthManager;
import com.britesnow.samplesocial.oauth.ServiceType;
import com.britesnow.samplesocial.service.SalesForceAuthService;
import com.britesnow.samplesocial.service.SalesForceService;
import com.britesnow.snow.web.RequestContext;
import com.britesnow.snow.web.param.annotation.WebModel;
import com.britesnow.snow.web.param.annotation.WebParam;
import com.britesnow.snow.web.rest.annotation.WebGet;
import com.britesnow.snow.web.rest.annotation.WebPost;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SFContactWebHandlers {
    @Inject
    private SalesForceService salesforceService;
    @Inject
    private SalesForceAuthService salesForceAuthService;
    @Inject
    private OAuthManager oauthManager;
    
    @WebGet("/salesforce/listContacts")
    public Map listSFContacts(@WebModel Map m,
                               @WebParam("pageSize") Integer pageSize, @WebParam("pageIndex") Integer pageIndex,RequestContext rc) {
        String token = salesForceAuthService.getSocialIdEntity().getToken();
        if(pageIndex == null){
            pageIndex = 0;
        }
        if(pageSize == null){
            pageSize = 10;
        }
        String instanceUrl = oauthManager.getInfo(ServiceType.SalesForce).get("instance_url");
        Map result = salesforceService.listContacts(token,instanceUrl, pageIndex * pageSize, pageSize);
        m.putAll(result);
        return m ;
    }
	
	@WebGet("/salesforce/getContact")
	public Map getContact(@WebModel Map m,@WebParam("id") String id,RequestContext rc) {
	    String token = salesForceAuthService.getSocialIdEntity().getToken();
	    String instanceUrl = oauthManager.getInfo(ServiceType.SalesForce).get("instance_url");
	    JSONObject obj = salesforceService.getContact(token, instanceUrl, id);
	    m.put("result", obj);
	    return m ;
	}
	
	@WebPost("/salesforce/saveContact")
	public Object saveSFContact(@WebModel Map m,@WebParam("id") String id,@WebParam("name") String name,RequestContext rc) {
	    String token = salesForceAuthService.getSocialIdEntity().getToken();
	    String instanceUrl = oauthManager.getInfo(ServiceType.SalesForce).get("instance_url");
	    salesforceService.saveContact(token, instanceUrl, id, name);
        return null;
	}
	
	@WebPost("/salesforce/deleteContact")
	public Object deleteSFContact(@WebModel Map m,@WebParam("id") String id,RequestContext rc) {
        String token = salesForceAuthService.getSocialIdEntity().getToken();
        String instanceUrl = oauthManager.getInfo(ServiceType.SalesForce).get("instance_url");
        salesforceService.deleteContact(token,instanceUrl,id);
        return null;
	}
}
