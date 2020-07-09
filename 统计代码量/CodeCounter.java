package ͳ�ƴ�����;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 
 * ������CodeCounter.java<br>
 * <p>
 * ���ܣ�ͳ����Ŀ������ ������
 * </p>
 */
public class CodeCounter {
 
	static long commentLine = 0;
	static long whiteLine = 0;
	static long normalLine = 0;
	static long totalLine = 0;
	static boolean comment = false;
 
	public static void main(String[] args) {
//		File file = new File("F:/nhs/WEB-INF/classes/com"); // ������������Ҫͳ�Ƶ��ļ���·��
//		File file = new File("E:/wilmar/workspace/svn-master-branch/target/classes/com"); // ������������Ҫͳ�Ƶ��ļ���·��
		File file = new File("D:\\myeclipseWorkSpace\\gjj"); // ������������Ҫͳ�Ƶ��ļ���·��
		getChild(file);
		System.out.println("��Ч��������: " + normalLine);
		System.out.println("ע������: " + commentLine);
		System.out.println("�հ�����: " + whiteLine);
		System.out.println("�ܴ�������: " + totalLine);
	}
 
	private static void getChild(File child) { // ������Ŀ¼
		if (child.getName().matches(".*\\.class$")) { // ֻ��ѯjava�ļ�
			try {
				BufferedReader br =  new BufferedReader(new FileReader(child));
				String line = "";
				while ((line = br.readLine()) != null) {
					parse(line);
				}
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (child.listFiles() != null) {
			for (File f : child.listFiles()) {
				getChild(f);
			}
		}
	}
 
	private static void parse(String line) {
		line = line.trim();
		totalLine++;
		if (line.length() == 0) {
			whiteLine++;
		} else if (comment) {
			commentLine++;
			if (line.endsWith("*/")) {
				comment = false;
			} else if (line.matches(".*\\*/.+")) {
				normalLine++;
				comment = false;
			}
		} else if (line.startsWith("//")) {
			commentLine++;
		} else if (line.matches(".+//.*")) {
			commentLine++;
			normalLine++;
		} else if (line.startsWith("/*") && line.matches(".+\\*/.+")) {
			commentLine++;
			normalLine++;
			if (findPair(line)) {
				comment = false;
			} else {
				comment = true;
			}
		} else if (line.startsWith("/*") && !line.endsWith("*/")) {
			commentLine++;
			comment = true;
		} else if (line.startsWith("/*") && line.endsWith("*/")) {
			commentLine++;
			comment = false;
		} else if (line.matches(".+/\\*.*") && !line.endsWith("*/")) {
			commentLine++;
			normalLine++;
			if (findPair(line)) {
				comment = false;
			} else {
				comment = true;
			}
		} else if (line.matches(".+/\\*.*") && line.endsWith("*/")) {
			commentLine++;
			normalLine++;
			comment = false;
		} else {
			normalLine++;
		}
	}
 
	private static boolean findPair(String line) { // ����һ����/*��*/�Ƿ�ɶԳ���
		int count1 = 0;
		int count2 = 0;
		Pattern p = Pattern.compile("/\\*");
		Matcher m = p.matcher(line);
		while (m.find()) {
			count1++;
		}
		p = Pattern.compile("\\*/");
		m = p.matcher(line);
		while (m.find()) {
			count2++;
		}
		return (count1 == count2);
	}
}
