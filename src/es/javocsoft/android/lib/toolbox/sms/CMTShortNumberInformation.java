package es.javocsoft.android.lib.toolbox.sms;

import java.util.Locale;


/**
 * Información.<br><br>
 * 
 * Ver <a href="http://www.cmt.es/descarga-ficheros-numeracion">Descarga de numeraciones</a>
 * 
 * Ver <a href="http://www.altiria.com/regulacion-normativa-sms-premium/">Normativa 2008 SMS Premium</a><br> 
 * Ver <a href="http://cnmcblog.es/2009/09/01/su-numero-gracias/">Más Información</a><br>
 * Ver <a href="http://www.cmt.es/instrucciones">CMT</a><br><br>
 * 
 *	Servicios SMS/MMS: aquellos servicios basados en mensajes de texto y multimedia, entre los que se incluyen:<br>
 *		Servicios SMS/MMS gratuitos: aquellos códigos numéricos de seis dígitos de que empiezan por 205 y 207.<br>
 *		Servicios SMS/MMS con tarificación normal: aquellos códigos numéricos de seis dígitos de que empiezan por 215 y 217.<br>
 *		Servicios SMS/MMS Premium: aquellos códigos numéricos pertenecientes a los rangos asociados a las siguentes modalidades:<br>
 *			a) Precio inferior o igual a 1,2 euros: Códigos de cinco dígitos que empiezan por 25 y 27 y 280 (este último para campañas benéficas y solidarias).<br>
 *			b) Precio entre 1,2 y 6 euros:Códigos de cinco dígitos que empiezan por 35 y 37.<br>
 *			c) Suscripción con precio inferior o igual a 1,2 euros: Códigos de seis dígitos que empiezan por 795 y 797.<br>
 *			d) Adultos con precio inferior o igual a 6 euros: Códigos de seis dígitos que empiezan por 995 y 997.<br><br>
 * 
 * @author JavocSoft 2013
 * @version 1.0<br>
 * $Rev: 331 $<br>
 * $LastChangedDate: 2013-11-01 14:37:01 +0100 (Fri, 01 Nov 2013) $<br>
 * $LastChangedBy: jgonzalez $
 *
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
	public static final String SMS_MMS_PREMIUM_6DIGITS__UPTO_1_2_E_STARTING_27 = "797";
	
	public static final String SMS_MMS_PREMIUM_5DIGITS__FROM_1_2_TO_6_E_STARTING_35 = "35";
	public static final String SMS_MMS_PREMIUM_5DIGITS__FROM_1_2_TO_6_E_STARTING_37 = "37";
	
	public static final String SMS_MMS_PREMIUM_6DIGITS__UPTO_6_E_STARTING_995 = "995";
	public static final String SMS_MMS_PREMIUM_6DIGITS__UPTO_6_E_STARTING_997 = "997";
	
	
	public enum CMTSMSMMSShortNumberIdentifier {
		SMS_MMS_FREE_6DIGITS__STARTING_205(205),
		SMS_MMS_FREE_6DIGITS__STARTING_207(207),
		SMS_MMS_NORMAL_6DIGITS__STARTING_215(215),
		SMS_MMS_NORMAL_6DIGITS__STARTING_217(217),
		SMS_MMS_PREMIUM_5DIGITS__UPTO_1_2_E_STARTING_25(25),
		SMS_MMS_PREMIUM_5DIGITS__UPTO_1_2_E_STARTING_27(27),
		SMS_MMS_PREMIUM_5DIGITS__UPTO_1_2_E_STARTING_280(280),
		SMS_MMS_PREMIUM_6DIGITS__UPTO_1_2_E_STARTING_795(795),
		SMS_MMS_PREMIUM_6DIGITS__UPTO_1_2_E_STARTING_27(27),
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
		 
		 /**
		  * Returns TRUE if the number is recognized by
		  * the CMT.
		  * 
		  * @param number
		  * @return
		  */
		 public static boolean isRecognized(String number){
			 boolean res = false;
			
			 if(number.length()==5 || number.length()==6){
			 
				 String id3digits = number.substring(0, 2);
				 String id2digits = number.substring(0, 1);
				 
				 try{
					 valueOf(id3digits);
					 res = true;
				 }catch(Exception e){}
				 
				 try{
					 valueOf(id2digits);
					 res = true;
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
	};

}
