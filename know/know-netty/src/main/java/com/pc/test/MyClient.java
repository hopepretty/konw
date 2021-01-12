package com.pc.test;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * 测试客户端
 *
 * @author pc
 * @Date 2021/1/12
 **/
public class MyClient {

	public static void main(String[] args) {
		//设置线程组
		NioEventLoopGroup executors = new NioEventLoopGroup();
		try {
			//设置客户端对象，并设置参数
			Bootstrap client = new Bootstrap();
			client.group(executors)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							socketChannel.pipeline().addLast(new MyClientHandler());
						}
					});
			System.out.println("客户端准备就绪，随时可以起飞~");
			//连接服务端
			ChannelFuture sync = client.connect("127.0.0.1", 6666).sync();
			//对关闭通道进行异步监听
			sync.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			executors.shutdownGracefully();
		}
	}

}
