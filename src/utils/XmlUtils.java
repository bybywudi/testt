package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtils {
	
private static String filepath;

public static String getFilepath() {
	return filepath;
}

static {
	filepath = XmlUtils.class.getClassLoader().getResource("users.xml").getPath();//获取文件路径以进行操作，由于是静态的，只需要获取一次
	
	//System.out.println(filepath+"path");
}
	
public static Document getDocument() throws DocumentException {
	SAXReader reader = new SAXReader();
	
	//System.out.println(filepath+"getDocument");
	
	Document document = reader.read(new File(filepath));
	return document;
		
	}
	
public static void write2Xml(Document document) throws IOException {
	//将传入的document写入filepath指定的XML中
	OutputFormat format = OutputFormat.createPrettyPrint();
	format.setEncoding("UTF-8");
	XMLWriter writer = new XMLWriter(new FileOutputStream(filepath), format );
	writer.write( document );

	writer.close();
}
}
