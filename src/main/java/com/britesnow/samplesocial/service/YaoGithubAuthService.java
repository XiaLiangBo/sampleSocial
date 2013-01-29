package com.britesnow.samplesocial.service;

import static org.scribe.model.OAuthConstants.EMPTY_TOKEN;

import org.scribe.oauth.OAuthService;

import com.britesnow.samplesocial.dao.SocialIdEntityDao;
import com.britesnow.samplesocial.entity.SocialIdEntity;
import com.britesnow.samplesocial.oauth.OAuthServiceHelper;
import com.britesnow.samplesocial.oauth.ServiceType;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class YaoGithubAuthService implements AuthService{

	@Inject
    private SocialIdEntityDao socialIdEntityDao;
    private OAuthService oAuthService;
	
    @Inject
    public YaoGithubAuthService(OAuthServiceHelper oauthServiceHelper) {
        oAuthService = oauthServiceHelper.getOauthService(ServiceType.SalesForce);
    }

	@Override
	public SocialIdEntity getSocialIdEntity(Long userId) {
		return socialIdEntityDao.getSocialdentity(userId, ServiceType.Github);
	}
	
	public String getAuthorizationUrl() {
	    return oAuthService.getAuthorizationUrl(EMPTY_TOKEN);
	}

}
