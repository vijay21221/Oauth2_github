package myoauthproject

import grails.gorm.transactions.Transactional
import org.springframework.context.annotation.Primary

import java.nio.charset.StandardCharsets

@Transactional
class GoogleService {
    static final String ACCESS_TOKEN_ENDPOINT = "https://oauth2.googleapis.com/token"
    static final String CLIENT_ID = "GOOGLE_CLIENT_ID"
    static final String CLIENT_SECRET = "GOOGLE_CLIENT_SECRET"
    static final String CALLBACK_URL = "http://localhost:8080/oauth/callback"
    static final String AUTH_URL = "https://accounts.google.com/o/oauth2/v2/auth"
    static final String SCOPES = "https://www.googleapis.com/auth/userinfo.profile"
    static final String USER_PROFILE = "https://www.googleapis.com/oauth2/v3/userinfo"
    static String accessToken = null
    static String refreshToken = null


    String getProviderName() {
        return "google"
    }


    String getAuthUrl() {

        return AUTH_URL + "?scope=${URLEncoder.encode(SCOPES, StandardCharsets.UTF_8.toString())}" +
        "&access_type=offline&include_granted_scopes=true&response_type=code&redirect_uri=${URLEncoder.encode(CALLBACK_URL, StandardCharsets.UTF_8.toString())}" +
        "&client_id=${CLIENT_ID}"
    }


    String getAccessToken(String code) {
        Map body = [
                client_id: CLIENT_ID,
                client_secret : CLIENT_SECRET,
                code : code,
                grant_type : "authorization_code",
                redirect_uri : CALLBACK_URL
        ]
        def response = HttpUtilsService.getJSONContentByPOST(ACCESS_TOKEN_ENDPOINT, [:], body)
        accessToken = response.access_token
        refreshToken = response.refresh_token
        return accessToken
    }


    Map getProfileDetails() {
        Map profileDetails = [:]

        if(accessToken){
            Map headers = ["Authorization": "Bearer "+accessToken]
            def response = HttpUtilsService.getJSONContentByGET(USER_PROFILE, headers)
            profileDetails = response
        }
        return profileDetails
    }
}
