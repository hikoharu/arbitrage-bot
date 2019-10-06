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

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import jp.nyatla.nyansat.utils.xml.XPathUtil;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * 認証の必要なAPIに使うAPIキーです。
 * APIキーはzaifのウェブサイトから入手してください。
 */
public class ApiKey
{
	final public String key;
	final public String secret;
	/**
	 * APIキーとシークレットキーからインスタンスを構築します。
	 * @param i_key
	 * APIキー文字列。
	 * @param i_secret
	 * シークレットキー文字列。
	 */
	public ApiKey(String i_key,String i_secret)
	{
		this.key=i_key;
		this.secret=i_secret;
	}
	/**
	 * XMLファイルからAPIキーとシークレットキーのペアを読みだしてインスタンスを生成します。
	 * <pre>
	 * &lt;root&gt;
	 * &lt;key&gt;YOUR API KEY&lt;/key&gt;
	 * &lt;secret&gt;YOUR SECRET KEY&lt;/secret&gt;
	 * &lt;/root&gt;
	 * </pre>
	 * @param i_file
	 * XMLファイルのパスを指定します。
	 * @return
	 * 生成したインスタンス。
	 */
	public static ApiKey loadFromXml(File i_file)
	{
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc=builder.parse(i_file);
			XPathUtil xpath=new XPathUtil(doc);
			return new ApiKey(
					xpath.selectSingleElement("//key").getTextContent(),
					xpath.selectSingleElement("//secret").getTextContent());
		} catch (ParserConfigurationException | SAXException | IOException e) {
			throw new RuntimeException(e);
		} catch (DOMException | XPathExpressionException e) {
			throw new RuntimeException(e);
		}
	}	
	
	/**
	 * メッセージをSHA512で署名します。
	 * @param i_message
	 * 署名するメッセージ
	 * @return
	 * 署名されたメッセージ
	 */
	public String makeSignedHex(String i_message)
	{
		SecretKeySpec signingKey = new SecretKeySpec(this.secret.getBytes(),"HmacSHA512");
		Mac mac;
		try {
			mac = Mac.getInstance("HmacSHA512");
			mac.init(signingKey);
			StringBuffer sb=new StringBuffer();
			// compute the hmac on input data bytes
			byte[] rawHmac = mac.doFinal(i_message.getBytes());
		    for (byte b :rawHmac) {
		        String hex = String.format("%02x", b);
		        sb.append(hex);
		    }
		    return sb.toString();
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new RuntimeException(e);
		}
	}
}
