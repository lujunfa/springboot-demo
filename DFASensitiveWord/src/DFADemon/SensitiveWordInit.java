package DFADemon;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//�������дʳ�ʼ��

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SensitiveWordInit {
	// �ַ�����
	private String ENCODING = "UTF-8";

	// ��ʼ�������ֿ�
	public Map initKeyWord() {
		// ��ȡ���дʿ� ,����Set��
		Set<String> wordSet = readSensitiveWordFile();
		// �����дʿ���뵽HashMap��//ȷ�������Զ���DFA
		return addSensitiveWordToHashMap(wordSet);
	}

	/**
	 * ��ȡ���дʿ⣬�����дʷ���HashSet�У�����һ��DFA�㷨ģ�ͣ�<br>
	 * �� = { isEnd = 0 �� = { isEnd = 1 �� = {isEnd = 0 �� = {isEnd = 1} } �� = {
	 * isEnd = 0 �� = { isEnd = 1 } } } } �� = { isEnd = 0 �� = { isEnd = 0 �� = {
	 * isEnd = 0 �� = { isEnd = 1 } } } }
	 * 
	 * @author �ﴴ
	 * @date 2017��2��15�� ����3:20:20
	 * @param keyWordSet
	 *            ���дʿ�
	 */

	// ��ȡ���дʿ� ,����HashMap��
	private Set<String> readSensitiveWordFile() {

		Set<String> wordSet = null;
		// appΪ��Ŀ��ַ
		/*
		 * String app = System.getProperty("user.dir"); System.out.println(app);
		 * URL resource = Thread.currentThread().getContextClassLoader()
		 * .getResource("/"); String path = resource.getPath().substring(1);
		 * System.out.println(path); File file = new File(path +
		 * "censorwords.txt");
		 */

		// ���дʿ�
		File file = new File(
				"E:/SpringBootGit/DFASensitiveWord/Resource/censorwords.txt");
		try {
			// ��ȡ�ļ�������
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), ENCODING);

			// �ļ��Ƿ����ļ� �� �Ƿ����
			if (file.isFile() && file.exists()) {

				wordSet = new HashSet<String>();
				// StringBuffer sb = new StringBuffer();
				// BufferedReader�ǰ�װ�࣬�Ȱ��ַ�������������������ˣ��ٶ����ڴ棬����˶���Ч�ʡ�
				BufferedReader br = new BufferedReader(read);
				String txt = null;

				// ��ȡ�ļ������ļ����ݷ��뵽set��
				while ((txt = br.readLine()) != null) {
					wordSet.add(txt);
				}

				br.close();

				/*
				 * String str = sb.toString(); String[] ss = str.split("��"); for
				 * (String s : ss) { wordSet.add(s); }
				 */
			}

			// �ر��ļ���
			read.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return wordSet;
	}

	// ��HashSet�е����д�,����HashMap��
	private Map addSensitiveWordToHashMap(Set<String> wordSet) {

		// ��ʼ�����д��������������ݲ���
		Map wordMap = new HashMap(wordSet.size());

		for (String word : wordSet) {
			Map nowMap = wordMap;
			for (int i = 0; i < word.length(); i++) {
				// ת����char��
				char keyChar = word.charAt(i);
				// ��ȡ
				Object tempMap = nowMap.get(keyChar);
				// ������ڸ�key��ֱ�Ӹ�ֵ
				if (tempMap != null) {
					nowMap = (Map) tempMap;
				}
				// ���������򹹽�һ��map��ͬʱ��isEnd����Ϊ0����Ϊ���������һ��
				else {
					// ���ñ�־λ
					Map<String, String> newMap = new HashMap<String, String>();
					newMap.put("isEnd", "0");
					// ��ӵ�����
					nowMap.put(keyChar, newMap);
					nowMap = newMap;
				}
				// ���һ��
				if (i == word.length() - 1) {
					nowMap.put("isEnd", "1");
				}
			}
		}
		return wordMap;
	}

}
