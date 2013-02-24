
package GameofLife;

/**
 *
 * @author Arfan
 */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class Grid {
    
    private Patch[][] _grid;

    public Grid(){
        _grid = new Patch[10][10];
        for(int i = 0 ; i < _grid.length; i++){
            for(int j = 0; j < _grid.length; j++){
                _grid[i][j] = new Patch(this, j,i);
            }
        }
    }

    public Grid(int x, int y) {
	_grid = new Patch[y][x];
	for(int i = 0 ; i < x; i++){
            for(int j = 0; j < y; j++){
		_grid[i][j] = new Patch(this, j,i);
            }
        }
    }
  
    public Patch getPatch(int x, int y) {
        return _grid[y][x];
    }

    public void setPatch(int x, int y, Patch p) {
        _grid[y][x] = p;
    }

    public String toString() {
        String ans = "";
        for (int i = 0; i < _grid.length; i++) {
            for (int j = 0; j < _grid[i].length; j++) {
                ans += _grid[i][j];
            }
            ans += "\n";
        }
        return ans;
    }       
}
