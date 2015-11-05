javocsoft-toolbox
=================

It is an Android commons utility library. For <b>HowTo</b> and <b>usage</b> see the [WiKi](https://github.com/javocsoft/javocsoft-toolbox/wiki).

This library contains a set of useful functions, classes and modules ready to use.

This project is a library for Android. While i was making my own projects i always tried to reuse the written code so i created this library. It was very useful so i decided to share it with all. The idea beyond is to make easier programming for Android :)

Contains a set of utility classes, the most important called "ToolBox", with a set of quite large collection of different functions ready to be used just by including this library in your project.

We have the ToolBox.java main class but also there are other utility classes and modules:

* Encoding utilities that i use (B64)
* Class "MediaScannerNotifier.java" that allows to add easily a resource to the Android content scanner.
* <i>ZIP</i>. A class called "<b>Unzipper</b>.java" allowing us to work with ZIP files in a easy way.
* <i>SMS</i>. The class "<b>SMSObserver</b>.java" to be able to be aware of sent/received SMS in the mobile having the possibility to do something when sending/receiving a SMS.
* <i>SMS</i>. The class "<b>CMTShortNumberInformation</b>.java" that allows to recognize if a short number is one of the CMT recognized additional tarification number (like SMS Premium) having the option to know the price of the message.
* <i>SMS</i>. The class "<b>CMTInfoHelper</b>.java" that allows to download automatically the CMT short-numbers information. This will give you the possibility of knowing the owner company of a given short-number.
* <b>HTTP Operations</b>. The class "HttpOperations.java" that allows to make HTTP POST/GET request easily.
* <b>Facebook</b> Login, Share (SDK v2.1) helper classes to integrate Facebook Login easily in your apps.
* <i>Google Play Services</i>. A <b>Google Cloud Messaging</b> (GCM) module. very easy and fast to use and integrate in your project to allow receiving/sending push notifications from your application.
* <i>Google Play Services</i>. An Ads module for <b>AdMob</b>. Easy to integrate in your project.
* <i>Google Play Services</i>. Google <b>Analytics</b> v4 helper classes to have working the tracking of your app. Also with a <b>Custom Campaign Receiver</b> to be able to measure your ads campaigns (allowing also getting the campaign info giving the opportunity to do something when is received).
* <i><b>SQLite</b></i>. Now library allows to use easily SQLite databases in your applications.
* <i>Google Play Services. <b>Google Drive</b></i>. Now library allows to use in a really easy way the Drive to store your application data in the cloud.
* <b>Notifications creation</b> using the last API maintaining retro-compatibility.
* <b>GCM module</b>. This allows to integrate Android notifications in your project in a easy way.
* A <b>GsonProcessor</b>. This class allows to work with JSON.
* The <b>HTMLStyledTextView</b>. A class that allows to show HTML content in a textview.
* An <b>animation factory</b> class set.
* An <b>MessengerService</b> class. This, in conjuction with "MessengerIncomingHandler.java" class, will allow to have a messenger service very easily.
* A class to connect to other applications messenger services. See <b>Mezzenger.java</b> class.
* The <b>PRNGFixes</b> class. For Android Jelly Bean or minor versions. <i>Applications which use Android's Java Cryptography Architecture (JCA) or the OpenSSL PRNG for key generation, signing, or random number generation may not receive cryptographically strong values on Android devices due to improper initialization of the underlying PRNG. Applications that 
directly invoke the system-provided OpenSSL PRNG without explicit initialization on Android are also affected</i>.
* Methods to handle <b>new Android M (v6, API level 23) permissions usage approach</b> allowing to adapt the applications to new permissions system in Android. 
* A <b>ready-to-use localization service</b>. This makes very easy to have a service running in background watching for any location or GPS status changes, see the Wiki :)
* <b>Geofencing</b> supported.

This library is under development because i use it for all my projects so i am all time adding new features :).


Greetings and i hope it will save time in your projects as it does for me.

##Project Integration##

This is an Eclipse Android Library project. To integrate in your project avoiding any issues with support libraries, follow these steps:  

1. Remove, if there is one, the project "appcompat7" that Eclipse creates by default in your workspace.
2. Open Eclipse and import these projects into your workspace:
	1. Android v7 support library project (located in "<android_sdk>\extras\android\support\v7". You can install through the Android SDK Manager).
	2. Android Google Play Services library project (located in "<android_sdk>\extras\google\google_play_services\libproject\google-play-services_lib". You can install through the Android SDK Manager)
	3. Facebook SDK library project:
		1. Download it from <a href="https://developers.facebook.com/docs/android/downloads/">Facebook SDK</a>
		2. Import the library project located in "<fb_sdk_folder>\facebook".
3. Clone from GitHub "javocsoft_toolbox" library and import into your workspace.
4. Copy from the Android v7 Android Support library the file "android-support-v4.jar" in the "libs" folder to the lib folder of:
	1. javocsoft_toolbox library project
	2. FacebookSDK library project.
	3. Your project.

Internally it also uses some other libraries like GSON, json_simple and commons-codec. These are included in the libs folder. 

##LICENSE##

Copyright 2010-2015 JavocSoft.

JavocSoft Android Toolbox is free software: you can redistribute it 
and/or modify it under the terms of the GNU General Public License as 
published by the Free Software Foundation, either version 3 of the 
License, or (at your option) any later version.

JavocSoft Android Toolbox is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this library.  If not, see <http://www.gnu.org/licenses/>.
