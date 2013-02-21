package manualStrategies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import game.*;

public class ManualStrategyCLI extends Strategy {

	String playerName;
	BufferedReader br;
	
	Move next = new Move();
	
	final int int_a = 'a', int_A= 'A';//'a'��'A'�̕����R�[�h����
	
	public ManualStrategyCLI(Player _thisPlayer, int size) {
		super(_thisPlayer, size);

		//�R���\�[������̓��̓X�g���[���̏���
		br = new BufferedReader(new InputStreamReader(System.in));
		
		if(thisPlayer == Player.Black)
			playerName = "��";
		else
			playerName = "��";

	}

	@Override
	public Move nextMove(GameState currentState, int remainingTime) {
//		if(errorState != ErrorState.None){
//			System.err.println("���̏ꏊ�ɐ΂�u�����Ƃ͂ł��܂���B");
//		}

		System.out.print(playerName+"�̎����͂��Ă�������:");
		
		
		try {
			while(!parse(br.readLine()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return next;
	}
	
	private boolean parse(String str){
		if(str.length()!=2){
			System.err.print("���͂��镶���́A'a2'�̂悤�ɁA�񕶎��łȂ��Ă͂����܂���B"+playerName+"�̎����͂��Ă�������:");
			return false;
		}
		char colStr = str.charAt(0);
		char rowStr = str.charAt(1);
		
		int colInt = colStr;
		if(Character.isUpperCase(colStr)){
			next.x = colInt - int_A;
		}else if(Character.isLowerCase(colStr)){
			next.x = colInt - int_a;			
		}else{
			System.err.print("�ŏ��̕����̓A���t�@�x�b�g�łȂ��Ă͂����܂���B"+playerName+"�̎����͂��Ă�������:");
			return false;
		}
		
		if(Character.isDigit(rowStr)){
			next.y = Integer.valueOf(String.valueOf(rowStr))-1;
		}else{
			System.err.print("�񕶎��ڂ̓A���t�@�x�b�g�łȂ��Ă͂����܂���B"+playerName+"�̎����͂��Ă�������:");
			return false;			
		}
		
		System.out.println("("+next.x+","+next.y+")");
		
		return true;
	}
	

}
