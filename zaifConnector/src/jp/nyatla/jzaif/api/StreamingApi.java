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

import org.json.JSONObject;

import jp.nyatla.jzaif.api.result.StreamingNotify;
import jp.nyatla.jzaif.io.IWebsocketClient;
import jp.nyatla.jzaif.io.IWebsocketObserver;
import jp.nyatla.jzaif.io.JsrWebsocketClient;
import jp.nyatla.jzaif.types.CurrencyPair;

/**
 * ZaifストリーミングAPIのラッパークラスです。
 * https://corp.zaif.jp/api-docs/trade-api/
 * このクラスは継承して使います。
 * 通知される非同期メッセージは、{@link #onUpdate}関数をオーバライドして受信します。
 * 
 */
public class StreamingApi
{
	final private String API_URL_PREFIX="ws://api.zaif.jp:8888/stream?currency_pair=";
	final private IWebsocketClient _client;
	/**
	 * カスタムインタフェイスを使う場合に使用します。
	 * @param i_client
	 * @param i_cpair
	 */
	public StreamingApi(IWebsocketClient i_client,CurrencyPair i_cpair)
	{
		i_client.connect(API_URL_PREFIX+i_cpair.symbol,new WsObserver(this));
		this._client=i_client;
	}
	/**
	 * 通貨ペアに対応したAPIラッパーを構築します。
	 * @param i_cpair
	 * 通貨ペアの種類。
	 */
	public StreamingApi(CurrencyPair i_cpair)
	{
		this(new JsrWebsocketClient(),i_cpair);
	}
	/**
	 * インスタンスをシャットダウンします。
	 * 関数終了後に、非同期イベントが停止しします。
	 */
	public void shutdown()
	{
		this._client.disconnect();
	}
	private class WsObserver implements IWebsocketObserver
	{
		final StreamingApi _parent;
		WsObserver(StreamingApi i_parent){
			this._parent=i_parent;
		}
		@Override
		public void onStringPacket(String i_value)
		{
			this._parent.onUpdate(i_value);
			JSONObject jso=new JSONObject(i_value);
			this._parent.onUpdate(new StreamingNotify(jso));
			return;
		}
		@Override
		public void onError(String i_message)
		{
		}
	}
	/**
	 * Notyfyの受信を生JSONで通知します。受信するにはこの関数をオーバライドしてください。
	 * @param i_data
	 * JSONテキストデータ。
	 */
	public void onUpdate(String i_data)
	{
	}
	/**
	 * Notyfyの受信をパースして通知します。受信するにはこの関数をオーバライドしてください。
	 * @param i_data
	 * パース済のNotifyオブジェクト。
	 */
	public void onUpdate(StreamingNotify i_data)
	{
	}
	
}
