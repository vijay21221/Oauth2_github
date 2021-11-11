package myoauthproject

import grails.converters.JSON

class Oauth2Controller {
    GoogleService googleService
    GithubService githubService

    def index() {
        // Redirecting to provider auth url when user clicks login with google
        String authUrl = googleService.getAuthUrl()
        redirect(url: authUrl)
    }

    def oauthCallback(){
        // provider will call this endpoint and gives code
        // this code is used to fetch access token along with client Id and Client Secret
        String code = params.code
        googleService.getAccessToken(code)

        // show profile details in order to verify access token is working fine
        Map profileDetails = googleService.getProfileDetails()
        render profileDetails as JSON
    }

    def githubAuth(){
        String authUrl = githubService.getAuthUrl()
        redirect(url: authUrl)
    }

    def githubOauthCallback(){
        String code = params.code
        githubService.getAccessToken(code)
        Map profileDetails = githubService.getProfileDetails()
        render profileDetails as JSON
    }


}
