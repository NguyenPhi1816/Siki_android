package com.example.siki.API;

public interface MediaApiResponseListener {
    void onUrlReceived(String imageUrl);
    void onFailure(String errorMessage);
}
