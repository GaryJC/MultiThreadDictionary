package client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextPane;

import server.Server;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class UI extends Thread{

	private JFrame frmDictionary;
	private JTextField wordField;
	private JTextField meaningField;
	
	JTextPane textPane = new JTextPane();
	/**
	 * Launch the application.
	 */
	@Override
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UI window = new UI();
					window.frmDictionary.setVisible(true);
				} catch (Exception e) {
//					e.printStackTrace();
					System.out.println("Exception: " + e);
				}			
			}
		});
	}
	

	/**
	 * Create the application.
	 */
	public UI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	
	public void communication(String request) throws IOException {
		Client.bufferedWriter.write(request);
		Client.bufferedWriter.flush();
		
		String res = Client.bufferedReader.readLine();
		System.out.println("res: " + res);
		textPane.setText(res);	
	}
	
	private void initialize() {
		frmDictionary = new JFrame();
		frmDictionary.setTitle("Dictionary");
		frmDictionary.setResizable(false);
		frmDictionary.setBounds(100, 100, 550, 425);
		frmDictionary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDictionary.getContentPane().setLayout(null);
		
		JLabel headerLabel = new JLabel("Dictionary");
		headerLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
		headerLabel.setBounds(200, 23, 121, 32);
		frmDictionary.getContentPane().add(headerLabel);
		
		JLabel wordLabel = new JLabel("Word:");
		wordLabel.setBounds(115, 75, 46, 14);
		frmDictionary.getContentPane().add(wordLabel);
		
		JLabel meaningLabel = new JLabel("Meaning:");
		meaningLabel.setBounds(115, 122, 46, 14);
		frmDictionary.getContentPane().add(meaningLabel);
		
		wordField = new JTextField();
		wordField.setBounds(171, 72, 216, 20);
		frmDictionary.getContentPane().add(wordField);
		wordField.setColumns(10);
		
		meaningField = new JTextField();
		meaningField.setBounds(171, 119, 216, 20);
		frmDictionary.getContentPane().add(meaningField);
		meaningField.setColumns(10);
		
		
		textPane.setEditable(false);
		textPane.setBounds(171, 212, 216, 128);
		frmDictionary.getContentPane().add(textPane);
		
		
		JButton addButton = new JButton("Add");
		addButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String word = wordField.getText();
				String meaning = meaningField.getText();
				if(word.isEmpty() || meaning.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Word or Meaning can not be empty!");
					System.out.println("Word or Meaning can not be empty");
				}else {
					System.out.println(word.isEmpty() || meaning.isEmpty());
					//JSON
					String request = "Add" + "-" + word + '-' + meaning + "\n";
					System.out.println("add request: " + request);
					try {
						communication(request);
					} catch (IOException e1) {
						System.out.println("IOException: " + e1);
					}
				}
			}
		});
		addButton.setBounds(72, 164, 89, 23);
		frmDictionary.getContentPane().add(addButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String word = wordField.getText();
				if(word.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Word can not be empty!");
				}else {
					String request = "Delete" + "-" + word + "\n";
					System.out.println("delete request: " + request);
					try {
						communication(request);
					} catch (IOException e1) {
						System.out.println("IOException: " + e1);
					}
				}
			}
		});
		deleteButton.setBounds(171, 164, 89, 23);
		frmDictionary.getContentPane().add(deleteButton);
		
		JButton updateButton = new JButton("Update");
		updateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String word = wordField.getText();
				String meaning = meaningField.getText();
				if(word.isEmpty() || meaning.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Word or Meaning can not be empty!");
					System.out.println("Word or Meaning can not be empty");
				}else {
					//JSON
					String request = "Update" + "-" + word + '-' + meaning + "\n";
					System.out.println("update request: " + request);
					try {
						communication(request);
					} catch (IOException e1) {
						System.out.println("IOException: " + e1);
					}
				}
			}
		});
		updateButton.setBounds(270, 164, 89, 23);
		frmDictionary.getContentPane().add(updateButton);
		
		JButton searchButton = new JButton("Search");
		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String word = wordField.getText();
				if(word.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Word can not be empty!");
				}else {
					String request = "Search" + "-" + word + "\n";
					System.out.println("search request: " + request);
					try {
						communication(request);
					} catch (IOException e1) {
						System.out.println("IOException: " + e1);
					}
				}
			}
		});
		searchButton.setBounds(369, 164, 89, 23);
		frmDictionary.getContentPane().add(searchButton);
	}
}
