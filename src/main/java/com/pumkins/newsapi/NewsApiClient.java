package com.pumkins.newsapi;

import com.pumkins.newsapi.client.APIClient;
import com.pumkins.newsapi.client.APIService;
import com.pumkins.newsapi.dto.request.EverythingRequest;
import com.pumkins.newsapi.dto.request.Requester;
import com.pumkins.newsapi.dto.request.SourcesRequest;
import com.pumkins.newsapi.dto.request.TopHeadlinesRequest;
import com.pumkins.newsapi.dto.response.ArticleResponse;
import com.pumkins.newsapi.dto.response.SourcesResponse;
import org.json.JSONException;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

/**
 * @author: dengKai
 * @date: 2023/08/05 08:47
 * @description: NewsApiClient
 */

public class NewsApiClient {
    private String mApiKey;
    private Map<String, String> query;
    private APIService mAPIService;

    public NewsApiClient(String apiKey){
        mApiKey = apiKey;
        mAPIService = APIClient.getAPIService();
    }

    //Callbacks
    public interface SourcesCallback{
        void onSuccess(SourcesResponse response);
        void onFailure(Throwable throwable);
    }

    public interface ArticlesResponseCallback{
        void onSuccess(ArticleResponse response);
        void onFailure(Throwable throwable);
    }


    private Throwable errMsg(String str) {
        Throwable throwable = null;
        try {
            JSONObject obj = new JSONObject(str);
            throwable = new Throwable(obj.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (throwable == null){
            throwable = new Throwable("An error occured");
        }
        return throwable;
    }

    private Map<String, String> createQuery(Requester requester) throws Exception {
        query = Requester.convertToQueryMap(requester);
        query.put("apiKey", mApiKey);
        return query;
    }


    //Get Sources
    public void getSources(SourcesRequest sourcesRequest, final SourcesCallback callback) throws Exception {
        query = createQuery(sourcesRequest);
        mAPIService.getSources(query)
                .enqueue(new Callback<SourcesResponse>() {
                    @Override
                    public void onResponse(Call<SourcesResponse> call, retrofit2.Response<SourcesResponse> response) {
                        if (response.code() == HttpURLConnection.HTTP_OK){
                            callback.onSuccess(response.body());
                        }

                        else{
                            try {
                                callback.onFailure(errMsg(response.errorBody().string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<SourcesResponse> call, Throwable throwable) {
                        callback.onFailure(throwable);
                    }
                });
    }


    public void getTopHeadlines(TopHeadlinesRequest topHeadlinesRequest, final ArticlesResponseCallback callback) throws Exception {
        query = createQuery(topHeadlinesRequest);
        mAPIService.getTopHeadlines(query)
                .enqueue(new Callback<ArticleResponse>() {
                    @Override
                    public void onResponse(Call<ArticleResponse> call, retrofit2.Response<ArticleResponse> response) {
                        if (response.code() == HttpURLConnection.HTTP_OK){
                            callback.onSuccess(response.body());
                        }

                        else{
                            try {
                                callback.onFailure(errMsg(response.errorBody().string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArticleResponse> call, Throwable throwable) {
                        callback.onFailure(throwable);
                    }
                });
    }


    public void getEverything(EverythingRequest everythingRequest, final ArticlesResponseCallback callback) throws Exception {
        Map<String, String> query = Requester.convertToQueryMap(everythingRequest);
        query.put("apiKey", mApiKey);
        mAPIService.getEverything(query)
                .enqueue(new Callback<ArticleResponse>() {
                    @Override
                    public void onResponse(Call<ArticleResponse> call, retrofit2.Response<ArticleResponse> response) {
                        if (response.code() == HttpURLConnection.HTTP_OK){
                            callback.onSuccess(response.body());
                        }

                        else{
                            try {
                                callback.onFailure(errMsg(response.errorBody().string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ArticleResponse> call, Throwable throwable) {
                        callback.onFailure(throwable);
                    }
                });
    }
}