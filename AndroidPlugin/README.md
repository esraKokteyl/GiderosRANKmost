GiderosRANKmostAndroidPlugin
============================

RANKmost Plugin for Android apps made with Gideros Studio

Thank you for downloading RANKmost SDK. For more information and registration please visit http://dev.rankmost.com.

###Installation instructions:

Download RANKmost SDK here: https://github.com/kokteyl/RANKmost-sdk.git

Export Gideros project to Android project

Import Android project to eclipse (Note you need to import it in workspace)

Find RANKmost SDK using instructions in README.md file.

Copy .so files to each separate armeabi folder

Copy Rankmost.java folder to src/giderosmobile/android/plugins folder

 In your main activity:
 
Add lib as `System.loadLibrary("rankmost");`
  
Add external class as `"com.giderosmobile.android.plugins.Rankmost"`

Add <activity android:name="com.kokteyl.rankmost.RANKmostPortal"/> on your AndroidManifest.xml

Clean your project and launch it. You are good to go! 

Please find Gideros example to see usage of methods from lua. 

More information about RANKmost, visit our website.

