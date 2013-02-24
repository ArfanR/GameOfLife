
package GameofLife;

/**
 *
 * @author Arfan Rehab, Ellie, Ethan Schwab
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Life extends JFrame implements ActionListener {
       
	private static final long serialVersionUID = 1L; 
	private Font _defaultFont = new Font("TimesNewRoman", Font.BOLD, 14);
	private JLabel _status = new JLabel("_paused", JLabel.LEFT);
	private JButton _step = new JButton("_step");
	private JButton _run = new JButton("_run");
	private JButton _pause = new JButton("_pause");
	private JButton _clear = new JButton("_clear");
	private JButton inc_speed = new JButton("Increase Speed");
	private JButton dec_speed = new JButton("Decrease Speed");
	
	private JPanel game_panel = new JPanel();
	private JPanel button_panel = new JPanel();
	private JPanel color_panel = new JPanel();
	private JPanel controls_panel = new JPanel(); 
	private JComboBox _alive = new JComboBox();
	private JComboBox _dead = new JComboBox();
        private JLabel label_alive = new JLabel("_alive Color: ",JLabel.LEFT);
	private JLabel label_dead = new JLabel("_dead Color: ",JLabel.LEFT);

	private static final int ROWS = 40;
	private static final int COLS = 40;
	
        private Grid _board = new Grid(COLS,ROWS);
        private Patch[][] _cells = new Patch[ROWS][COLS];
	private boolean[][] test_grid = new boolean[ROWS][COLS];
        
	private int _timer_rate = 200;
	javax.swing.Timer timer = new javax.swing.Timer(_timer_rate, this);
        
	private MyColor[] _colors = { new MyColor(Color.BLUE,"Blue"), 
			new MyColor(Color.BLACK,"Black"), new MyColor(Color.GREEN,"Green"),
			new MyColor(Color.RED,"Red"), new MyColor(Color.GRAY,"Gray"),
			new MyColor(Color.YELLOW,"Yellow")}; 
	
	public Life() {   
            
            _status.setFont(_defaultFont);
            _step.setFont(_defaultFont);
            _run.setFont(_defaultFont);		
            _pause.setFont(_defaultFont);
            _clear.setFont(_defaultFont);
            inc_speed.setFont(_defaultFont);
            dec_speed.setFont(_defaultFont);
            
            _step.addActionListener(this);
            _run.addActionListener(this);
            _pause.addActionListener(this);
            _clear.addActionListener(this);
            inc_speed.addActionListener(this);
            dec_speed.addActionListener(this);
            _alive.addActionListener(this);
            _dead.addActionListener(this);
		
            game_panel.setLayout(new GridLayout(ROWS,COLS,0,0));
		
            for(int i = 0; i < ROWS; i++) {
                for(int j = 0;j < COLS; j++) {
                    game_panel.add(_cells[i][j] = new Patch(_board,j,i));
                }
            }
		
            button_panel.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));	
            button_panel.add(_step);
            button_panel.add(_run);
            button_panel.add(_pause);
            button_panel.add(_clear);
            button_panel.add(inc_speed);
            button_panel.add(dec_speed);
		
            color_panel.setLayout(new FlowLayout(FlowLayout.CENTER,20,20));
            for(int i = 0;i < _colors.length; i++) {
		_alive.addItem(_colors[i]);
		_dead.addItem(_colors[i]);
            }
            color_panel.add(label_alive);
            color_panel.add(_alive);
            color_panel.add(label_dead);
            color_panel.add(_dead);
         
            _alive.setSelectedItem(_colors[0]);
            _dead.setSelectedItem(_colors[2]);	
		
            controls_panel.setLayout(new BorderLayout());	
            controls_panel.add(button_panel,BorderLayout.NORTH);
            controls_panel.add(color_panel, BorderLayout.SOUTH);	
            setLayout(new BorderLayout());
            add(_status, BorderLayout.NORTH);
            add(game_panel, BorderLayout.CENTER);
            add(controls_panel, BorderLayout.SOUTH);
		
            _pause.setEnabled(false);
		
	}
	
	public void actionPerformed(ActionEvent e) {
            if(e.getSource() == _step) {
		step();
            }
            else if(e.getSource() == _run) {
		_status.setText("_running...");
		_step.setEnabled(false);
		_run.setEnabled(false);
                _pause.setEnabled(true);
		timer.start();
            }
	    else if(e.getSource() == _pause) {
		pause();
	    }
	    else if(e.getSource() == _clear) {
		pause();
		clear();
            }
            else if(e.getSource() == inc_speed) {
                increaseSpeed();
            }
            else if(e.getSource() == dec_speed) {
                decreaseSpeed();
            }
            else if(e.getSource() == _alive) {
		MyColor color = (MyColor)_alive.getSelectedItem();
		changeColor('a',color.getColor());		
            }
            else if(e.getSource() == _dead) {
		MyColor color = (MyColor)_dead.getSelectedItem();
                changeColor('d',color.getColor());
            }
            else if(e.getSource() == timer) {
		step();
            }
	}
	
	private void increaseSpeed() {
            if(timer.isRunning())
		if(_timer_rate - 10 > 0) {
                    timer.stop();
                    _timer_rate = _timer_rate - 10;
                    timer = new javax.swing.Timer(_timer_rate,this);
                    timer.start();
                }
	}
        
	private void decreaseSpeed() {
            if(timer.isRunning())
                if(_timer_rate + 10 < 400) {
                    timer.stop();
                    _timer_rate = _timer_rate + 10;
                    timer = new javax.swing.Timer(_timer_rate,this);
                    timer.start();
		}
	}
	
	private void changeColor(char type, Color color) {
            if(type == 'a')
		Patch.setLiveColor(color);
            if(type == 'd')
		Patch.setDeadColor(color);
            redraw();
	}
	
	private void redraw() {
            for(int i = 0;i < ROWS; i++) {
                for(int j = 0;j < COLS; j++) {
                    if(_cells[i][j].isAlive())
                        _cells[i][j].revive();
                    else
                        _cells[i][j].kill();
                }
            }
	}
	
	private void pause() {
            _status.setText("_paused");
            _step.setEnabled(true);
            _run.setEnabled(true);
            _pause.setEnabled(false);
            timer.stop();
            _timer_rate = 200;
	}
	
	private void step() {
            update_board();
            update__cells();
	}

	private void clear() {
            for(int i = 0;i < ROWS; i++) {
                for(int j = 0;j < COLS; j++) {
                    if(_cells[i][j].isAlive())
                       _cells[i][j].toggleHealth();
                }
            }
	}

	private void update_board() {
            for(int i = 0; i < ROWS; i++) {
                for(int j = 0;j < COLS; j++) {				
                    int numNeighbors = howManyNeighbors(i,j);		
                    if (_cells[i][j].isAlive()) {
                        if(numNeighbors == 2 || numNeighbors == 3) {
                            test_grid[i][j] = true;
                        }
                        else {
                            test_grid[i][j] = false;
                        }
                    }
                    else {
                        if(numNeighbors == 3) {
                            test_grid[i][j] = true;
                        }
                        else {
                            test_grid[i][j] = false;
                        }
                    }
                }
            }
        }
	
	private int howManyNeighbors(int row, int col) {
            int numNeighbors = 0;
            int tempRow;
            int tempCol;
            for(int i = row - 1;i <= row + 1; i++) {
                for(int j = col - 1;j <= col + 1;j++) {		
                    tempRow = getIndex(i, 'r');
                    tempCol = getIndex(j, 'c');
                    if(i == row && j == col)
                        continue;
                    if(_cells[tempRow][tempCol].isAlive()) 
                        numNeighbors++;
                }		
            }
		return numNeighbors;
	}

	private int getIndex(int i, char type) {	
            if(type != 'c' && type != 'r')
                return -2;
            if(type == 'r') {
                if(i >= ROWS)
                    return 0;
                else if(i < 0)
                    return ROWS - 1;
            }
            if(type == 'c') {
                if(i >= COLS)
                    return 0;
		else if(i < 0)
                    return COLS - 1;
            }
            return i;
	}
	
	private void update__cells() {
            for(int i = 0;i < ROWS; i++) {
		for(int j = 0;j < COLS; j++) {
                    if(test_grid[i][j])
                        _cells[i][j].revive();
                    else
                        _cells[i][j].kill();
		}
            }
	}
	
	public static void main(String[] args) {
            Life life = new Life();
            life.setTitle("John Conway's Game of Life By: Ethan, Ellie and Arfan");
            life.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            life.setSize(750,750);
            life.setVisible(true);
	}
}
class MyColor {
	
	private Color color;
	private String name;
	
	MyColor(Color color, String name) {
		this.color = color;
		this.name = name;
	}
	
	public Color getColor() {
		return color;
	}
	public String toString() {
		return name;
	}
}
