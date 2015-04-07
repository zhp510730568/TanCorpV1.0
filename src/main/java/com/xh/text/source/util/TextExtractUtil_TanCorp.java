package com.xh.text.source.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 提取训练语料和测试语料工具类
 * 
 */
public class TextExtractUtil_TanCorp {
	/**
	 * 提取训练语料和测试语料
	 * 
	 * @param rootPath 各类别语料存储的根目录
	 * @param outputFilePath 训练语料存储的文件路径
	 * @param testSetPath 测试语料语料存储的文件路径
	 * @param testArtiCount 测试语料数量
	 * 
	 * @return void
	 */
	public static void extractCorpus(String rootPath, String outputFilePath, String testSetPath, int testArtiCount) throws IOException {
		Map<String, Integer> nameClassMap = new HashMap<String, Integer>();
		List<String> pathList = getRootDicPath(rootPath, nameClassMap);
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFilePath, false), "GB2312"));
		BufferedWriter bw_test = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(testSetPath, false), "GB2312"));
		BufferedReader br = null;
		for(String dicPath: pathList) {
			File subDic = new File(dicPath);
			if(subDic.exists() && subDic.isDirectory()) {
				String dicName = subDic.getName();
				int classID = -1;
				if(nameClassMap.containsKey(dicName)) {
					classID = nameClassMap.get(dicName);
				} else {
					continue;
				}
				File[] subFiles = subDic.listFiles();
				int count = 0;
				for(File file: subFiles) {
					br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GB2312"));
					String line = "";
					String text = "";
					while((line = br.readLine()) != null) {
						text += line;
					}
					if(count <= testArtiCount) {
						bw_test.write(String.format("%d\t%s\r\n", classID, text));
						bw_test.flush();
					} else {
						bw.write(String.format("%d\t%s\r\n", classID, text));
						bw.flush();
					}
					count++;
					br.close();
				}
			}
		}
		bw_test.close();
		bw.close();
	}
	
	/**
	 * 获取语料各类别文章所在的根目录
	 * 
	 * @param dicPath 语料存储的根目录
	 * @param nameMap 保存类别名称及其对应类别识的字典
	 * 
	 * @return 各类别文章存储的路径
	 */
	public static List<String> getRootDicPath(String dicPath, Map<String, Integer> nameMap) {
		List<String> pathList = new LinkedList<String>();
		File file = new File(dicPath);
		dicPath = file.getAbsolutePath();
		if(file.isDirectory()) {
			String[] paths = file.list();
			int len = paths.length;
			int classID = 0;
			for(int index = 0; index < len; index++) {
				String dicName = paths[index];
				File subFile = new File(dicPath + "//" + dicName);
				if(subFile.isDirectory()) {
					String subDicPath = subFile.getAbsolutePath();
					pathList.add(subDicPath);
					if(!nameMap.containsKey(dicName)) {
						nameMap.put(dicName, classID);
						classID ++;
					}
				}
			}
		}
		
		return pathList;
	}
}
