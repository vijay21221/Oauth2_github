package myoauthproject

import grails.gorm.transactions.Transactional

import java.nio.charset.StandardCharsets

@Transactional
class GithubService {
    static final String ACCESS_TOKEN_ENDPOINT = "https://github.com/login/oauth/access_token"
    static final String CLIENT_ID = "GITHUB_CLIENT_ID"
    static final String CLIENT_SECRET = "GITHUB_CLIENT_SECRET"
    static final String CALLBACK_URL = "http://localhost:8080/oauth/github/callback"
    static final String AUTH_URL = "https://github.com/login/oauth/authorize"
    static final String SCOPES = "read:user"
    static final String USER_PROFILE = "https://api.github.com/user"
    static String accessToken = null
    static String refreshToken = null


    String getProviderName() {
        return "github"
    }


    String getAuthUrl() {

        return AUTH_URL + "?scope=${URLEncoder.encode(SCOPES, StandardCharsets.UTF_8.toString())}" +
                "&redirect_uri=${URLEncoder.encode(CALLBACK_URL, StandardCharsets.UTF_8.toString())}" +
                "&client_id=${CLIENT_ID}"
    }


    String getAccessToken(String code) {
        Map body = [
                client_id: CLIENT_ID,
                client_secret : CLIENT_SECRET,
                code : code,
                redirect_uri : CALLBACK_URL
        ]
        def response = HttpUtilsService.getJSONContentByPOST(ACCESS_TOKEN_ENDPOINT, [Accept:"application/json"], body)
        accessToken = response.access_token
        // refreshToken = response.refresh_token
        return accessToken
    }


    Map getProfileDetails() {
        Map profileDetails = [:]

        if(accessToken){
            Map headers = ["Authorization": "token "+accessToken]
            def response = HttpUtilsService.getJSONContentByGET(USER_PROFILE, headers)
            profileDetails = response
        }
        return profileDetails
    }
}
