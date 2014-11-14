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
* <b>Facebook</b> Login (SDK v2.1) helper classes to integrate Facebook Login easily in your apps.
* <i>Google Play Services</i>. A <b>Google Cloud Messaging</b> (GCM) module. very easy and fast to use and integrate in your project to allow receiving/sending push notifications from your application.
* <i>Google Play Services</i>. An Ads module for <b>AdMob</b>. Easy to integrate in your project.
* <i>Google Play Services</i>. Google <b>Analytics</b> v4 helper classes to have working the tracking of your app. Also with a <b>Custom Campaign Receiver</b> to be able to measure your ads campaigns (allowing also getting the campaign info giving the opportunity to do something when is received).

This library is under development because i use it for all my projects so i am all time adding new features :).


Greetings and i hope it will save time in your projects as it does for me.

##Project Integration##

This is an Eclipse Android Library project. To integrate in your project do the following:  

1. Clone this project and import into your Eclipse workspace.
2. Android-Toolbox requires some external libraries to work, import them as a library in the project.  
  1. <u>Gogle Play Services</u>. Located in the Android SDK under the path  "extras/google/google_play_services/libproject/google-play-services_lib". Use the Android SDK Manager to install it.
  2. <u>Facebook SDK</u>. Download it and add the facebook project folder name "facebook". Available at  <https://developers.facebook.com/docs/android/>
  3. <u>Support Library v7</u> project. Located in the Android SDK under the path "extras/android/support/v7/appcompat". Use the Android SDK Manager to install it.

Internally it also uses some other libraries like GSON, json_simple, commons-codec and android-support-v4 (available through  Android SDK Manager). These are included in the libs folder. 

##LICENSE##

Copyright 2010-2014 JavocSoft.

JavocSoft Android Toolbox is free software: you can redistribute it 
and/or modify it under the terms of the GNU General Public License as 
published by the Free Software Foundation, either version 3 of the 
License, or (at your option) any later version.

Foobar is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
