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
 * PublicAPIのticker APIの戻り値を格納するクラスです。
 */
public class TickerResult
{
	/** 終値(last_priceに相当)*/
	final public double last;
	/** 過去24時間の高値*/
	final public double high;
	/** 過去24時間の安値*/
	final public double low;
	/** 過去24時間の加重平均*/
	final public double vwap;
	/** 過去24時間の出来高*/
	final public double volume;
	/** 買気配値*/
	final public double bid;
	/** 売気配値*/
	final public double ask;
	/** パース済みJSONからインスタンスを構築します。*/
	public TickerResult(JSONObject i_jso)
	{
		this.last=i_jso.getDouble("last");
		this.high=i_jso.getDouble("high");
		this.low=i_jso.getDouble("low");
		this.vwap=i_jso.getDouble("vwap");
		this.volume=i_jso.getDouble("volume");
		this.bid=i_jso.getDouble("bid");
		this.ask=i_jso.getDouble("ask");
		return;
	}
	@Override
	public String toString()
	{
		return String.format("last:%f\thigh:%f\tlow:%f\tvwap:%f\tvoulme:%f\tbid:%f\task:%f\t",
				this.last,this.high,this.low,this.vwap,this.volume,this.bid,this.ask);
	}
}
