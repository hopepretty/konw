package com.pc.test;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 测试服务类
 *
 * @author pc
 * @Date 2020/12/23
 **/
public class MyServer {

	public static void main(String[] args) {
		//创建两个线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup();
		try {
			//创建服务端的启动对象，设置参数
			ServerBootstrap server = new ServerBootstrap();
			server.group(bossGroup, workGroup)
					 //设置服务端通道实现类型
			        .channel(NioServerSocketChannel.class)
					//设置线程队列得到连接个数
					.option(ChannelOption.SO_BACKLOG, 128)
					//设置保持活动连接状态
					.childOption(ChannelOption.SO_KEEPALIVE, true)
					//使用匿名内部类的形式初始化通道对象
					.childHandler(new ChannelInitializer<SocketChannel>() {
						//给workerGroup的EventLoop对应的管道设置处理器
						@Override
						protected void initChannel(SocketChannel socketChannel) throws Exception {
							socketChannel.pipeline().addLast(new MyServerSyncHandler());
						}
					});
			System.out.println("java技术爱好者的服务端已经准备就绪...");
			//绑定端口，启动服务端
			ChannelFuture channelFuture = server.bind(6666).sync();
			//添加监听器
			channelFuture.addListener((ChannelFutureListener) future -> {
				//判断是否操作成功
				if (future.isSuccess()) {
					System.out.println("连接成功");
				} else {
					System.out.println("连接失败");
				}
			});
			//对关闭通道进行异步监听
			channelFuture.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workGroup.shutdownGracefully();
		}
	}

}
