package com.pumkins.newsapi.callback;

import com.pumkins.newsapi.dto.response.SourcesResponse;

/**
 * @Author: dengKai
 * @Date: 2023-08-06-16-15
 * @Description: TODO
 */

public interface SourcesCallback {
    void onSuccess(SourcesResponse response);
    void onFailure(Throwable throwable);
}
