
package GameofLife;

/**
 *
 * @author Arfan
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Patch extends JPanel implements MouseListener {
	
    private static final long serialVersionUID = 1L;
    private static Color _deadColor = Color.GREEN;
    private static Color _liveColor = Color.BLUE;
    private Grid _grid;
    private int _pycor, _pxcor;
    private boolean _state;
	
    public Patch(Grid grid, int x, int y) {
        setBorder(new LineBorder(Color.BLACK, 1));
        setBackground(_deadColor);
        addMouseListener(this);
        _state = false;
        _pxcor = x;
        _pycor = y;
        _grid = grid;
        
    }
    
    public int pycor(){
	return _pycor;
    }

    public int pxcor(){
	return _pxcor;
    }
    
    public Grid getGrid() {
        return _grid;
    }
	
    public void toggleHealth() {
        if(isAlive()) {
            kill();
        }
        else {
            revive();
        }
    }	

    public void kill() {
        setBackground(_deadColor);
        _state = false;
    }
	
    public void revive() {
	setBackground(_liveColor);
	_state = true;
    }
    
    public boolean isAlive() {
	return _state;
    }
    
    public static Color getLiveColor() {
	return _liveColor;
    }
    
    public static void setLiveColor(Color color) {
	_liveColor = color;
    }
    
    public static Color getDeadColor() {
	return _deadColor;
    }
	
    public static void setDeadColor(Color color) {
        _deadColor = color;
    }
		
    public void mouseClicked(MouseEvent e) {
	toggleHealth();
    }
        
    public void mousePressed(MouseEvent e) {}
        
    public void mouseReleased(MouseEvent e) {} 
        
    public void mouseEntered(MouseEvent e) {}
    
    public void mouseExited(MouseEvent e) {}
        
}
