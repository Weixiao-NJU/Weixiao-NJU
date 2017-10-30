package org.wx.weixiao.service.implTest;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by Jerry Wang on 2016/12/18.
 */
public class AnalyzerTest {

    @Test
    public void AnalyzerTest(){
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();

        try {

            TokenStream tokenStream = analyzer.tokenStream("field","学分");
            OffsetAttribute offsetAttribute = tokenStream.addAttribute(OffsetAttribute.class);
            PositionIncrementAttribute positionIncrementAttribute = tokenStream.addAttribute(PositionIncrementAttribute.class);
            CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);
            TypeAttribute typeAttribute = tokenStream.addAttribute(TypeAttribute.class);

            tokenStream.reset();
            int position = 0;
            while (tokenStream.incrementToken()) {
                int increment = positionIncrementAttribute.getPositionIncrement();
                if(increment > 0) {
                    position = position + increment;
                    System.out.print(position + ":");
                }
                int startOffset = offsetAttribute.startOffset();
                int endOffset = offsetAttribute.endOffset();
                String term = charTermAttribute.toString();
                System.out.println("[" + term + "]" + ":(" + startOffset + "-->" + endOffset + "):" + typeAttribute.type());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


//    分析器类型            基本介绍
//    WhitespaceAnalyzer   以空格作为切词标准，不对语汇单元进行其他规范化处理
//    SimpleAnalyzer       以非字母符来分割文本信息，并将语汇单元统一为小写形式，并去掉数字类型的字符
//    StopAnalyzer         该分析器会去除一些常有a,the,an等等，也可以自定义禁用词
//    StandardAnalyzer     Lucene内置的标准分析器，会将语汇单元转成小写形式，并去除停用词及标点符号
//    CJKAnalyzer          能对中，日，韩语言进行分析的分词器，对中文支持效果一般。
//    SmartChineseAnalyzer 对中文支持稍好，但扩展性差


}
