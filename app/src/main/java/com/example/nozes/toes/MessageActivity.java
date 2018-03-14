package com.example.nozes.toes;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.nozes.toes.client.MyTwitterApiClient;
import com.example.nozes.toes.model.message.Event;
import com.example.nozes.toes.model.message.ModelMessage;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by nozes on 3/11/18.
 */

public class MessageActivity extends AppCompatActivity {
    private MessageActivity activity = this;

    private long id = 0;

    TwitterSession twitterSession;
    TwitterAuthToken twitterAuthToken;

    long loggedUserTwitterId;

    Button atualizarMessage;
    ListView messageListView;

    private ArrayAdapter<String> listAdapter ;


    List<Event> twitterMessage;
    ArrayList<String> messageList = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_message);

        atualizarMessage = findViewById(R.id.atualizarMessage);
        messageListView = (ListView) findViewById(R.id.messagenListView);

        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, messageList);
        messageListView.setAdapter(listAdapter);

        atualizarMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadTwitterMessage(1);
            }
        });
        // ListView Item Click Listener
//        messageListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                if (twitterFriends.get(position).getId() != 0){
////                    sendMsg(twitterFriends.get(position).getId(),twitterFriends.get(position).getScreenName(),"Hello, This is test msg");
//                    loadTwitterMessage(twitterFriends.get(position).getId());
//                }
//
//            }
//
//        });
    }
//
//    public void atualizarMe(View v){
//
//        loadTwitterMessage(1);
//    }







    private void loadTwitterMessage(long idme) {
        MyTwitterApiClient myTwitterApiClient = new MyTwitterApiClient(twitterSession);
        myTwitterApiClient.getCustomTwitterService().message().enqueue(new retrofit2.Callback<ModelMessage>() {
            @Override
            public void onResponse(Call<ModelMessage> call, Response<ModelMessage> response) {
                Log.e("onResponse",response.toString());

                twitterMessage = fetchMessageResults(response);

                Log.e("onResponse","twittermessage:"+twitterMessage.size());
                for (int k=0;k<twitterMessage.size();k++){
//                    messageList.add(twitterMessage.get(k).getSender_id());
                    Log.e("Twitter message","Name:"+twitterMessage.get(k).getId());
                }
                listAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ModelMessage> call, Throwable t) {
//                Log.e("teste ",twitterMessage.get(0).getText());
                Log.e("onFailure",t.toString());
            }

        });
    }

    private List<Event> fetchMessageResults(Response<ModelMessage> response) {

        ModelMessage responseModel = response.body();
        return responseModel.getEvents();
    }

    public void sendMsg(long userId,String replyName,String msg){
        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
        MyTwitterApiClient myTwitterApiClient = new MyTwitterApiClient(session);
        Call<Tweet> call = myTwitterApiClient.getCustomTwitterService().sendPrivateMessage(userId,replyName,msg);
        call.enqueue(new Callback<Tweet>() {
            @Override
            public void success(Result<Tweet> result) {
                Toast.makeText(activity,"Message sent", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(activity, exception.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    //***************************************End Twitter invites********************************************************


}
