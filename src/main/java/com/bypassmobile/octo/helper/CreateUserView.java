package com.bypassmobile.octo.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bypassmobile.octo.FollowingActivity;
import com.bypassmobile.octo.MainActivity;
import com.bypassmobile.octo.R;
import com.bypassmobile.octo.image.ImageLoader;
import com.bypassmobile.octo.model.User;
import com.bypassmobile.octo.model.UserListViewHolder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoTools;

/**
 * Created by Miguel E. Garcia Calderon on 7/12/2014.
 */

public class CreateUserView
{
    private Context context;    // Contain the current Activity from which this class is being call
    private User user;          // Object User that contains the user information
    private View convertView;   // This is an undefined View that will be use to contain and become the custom view known has singleUser
    private int position;       /* This contains the position of the user in the list of the HTTP
                           response, this is intent to be use has a unique ID for the custom view*/

////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////// createUserView Class Constructor //////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////
    public CreateUserView(Context c, int p , User u)
    {
        context = c;
        position = p;
        user = u;
        convertView = null;
    }

////////////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////Create and Fill User View//////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////
    public View createView() {

        View v = convertView;

        //Creates a holder from the custom holder class "UserListViewHolder"
        UserListViewHolder holder = new UserListViewHolder();

        // This a new view we inflate the new layout
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.single_user, null);

        // Now we can fill the layout with the right values
        TextView nameView = (TextView) v.findViewById(R.id.userNameTextView);

        //Find the views were the information is gone a be put from the custom adapter "single_user_layout.xml"
        ImageView thumbnailView = (ImageView) v.findViewById(R.id.userThumbView);
        ProgressBar pb = (ProgressBar) v.findViewById(R.id.userProgressBarThumbnail);
        LinearLayout progressbarThumbnailName = (LinearLayout) v.findViewById(R.id.Root);

        //Assign the temporary views to the holders
        holder.userNameTextView = nameView;
        holder.userProgressBarThumbnail = pb;
        holder.userThumbView = thumbnailView;
        holder.userBody = progressbarThumbnailName;

        v.setTag(holder);

        /*This is were the information obtained from the "User" gets distribute to their proper views
          to be display on the Users List*/
        holder.userNameTextView.setText(user.getName());
        holder.userThumbView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        //Load users avatar image and set it into it's respecting ImageView
        ImageLoader.createImageLoader( context ).load(user.getProfileURL()).into(holder.userThumbView);

        holder.userProgressBarThumbnail.setVisibility(View.GONE);

        /*This is the layout that holds the views for the Title and Thumbnail view so in this way this
          layout can be treated as a button to call the list of users this specific "User" follows.*/
        holder.userBody.setId(position);

        /* This is to allow the view to access the user information without the need of making the variable
        user public*/
        holder.userBody.setTag(user);
        holder.userBody.setOnClickListener(new View.OnClickListener(){

            //Opens the list of users this specific User follows onClick
            @Override
            public void onClick(final View arg0){

                arg0.setSelected(true);

                /*This handler with timmer is use to allow time to show the highlighting
                of the row of the user being selected*/

                new Handler().postDelayed(new Runnable()
                {
                    @Override
                    public void run() {
                        //This is to revert back the Unknown Object in to a User Object
                        User u = (User) arg0.getTag();

                        Intent intent = new Intent(context, FollowingActivity.class);
                        intent.putExtra("userName", u.getName());
                        intent.putExtra("userThumbnail", u.getProfileURL());

                        context.startActivity(intent);
                        arg0.setSelected(false);
                    }
                }, 150);

            }

        });
        return v;
    }
}
