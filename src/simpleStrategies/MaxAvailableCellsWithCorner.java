package simpleStrategies;

import game.GameMaster;
import game.GameState;
import game.Move;
import game.OthelloMoveException;
import game.Player;
import game.Strategy;

//�P�肾����ǂ݂���V���v���Ȑ헪�B�]���֐��͐΂�u����}�X�̐��i�萔�j�Ɗp�݂̂��l���B
public class MaxAvailableCellsWithCorner extends Strategy {

    final static int PenaltyCorner = 100; // ����Ɋp���Ƃ��Ă��܂��헪�ɑ΂���y�i���e�B�[
    final static int corners[][] = { { 0, 0 }, { 0, GameMaster.SIZE - 1 },
	    { GameMaster.SIZE - 1, 0 },
	    { GameMaster.SIZE - 1, GameMaster.SIZE - 1 } }; // �p�̈ʒu

    public MaxAvailableCellsWithCorner(Player _thisPlayer, int size) { // �R���X�g���N�^
	super(_thisPlayer, size);
    }

    @Override
    // �e�N���Xgame.Strategy�̒��ۃ��\�b�h������
    public Move nextMove(GameState currentState, int remainingTime) {
	Move bestMove = new Move();

	// �p������Ȃ���
	for (int i = 0; i < 4; i++) {
	    if (currentState.isLegal(thisPlayer, corners[i][0], corners[i][1])) {
		bestMove.x = corners[i][0];
		bestMove.y = corners[i][1];
		return bestMove;
	    }
	}

	// ���Ȃ���΁A�΂�ł�����̔Ֆʂ̃X�R�A�������Ƃ��ǂ��헪��I��
	int maxScore = Integer.MIN_VALUE;
	for (int i = 0; i < SIZE; i++) {
	    for (int j = 0; j < SIZE; j++) {
		if (currentState.isLegal(thisPlayer, i, j)) { // ������(i,j)�Ɏ����̐΂�u����Ȃ��
		    try {
			GameState expectedState = currentState.nextState(
				thisPlayer, i, j); // �����ɐ΂�u�����ꍇ�̔Ֆʂ��擾
			int score = computeScore(expectedState); // ���̔Ֆʂ̕]���l���v�Z
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

    // �Ֆʂ̃X�R�A��]�����郁�\�b�h
    private int computeScore(GameState expectedState) {
	int scoreMaxAvailable = expectedState.numAvailable(thisPlayer)
		- expectedState.numAvailable(thisPlayer.oppositePlayer()); // �������΂�u����}�X�̐�
									   // -
									   // ���肪�΂�u����}�X�̐�
	int scoreCorner = 0;
	for (int i = 0; i < 4; i++) { // �������̔Ֆʂő��肪�p�ɐ΂�u����Ȃ�A���̕]���l��PenaltyCorner����������
	    if (expectedState.isLegal(thisPlayer.oppositePlayer(),
		    corners[i][0], corners[i][1]))
		scoreCorner = -PenaltyCorner;
	}

	return scoreMaxAvailable + scoreCorner;
    }
}