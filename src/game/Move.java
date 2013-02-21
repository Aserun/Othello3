package game;

/**
 * ��A���Ȃ킿�ǂ��̃Z���ɐ΂�u������\���N���X�B��������_�Ƃ��A�������E������x�A�c������������y�B�C���f�b�N�X�̓[���͂��܂�B�Ղ̑傫����8x8�̂Ƃ��A����̋���(x,y)=(0,0)�A�E��̋���(7,0)�A�����̋���(0,7)�A�E���̋���(7,7)�ƂȂ�B
 * @author Hiro
 *
 */
public class Move {
	public int x, y;
	static final String[] alphabet = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};

	
	public Move(){}
	
	public Move(int _x, int _y){
		x = _x;
		y = _y;
	}
	
	public String toString(){
		return "(" + alphabet[x] + "," + (y+1) + ")";
	}
}
