package game;

/**
 * �e�Q���҂���������{@link game.Strategy}�̎q�N���X�̃I�u�W�F�N�g�𐶐�����factory�̒��ۃN���X�B 
 * �e�Q���҂͂��̃N���X���������˂΂Ȃ�Ȃ��BcreateStrategy()���ĂԂƁA�����̍�����헪�̃I�u�W�F�N�g���Ԃ����悤��
 * ���邱�ƁB
 * �e�Q���҂��쐬����q�N���X�͕K���A�{�N���X�̃R���X�g���N�^�Ɠ��l�ɁA(Player thisPlayer, int size)�̓�݂̂�������
 * ����public�ȃR���X�g���N�^��݂��邱�ƁB
 * �ǂ����Đ헪�N���X�݂̂Ȃ炸factory����邩�ɂ��ẮA<a href="http://www.nulab.co.jp/designPatterns/designPatterns2/designPatterns2-2.html" target="new"><b>������ւ�</b></a>���Q�ƁB
 * @author Hiro
 *
 */
public abstract class StrategyFactory {
	protected Player thisPlayer;
	protected int SIZE;
	
	/**
	 * �R���X�g���N�^�B�q�N���X�̃R���X�g���N�^�̈����͖{�R���X�g���N�^�Ɠ��l��(Player thisPlayer, int size)�̓�݂̂Ƃ��A�K�v�Ȑݒ�i�T���̐[���Ȃǁj�͎q�N���X�̃R���X�g���N�^�̒��ōs�����ƁB
	 * @param _thisPlayer
	 * @param size
	 */
	public StrategyFactory(Player _thisPlayer, int size){
		thisPlayer = _thisPlayer;
		SIZE = size;
	}
	
	/**
	 * �e�Q���҂͂��̃��\�b�h�����Ȃ炸�������邱�ƁB���̃��\�b�h�͎����������{@link Strategy}�̎q�N���X�̃I�u�W�F�N�g��Ԃ��B
	 * @return
	 */
	abstract public Strategy createStrategy();
}
