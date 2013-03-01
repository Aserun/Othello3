package magiSystemStrategy;

import game.GameState;
import game.Move;
import game.OthelloMoveException;
import game.Player;
import game.Strategy;

//Melchior1�́A�J���x���_�Ɋ�Â��]���l�A�őP���Ԃ��헪�Q
public class Melchior1 extends Strategy {

    public Melchior1(Player _thisPlayer, int size) { // �R���X�g���N�^
	super(_thisPlayer, size);
    }

    @Override
    // �e�N���Xgame.Strategy�̒��ۃ��\�b�h������
    public Move nextMove(GameState currentState, int remainingTime) {
	Move bestMove = new Move();

	int maxScore = Integer.MIN_VALUE;
	for (int i = 0; i < SIZE; i++) {
	    for (int j = 0; j < SIZE; j++) {
		if (currentState.isLegal(thisPlayer, i, j)) { // ������(i,j)�Ɏ����̐΂�u����Ȃ��
		    try {
			GameState expectedState = currentState.nextState(
				thisPlayer, i, j); // �����ɐ΂�u�����ꍇ�̔Ֆʂ��擾
			int score = 0; // ���̔Ֆʂ̕]���l���v�Z
			if (score > maxScore) { // �������ꂪ�őP�̕]���n�Ȃ�A���̎���L��
			    maxScore = score;
			    bestMove.x = i;
			    bestMove.y = j;
			}
		    } catch (OthelloMoveException e) { // �G���[�����B���̃G���[���������邱�Ƃ͂Ȃ��͂�
			e.printStackTrace();
		    }
		}
	    }
	}
	System.out.println("CPU's move:" + bestMove.toString()); // �f�o�b�N�p�B�R���\�[����CPU�̎��\���B
	return bestMove; // �ŗǂ̎��Ԃ�
    }

    // �J���x��]�����郁�\�b�h
    public int KaihoScore(GameState nPulus1State, GameState nState) {
	// nState�@���݂̔Ֆ�
	// nPulus1State ���̔Ֆ�

	int KaihoSum = 0; // �J���x���̂���
	// �J���x��]�����邽�߂̃��[�v
	for (int i = 0; i < SIZE; i++) {
	    for (int j = 0; j < SIZE; j++) {
		int KaihoValue = 0; // �J���x��]������p�����[�^�[
		// �Ֆʏ�̐΂̐F���ς���Ă���΁A���̐΂̎���̊J���x�����߂�.�����甒�A�����獕�̏ꍇ�͐����邪�A�󔒂���΂�u�����}�X�̓J�E���g���Ȃ��B
		if ((nPulus1State.cells[i][j].getValue() - nState.cells[i][j]
			.getValue())
			* (nPulus1State.cells[i][j].getValue() - nState.cells[i][j]
				.getValue()) == 4) {
		    for (int k = i - 1; k <= i + 1; k++) {
			for (int l = j - 1; l <= j + 1; l++) {
			    if (nState.isWithinRange(k, l) == false) {
				continue;
			    }
			    if (nState.isEmpty(k, l) == true) {
				KaihoValue--; // �Ђ�����Ԃ����R�}�̎��肪�󔒂Ȃ炻�̕�KaihoValue������������
			    }
			}
		    }
		}
		KaihoSum += KaihoValue;
	    }
	}
	return KaihoSum;
    }

    // �J���x�ŕ]�������ꍇ�̑P����N���Ԃ��B�����́A�Ֆʂ̏�ԁA�ǂ���̐F��ł��A��ʉ���܂ŕ]�����邩�iNumberOfRanks)
    public int[][] KaihoRankNs(GameState nState, Player currentPlayer,
	    int NumberOfRanks) {
	// �z��̊m�ۂƏ�����BestKaihoMoves[][0]��i[][1]��j,[][2]�͊J���x��\��
	int BestKaihoMoves[][];
	BestKaihoMoves = this.ArraysGenerater(NumberOfRanks);

	for (int i = 0; i < SIZE; i++) {
	    for (int j = 0; j < SIZE; j++) {
		if (nState.isLegal(currentPlayer, i, j)) { // ������(i,j)�Ɏ����̐΂�u����Ȃ��
		    try {
			GameState nPulus1State = nState.nextState(
				currentPlayer, i, j); // �����ɐ΂�u�����ꍇ�̔Ֆʂ��擾
			int score = KaihoScore(nPulus1State, nState); // ���̔Ֆʂ̊J���x���v�Z
			// �J���x�̕��בւ�
			for (int k = 0; k < NumberOfRanks; k++) {
			    if (score > BestKaihoMoves[k][2]) {
				for (int l = NumberOfRanks - 2; l > k - 1; l--) {
				    BestKaihoMoves[l + 1][0] = BestKaihoMoves[l][0];
				    BestKaihoMoves[l + 1][1] = BestKaihoMoves[l][1];
				    BestKaihoMoves[l + 1][2] = BestKaihoMoves[l][2];
				}
				BestKaihoMoves[k][0] = i;
				BestKaihoMoves[k][1] = j;
				BestKaihoMoves[k][2] = score;
				break;
			    }
			}

		    } catch (OthelloMoveException e) { // �G���[�����B���̃G���[���������邱�Ƃ͂Ȃ��͂�
			e.printStackTrace();
		    }
		}
	    }
	}
	return BestKaihoMoves;
    }

    // ��nState��currentState�����݂��ĂăJ�I�X���u������B�v�����B�i�������A��������Ƃ��Ԃ�s�s�����N����B�j

    // �����̎�ɂ��J���x�]���l���ő剻���A���̎��̑���̊J���x�]���l�̍ő�l���ŏ���������Ԃ��B
    public int[][] BestKaihoMoveDepth2(GameState nState) {
	int BestMove[][] = { { -1, -1, -100 } };
	int SubMove[][];// �����̎�̊J���x�]���p�����[�^
	int SubMovePulus1[][];// ����̎�̊J���x�]���p�����[�^
	GameState nPulus1State = nState;// �o�O�𐶂݂����ȏ������̎d���Ȃ̂Ŗ{���͂�肽���Ȃ��B���A���Ȃ��Ɠ{����B
	SubMove = KaihoRankNs(nState, thisPlayer,
		nState.numAvailable(thisPlayer));// ���݂̔Ֆʂ���łĂ��̓��A�J���x�I�ɗǂ��肩�珇�ɕ��ׂ���ꗗ���擾
	for (int i = 0; i < nState.numAvailable(thisPlayer); i++) {
	    try {
		nPulus1State = nState.nextState(thisPlayer.oppositePlayer(),
			SubMove[i][0], SubMove[i][1]);
	    } catch (OthelloMoveException e) {
		// TODO �����������ꂽ catch �u���b�N
		e.printStackTrace(); // ����Ȃ�ł���Ȃ��Ƃ��Ȃ��Ƃ����Ȃ��́H
	    }
	    SubMovePulus1 = KaihoRankNs(nPulus1State,
		    thisPlayer.oppositePlayer(), 1);// ���̔ՖʂŁA���肪�ł��J���x�I�ɗǂ����I�яo��
	    // �����A(�����̎�̊J���x)-(����̊J���x)�����ݒm���Ă���őP����悯��΁A������őP��Ƃ��ċL������
	    if (BestMove[0][2] < SubMove[i][2] - SubMovePulus1[0][2]) {
		BestMove[0][0] = SubMove[i][0];
		BestMove[0][1] = SubMove[i][1];
		BestMove[0][2] = SubMove[i][2] - SubMovePulus1[0][2];
	    }
	}
	// BestMove��[0][0]��i�A[0][1]��j�A[0][2]�ɂ��̎����̊J���x-����̊J���x���i�[����Ă���B
	return BestMove;
    }

    // �J���x�̕]���ȂǂɎg���z��𐶐�
    private int[][] ArraysGenerater(int Length) {
	int Arrays[][];
	Arrays = new int[Length][3];
	for (int i = 0; i < Length; i++) {
	    for (int j = 0; j <= 2; j++) {
		if (j <= 1) {
		    Arrays[i][j] = -1; // �������W��(i,j)=(-1,-1)�������A���̊J���x��-100�����Ă���B
		} else {
		    Arrays[i][j] = -100;
		}
	    }
	}
	return Arrays;
    }
}
