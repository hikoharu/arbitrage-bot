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

import java.util.Date;

import org.json.JSONObject;

/**
 * ExchangeAPIのgetinfo APIの戻り値を格納するクラスです。
 * 拡張パラメータは{@link #success}がtrueのときのみ有効です。
 */
public class GetInfoResult extends ExchangeCommonResult
{
	/** パース済みJSONからインスタンスを構築します。*/
	public GetInfoResult(JSONObject i_jso)
	{
		super(i_jso);
		if(!this.success){
			this.funds=null;
			this.deposit=null;
			this.rights=null;
			this.trade_count=0;
			this.open_orders=0;
			this.server_time=null;
			return;
		}
		JSONObject r=i_jso.getJSONObject("return");
		this.funds=new Funds(r.getJSONObject("funds"));
		this.deposit=new Deposit(r.getJSONObject("deposit"));
		this.rights=new Rights(r.getJSONObject("rights"));
		this.trade_count=r.getLong("trade_count");
		this.open_orders=r.getInt("open_orders");
		this.server_time=new Date(r.getLong("server_time")*1000);
	}

	public class Deposit{
		final public double jpy;
		final public double btc;
		final public double mona;
		final public double eth;
		public Deposit(JSONObject i_jso)
		{
			this.jpy=i_jso.getDouble("jpy");
			this.btc=i_jso.getDouble("btc");
			this.mona=i_jso.getDouble("mona");
			this.eth =i_jso.getDouble("ETH");
		}
	}
	public class Rights{
		final public boolean info;
		final public boolean trade;
		final public boolean withdraw;		
		public Rights(JSONObject i_jso)
		{
			this.info=i_jso.getInt("info")==1?true:false;
			this.trade=i_jso.getInt("trade")==1?true:false;
			this.withdraw=i_jso.getInt("withdraw")==1?true:false;
		}
	}

	final public Funds funds;
	final public Deposit deposit;
	final public Rights rights;
	final public long trade_count;
	final public int open_orders;
	final public Date server_time;
}
