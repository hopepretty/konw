package com.pc;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.suggest.Suggester;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 开启测试类需要注意的问腿
 * 1、引入test-starter
 * 2、必须要有启动类
 * 3、启动类所在包必须与test包相同
 *
 * @author pc
 * @Date 2020/12/8
 **/
@RunWith(SpringRunner.class)
@SpringBootTest
public class HanlpTest {

	/**
	 *
	 * 分词
	 */
	@Test
	public void testSegment() {
		System.out.println(HanLP.segment("欢迎访问翻车现场的技术博客").toString());
	}

	/**
	 *
	 * 文本推荐
	 */
	@Test
	public void testSuggest() {
		Suggester suggester = new Suggester();
		String[] tempArray = (
				"云对雨，雪对风，晚照对晴空。来鸿对去燕，宿鸟对鸣虫。三尺剑，六钧弓，岭北对江东。" +
						"人间清暑殿，天上广寒宫。两岸晓烟杨柳绿，一园春雨杏花红。" +
						"两鬓风霜，途次早行之客；一蓑烟雨，溪边晚钓之翁。").split("。");
		for (String title : tempArray) {
			suggester.addSentence(title);
		}

		System.out.println(suggester.suggest("杏花村", 2).toString());
		System.out.println(suggester.suggest("baifa", 1).toString());
	}

	/**
	 * 关键字提取
	 *
	 */
	@Test
	public void testKeyExtract() {
		String content = "苹果公司（Apple Inc. ）是美国一家高科技公司。由史蒂夫·乔布斯、斯蒂夫·沃兹尼亚克和罗·韦恩(Ron Wayne)等人于1976年4月1日创立，"
				+ "并命名为美国苹果电脑公司（Apple Computer Inc. ），2007年1月9日更名为苹果公司，总部位于加利福尼亚州的库比蒂诺。";
		List<String> keywordList = HanLP.extractKeyword(content, 5);
		System.out.println(keywordList);
	}

}
