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
package jp.nyatla.jzaif.test;


import jp.nyatla.jzaif.api.StreamingApi;
import jp.nyatla.jzaif.api.result.StreamingNotify;
import jp.nyatla.jzaif.types.CurrencyPair;

/**
 * ストリーミングAPIのテストプログラムです。
 */
public class StreamingApiTest {
	static public class Sa extends StreamingApi
	{
		public Sa(CurrencyPair i_cpair) {
			super(i_cpair);
		}
		@Override
		public void onUpdate(String i_data)
		{
			//生JSONテキスト
			System.out.println(i_data);
		}
		@Override
		public void onUpdate(StreamingNotify i_data)
		{
			return;
		}
	}
	public static void main(String[] args)
	{
		StreamingApi lp=new Sa(CurrencyPair.BTCJPY);
		try {
			Thread.sleep(100000000);
		} catch (InterruptedException e) {
		}
		lp.shutdown();
		return;
	}
}
