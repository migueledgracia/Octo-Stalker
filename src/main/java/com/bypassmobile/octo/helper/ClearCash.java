package com.bypassmobile.octo.helper;

import android.content.Context;
import android.util.Log;

import java.io.File;

/**
 * Created by Android Novice 08/28/2012.
 * Modify by Miguel E. Garcia Calderon on 7/13/2014.
 */

public class ClearCash {

//////////////////////////////////////////////////////////////////////////
/////////////////////////// Clears APP Cache ////////////////////////////
////////////////////////////////////////////////////////////////////////
    public static void clearApplicationData(Context context)
    {
        //Points to the above folder of the app directory.
        File appDir = new File(context.getCacheDir().getParent());

        //If in did a cache folder has been created then...
        if (appDir.exists()) {

            //Get the list of files and folder inside the app cache directory
            String[] children = appDir.list();

            //Loops thought the list of directories and files
            for (String s : children) {

                //This if statement is to avoid deleting any essential directory and files
                if (!s.equals("lib")) {

                    deleteDir(new File(appDir, s));
                    Log.i("OCTO-STALKER TAG", "APP Cache Deleted");
                }
            }
        }
    }

//////////////////////////////////////////////////////////////////////////
/////////////////////////// Clears APP Cache ////////////////////////////
////////////////////////////////////////////////////////////////////////
    public static boolean deleteDir(File dir)
    {
        /*This if statement makes sure the directory is not null and that
         it is a directory and not a file.*/
        if (dir != null && dir.isDirectory()) {

            //Loops thought the list of directories and files
            String[] children = dir.list();

            //Loops thought the list of directories and files
            for (int i = 0; i < children.length; i++) {

                /*In here a recursive action happens, if item in the list is a
                  folder it will call this method again and it will go through
                  it's content until it stumble with a file,once it stumble with
                  a file it will delete it on the return bellow at the bottom*/
                boolean success = deleteDir(new File(dir, children[i]));

                /*This if statement is intended to know when it has arrive to the
                  bottom of the nested directories, when it does it end the recursion
                  and goes back to the method clearApplicationData to keep calling this
                  function starting with another file on the top and it will keep going
                  over and over until every single file is deleted*/
                if (!success) {
                    return false;
                }
            }
        }
        //Deletes the file
        return dir.delete();
    }
}
