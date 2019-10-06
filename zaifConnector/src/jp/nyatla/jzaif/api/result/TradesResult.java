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



import jp.nyatla.jzaif.types.CurrencyPair;
import jp.nyatla.jzaif.types.TradeType;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * PublicAPIのtrade APIの戻り値を格納するクラスです。
 * 拡張パラメータは{@link #success}がtrueのときのみ有効です。
 */
public class TradesResult extends ArrayList<TradesResult.Item>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4431120995766718657L;
	public class Item{
		final public Date date;
		final public double price;
		final public double amount;
		final public long tid;
		final public CurrencyPair currency_pair;
		final public TradeType trade_type;
		public Item(JSONObject i_src){
			this.date=new Date(i_src.getLong("date")*1000);
			this.price=i_src.getDouble("price");
			this.amount=i_src.getDouble("amount");
			this.tid=i_src.getLong("tid");
			if(i_src.has("currency_pair")){
				this.currency_pair=CurrencyPair.toEnum(i_src.getString("currency_pair"));
			}else{
				//StreamingAPIのbug対応
				this.currency_pair=CurrencyPair.toEnum(i_src.getString("currenty_pair"));
			}
			this.trade_type=TradeType.toEnum(i_src.getString("trade_type"));
		}
		@Override
		public String toString()
		{
			return super.toString();
		}
	}
	/** パース済みJSONからインスタンスを構築します。*/
	public TradesResult(JSONArray i_src)
	{
		for(int i=0;i<i_src.length();i++){
			this.add(new Item(i_src.getJSONObject(i)));
		}
	}
}
