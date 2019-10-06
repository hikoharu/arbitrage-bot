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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.nyatla.jzaif.Utils;
import jp.nyatla.jzaif.types.CurrencyPair;
import jp.nyatla.jzaif.types.TradeType;

import org.json.JSONArray;
import org.json.JSONObject;
/**
 * SreamingAPIの通知を格納するクラスです。
 * {@ #currenty_pair}はzaifAPIの不具合だと思うので名前を変えてあります。
 */
public class StreamingNotify {
	/**last_price.priceの値*/
	final public double last_price;
	/**last_price.actionの値*/
	final public TradeType last_action;
	final public Date timestamp;
	final public DepthResult depth;
	final public TradesResult trades;
	final public List<String> target_users;
	final public CurrencyPair currency_pair;
	/** パース済みJSONからインスタンスを構築します。*/
	public StreamingNotify(JSONObject i_jso)
	{
		//asks,bids
		this.depth=new DepthResult(i_jso);
		this.trades=new TradesResult(i_jso.getJSONArray("trades"));
		{
			this.target_users=new ArrayList<String>();
			JSONArray a=i_jso.getJSONArray("target_users");
			for(int i=0;i<a.length();i++){
				this.target_users.add(a.getString(i));
			}
		}
		this.currency_pair=CurrencyPair.toEnum(i_jso.getString("currency_pair"));
		JSONObject lp=i_jso.getJSONObject("last_price");
		this.last_action=TradeType.toEnum(lp.getString("action"));
		this.last_price=lp.getDouble("price");
		this.timestamp=Utils.parseZaifFullTimeText(i_jso.getString("timestamp"));
		return;
	}
}
