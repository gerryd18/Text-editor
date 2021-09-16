package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class TextEditor extends JFrame implements ActionListener{

JTextArea textArea;
JScrollPane scrollPane;
JSpinner fontSizeSpinner;
JLabel fontLabel;
JButton colorButton;
JComboBox fontBox;

//Menu bar
JMenuBar menuBar;
JMenu fileMenu;
JMenuItem openMenuItem, saveMenuItem, exitMenuItem;

	public TextEditor() {
		setFrame();
		
//		text area
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new Font("Arial", Font.PLAIN, 20));
		
		scrollPane = new JScrollPane(textArea);
		scrollPane.setPreferredSize(new Dimension(400,400)); 
		
//		Font size
		fontLabel = new JLabel("Font: ");
		fontSizeSpinner = new JSpinner();
		fontSizeSpinner.setPreferredSize(new Dimension(50,25));
		fontSizeSpinner.setValue(20);
		fontSizeSpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				textArea.setFont(new Font(textArea.getFont().getFamily(),(int)Font.PLAIN,(int) fontSizeSpinner.getValue()));
			}
		});
		
//		color
		colorButton = new JButton("Color");
		colorButton.addActionListener(this);
		
//		font combo box
		String[]fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();//take all the fonts alvailable in java
		
		fontBox = new JComboBox(fonts);
		fontBox.addActionListener(this);
		fontBox.setSelectedItem("Arial");
		
//		add component to frame
		this.add(fontLabel);
		this.add(fontSizeSpinner);
		this.add(colorButton);
		this.add(fontBox);
		this.add(scrollPane);
		
		
//		set color
		
	}
	
	void setFrame() {
//		Menu Bar =========================================================
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		
		openMenuItem = new JMenuItem("Open");
		openMenuItem.addActionListener(this);
		
		saveMenuItem = new JMenuItem("Save");
		saveMenuItem.addActionListener(this);
		
		exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(this);
		
		//add
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(exitMenuItem);
		menuBar.add(fileMenu);
		
		
		this.getContentPane().setBackground(Color.decode("#79B4B7"));
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(500,500);
		this.setVisible(true);
		this.setLocation(900,300);
		this.setLayout(new FlowLayout());
		this.setTitle("Text Editor");
		this.setJMenuBar(menuBar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==colorButton) {
			JColorChooser colorChooser = new JColorChooser();
			Color color = colorChooser.showDialog(null, "Choose a color!", Color.black);
			
			textArea.setForeground(color);
		}else
			if(e.getSource()==fontBox) {
				//change font family 
				textArea.setFont(new Font((String) fontBox.getSelectedItem(),Font.PLAIN,textArea.getFont().getSize()));
		}else
			if(e.getSource()==openMenuItem) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File("."));
				
				//filter to only show .txt files
				FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files","txt");
				fileChooser.setFileFilter(filter);
				
				int response = fileChooser.showOpenDialog(null);
				
				if (response == JFileChooser.APPROVE_OPTION) {
					String path = fileChooser.getSelectedFile().getAbsolutePath();
					File file = new File(path);
					Scanner reader;
					
					try {
						reader = new Scanner(file);
						if (file.isFile()) {
							while(reader.hasNextLine()) {
								String data = reader.nextLine();
								textArea.setText(data);
							}
							reader.close();
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
		}else 
			if(e.getSource()==saveMenuItem) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("."));
			
			int response = fileChooser.showSaveDialog(null); // 1 or 0 response
			
			if (response == JFileChooser.APPROVE_OPTION) { //if they find a suitable file location
				FileWriter fileOut;
				
				String path = fileChooser.getSelectedFile().getAbsolutePath();
				File file = new File(path);
				
//				print
				try {
					fileOut = new FileWriter(path);
//					fileOut.println(textArea.getText());
					fileOut.write(textArea.getText());
					fileOut.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}else
			if(e.getSource()==exitMenuItem) {
				if (JOptionPane.showConfirmDialog(this, "Confirm if you want to exit", "tic tac toe", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION) {
					System.exit(0);
				}
			}
	}

}
