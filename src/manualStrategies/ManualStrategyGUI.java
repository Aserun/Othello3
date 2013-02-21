package manualStrategies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


import game.*;
import graphix.OthelloGraphics;

public class ManualStrategyGUI extends StrategyGUI{

	String playerName;
	BufferedReader br;
	
//	volatile boolean flagWaitingForMouseClick;
	
	Move next = new Move();
	
	final int int_a = 'a', int_A= 'A';//'a'��'A'�̕����R�[�h����
	
	public ManualStrategyGUI(Player _thisPlayer, int size, OthelloGraphics gui) {
		super(_thisPlayer, size);
		
		gui.registerStrategy(this);

		//�R���\�[������̓��̓X�g���[���̏���
		br = new BufferedReader(new InputStreamReader(System.in));
		
//		flagWaitingForMouseClick = false;
		
		if(thisPlayer == Player.Black)
			playerName = "��";
		else
			playerName = "��";

	}


	@Override
	public synchronized Move nextMove(GameState currentState, int remainingTime) {
//		if(errorState != ErrorState.None){
//			System.err.println("���̏ꏊ�ɐ΂�u�����Ƃ͂ł��܂���B");
//		}
		
		System.out.print(playerName+"�̐΂�u���ꏊ���N���b�N���đI��ł��������B");
		
		try {
			wait();
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		
		return next;
	}
	

	@Override
	public synchronized void pickCell(int i, int j) {
		next.x = i;
		next.y = j;
		notifyAll();
	}
	

}
