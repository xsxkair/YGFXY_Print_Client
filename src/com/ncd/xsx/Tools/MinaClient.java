package com.ncd.xsx.Tools;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import javax.annotation.PostConstruct;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.service.IoServiceListener;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ncd.xsx.Define.RequestObject;
import com.ncd.xsx.Define.ResponseObject;
import com.ncd.xsx.Handlers.Activity;
import com.ncd.xsx.Spring.ActivitySession;

@Component
public class MinaClient extends IoHandlerAdapter implements Runnable, IoServiceListener  {

	private NioSocketConnector connector = null;
	private IoSession session = null;
	private static final long CONNECT_TIMEOUT = 30*1000L; // 30 seconds
	private InetSocketAddress serverAddress = null;
	private Boolean isConnect = false;
	private Thread clientThread = null;
	private ObjectMapper mapper = new ObjectMapper();
	
	@Autowired ActivitySession activitySession;
	
	@PostConstruct
	public void  MinaClientInit() {
		connector = new NioSocketConnector();
	    connector.setConnectTimeoutMillis(CONNECT_TIMEOUT);
	    TextLineCodecFactory textLineCodecFactory = new TextLineCodecFactory(Charset.forName( "GBK" ));
	    textLineCodecFactory.setDecoderMaxLineLength(10240);
	    textLineCodecFactory.setEncoderMaxLineLength(10240);

	    connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter(textLineCodecFactory));
	    connector.setHandler(this);
	    connector.addListener(this);
	    serverAddress = new InetSocketAddress("192.168.0.33", 9200);
	    
	    clientThread = new Thread(this);
	    clientThread.start();
	    
	    isConnect = true;
	}
	
	public Boolean sendMessage(RequestObject requestObject)
	{
		if(session == null) {
			System.err.println("session is null");
			return false;
		}
		
		try {
			String jsonStr = mapper.writeValueAsString(requestObject);
			
			session.write(jsonStr);
			System.out.println("send success");
			return true;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public void messageReceived(IoSession session, Object message){
		// TODO Auto-generated method stub
		try {
			super.messageReceived(session, message);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Activity activity = activitySession.topActivity();
		ResponseObject responseObject = null;
		System.out.println(message.toString());
		
		try {
			responseObject = mapper.readValue(message.toString(), ResponseObject.class);	
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			responseObject = null;
		}
		
		if(activity != null)
			activity.socketMessageCallBack(responseObject);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			while(isConnect)
			{
				try {
					
					System.out.println("start connect server");
		            ConnectFuture future = connector.connect(serverAddress);
		            future.awaitUninterruptibly();
		            session = future.getSession();
		            isConnect = false;
		            System.out.println("connect success");
		            break;
		        } catch (RuntimeIoException e) {
		            System.err.println("connect fail");
		           
		            try {
						Thread.sleep(5000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		        }
			}

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void serviceActivated(IoService arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serviceDeactivated(IoService arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void serviceIdle(IoService arg0, IdleStatus arg1) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sessionDestroyed(IoSession arg0) throws Exception {
		// TODO Auto-generated method stub
		System.err.println("session destroyed");
		isConnect = true;
	}

}
