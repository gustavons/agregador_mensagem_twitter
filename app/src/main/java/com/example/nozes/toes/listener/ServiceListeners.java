package com.example.nozes.toes.listener;

import com.example.nozes.toes.model.FriendsResponseModel;
import com.example.nozes.toes.model.message.ModelMessage;
import com.twitter.sdk.android.core.models.Tweet;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
/**
 * Created by nozes on 3/7/18.
 */

public interface ServiceListeners {
    //For getting friends :  @GET("1.1/friends/list.json")
    @GET("1.1/followers/list.json")
    Call<FriendsResponseModel> list(@Query("user_id") long id);

    // get the message
//    @GET("/1.1/direct_messages/events/show.json?")
//    @GET("/1.1/direct_messages/events/list.json")
    @GET("1.1/direct_messages/events/list.json")
    Call<ModelMessage> message();


    @FormUrlEncoded
    @POST("/1.1/direct_messages/new.json?" +
            "tweet_mode=extended&include_cards=true&cards_platform=TwitterKit-13")
    Call<Tweet> sendPrivateMessage(@Field("user_id") Long userId,
                                   @Field("screen_name") String screenName,
                                   @Field("text") String text);
}
