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
package jp.nyatla.jzaif.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.nyatla.jzaif.api.ExchangeApi;
import jp.nyatla.nyansat.client.BasicHttpClient;

/**
 * NyanStockを使うHTTPインタフェイスの実装です。
 */
public class NyansatHttpClient implements IHttpClient {

	private static Logger log = LoggerFactory.getLogger(NyansatHttpClient.class);
	final private BasicHttpClient _cl;

	public NyansatHttpClient() {
		this._cl = new BasicHttpClient();
	}

	@Override
	public String getText(String i_url) {
		return this._cl.getTextContents(i_url);
	}

	@Override
	public String postText(String i_url, String i_key, String i_sign, String i_msg) {
		String[][] header = { { "Key", i_key }, { "Sign", i_sign } };
		String body = this._cl.postTextContents(i_url, "UTF-8", header, i_msg);
		if (body == null) {
			log.info("[Zaif HTTP error]");
			log.info("lastStatus is "+ this._cl.getLastStatus());
		}
		return body;
	}
}
