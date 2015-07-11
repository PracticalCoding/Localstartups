package com.praticalcoding.localstartups;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class ParseApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();

    // Initialize Crash Reporting.
    ParseCrashReporting.enable(this);

    // Enable Local Datastore.
    Parse.enableLocalDatastore(this);

    // Add your initialization code here
    
   // Parse.initialize(this);
    Parse.initialize(this, "YZIKpJ8fnPhJYtJtBUhuLtLGc1z1dhCcITLGjX5E", "jxGoers7lPhtwlmkHulXeBgUaSu5bbXvejuZyM8u");

    ParseUser.enableAutomaticUser();
    //ParseACL defaultACL = new ParseACL();
    // Optionally enable public read access.
    // defaultACL.setPublicReadAccess(true);
    //ParseACL.setDefaultACL(defaultACL, true);
  }
}
