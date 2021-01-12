package com.pc.test;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

/**
 * 异步处理服务端请求
 *
 * @author pc
 * @Date 2021/1/12
 **/
public class MyServerSyncHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		//获取到线程池eventLoop，添加线程，执行
//		ctx.channel().eventLoop().execute(() -> {
//			try {
//				//长时间操作，不至于长时间的业务操作导致Handler阻塞
//				Thread.sleep(1000);
//				System.out.println("长时间的业务处理");
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
		//延时执行
		ctx.channel().eventLoop().schedule(() -> {
			try {
				//长时间操作，不至于长时间的业务操作导致Handler阻塞
				Thread.sleep(1000);
				System.out.println("长时间的业务处理");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}, 5, TimeUnit.SECONDS);
	}

}
