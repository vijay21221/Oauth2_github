package myoauthproject

import grails.gorm.transactions.Transactional
import groovy.json.JsonOutput
import groovy.json.JsonSlurper
import org.apache.http.HttpEntity
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients
import org.apache.http.util.EntityUtils

@Transactional
class HttpUtilsService {

    static def getJSONContentByPOST(String url, Map headers, Map body){
        def retVal = null
        CloseableHttpClient httpClient = HttpClients.custom().build()
        HttpPost httpPost = new HttpPost(url)
        headers.each {key, val ->
            httpPost.setHeader(key as String, val as String)
        }
        String postDataJson = JsonOutput.toJson(body)
        HttpEntity stringEntity = new StringEntity(postDataJson, ContentType.APPLICATION_JSON)
        httpPost.setEntity(stringEntity)
        CloseableHttpResponse response = httpClient.execute(httpPost)
        HttpEntity entity = response.getEntity()
        String resJson = EntityUtils.toString(entity)
        retVal = new JsonSlurper().parseText(resJson)
        response.close()
        httpClient.close()
        return retVal
    }


    static def getJSONContentByGET(String url, Map headers){
        def retVal = null
        CloseableHttpClient httpClient = HttpClients.custom().build()
        HttpGet httpGet = new HttpGet(url)
        headers.each {key, val->
            httpGet.setHeader(key as String, val as String)
        }
        CloseableHttpResponse response = httpClient.execute(httpGet)
        HttpEntity entity = response.getEntity()
        String resJson = EntityUtils.toString(entity)
        retVal = new JsonSlurper().parseText(resJson)
        response.close()
        httpClient.close()
        return retVal
    }


}
