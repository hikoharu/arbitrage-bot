/*
 * Copyright (c) 2016, nyatla
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: 
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * 
 * The views and conclusions contained in the software and documentation are those
 * of the authors and should not be interpreted as representing official policies, 
 * either expressed or implied, of the FreeBSD Project.
 */
package jp.nyatla.jzaif;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.nyatla.jzaif.types.CurrencyPair;



/**
 * 便利関数などを定義します。
 */
public class Utils {
	private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * YYYY-MM-DD hh:mm:ss.SSSSSS形式の文字列を日付に変換する。
	 * SSSのナノ秒フィールドは無視する。
	 * @param i_str
	 */
	public synchronized static Date parseZaifFullTimeText(String i_str)
	{
		String[]l=i_str.split("\\.");
		try {
			long t=sdf1.parse(l[0]).getTime();
			if(l.length>1){
				t+=(Integer.parseInt(l[1])/1000);
			}
			return new Date(t);
		} catch (NumberFormatException | ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
	/**
	 * 注文価格を取引所の単位に正規化します。
	 * @param i_cpair
	 * @param s
	 * @return
	 */
	public static double toOrderPrice(CurrencyPair i_cpair,double s)
	{
		switch(i_cpair){
		case BTCJPY:
			return Math.round(s/5f)*5;//5円刻み
		case MONAJPY:
			return new BigDecimal(s).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		case XEMJPY:
			return new BigDecimal(s).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		case XEMBTC:
			return new BigDecimal(s).setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();
		case MONABTC:
			return new BigDecimal(s).setScale(8, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		throw new IllegalArgumentException();
	}
	/**
	 * 注文単位を取引所の単位に正規化します。
	 * @param i_cpair
	 * @param s
	 * @return
	 */
	public static double toOrderAmount(CurrencyPair i_cpair,double s){
		switch(i_cpair){
		case BTCJPY:
			return new BigDecimal(s).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		case MONAJPY:
			return new BigDecimal(s).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
		case XEMJPY:
			return new BigDecimal(s).setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
		case XEMBTC:
			return new BigDecimal(s).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
		case MONABTC:
			return new BigDecimal(s).setScale(0, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		throw new IllegalArgumentException();
	}	
	public static void main(String[] args)
	{
		Date d=parseZaifFullTimeText("2016-07-04 21:17:08.662854");
		System.out.println(d);
		Date d2=parseZaifFullTimeText("2016-07-04 21:17:08");
		System.out.println(d2);
		return;
	}
/*
	public static void main(String[] args)
	{
		for(int i=0;i<10;i++){
			System.out.println(toOrderPrice(CurrencyPair.BTCJPY,Math.random()*100));
		}
		System.out.println();
		for(int i=0;i<10;i++){
			System.out.println(toOrderPrice(CurrencyPair.MONAJPY,Math.random()*10));
		}
		System.out.println();
		for(int i=0;i<10;i++){
			System.out.println(toOrderPrice(CurrencyPair.XEMJPY,Math.random()*2));
		}
		System.out.println();
		for(int i=0;i<10;i++){
			System.out.println(toOrderPrice(CurrencyPair.XEMBTC,Math.random()*1));
		}
		System.out.println();
		for(int i=0;i<10;i++){
			System.out.println(toOrderPrice(CurrencyPair.MONABTC,Math.random()*1));
		}
		System.out.println();
		for(int i=0;i<10;i++){
			System.out.println(toOrderAmount(CurrencyPair.BTCJPY,(Math.random()*100)));
		}
		System.out.println();
		for(int i=0;i<10;i++){
			System.out.println(toOrderAmount(CurrencyPair.MONAJPY,(Math.random()*10)));
		}
		System.out.println();
		for(int i=0;i<10;i++){
			System.out.println(toOrderAmount(CurrencyPair.XEMJPY,(Math.random()*2)));
		}
		System.out.println();
		for(int i=0;i<10;i++){
			System.out.println(toOrderAmount(CurrencyPair.XEMBTC,(Math.random()*10)));
		}
		System.out.println();
		for(int i=0;i<10;i++){
			System.out.println(toOrderAmount(CurrencyPair.MONABTC,(Math.random()*10)));
		}

		return;
	}
*/

}
