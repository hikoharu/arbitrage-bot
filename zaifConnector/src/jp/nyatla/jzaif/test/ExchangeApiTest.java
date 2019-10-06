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

import java.io.File;

import jp.nyatla.jzaif.api.ApiKey;
import jp.nyatla.jzaif.api.ExchangeApi;
import jp.nyatla.jzaif.api.result.ActiveOrdersResult;
import jp.nyatla.jzaif.api.result.CancelOrderResult;
import jp.nyatla.jzaif.api.result.DepositHistoryResult;
import jp.nyatla.jzaif.api.result.GetInfoResult;
import jp.nyatla.jzaif.api.result.TradeHistoryResult;
import jp.nyatla.jzaif.api.result.TradeResult;
import jp.nyatla.jzaif.api.result.WithdrawHistoryResult;
import jp.nyatla.jzaif.types.Currency;
import jp.nyatla.jzaif.types.CurrencyPair;
import jp.nyatla.jzaif.types.TradeType;

/**
 * ExchangeAPIのテストを行います。 Withdrawのテストでは0.1MONAが送信されるので注意してください。
 */
public class ExchangeApiTest {

	public static void main(String[] args) {
		// ApiKey k=ApiKey.loadFromXml(new File("setting.xml"));
		// ExchangeApi lp=new ExchangeApi(k);
		// GetInfoResult r1=lp.getInfo();
		//// TradeHistoryResult
		// r2=lp.tradeHistory(null,null,null,null,null,null,null,null);
		// TradeResult r4=lp.trade(CurrencyPair.MONAJPY,TradeType.BID,1,1,null);
		// ActiveOrdersResult r3=lp.activeOrders(null);
		// CancelOrderResult r5=lp.cancelOrder(r3.orders.get(0).id);
		// r5=lp.cancelOrder(r3.orders.get(0).id);
		//
		//
		//
		//// WithdrawResult r7=lp.withdraw(Currency.MONA,
		// "",0.1,0.0);
		// DepositHistoryResult
		// r8=lp.depositHistory(Currency.MONA,null,null,null,null,null,null,null);
		// WithdrawHistoryResult
		// r10=lp.withdrawHistory(Currency.MONA,null,null,null,null,null,null,null);
		//
		return;
	}
}
