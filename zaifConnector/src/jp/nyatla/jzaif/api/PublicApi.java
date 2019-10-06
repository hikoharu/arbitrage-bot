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
package jp.nyatla.jzaif.api;


import org.json.JSONArray;
import org.json.JSONObject;


import jp.nyatla.jzaif.api.result.DepthResult;
import jp.nyatla.jzaif.api.result.TickerResult;
import jp.nyatla.jzaif.api.result.TradesResult;
import jp.nyatla.jzaif.io.IHttpClient;
import jp.nyatla.jzaif.io.NyansatHttpClient;
import jp.nyatla.jzaif.types.CurrencyPair;
/**
 * Zaif公開情報APIのラッパークラスです。
 * https://corp.zaif.jp/api-docs/
 */
public class PublicApi
{
	final private static String API_URL_PREFIX="https://api.zaif.jp/api/1/";
	final private CurrencyPair _cu_pair;
	final private IHttpClient _cl=new NyansatHttpClient();
	/**
	 * コンストラクタ。
	 * @param i_pair
	 * 通貨ペアを指定します。
	 */
	public PublicApi(CurrencyPair i_pair)
	{
		this._cu_pair=i_pair;
	}
	/**
	 * depth APIを実行して結果を得ます。
	 * @return
	 * 結果を格納した{@link DeapthResult}オブジェクト
	 */
	public DepthResult depth()
	{
		String json_str=this._cl.getText(API_URL_PREFIX+"depth/"+this._cu_pair.symbol);		
		JSONObject jso=new JSONObject(json_str);
		return new DepthResult(jso);
	}
	/**
	 * ticker APIを実行して結果を得ます。
	 * @return
	 * 結果を格納した{@link TickerResult}オブジェクト
	 */
	public TickerResult ticker()
	{
		String json_str=this._cl.getText(API_URL_PREFIX+"ticker/"+this._cu_pair.symbol);	
		JSONObject jso=new JSONObject(json_str);
		return new TickerResult(jso);
	}
	/**
	 * trade APIを実行して結果を得ます。
	 * @return
	 * 結果を格納した{@link TradesResult}オブジェクト
	 */	
	public TradesResult trades()
	{
		String json_str=this._cl.getText(API_URL_PREFIX+"trades/"+this._cu_pair.symbol);		
		JSONArray jso=new JSONArray(json_str);
		TradesResult r=new TradesResult(jso);
		return r;
	}
	/**
	 * last_price APIを実行して結果を得ます。
	 * @return
	 * lastpriceの値。
	 */	
	public double lastPrice()
	{
		String json_str=this._cl.getText(API_URL_PREFIX+"last_price/"+this._cu_pair.symbol);		
		JSONObject jso=new JSONObject(json_str);
		return jso.getDouble("last_price");
	}	
}
