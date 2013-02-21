package graphix;

import game.GameMaster;

import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import manualStrategies.StrategyGUI;

/**
 * �I�Z���̃O���t�B�b�N�X�̒��ۃN���X�B
 * ������̒��ӁFenableMouseEvent���Ă΂�Astrategy��null�ł͂Ȃ��Ƃ��́Athis.mousePressed()�Ăяo�����ɁA
 * strategy.pickCell()���Ăяo���āA�����ꂽ�Z����`�B���邱�ƁB
 * @author Hiro
 *
 */
public abstract class OthelloGraphics extends JPanel implements Observer, MouseListener{
	
	protected ArrayList<StrategyGUI> strategy;
	protected boolean flagMouseEnabled = false;
	
	public OthelloGraphics(GameMaster gm){
		strategy = new ArrayList<StrategyGUI>();
		gm.addObserver(this);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		repaint();
	}
	
	public void registerStrategy(StrategyGUI s){
		strategy.add(s);
	}


}
