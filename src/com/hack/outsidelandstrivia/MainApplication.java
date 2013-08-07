package com.hack.outsidelandstrivia;

import android.app.Application;

import com.parse.Parse;

public class MainApplication extends Application {
	
	public String twitterId;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		 Parse.initialize(this, "AJMVy2PftvJBCJtfvJTernW5POWczJgj6c8ZRh2r", "5OSGu81KrkKuz5qKa1XRprXTdq3eS3ZGfO9YAPpf"); 
	}
	
}
