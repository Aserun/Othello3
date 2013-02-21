package game;

/**
 * �I�Z���̐헪��\�����ۃN���X�B�e�Q���҂͂��̃N���X���g�����A���ۃ��\�b�hnextMove()�����������N���X���쐬���邱�ƂŁA
 * ������������B
 * 
 * @author Hiro
 *
 */
public abstract class Strategy {
	/**
	 * ���̃v���[���[�����������B
	 */
	protected final Player thisPlayer; 
	
	/**
	 * �Q�[���̃T�C�Y�B�܂�A�Ղ̈�Ђ̃}�X���B
	 */
	protected final int SIZE;

	/**
	 * �R���X�g���N�^�B�q�N���X���super()�ŌĂяo�����B�Ֆʂ̑傫���Ƃǂ���̃v���[���[�����w�肷��B
	 * @param _thisPlayer
	 * @param size
	 */
	public Strategy(Player _thisPlayer, int size){
		thisPlayer = _thisPlayer;
		SIZE = size;
	}
	
	/**
	 * �e�Q���҂͂��̃N���X��K���������邱�ƁB���ꂪ�헪�̍����ƂȂ�֐��B���݂̔Ֆ� currentState�����
	 * �c�莞�� remainingTime���󂯎��A���̈���Ԃ��B
	 * 
	 * @param currentState ���݂̔Ֆʂ̏��
	 * @param remainingTime �c�莞�� [�~���b]
	 * @return�@���̈��
	 */
	abstract public Move nextMove(GameState currentState, int remainingTime);
	
	
	/**
	 * �Q�[���I�����ɌĂ΂��B�I���������K�v�ȏꍇ�A���̃N���X���I�[�o�[���C�h����B���Ƃ��΁A���O�t�@�C�����N���[�Y����Ƃ��Ƃ��B
	 * �I���������K�v�łȂ��ꍇ�͂��̃N���X�͎������Ȃ��Ă悢�B
	 */
	public void terminate(){
		
	}
	

	
}
