package DFADemon;

public class Main {
	public static void main(String[] args) {

		SensitiveFilterService filter = SensitiveFilterService.getInstance();
		String txt = "�������ձ����ˡ��ᷢ�����ձ����ӿ������ꡯ�ձ���¿�η�";
		String hou = filter.replaceSensitiveWord(txt, 1, "*");
		System.out.println("�滻ǰ������Ϊ��" + txt);
		System.out.println("�滻�������Ϊ��" + hou);
	}
}