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

package jp.nyatla.jzaif.api.result;

import org.json.JSONObject;

/**
 * ExchangeAPIのtrade APIの戻り値を格納するクラスです。
 * 拡張パラメータは{@link #success}がtrueのときのみ有効です。
 */
public class TradeResult extends ExchangeCommonResult
{
	final public double received;
	final public double remains;
	final public long order_id;
	final public Funds funds;
	
	/** パース済みJSONからインスタンスを構築します。*/
	public TradeResult(JSONObject i_jso)
	{
		super(i_jso);
		if(!this.success){
			this.received=Double.NaN;
			this.remains=Double.NaN;
			this.order_id=0;
			this.funds=null;
			return;
		}
		JSONObject jso=i_jso.getJSONObject("return");
		this.received=jso.getDouble("received");
		this.remains=jso.getDouble("remains");
		this.order_id=jso.getLong("order_id");
		this.funds=new Funds(jso.getJSONObject("funds"));
	}
}
