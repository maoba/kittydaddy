package com.kittydaddy.search;

import java.io.File;
import java.io.FileFilter;

/**
 * 此类用于为 .txt 文件过滤器
 * @author kitty daddy
 *
 */
public class TextFileFilter implements FileFilter {
	@Override
	public boolean accept(File file) {
		 return file.getName().toLowerCase().endsWith(".txt");
	}

}
