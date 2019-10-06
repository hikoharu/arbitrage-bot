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
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * PublicAPIのdepth APIの戻り値を格納するクラスです。
 */
public class DepthResult
{
	public class Item{
		final public double price;
		final public double volume;
		public Item(JSONArray i_src){
			this.price=i_src.getDouble(0);
			this.volume=i_src.getDouble(1);
		}
		@Override
		public String toString()
		{
			return "price:"+this.price+",volume:"+this.volume;
		}
	}
	final public List<Item> asks=new ArrayList<Item>();
	final public List<Item> bids=new ArrayList<Item>();
	/** パース済みJSONからインスタンスを構築します。*/
	public DepthResult(JSONObject i_src){
		JSONArray a=i_src.getJSONArray("asks");
		for(int i=0;i<a.length();i++){
			this.asks.add(new Item(a.getJSONArray(i)));
		}
		JSONArray b=i_src.getJSONArray("bids");
		for(int i=0;i<b.length();i++){
			this.bids.add(new Item(b.getJSONArray(i)));
		}
	}
	@Override
	public String toString()
	{
		return "{"+this.asks.toString()+"},{"+this.bids.toString()+"}";
	}	
}
