package com.maslke.spring.demos.authenticationservice.security;

import com.maslke.spring.demos.authenticationservice.model.UserOrganization;
import com.maslke.spring.demos.authenticationservice.repository.OrgUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class JWTTokenEnhancer implements TokenEnhancer {

    @Autowired
    private OrgUserRepository repository;

    private String getOrgId(String userName) {
        UserOrganization orgUser = repository.findByUserName(userName);
        return orgUser.getOrganizationId();
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        Map<String, Object> additionalInfo = new HashMap<>();
        String orgId = getOrgId(oAuth2Authentication.getName());
        additionalInfo.put("organizationId", orgId);
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}
