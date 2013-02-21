package game;

public class OthelloMoveException extends Exception {
	/**
	 * Error�̎�ނ̒�`
	 */
	public enum ErrorState{
		/** �G���[�Ȃ� */
		None,
		/** �w�肳�ꂽ�ꏊ�Ɏ����̐΂�u���Ȃ� */
		NotAvailable,
		/** �w�肳�ꂽ�ꏊ�͔Ղ̊O�ł��� */
		OutOfRange,
		/** ���ɃQ�[���I�[�o�[ */
		NotInGame;
	}
	
	public ErrorState errorState;
	
	public OthelloMoveException(ErrorState state){
		errorState = state;
	}
}
