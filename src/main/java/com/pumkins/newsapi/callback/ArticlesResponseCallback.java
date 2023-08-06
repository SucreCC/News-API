package com.pumkins.newsapi.callback;

import com.pumkins.newsapi.dto.response.ArticleResponse;

/**
 * @Author: dengKai
 * @Date: 2023-08-06-16-15
 * @Description: TODO
 */

public interface ArticlesResponseCallback {
    void onSuccess(ArticleResponse response);
    void onFailure(Throwable throwable);
}
