package myoauthproject

class UrlMappings {

    static mappings = {
        "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')

        "/oauth/callback"{
            controller = "Oauth2"
            action = "oauthCallback"
        }

        "/oauth/github/callback"{
            controller = "Oauth2"
            action = "githubOauthCallback"
        }

        "/oauth/github/auth"{
            controller = "Oauth2"
            action = "githubAuth"
        }
    }
}
