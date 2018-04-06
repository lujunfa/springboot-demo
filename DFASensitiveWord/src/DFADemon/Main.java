package DFADemon;

public class Main {
	public static void main(String[] args) {

		SensitiveFilterService filter = SensitiveFilterService.getInstance();
		String txt = "但是他日本男人‘会发生的日本鬼子恐龙过年’日本人驴肝肺";
		String hou = filter.replaceSensitiveWord(txt, 1, "*");
		System.out.println("替换前的文字为：" + txt);
		System.out.println("替换后的文字为：" + hou);
	}
}