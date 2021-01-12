package com.pc.config;

import com.hankcs.hanlp.corpus.io.IIOAdapter;

import java.io.*;

/**
 * 使用读写静态资源文件的方法来读取HanLP所需的词典和模型数据
 *
 * @author pc
 * @Date 2020/12/8
 **/
public class HanlpFileIoAdapter implements IIOAdapter {

	@Override
	public InputStream open(String s) throws IOException {
		return new FileInputStream(s);
	}

	@Override
	public OutputStream create(String s) throws IOException {
		return new FileOutputStream(s);
	}
}
