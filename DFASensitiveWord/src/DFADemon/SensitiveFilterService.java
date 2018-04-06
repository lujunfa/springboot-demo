package DFADemon;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

//���дʹ�����������DFA�㷨  �������дʹ���

@SuppressWarnings("rawtypes")
public class SensitiveFilterService {

	private Map sensitiveWordMap = null;

	// ��Сƥ�����
	public static int minMatchTYpe = 1;

	// ���ƥ�����
	public static int maxMatchType = 2;

	// ����
	private static SensitiveFilterService instance = null;

	// ���캯������ʼ�����дʿ�
	private SensitiveFilterService() {

		sensitiveWordMap = new SensitiveWordInit().initKeyWord();
	}

	// ��ȡ����
	public static SensitiveFilterService getInstance() {
		if (null == instance) {
			instance = new SensitiveFilterService();
		}
		return instance;
	}

	// ��ȡ�����е����д�

	public Set<String> getSensitiveWord(String txt, int matchType) {
		Set<String> sensitiveWordList = new HashSet<String>();

		for (int i = 0; i < txt.length(); i++) {

			// �ж��Ƿ���������ַ�
			int length = CheckSensitiveWord(txt, i, matchType);

			// ����,����list��
			if (length > 0) {
				sensitiveWordList.add(txt.substring(i, i + length));

				// ��1��ԭ������Ϊfor������
				i = i + length - 1;
			}
		}

		return sensitiveWordList;
	}

	// �滻�������ַ�

	public String replaceSensitiveWord(String txt, int matchType,
			String replaceChar) {
		String resultTxt = txt;
		// ��ȡ���е����д�
		Set<String> set = getSensitiveWord(txt, matchType);
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}
		return resultTxt;
	}

	/**
	 * ��ȡ�滻�ַ���
	 * 
	 * @param replaceChar
	 * @param length
	 * @return
	 */
	private String getReplaceChars(String replaceChar, int length) {
		String resultReplace = replaceChar;
		for (int i = 1; i < length; i++) {
			resultReplace += replaceChar;
		}

		return resultReplace;
	}

	/**
	 * ����������Ƿ���������ַ������������£�<br>
	 * ������ڣ��򷵻����д��ַ��ĳ��ȣ������ڷ���0
	 * 
	 * @param txt
	 * @param beginIndex
	 * @param matchType
	 * @return
	 */
	public int CheckSensitiveWord(String txt, int beginIndex, int matchType) {

		// ���дʽ�����ʶλ���������д�ֻ��1λ�����
		boolean flag = false;

		// ƥ���ʶ��Ĭ��Ϊ0
		int matchFlag = 0;
		Map nowMap = sensitiveWordMap;
		for (int i = beginIndex; i < txt.length(); i++) {
			char word = txt.charAt(i);

			// ��ȡָ��key
			nowMap = (Map) nowMap.get(word);

			// ���ڣ����ж��Ƿ�Ϊ���һ��
			if (nowMap != null) {

				// �ҵ���Ӧkey��ƥ���ʶ+1
				matchFlag++;

				// ���Ϊ���һ��ƥ�����,����ѭ��������ƥ���ʶ��
				if ("1".equals(nowMap.get("isEnd"))) {

					// ������־λΪtrue
					flag = true;

					// ��С����ֱ�ӷ���,���������������
					if (SensitiveFilterService.minMatchTYpe == matchType) {
						break;
					}
				}
			}

			// �����ڣ�ֱ�ӷ���
			else {
				break;
			}
		}

		if (matchFlag < 2 && !flag) { // ���ȱ�����ڵ���1��Ϊ��
			matchFlag = 0;
		}
		return matchFlag;
	}

}
