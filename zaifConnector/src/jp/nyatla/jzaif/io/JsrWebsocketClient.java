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

import java.io.IOException;
import java.net.URI;

import javax.websocket.*;


/**
 * Java Websocketを使うWebsocketインタフェイスの実装です。
 *
 */
public class JsrWebsocketClient implements IWebsocketClient{
	private Session _session;
	public JsrWebsocketClient() {
	}

	@Override
	public void connect(String i_url, IWebsocketObserver i_observer) {
		assert(i_observer!=null);
		if (this._session != null) {
			throw new RuntimeException();
		}
		URI uri = URI.create(i_url);
		WebSocketContainer cn = ContainerProvider.getWebSocketContainer();
		try {
			Session session = cn.connectToServer(new WsClient(i_observer), uri);
			// OpenCheck?
			long s=System.currentTimeMillis();
			//10secの接続待ち
			do{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				if(session.isOpen()){
					this._session = session;
					return;
				}
			}while(System.currentTimeMillis()-s<10*1000);
			this._session.close();
			throw new RuntimeException();
		} catch (DeploymentException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	public void disconnect() {
		if (this._session != null) {
			try {
				this._session.close();
			} catch (IOException e) {
			}
			this._session = null;
		}
	}
	@Override
	public boolean isConnectionEnable()
	{
		return this._session.isOpen();
	}
	
	@ClientEndpoint
	public class WsClient {
		final private IWebsocketObserver _observer;	
		public WsClient(IWebsocketObserver i_observer)
		{
			this._observer=i_observer;
		}

		@OnOpen
		public void onOpen(Session session) {
		}
		@OnMessage
		public void onMessage(String message)
		{
			this._observer.onStringPacket(message);
			//しくじったときここでcloseかけられるのかな？
		}
		@OnError
		public void onError(Throwable t)
		{
			this._observer.onError("An error was occurred.");
		}
		@OnClose
		public void onClose(Session session) {
		}
	}




}
