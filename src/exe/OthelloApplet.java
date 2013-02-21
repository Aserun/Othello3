package exe;

import game.*;
import graphix.*;


import java.applet.Applet;
import java.awt.Color;

import manualStrategies.ManualGUIStrategyFactory;
import simpleStrategies.SimpleStrategyFactory;

/**
 * SimpleStrategy�Ɛl�Ԃ��ΐ킷��v���O�����̃A�v���b�g��
 * @author Hiro
 *
 */
public class OthelloApplet extends Applet {
	
	final static int blackAllottedTime = 3*60000; //���v���[���[�̎������� [�~���b] 1�� = 60000 msec�@���̒l��ݒ肷��Ǝ��Ԗ������ɂȂ�
	final static int whiteAllottedTime = 5*60000; //���v���[���[�̎������� [�~���b] 1�� = 60000 msec�@���̒l��ݒ肷��Ǝ��Ԗ������ɂȂ�
	
	static GameMaster game;
	static StrategyFactory blackFactory, whiteFactory; //�헪�I�u�W�F�N�g�𐶐�����I�u�W�F�N�g
	static Strategy blackStrategy, whiteStrategy;

	public void init(){

		setBackground(Color.WHITE);
		
		game = new GameMaster(blackAllottedTime, whiteAllottedTime);
		
		OthelloGraphics panel = new SimpleGraphics(game);
		add(panel);
		
		blackFactory = new SimpleStrategyFactory(Player.Black, GameMaster.SIZE);

		whiteFactory = new ManualGUIStrategyFactory(Player.White, GameMaster.SIZE, panel); 

		blackStrategy = blackFactory.createStrategy();
		whiteStrategy = whiteFactory.createStrategy();
		
		Thread gameThread = new Thread() {
			public void run(){
				try{
					while(game.isInGame()){
						if(game.getCurrentPlayer() == Player.Black){
							game.startTimer(Player.Black);
							while(!game.putPiece(blackStrategy.nextMove(game.getGameState(),game.getRemainingTime(Player.Black)))); //�����΂�u���Ă͂����Ȃ��ꏊ�ɐ΂��u���ꂽ��A�����Ƃ����ꏊ�ɒu�����܂ŌJ��Ԃ��B
							game.stopTimer(Player.Black);
						}else{
							game.startTimer(Player.White);
							while(!game.putPiece(whiteStrategy.nextMove(game.getGameState(),game.getRemainingTime(Player.White)))); //�����΂�u���Ă͂����Ȃ��ꏊ�ɐ΂��u���ꂽ��A�����Ƃ����ꏊ�ɒu�����܂ŌJ��Ԃ��B
							game.stopTimer(Player.White);
						}
					}
				}catch(Exception e){
					//TODO
				}				
			}
		};
		gameThread.start();
			

	}

}
