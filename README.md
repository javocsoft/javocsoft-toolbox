javocsoft-toolbox
=================

Android commons utility library.

This library contains a set of useful functions, classes and modules ready to use.

This project is a library for Android. While i was making my own projects i always tried to reuse the written code so i created this library. Today i have decided to share it with all. The idea is to make easier programming for Android :)

Contains a set of utility classes, the most important called "ToolBox", with a set of quite large collection of different functions ready to be used just by including this library in your project.

We have the ToolBox.java main class but also there are other utility classes and modules:

* Encoding utilities that i use (B64)
* Class "MediaScannerNotifier.java" that allows to add easily a resource to the Android content scanner.
* <b>ZIP</b>. A class called "Unzipper.java" allowing us to work with ZIP files in a easy way.
* <b>SMS</b>. The class "SMSObserver.java" to be able to be aware of sent/received SMS in the mobile having the possibility to do something when sending/receiving a SMS.
* <b>SMS</b>. The class "CMTShortNumberInformation.java" that allows to recognize if a short number is one of the CMT recognized additional tarification number (like SMS Premium) having the option to know the price of the message.
* <b>SMS</b>. The class "CMTInfoHelper.java" that allows to download automatically the CMT short-numbers information. This will give you the possibility of knowing the owner company of a given short-number.
* <b>HTTP Operations</b>. The class "HttpOperations.java" that allows to make HTTP POST/GET request easily.
* <b>Facebook</b> Login (SDK v2.1) helper classes to integrate Facebook Login easily in your apps.
* <b>Google Play Services</b>. A <u>Google Cloud Messaging</u> (GCM) module. very easy and fast to use and integrate in your project to allow receiving/sending push notifications from your application.
* <b>Google Play Services</b>. An Ads module for <u>AdMob</u>. Easy to integrate in your project.
* <b>Google Play Services</b>. Google <u>Analytics</u> v4 helper classes to have working the tracking of your app. Also with a <u>Custom Campaign Receiver</u> to be able to measure your ads campaigns (allowing also getting the campaign info giving the opportunity to do something when is received).

The library needs some libraries to work, import them to Eclipse:

* <u>Gogle Play Services</u>. Located in the Android SDK under the path "extras/google/google_play_services/libproject/google-play-services_lib".
* <u>Facebook SDK</u>. Download it and add the facebook project folder name "facebook".
* <u>Support Library v7</u> project. Located in the Android SDK under the path "extras/android/support/v7/appcompat".

...and each one to the project as a library.


This library is under development because i use it for all my projects so i am all time adding new features :).


Greetings and i hope it will save time in your projects as it does for me.


