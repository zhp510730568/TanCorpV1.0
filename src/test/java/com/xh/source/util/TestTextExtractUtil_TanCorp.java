package com.xh.source.util;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.xh.text.source.util.TextExtractUtil_TanCorp;

public class TestTextExtractUtil_TanCorp {
	@Test
	public void TestGetRootDicPath() {
		Map<String, Integer> nameMap = new HashMap<String, Integer>();
		List<String> list = TextExtractUtil_TanCorp.getRootDicPath("D:\\NLP\\中文文本分类新闻语料库\\TanCorp-12-Txt\\TanCorp-12-Txt", nameMap);
		for(String dicPath : list) {
			System.out.println(dicPath);
		}
		System.out.println(nameMap.get("地域"));
	}
	
	@Test
	public void TestExtractCorpus() throws IOException {
		TextExtractUtil_TanCorp.extractCorpus("D:\\TanCorp-12-Txt\\TanCorp-12-Txt", "D:\\TanCorp-12-Txt\\corpus.txt", "D:\\NLP\\中文文本分类新闻语料库\\TanCorp-12-Txt\\testcorpus.txt", 20);
	}
}
