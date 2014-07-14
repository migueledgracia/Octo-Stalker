package com.bypassmobile.octo;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bypassmobile.octo.helper.CreateUserView;
import com.bypassmobile.octo.image.ImageCache;
import com.bypassmobile.octo.image.ImageLoader;
import com.bypassmobile.octo.model.User;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoTools;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Miguel E. Garcia Calderon on 7/12/2014.
 */

public class FollowingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_following);
        //new ImageCache().clear();

        String userName = "";
        Bundle extras = getIntent().getExtras();
        ImageView thumbnail = (ImageView) findViewById(R.id.userFollowingThumbView);
        TextView userNameTextView = (TextView) findViewById(R.id.userNameFollowing);

        //The list that will contain the users
        LinearLayout userFollowingListLayout = (LinearLayout) findViewById(R.id.userFollowingList);

        //Check for parameter passed to this Activity
        if (extras != null)
        {
            userName = extras.getString("userName");
            userNameTextView.setText(userName);

            ImageLoader.createImageLoader(this).load(extras.getString("userThumbnail")).into(thumbnail);
        }

        //Getting the list of users this user follows...
        loadFollowerUsers(userName,userFollowingListLayout);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.following, menu);
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
    public void loadFollowerUsers(String uN, final LinearLayout listContainer)
    {
        getEndpoint().getFollowingUser(uN, new Callback<List<User>>() {

            @Override
            public void success(List<User> users, Response response)
            {
                TextView numberUserFollowing = (TextView) findViewById(R.id.numberFollowingTextView);

                if(users.size() > 0) {
                    listContainer.removeAllViews();
                    numberUserFollowing.setText(Integer.toString(users.size()));

                    for (int i = 0; i < users.size(); i++) {
                         /*
                        *In here we are calling the class "createUserView" and we feed
                        *the response obtained from the "HTTP Client", in return we will get
                        *a "singleUser View(Custom View)" that contain all the information
                        *from the response.
                        */
                        listContainer.addView(new CreateUserView(FollowingActivity.this, i, users.get(i)).createView());
                    }
                }
                // In case the user doesn't follow any one.
                else
                {
                    // Hide the user list and display the empty list message
                    numberUserFollowing.setVisibility((View.GONE));
                    findViewById(R.id.userFollowingScrollList).setVisibility(View.GONE);
                    findViewById(R.id.emptyMessageUserFollowing).setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void failure(RetrofitError error) {

                Log.d("OCTO-STALKER TAG", "DIDN'T WENT THROUGH");
            }
        });
    }

 //////////////////////////////////////////////////////////////////////////
/////////////////////////// OnDestroy ///////////////////////////////////
////////////////////////////////////////////////////////////////////////
    @Override
    protected void onDestroy() {super.onDestroy();}
}
