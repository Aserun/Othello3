package game;

import java.util.Observable;

/**
 * �Q�[���̐i�s���s���N���X�B�ʏ�A�Q���҂����̃N���X�̃��\�b�h��p���邱�Ƃ͂Ȃ��B
 * @author Hiro
 *
 */
public class GameMaster extends Observable {
	

	public final static int SIZE = 8; //�Ֆʂ̃^�e�E���R�̃T�C�Y
	private GameState state;
	final static int ThreadSleepTime = 10;

	
	private int blackRemainingTime, whiteRemainingTime; //���A���̎c�莞�� [�~���b]
	Thread blackTimer, whiteTimer; //�^�C�}�[�p�̃X���b�h
	private boolean flagBlackTimerRunning, flagWhiteTimerRunning; //���̃t���O��true�̎��Ɏ������Ԃ�����
	private boolean flagBlackTimeUp, flagWhiteTimeUp;

	
	
	/**
	 * �R���X�g���N�^�B
	 * @param blackAllottedTime ���v���[���[�̎������� [�~���b]
	 * @param whiteAllotedTime�@���v���[���[�̎������� [�~���b]
	 */
	public GameMaster(int blackAllottedTime, int whiteAllottedTime){
		//Initialize the game
		state = new GameState(SIZE);
		
		//�^�C�}�[�̃Z�b�g�A�b�v
		blackRemainingTime = blackAllottedTime;
		whiteRemainingTime = whiteAllottedTime;
		flagBlackTimeUp = false;	flagWhiteTimeUp = false;
		flagBlackTimerRunning = false; flagWhiteTimerRunning = false;

		blackTimer = new Thread() {
			public void run(){
				try{
					while(blackRemainingTime>0){
						Thread.sleep(ThreadSleepTime); //10�~���b�X���[�v
						if(flagBlackTimerRunning){
							blackRemainingTime = blackRemainingTime - ThreadSleepTime;
							if(blackRemainingTime%1000 < ThreadSleepTime){ 
								setChanged(); notifyObservers();//1�b���ƂɃO���t�B�b�N�X���ĕ`�悷��V�O�i���𑗂�
							}
						}
					}
					state.inGame = false; 
					flagBlackTimeUp = true;
					setChanged(); notifyObservers();//�O���t�B�b�N�X���ĕ`�悷��V�O�i���𑗂�
				}catch(InterruptedException ex){
					System.err.println(ex.getStackTrace());
				}
			}
		};
		
		whiteTimer = new Thread() {
			public void run(){
				try{
					while(whiteRemainingTime>0){
						Thread.sleep(ThreadSleepTime); //10�~���b�X���[�v
						if(flagWhiteTimerRunning){
							whiteRemainingTime = whiteRemainingTime - ThreadSleepTime;
							if(whiteRemainingTime%1000 < ThreadSleepTime){ 
								setChanged(); notifyObservers();//1�b���ƂɃO���t�B�b�N�X���ĕ`�悷��V�O�i���𑗂�
							}
						}
					}
					state.inGame = false; 
					flagWhiteTimeUp = true;
					setChanged(); notifyObservers();//�O���t�B�b�N�X���ĕ`�悷��V�O�i���𑗂�
				}catch(InterruptedException ex){
					System.err.println(ex.getStackTrace());
				}
			}
		};

		//�^�C�}�[���n���B�������A�����������Ԃ����ɐݒ肳��Ă�����A�^�C�}�[�𓮂����Ȃ��B
		if(blackAllottedTime >= 0){
			blackTimer.setPriority(Thread.MAX_PRIORITY);
			blackTimer.start();
		}
		if(whiteAllottedTime >= 0){
			whiteTimer.setPriority(Thread.MAX_PRIORITY);
			whiteTimer.start();
		}

	}
	
	/**
	 * player�̎c�莞�Ԃ��~���b�P�ʂŕԂ�
	 * @param player
	 * @return
	 */
	public int getRemainingTime(Player player){
		if(player == Player.Black){
			return blackRemainingTime;
		}else{
			return whiteRemainingTime;
		}
	}
	
	
	public void stopTimer(Player player){
		if(player == Player.Black){
			flagBlackTimerRunning = false;
		}else{
			flagWhiteTimerRunning = false;			
		}
	}
	
	public void startTimer(Player player) throws Exception{
		if(player == Player.Black && !flagWhiteTimerRunning){
			flagBlackTimerRunning = true;
		}else if(player == Player.White && !flagBlackTimerRunning){
			flagWhiteTimerRunning = true;			
		}else{
			throw new Exception("�����̃^�C�}�[���Ƃ܂��Ƃ��");
		}
	}

	
	public int getSize(){
		return SIZE;
	}

	
	/**
	 * ���݂ǂ���̎�Ԃ���Ԃ�
	 * @return
	 */
	public Player getCurrentPlayer(){
		return state.currentPlayer;
	}
	
	/**
	 * ����player�ŗ^����ꂽ�v���[���[�̐F�̐΂̐��𐔂���B
	 * @param player
	 * @return
	 */
	public int getNumPieces(Player player){
		return state.numPieces(player);
	}
	
	/**
	 * �����Q�[�����s���Ȃ�true,�����łȂ����false.
	 * @return
	 */
	public boolean isInGame(){
		return state.inGame;
	}
	
	
//	/**
//	 * (x, y)�Ɍ��݂̃v���[���[�̐΂�u�����Ƃŗ��Ԃ���΂����ׂė��Ԃ��B
//	 * @param player
//	 * @param x
//	 * @param y
//	 */
//	void reverseStones(Player player, int x, int y){
//		state.isValid(player, x, y, true);
//	}
	
	/**
	 * �΂�u��
	 */
	public boolean putPiece(Move move){
		return putPiece(move.x, move.y);
	}
	
	/**
	 * �΂�u��
	 */
	public boolean putPiece(int i, int j){
		
		//i,j�Ɍ��݂̃v���[���[�̐΂�u���B
		try {
			state.putPiece(state.currentPlayer, i, j);
		} catch (OthelloMoveException e) {
			e.printStackTrace();
			return false;
		}
		
		//�O���t�B�b�N�X���ĕ`�悷��V�O�i���𑗂�
		setChanged();
		notifyObservers();
		
		return true;
	}
	
	/**
	 * Returns the array of the state of all cells
	 * @return
	 */
	public GameState getGameState(){
		return state.deepCopy();
	}
	
	public CellState getState(int x, int y){
		return state.cells[x][y];
	}

	/**
	 * �Q�[�����I�����Ă���ꍇ�A���҂�Ԃ��B�܂��Q�[�����I�����Ă��Ȃ��A�܂��͈��������Ȃ��null��Ԃ��B
	 * @return
	 */
	public Player getWinner(){
		if(state.inGame){
			return null;
		}else{
			if(flagBlackTimeUp){//�����Ԑ؂�
				return Player.White;
			}else if(flagWhiteTimeUp){//�����Ԑ؂�
				return Player.Black;
			}else if(getNumPieces(Player.Black) > getNumPieces(Player.White)){
				return Player.Black;
			}else if(getNumPieces(Player.Black) < getNumPieces(Player.White)){
				return Player.White;
			}else{
				return null;  //��������
			}
		}
	}
}
