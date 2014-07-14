
/**
 * Created by MiguelEduardo on 7/13/2014.
 */
package com.squareup.picasso;

/*This was an a fail attempt to fork Picasso library jar to run my a function to clear the cache,
since I could'nt rewrite the jar.*/
public class PicassoTools {

    public static void clearCache (Picasso p) {
        p.cache.clear();
    }
}