import javax.swing.*;
import java.awt.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TilePanel extends JPanel {
	private BufferedImage image;
	private int row;
	private int column;
	private String imageName;
	private int tileValue;
	private GameFrame frame;
	
	public TilePanel(String imageName) {
		this.imageName = imageName;

		try {
			image = ImageIO.read(new File("assets/" + imageName +".png")); 
		} catch (Exception e) {
			System.out.println("Could not find image file!");
			System.exit(0);
		}
		
		setPreferredSize(new Dimension(GameFrame.SCALE, GameFrame.SCALE) );
		addMouseListener(new clickListener() );
	}
	
	public TilePanel(int tileValue) {
		this.tileValue = tileValue;
		this.imageName = Level.dictionary.get(tileValue);

		try {
			image = ImageIO.read(new File("assets/" + imageName +".png")); 
		} catch (Exception e) {
			System.out.println("Could not find image file!");
			System.exit(0);
		}
		
		setPreferredSize(new Dimension(GameFrame.SCALE, GameFrame.SCALE) );
		addMouseListener(new clickListener() );
	}
	
	public TilePanel(int tileValue, int row, int column, GameFrame frame) {
		this.tileValue = tileValue;
		this.imageName = Level.dictionary.get(tileValue);
		this.row = row;
		this.column = column;
		this.frame = frame;
		
		try {
			image = ImageIO.read(new File("assets/" + this.imageName +".png")); 
		} catch (Exception e) {
			System.out.println("Could not find image file!");
			System.exit(0);
		}
		
		setPreferredSize(new Dimension(GameFrame.SCALE, GameFrame.SCALE) );
		addMouseListener(new clickListener() );
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Don't understand this, but it is useful for pixel art ...
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(image, 0, 0, GameFrame.SCALE, GameFrame.SCALE, null);
    }
	
	public boolean isCLickable() {
		if ( tileValue % 2 == 0 ) { return true; }
		return false;
	}
	
	public int getRow() { return row; }
	public int getColumn() { return column; }
	public int getTileValue() { return tileValue; }
	public String getImageName() { return imageName; }
	
	class clickListener implements MouseListener {
		public void mouseReleased(MouseEvent e) {
			if (isCLickable()) {
				frame.handleTileCLick(TilePanel.this);
			}
		}
		public void mousePressed(MouseEvent e) {}
		public void mouseClicked(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e)  {}
	} 
	
}