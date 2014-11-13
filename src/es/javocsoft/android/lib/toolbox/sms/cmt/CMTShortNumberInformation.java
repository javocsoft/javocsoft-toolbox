/*
 * Copyright (C) 2010-2014 - JavocSoft - Javier Gonzalez Serrano
 * http://javocsoft.es/proyectos/code-libs/android/javocsoft-toolbox-android-library
 * 
 * This file is part of JavocSoft Android Toolbox library.
 *
 * JavocSoft Android Toolbox library is free software: you can redistribute it 
 * and/or modify it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation, either version 3 of the License, 
 * or (at your option) any later version.
 *
 * JavocSoft Android Toolbox library is distributed in the hope that it will be 
 * useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General 
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JavocSoft Android Toolbox library.  If not, see 
 * <http://www.gnu.org/licenses/>.
 * 
 */
package es.javocsoft.android.lib.toolbox.sms.cmt;

import java.util.Locale;


/**
 * This class allows to get official CMT information about telephony SMS/MMS
 * short numbers services.<br><br>
 * 
 * See <a href="http://www.cmt.es/descarga-ficheros-numeracion">Descarga de numeraciones</a>
 * 
 * See <a href="http://www.altiria.com/regulacion-normativa-sms-premium/">Normative 2008 SMS Premium</a><br> 
 * See <a href="http://cnmcblog.es/2009/09/01/su-numero-gracias/">More information</a><br>
 * See <a href="http://www.cmt.es/instrucciones">CMT Numeration related information</a><br><br>
 * 
 * Note from CMT web page about SMS/MMS services:<br><br>
 * 
 * SMS/MMS services: those services based in text and multimedia messages, types::<br>
 * <ul>
 *		<li>Free SMS/MMS: those numerical codes of six digits that start with 205 or 207.</li>
 *		<li>Normal price SMS/MMS: those codes of six digits that start with 215 or 217.</li>
 *		<li>Premium SMS/MMS: those numerical codes belonging to the associated ranges of the following modalities:</li>
 *			<ul>
 *				<li>a) Price minor or equal to a 1,2 euros: Five digits codes that start with 25, 27 and 280 (the last one for charities and charity campaigns).</li>
 *				<li>b) Price between 1,2 and 6 euros: Five digits codes that start with 35 or 37.</li>
 *				<li>c) Subscription with a price minor or equal to 1,2 euros: Six digits codes that start with 795 or 797.</li>
 *				<li>d) Adults with price minor or equal than 6 euros: Six digits codes that start with 995 or 997.</li>
 *			</ul>
 * </ul>
 * 
 * @author JavocSoft 2013
 * @version 1.0
 */
public class CMTShortNumberInformation {
	
	public static final String SMS_MMS_SUBSCRIPTION_KEYWORD_ES = "ALTA";
	public static final String SMS_MMS_UNSUBSCRIPTION_KEYWORD_ES = "BAJA";
	

	public static final String SMS_MMS_FREE_6DIGITS__STARTING_205 = "205"; 
	public static final String SMS_MMS_FREE_6DIGITS__STARTING_207 = "207";

	public static final String SMS_MMS_NORMAL_6DIGITS__STARTING_215 = "215";
	public static final String SMS_MMS_NORMAL_6DIGITS__STARTING_217 = "217";
	
	public static final String SMS_MMS_PREMIUM_5DIGITS__UPTO_1_2_E_STARTING_25 = "25";
	public static final String SMS_MMS_PREMIUM_5DIGITS__UPTO_1_2_E_STARTING_27 = "27";
	public static final String SMS_MMS_PREMIUM_5DIGITS__UPTO_1_2_E_STARTING_280 = "280";
	
	public static final String SMS_MMS_PREMIUM_6DIGITS__UPTO_1_2_E_STARTING_795 = "795";
	public static final String SMS_MMS_PREMIUM_6DIGITS__UPTO_1_2_E_STARTING_797 = "797";
	
	public static final String SMS_MMS_PREMIUM_5DIGITS__FROM_1_2_TO_6_E_STARTING_35 = "35";
	public static final String SMS_MMS_PREMIUM_5DIGITS__FROM_1_2_TO_6_E_STARTING_37 = "37";
	
	public static final String SMS_MMS_PREMIUM_6DIGITS__UPTO_6_E_STARTING_995 = "995";
	public static final String SMS_MMS_PREMIUM_6DIGITS__UPTO_6_E_STARTING_997 = "997";
	
	public static enum TARIFICATION {FREE, NORMAL, PREMIUM, UNKNOWN};
	
	public static enum PRICE {UPTO_1_2E, UPTO_6E};
	
	public enum CMTSMSMMSShortNumberIdentifier {
		SMS_MMS_FREE_6DIGITS__STARTING_205(205),
		SMS_MMS_FREE_6DIGITS__STARTING_207(207),
		SMS_MMS_NORMAL_6DIGITS__STARTING_215(215),
		SMS_MMS_NORMAL_6DIGITS__STARTING_217(217),
		SMS_MMS_PREMIUM_5DIGITS__UPTO_1_2_E_STARTING_25(25),
		SMS_MMS_PREMIUM_5DIGITS__UPTO_1_2_E_STARTING_27(27),
		SMS_MMS_PREMIUM_5DIGITS__UPTO_1_2_E_STARTING_280(280),
		SMS_MMS_PREMIUM_6DIGITS__UPTO_1_2_E_STARTING_795(795),
		SMS_MMS_PREMIUM_6DIGITS__UPTO_1_2_E_STARTING_797(797),
		SMS_MMS_PREMIUM_5DIGITS__FROM_1_2_TO_6_E_STARTING_35(35),
		SMS_MMS_PREMIUM_5DIGITS__FROM_1_2_TO_6_E_STARTING_37(37),
		SMS_MMS_PREMIUM_6DIGITS__UPTO_6_E_STARTING_995(995),
		SMS_MMS_PREMIUM_6DIGITS__UPTO_6_E_STARTING_997(997);
		
		
		private int value;
		 
		 private CMTSMSMMSShortNumberIdentifier(int value) {
		   this.value = value;
		 }
		 
		 public int getValue() {
		   return value;
		 }
	};

	
	/**
	  * Returns TRUE if the number is recognized by
	  * the CMT.
	  * 
	  * @param number Short-number to check
	  * @return
	  */
	 public static boolean isRecognized(String number){
		 boolean res = false;
		
		 if(number.length()==5 || number.length()==6){
		 
			 String id3digits = number.substring(0, 3);
			 String id2digits = number.substring(0, 2);
			 
			 try{
				 for (CMTSMSMMSShortNumberIdentifier item : CMTSMSMMSShortNumberIdentifier.values()) {
					  if(item.getValue()==Integer.valueOf(id3digits)){
						  res = true;
						  break;
					  }
				 }					 
			 }catch(Exception e){}
			 
			 try{
				 for (CMTSMSMMSShortNumberIdentifier item : CMTSMSMMSShortNumberIdentifier.values()) {
					  if(item.getValue()==Integer.valueOf(id2digits)){
						  res = true;
						  break;
					  }
				 }					 
			 }catch(Exception e){}
		 }				 
		 
		 return res;
	 }
	
	/**
	  * Tells if a text is a subscription text.
	  * 
	  * @param text
	  * @return
	  */
	 public static boolean isSubscription(String text){			 
		 if(text.toUpperCase(Locale.getDefault()).contains(SMS_MMS_SUBSCRIPTION_KEYWORD_ES)){
			 return true;
		 }else{
			 return false;
		 }
	 }
	 
	 /**
	  * Checks if a number is from a SMS/MMS subscription service.
	  * 
	  * @param number Short-number to check
	  * @return
	  */
	 public static boolean isFromASubscriptionShortNumber(String number){			 
		 boolean res = false;
			
		 if(number.length()==5 || number.length()==6){
		 
			 String id3digits = number.substring(0, 3);
			 
			 try{
				 for (CMTSMSMMSShortNumberIdentifier item : CMTSMSMMSShortNumberIdentifier.values()) {
					  if(item.getValue()==Integer.valueOf(id3digits)){
						  if(item == CMTSMSMMSShortNumberIdentifier.SMS_MMS_PREMIUM_6DIGITS__UPTO_1_2_E_STARTING_795 ||
							 item == CMTSMSMMSShortNumberIdentifier.SMS_MMS_PREMIUM_6DIGITS__UPTO_1_2_E_STARTING_797){
							  //These are the oficial short numbers for a subscription service.
							  res = true;
							  break;
						  }else if(item == CMTSMSMMSShortNumberIdentifier.SMS_MMS_PREMIUM_6DIGITS__UPTO_6_E_STARTING_995 ||
								   item == CMTSMSMMSShortNumberIdentifier.SMS_MMS_PREMIUM_6DIGITS__UPTO_6_E_STARTING_997){
							  //There are companies that for adult content use these prefixes to deliver subscription contents.
							  res = true;
							  break;
						  }
					  }
				 }					 
			 }catch(Exception e){}
		 }				 
		 
		 return res;
	 }	 
	
	/**
	 * Gets the tarification type for a given short number.
	 * 
	 * @param number Short-number to check
	 * @return	TARIFICATION
	 */
	 public static TARIFICATION getTarificationType(String number){
		 TARIFICATION res = null;
		 
		 if(number.length()==5 || number.length()==6){
			 
			 String id3digits = number.substring(0, 3);
			 String id2digits = number.substring(0, 2);
			 
			//Three digits ones
			 try{
				 for (CMTSMSMMSShortNumberIdentifier item : CMTSMSMMSShortNumberIdentifier.values()) {
					  if(item.getValue()==Integer.valueOf(id3digits)){
						  res = askForTarification(item);
						  break;					  
					  }
				 }					 
			 }catch(Exception e){}
			 
			 if(res==null){
				 //Two digits ones
				 try{
					 for (CMTSMSMMSShortNumberIdentifier item : CMTSMSMMSShortNumberIdentifier.values()) {
						  if(item.getValue()==Integer.valueOf(id2digits)){
							  res = askForTarification(item);
							  break;
						  }
					 }	 
				 }catch(Exception e){}
			 }
		 }		 
		 
		 return res;
	 }
	 
	 /**
	  * Gets the price of the given short premium number.
	  * 
	  * @param number Short-number to check
	  * @return PRICE
	  */
	 public static PRICE getPremiumPrice(String number) {
		 PRICE res = null;
		 
		 if(number.length()==5 || number.length()==6){
			 
			 String id3digits = number.substring(0, 3);
			 String id2digits = number.substring(0, 2);
			 
			//Three digits ones
			 try{
				 for (CMTSMSMMSShortNumberIdentifier item : CMTSMSMMSShortNumberIdentifier.values()) {
					  if(item.getValue()==Integer.valueOf(id3digits)){
						  res = askForPrice(item);
						  break;					  
					  }
				 }					 
			 }catch(Exception e){}
			 
			 if(res==null){
				 //Two digits ones
				 try{
					 for (CMTSMSMMSShortNumberIdentifier item : CMTSMSMMSShortNumberIdentifier.values()) {
						  if(item.getValue()==Integer.valueOf(id2digits)){
							  res = askForPrice(item);
							  break;
						  }
					 }	 
				 }catch(Exception e){}
			 }
		 }			 
		 
		 return res;
	 }
	
	 
	 
	//AUXILIAR
	 
	 private static TARIFICATION askForTarification(CMTSMSMMSShortNumberIdentifier item){
		 TARIFICATION res = null;
		 
		 if(item.name().contains("FREE")){
			 res = TARIFICATION.FREE;				  
		 }else if(item.name().contains("NORMAL")){
			 res = TARIFICATION.NORMAL;				  
		 }else if(item.name().contains("PREMIUM")){
			 res = TARIFICATION.PREMIUM;				  
		 }else{
			 res = TARIFICATION.UNKNOWN;				  
		 }	
		 
		 return res;
	 }
	 
	 private static PRICE askForPrice(CMTSMSMMSShortNumberIdentifier item){
		 PRICE res = null;
		 
		 if(item.name().contains("6_E")){
			 res = PRICE.UPTO_6E;				  
		 }else{
			 res = PRICE.UPTO_1_2E;				  
		 }
		 
		 return res;
	 }
}
