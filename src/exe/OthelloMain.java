package exe;

import game.*;
import graphix.*;

import javax.swing.*;

import simpleStrategies.*;
import manualStrategies.*;

/**
 * �I�Z���Q�[���̃��C���E�N���X�B
 * @author Hiro
 *
 */
public class OthelloMain extends JFrame {
	
	final static int blackAllottedTime = -1; //���v���[���[�̎������� [�~���b] 1�� = 60000 msec�@���̒l��ݒ肷��Ǝ��Ԗ������ɂȂ�
	final static int whiteAllottedTime = -1; //���v���[���[�̎������� [�~���b] 1�� = 60000 msec�@���̒l��ݒ肷��Ǝ��Ԗ������ɂȂ�
	
	static GameMaster game;
	static StrategyFactory blackFactory, whiteFactory; //�헪�I�u�W�F�N�g�𐶐�����I�u�W�F�N�g
	static Strategy blackStrategy, whiteStrategy;

	public OthelloMain(){
		setTitle("Othello");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//�V�����Q�[���𐶐�
		game = new GameMaster(blackAllottedTime, whiteAllottedTime);

		//�O���t�B�b�N�X������
		OthelloGraphics panel = new SimpleGraphics(game);
		getContentPane().add(panel);
		pack();
		
		//���̐헪��I���B�p�������헪�̃R�����g�A�E�g���O���B
//		blackFactory = new SimpleStrategyFactory(Player.Black, GameMaster.SIZE); //�Ȉ�CPU
//		blackFactory = new KaihoStrategyFactory(Player.Black, GameMaster.SIZE); //�J���x���_
		blackFactory = new ManualGUIStrategyFactory(Player.Black, GameMaster.SIZE, panel); //�l�� 
//		blackFactory = new ManualStrategyFactory(Player.Black, GameMaster.SIZE); //�l�� 

		//���̐헪��I���B�p�������헪�̃R�����g�A�E�g���O���B
//		whiteFactory = new SimpleStrategyFactory(Player.White, GameMaster.SIZE); //�Ȉ�CPU
		whiteFactory = new KaihoStrategyFactory(Player.White, GameMaster.SIZE); //�J���x���_
//		whiteFactory = new ManualGUIStrategyFactory(Player.White, GameMaster.SIZE, panel);  //�l��

		blackStrategy = blackFactory.createStrategy();
		whiteStrategy = whiteFactory.createStrategy();
			
	}
	
	public static void main(String[] args){
	
		OthelloMain app = new OthelloMain();
		app.setVisible(true);

		try{
			while(game.isInGame()){ //�Q�[���I���܂ŌJ��Ԃ�
				if(game.getCurrentPlayer() == Player.Black){ //���̎��
					game.startTimer(Player.Black); //�^�C�}�[���X�^�[�g
					while(!game.putPiece(blackStrategy.nextMove(game.getGameState(),game.getRemainingTime(Player.Black)))); //���̐헪���Ăяo���A���̎��łB�����΂�u���Ă͂����Ȃ��ꏊ�ɐ΂��u���ꂽ��A�����Ƃ����ꏊ�ɒu�����܂ŌJ��Ԃ��B
					game.stopTimer(Player.Black); //�΂��u���ꂽ��A�^�C�}�[���~�߂�B
				}else{ //���̎��
					game.startTimer(Player.White);
					while(!game.putPiece(whiteStrategy.nextMove(game.getGameState(),game.getRemainingTime(Player.White)))); //���̐헪���Ăяo���A���̎��łB�����΂�u���Ă͂����Ȃ��ꏊ�ɐ΂��u���ꂽ��A�����Ƃ����ꏊ�ɒu�����܂ŌJ��Ԃ��B
					game.stopTimer(Player.White);
				}
			}
		}catch(Exception e){
			//TODO
		}
		
		//�e�헪�N���X�̏I���������\�b�h���Ăяo���B
		blackStrategy.terminate();
		whiteStrategy.terminate();

	}
	
}