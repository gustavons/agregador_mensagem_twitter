package com.example.nozes.toes.client;

import com.example.nozes.toes.listener.ServiceListeners;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
/**
 * Created by nozes on 3/7/18.
 */

public class MyTwitterApiClient extends TwitterApiClient {

    public MyTwitterApiClient(TwitterSession session) {
        super(session);
    }

    /**
     * Provide CustomService with defined endpoints
     */
    public ServiceListeners getCustomTwitterService() {
        return getService(ServiceListeners.class);
    }
}
