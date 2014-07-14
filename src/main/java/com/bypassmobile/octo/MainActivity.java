package com.bypassmobile.octo;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bypassmobile.octo.helper.ClearCash;
import com.bypassmobile.octo.helper.CreateUserView;
import com.bypassmobile.octo.image.ImageLoader;
import com.bypassmobile.octo.model.User;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Jonathan Spies and  Eric Gonzalez for Bypass 01/21/2014.
 * Modify by Miguel E. Garcia Calderon on 7/12/2014.
 */

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This function is being call at the beginning of the app to ensure the cache is being clear
        ClearCash.clearApplicationData(this);

        //The list that will contain the users
        LinearLayout userListLayout = (LinearLayout) findViewById(R.id.userListLayout);
        ImageView thumbnail = (ImageView) findViewById(R.id.bypassThumbView);

        //Loads the Bypass logo
        ImageLoader.createImageLoader(this).load("http://vni.s3.amazonaws.com/111206062303874.jpg").into(thumbnail);

        //Getting the list of members of Bypass...
        loadMembers(userListLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////// Load the users the User Follows ///////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////
    public void loadMembers(final LinearLayout listLayout)
    {
        getEndpoint().getOrganizationMember("bypasslane", new Callback<List<User>>() {

            @Override
            public void success(List<User> users, Response response){

                listLayout.removeAllViews();

                if(users.size() > 0) {
                    for (int i = 0; i < users.size(); i++) {

                    /*
                    *In here we are calling the class "createUserView" and we feed
                    *the response obtained from the "HTTP Client", in return we will get
                    *a "singleUser View(Custom View)" that contain all the information
                    *from the response.
                    */
                        //Log.d("OCTO-STALKER TAG","testing"+Integer.toString(numberOfFollowers(users.get(i).getName())));
                        listLayout.addView(new CreateUserView(MainActivity.this, i, users.get(i)).createView());
                    }
                }
                // In case Bypass doesn't have any members
                else
                {
                    // Hide the user list and display the empty list message
                    findViewById(R.id.userMembersScrollList).setVisibility(View.GONE);
                    findViewById(R.id.emptyMessageUser).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("OCTO-STALKER TAG", "DIDN'T WENT THROUGH");
            }
        });
    }

 ////////////////////////////////////////////////////////////////////////////////////////////////
///////////////// Get the number of users that follow the User Follows ///////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////
/*
    public int numberOfFollowers(String userName)
    {
        final int[] number = {0};

        getEndpoint().getFollowersUser(userName, new Callback<List<User>>() {

            @Override
            public void success(List<User> users, Response response){

                number[0] = users.size();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("OCTO-STALKER TAG", "DIDN'T WENT THROUGH");
            }
        });

        return number[0];
    }
*/

//////////////////////////////////////////////////////////////////////////
/////////////////////////// OnDestroy ///////////////////////////////////
////////////////////////////////////////////////////////////////////////
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
