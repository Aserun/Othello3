package game;

/**
 * �e�Z���̏�Ԃ�\���^�B�}�X����̏��(Empty)��0�A�����u����Ă�����(White)��-1�A
 * �����u����Ă�����(Black)��1�̒l�����蓖�Ă��Ă���A���̒l��getValue()�Ŏ��o��
 * ���Ƃ��o����B
 * @author Hiro
 *
 */
public enum CellState {
	/** �����΂��u����Ă��炸�A���������u�����Ƃ��ł��Ȃ� */
	Empty(0),
	/** �����u����Ă��� */
	White(-1),
	/** �����u����Ă��� */
	Black(1);
	/** �����΂��u����Ă��炸�A���΂�u�����Ƃ��ł��邪���΂͒u���Ȃ�*/
	
	private int value;
	
	private CellState(int n){
		this.value=n;
	}
	
	/**
	 * ���̏�ԂɊ��蓖�Ă��Ă���l�����o���B�}�X����̏��(Empty)��0�A�����u����Ă�����(White)��-1�A
	 * �����u����Ă�����(Black)��1�̒l�����蓖�Ă��Ă���B
	 * @return
	 */
	public int getValue(){
		return this.value;
	}
	
}
