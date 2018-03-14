package com.example.nozes.toes;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nozes.toes.client.MyTwitterApiClient;
import com.example.nozes.toes.model.FriendsResponseModel;
import com.example.nozes.toes.model.TwitterFriends;
import com.example.nozes.toes.model.message.Event;
import com.example.nozes.toes.model.message.ModelMessage;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private MainActivity activity = this;
    Button atualizarMessage;
    ListView messageListView;

    TwitterSession twitterSession;
    TwitterAuthToken twitterAuthToken;

    long loggedUserTwitterId;

    Button buttonTwitterLogin;
    ListView mainListView;
    

    private ArrayAdapter<String> listAdapter ;
    private ArrayAdapter<String> listAdapterMessage ;

    List<TwitterFriends> twitterFriends;
    ArrayList<String> friendsList = new ArrayList<String>();

    List<Event> twitterMessage;
    ArrayList<String> messageList = new ArrayList<String>();
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        buttonTwitterLogin = (Button) findViewById(R.id.atualizarMessage);
        mainListView = (ListView) findViewById( R.id.mainListView );

        listAdapter = new ArrayAdapter<String>(this, R.layout.simplerow, friendsList);
        mainListView.setAdapter(listAdapter );
        // ListView Item Click Listener
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                final TwitterFriends user = twitterFriends.get(position);
                if (twitterFriends.get(position).getId() != 0){

//                    sendMsg(twitterFriends.get(position).getId(),twitterFriends.get(position).getScreenName(),"Hello, This is test msg");
//                    loadTwitterMessage(twitterFriends.get(position).getId());
//                    Intent intent = new Intent(view.getContext(), MessageActivity.class);
//                    intent.putExtra("twitter_session", twitterSession);
//                    startActivity(intent);
                    setContentView(R.layout.content_message);

                    atualizarMessage = findViewById(R.id.atualizarMessage);
                    messageListView = (ListView) findViewById(R.id.messagenListView);
                    TextView userName = findViewById(R.id.userName);
                    userName.setText(twitterFriends.get(position).getName());
                    listAdapter = new ArrayAdapter<String>(activity, R.layout.simplerow, messageList);
                    messageListView.setAdapter(listAdapter);
                    loadTwitterMessage();
                    atualizarMessage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            messageList.clear();
                            loadTwitterMessage();
                        }
                    });
                    Button voltar = findViewById(R.id.voltar);
                    voltar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });


                    Button enviar = findViewById(R.id.enviarMessage);
                    enviar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TextView txt = findViewById(R.id.textMessage);
                            String text = txt.getText().toString();

                            sendMsg(user.getId(),user.getScreenName(),text);
                            txt.setText("");

                            messageList.add(text);
                            listAdapter = new ArrayAdapter<String>(activity, R.layout.simplerow, messageList);
                            messageListView.setAdapter(listAdapter);





                        }
                    });

                }

            }

        });


        initTwitter();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //****************************Twitter Invites****************************************************
    public void initTwitter(){

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.com_twitter_sdk_android_CONSUMER_KEY),getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);

        twitterAuthClient = new TwitterAuthClient();
    }


    TwitterAuthClient twitterAuthClient;
    public void twitterLogin(View view){

        twitterAuthClient.authorize(activity, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                twitterSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
                twitterAuthToken = twitterSession.getAuthToken();

                TwitterSession twitterSession = result.data;

                buttonTwitterLogin.setText("Logged as "+ twitterSession.getUserName());

                Log.e("success",twitterSession.getUserName());

                loggedUserTwitterId = twitterSession.getId();

                loadTwitterFriends();
//                loadTwitterMessage();

            }

            @Override
            public void failure(TwitterException exception) {

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        twitterAuthClient.onActivityResult(requestCode, resultCode, data);
    }

    private void loadTwitterFriends() {
        MyTwitterApiClient myTwitterApiClient = new MyTwitterApiClient(twitterSession);
        myTwitterApiClient.getCustomTwitterService().list(loggedUserTwitterId).enqueue(new retrofit2.Callback<FriendsResponseModel>() {
            @Override
            public void onResponse(Call<FriendsResponseModel> call, Response<FriendsResponseModel> response) {
                Log.e("onResponse",response.toString());
                twitterFriends = fetchResults(response);

                Log.e("onResponse","twitterfriends:"+twitterFriends.size());

                for (int k=0;k<twitterFriends.size();k++){
                    friendsList.add(twitterFriends.get(k).getName());
                    Log.e("Twitter Friends","Id:"+twitterFriends.get(k).getId()+" Name:"+twitterFriends.get(k).getName()+" pickUrl:"+twitterFriends.get(k).getProfilePictureUrl());
                }
                listAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<FriendsResponseModel> call, Throwable t) {
                Log.e("onFailure",t.toString());
            }

        });
    }

    private List<TwitterFriends> fetchResults(Response<FriendsResponseModel> response) {
        FriendsResponseModel responseModel = response.body();
        return responseModel.getResults();
    }


    private void loadTwitterMessage() {
        MyTwitterApiClient myTwitterApiClient = new MyTwitterApiClient(twitterSession);
        myTwitterApiClient.getCustomTwitterService().message().enqueue(new retrofit2.Callback<ModelMessage>() {
            @Override
            public void onResponse(Call<ModelMessage> call, Response<ModelMessage> response) {
                Log.e("onResponse",response.toString());

                twitterMessage = fetchMessageResults(response);

                Log.e("onResponse","twittermessage:"+twitterMessage.size());
                for (int k=(twitterMessage.size()-1);k>=0;k--){
                   messageList.add(twitterMessage.get(k).getMessageCreate().getMessageData().getText());
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