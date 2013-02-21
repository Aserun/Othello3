package game;

/** 
 * �v���[���[���`����^�B
 *  
 */
public enum Player {
	/**�@���v���[���[ (���j*/
	Black(1),
	/** ���v���[���[ �i���j*/
	White(2);
	
	int value;
	
	private Player(int n){
		this.value=n;
	}
	
	/** 
	 * �����ŗ^����ꂽstoneColor�����̃v���[���[�Ɠ����F�Ȃ��true�A�����łȂ����false��Ԃ��B
	 * @param stoneColor
	 * @return
	 */
	public boolean isSameColor(CellState stoneColor){
		if(this.value == 1 && stoneColor==CellState.Black){
			return true;
		}else if(this.value == 2 && stoneColor==CellState.White){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * ���̃v���[���[�Ƌt�̃v���[���[��Ԃ��B
	 * @return
	 */
	public Player oppositePlayer(){
		if(this.value == 1){
			return Player.White;
		}else{
			return Player.Black;
		}
	}
}
