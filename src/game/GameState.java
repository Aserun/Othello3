package game;

import game.OthelloMoveException.ErrorState;

/**
 * �Ֆʂ̏�Ԃ�\���ϐ�����т���𑀍삷�郁�\�b�h���܂ރN���X�B�e�}�X���󂩁A�����A������public�ϐ��ł���cells�Ɋi�[����Ă���B�܂��A���ꂼ��̃}�X�ɔ��A���̐΂�u�����Ƃ��ł��邩�̏����ۑ����Ă���A
 * isLegal()�ł��̏������o�����Ƃ��ł���B
 * 
 * �e�����o�֐��Ŏw�肷��}�X�̃C���f�b�N�X(i,j)��0�n�܂�ł��邱�Ƃɒ��ӁB�Ղ̑傫����8x8�̂Ƃ��A����̋���[0][0]�A�E��̋���[7][0]�A�����̋���[0][7]�A�E���̋���[7][7]�ƂȂ�B
 * @author Hiro
 *
 */
public class GameState{
	final private int SIZE;
	
	/**
	 * �Ֆʂ̏�Ԃ�\���ϐ��B{@link game.CellState}���Q�ƁB
	 */
	public CellState[][] cells;
	private boolean[][] legalForBlack;
	private boolean[][] legalForWhite;
	/**
	 * ���݂͂ǂ���̎�Ԃ���\���ϐ��B{@link game.Player}���Q�ƁB
	 */
	public Player currentPlayer; 
	/**
	 * ���݂̃Q�[���̏�ԁBtrue�Ȃ�܂����������Ă��Ȃ��Bfalse�Ȃ�Q�[�����I�����Ă���B
	 */
	public boolean inGame; 
	private int nOpen; //�󂢂Ă���܂��̐�
	
	/**
	 * �R���X�g���N�^�B�Ֆʂ̃T�C�Y��size�Ŏw�肷��B
	 * @param �Ֆʂ̃T�C�Y�B�ʏ��8x8�̔ՖʂȂ��8������B
	 */
	public GameState(int size){
		SIZE = size;
		cells = new CellState[SIZE][SIZE];
		legalForBlack = new boolean[SIZE][SIZE];
		legalForWhite = new boolean[SIZE][SIZE];
				
		//Initialize the game
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++){
				cells[i][j] = CellState.Empty; 
				legalForBlack[i][j] = false;
				legalForWhite[i][j] = false;
			}
		}
		cells[SIZE/2-1][SIZE/2-1] = CellState.White;
		cells[SIZE/2][SIZE/2] = CellState.White;
		cells[SIZE/2-1][SIZE/2] = CellState.Black;
		cells[SIZE/2][SIZE/2-1] = CellState.Black;
		updateStates();
		
		//���͍�
		currentPlayer = Player.Black;
		inGame = true;
		nOpen = 60;
	}
	
	
	/**
	 * �}�X(i,j)��player�Ŏw�肵���F�̐΂��u����Ȃ��true�A�����łȂ����false��Ԃ��B
	 * @param player�@�v���[���[�̐F
	 * @param i�@�Z���̉����W
	 * @param j�@�Z���̏c���W
	 * @return
	 */
	public boolean isLegal(Player player, int i, int j){
		
		if(player == Player.Black){
			return legalForBlack[i][j];
		}else if(player == Player.White){
			return legalForWhite[i][j];
		}else{
			return false;
		}
	}
	
	/**
	 * �}�X(i,j)����̃}�X�Ȃ��true�A�����łȂ��Ȃ�false��Ԃ��B
	 * @param i
	 * @param j
	 * @return
	 */
	public boolean isEmpty(int i, int j){
		if(cells[i][j]  == CellState.Empty){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * x,y�Ŏw�肳�ꂽ�ʒu���Փ��ɂ����true���A�����łȂ����false��Ԃ��B
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isWithinRange(int x, int y){
		if(x<0 || y<0 || x>=SIZE || y>=SIZE){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * ���݂̔ՖʂŁAplayer�Ŏw�肵���F�̐΂��u����}�X�̌���Ԃ��B��ɐ헪�̕]���֐��Ŏg�p����B
	 * @param player
	 * @return
	 */
	public int numAvailable(Player player){
		int n=0;
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++){
				if(isLegal(player,i,j))
					n=n+1;
			}
		}
		return n;
	}
	
	/**
	 *  ����player�ŗ^����ꂽ�v���[���[�̐F�̐΂̐��𐔂���B
	 * @param player
	 * @return
	 */
	public int numPieces(Player player){
		int n=0;
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++){
				if(player.isSameColor(cells[i][j])){
					n=n+1;
				}
			}
		}
		return n;
	}
	
	/**
	 * ���̏�Ԃ̔ՖʂŁAplayer��(i,j)�ɐ΂�u�������Ɍ����Ֆʂ�Ԃ��B�Ԃ����Ֆʂ́A���̃I�u�W�F�N�g���f�B�[�v�E�R�s�[����
	 * �I�u�W�F�N�g�𑀍삵�����̂ł���B����āA�Ăяo�����̃I�u�W�F�N�g�ɂ͕ύX�͉������Ȃ��B�܂��A�΂������Ȃ��}�X���w�肳�ꂽ�ꍇ��
	 * ��O{@link game.OthelloMoveException}�𓊂���B
	 * @param player
	 * @param x
	 * @param y
	 * @return
	 */
	public GameState nextState(Player player, int i, int j) throws OthelloMoveException{
		GameState newState = this.deepCopy();
			
		newState.putPiece(player, i, j);
		
		return newState;
	}
	
	/**
	 * �}�X(i,j)��player�Ŏw�肳�ꂽ�F�̐΂�u���B���̃��\�b�h�͌Ăяo�����̃I�u�W�F�N�g���X�V����B�܂��A�΂������Ȃ��}�X���w�肳�ꂽ�ꍇ��
	 * ��O{@link game.OthelloMoveException}�𓊂���B
	 * @param player
	 * @param i
	 * @param j
	 * @throws OthelloMoveException
	 */
	public void putPiece(Player player, int i, int j)  throws OthelloMoveException{
		
		if(!inGame){
			throw new OthelloMoveException(ErrorState.NotInGame);
		}
		//���̏ꏊ�ɒu���邩���`�F�b�N
		if(!isWithinRange(i,j)){
			throw new OthelloMoveException(ErrorState.OutOfRange);
		}else if(!isLegal(player, i, j)){
			throw new OthelloMoveException(ErrorState.NotAvailable);
		}
		
		//�������̏ꏊ�ɒu����Ȃ�A���܂ꂽ�΂��Ђ����肩����
		isValid(currentPlayer,i, j,true);
			
		//�΂�u��
		if(currentPlayer == Player.White){
			cells[i][j] = CellState.White;
		}else if(currentPlayer == Player.Black){
			cells[i][j] = CellState.Black;
		}
		
		nOpen = nOpen-1;		
		updateStates();
		
	}
	
	/** 
	 * �Ֆʂ̏�Ԃ��X�V����B�܂��A�Q�[�����I�����Ă��Ȃ������肷��B
	 */
	private void updateStates(){
		//�Q�[���̏�Ԃ��X�V
		boolean blackAvailable = false;
		boolean whiteAvailable = false;
		boolean existsEmptyCell = false;

		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++){
				legalForBlack[i][j] = false;
				legalForWhite[i][j] = false;
				if(cells[i][j]==CellState.Empty){
					existsEmptyCell = true; //�󂢂Ă���Z��������
					legalForBlack[i][j] = false;
					legalForWhite[i][j] = false;
					if(isValid(Player.Black,i,j,false)){
						blackAvailable = true; //���̃v���[���[���u���ꏊ������
						legalForBlack[i][j] = true;
					}
					if(isValid(Player.White,i,j,false)){
						whiteAvailable = true;//���̃v���[���[���u���ꏊ������
						legalForWhite[i][j] = true;
					}
				}
			}
		}
		
		//���̎�Ԃ��ǂ��炩���`�F�b�N����B�܂��A�Q�[�����I�����Ă��Ȃ������`�F�b�N����B
		Player nextPlayer = currentPlayer;
		if(!existsEmptyCell){
			inGame = false; //�Q�[���I��
		}else if(currentPlayer == Player.Black){
			if(whiteAvailable)
				nextPlayer = Player.White;
			else if(blackAvailable)
				nextPlayer = Player.Black;
			else
				inGame = false;
		}else if(currentPlayer == Player.White){
			if(blackAvailable)
				nextPlayer = Player.Black;
			else if(whiteAvailable)
				nextPlayer = Player.White;
			else
				inGame = false;
		}
		currentPlayer = nextPlayer;
		
	}
	
	/**
	 * �}�X(i, j)��player�̐F�̐΂�u�����Ƃɂ���Ĉ�ȏ�̑���̐΂𗠕Ԃ����Ƃ��ł���Ȃ��true,�@�����łȂ����false��Ԃ��B
	 * �܂��A����reversePieces��true�Ɏw�肳�ꂽ�ꍇ�A���܂ꂽ�΂��Ђ�����Ԃ��B
	 * �ȉ��̃T�C�g���Q�l�ɍ쐬���܂����Fhttp://d.hatena.ne.jp/poor_code/20090711/1247269299
	 * @param player
	 * @param i
	 * @param j
	 * @param revercePieces�@���ꂪtrue�Ɏw�肳�ꂽ�ꍇ�A���܂ꂽ�΂��Ђ�����Ԃ��Bfalse�̏ꍇ�͂Ђ�����Ԃ��Ȃ��B�ʏ��false�Ŏg���B
	 * @return
	 */
	private boolean isValid(Player player, int i, int j, boolean revercePieces){
		int dir[][] = {
				{-1,-1}, {0,-1}, {1,-1},
				{-1, 0},         {1, 0},
				{-1, 1}, {0, 1}, {1, 1}
		};
		
		boolean reversed = false;
		
		for(int ii=0; ii<8; ii++){
			//�ׂ̃}�X���`�F�b�N
			int x0 = i+dir[ii][0];
			int y0 = j+dir[ii][1];
			if(!isWithinRange(x0,y0)){
				continue;
			}
			if(player.isSameColor(cells[x0][y0])){
//				System.out.println("�ׂ����F: " +x0 +","+ y0);
				continue;
			}else if(isEmpty(x0,y0)){
//				System.out.println("�ׂ͋�: " +x0 +","+ y0);
				continue;
			}else{
//				System.out.println("�ׂ͓G: " +x0 +","+ y0);
			}
			
			//�ׂׂ̗���[�܂ő������āA�����̐F������΃��o�[�X
			int jj = 2;
			while(true){
			
				int x1 = i + (dir[ii][0]*jj);
				int y1 = j + (dir[ii][1]*jj);
				if(!isWithinRange(x1,y1)){
					break;
				}
				
				//�����̋��������A���o�[�X
				if(player.isSameColor(cells[x1][y1])){
//					System.out.println("�Ђ����肩����: " +x1 +","+ y1);					
					if(revercePieces){
						for(int k=1; k<jj; k++){
							int x2 = i + (dir[ii][0]*k);
							int y2 = j + (dir[ii][1]*k);
							if(cells[x2][y2]==CellState.Black)
								cells[x2][y2]=CellState.White;
							else if(cells[x2][y2]==CellState.White)
								cells[x2][y2]=CellState.Black;
//							System.out.println("reversed: " +x2 +","+ y2);
						}
					}
					reversed = true;
					break;
				}
				
				//�󔒂���������A�I��
				if(isEmpty(x1,y1)){
					break;
				}
				
				jj++;
				
			}
			
		}
		
		return reversed;

	}

	/**
	 * ���݋󂢂Ă���Z���̐���Ԃ��B
	 * @return
	 */
	public int getNumberOfAvailableCells(){
		return this.nOpen;
	}
	
	/**
	 * ���̃I�u�W�F�N�g�̃f�B�[�v�E�R�s�[��Ԃ��B�u�f�B�[�v�E�R�s�[�v�Ƃ͉����ɂ��ẮA�������Ă݂Ă��������B
	 */
	public GameState deepCopy(){
		GameState copiedState = new GameState(SIZE);
		for(int i=0; i<SIZE; i++){
			for(int j=0; j<SIZE; j++){
				copiedState.cells[i][j] = this.cells[i][j];
				copiedState.legalForBlack[i][j] = this.legalForBlack[i][j];
				copiedState.legalForWhite[i][j] = this.legalForWhite[i][j];
			}
		}
		copiedState.currentPlayer = this.currentPlayer;
		copiedState.inGame = this.inGame;
		copiedState.nOpen = this.nOpen;
		return copiedState;
	}

}
