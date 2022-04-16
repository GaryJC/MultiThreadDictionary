package client;

import java.awt.EventQueue;

import org.json.simple.JSONObject;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextPane;

import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class UI extends Thread{

	private JFrame frmDictionary;
	private JTextField wordField;
	private JTextField meaningField;
	private String operation;
	private String word;
	private String meaning;
	
	JTextPane textPane = new JTextPane();
	
	@SuppressWarnings("unchecked")
	private JSONObject createJSON() {
		JSONObject requestJson = new JSONObject();
		requestJson.put("operation", operation);
		requestJson.put("word", word);
		requestJson.put("meaning", meaning);
		return requestJson;
	}

//	requestJson.addProperty()
	
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
		frmDictionary.setResizable(false);
		frmDictionary.setTitle("Dictionary");
		frmDictionary.setBounds(100, 100, 502, 392);
		frmDictionary.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmDictionary.getContentPane().setLayout(null);
		
		JLabel headerLabel = new JLabel("Dictionary");
		headerLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
		headerLabel.setBounds(179, 11, 136, 32);
		frmDictionary.getContentPane().add(headerLabel);
		
		JLabel wordLabel = new JLabel("Word:");
		wordLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		wordLabel.setBounds(110, 72, 45, 14);
		frmDictionary.getContentPane().add(wordLabel);
		
		JLabel meaningLabel = new JLabel("Meaning:");
		meaningLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		meaningLabel.setBounds(87, 109, 68, 19);
		frmDictionary.getContentPane().add(meaningLabel);
		
		wordField = new JTextField();
		wordField.setBounds(171, 71, 174, 20);
		frmDictionary.getContentPane().add(wordField);
		wordField.setColumns(10);
		
		meaningField = new JTextField();
		meaningField.setBounds(171, 110, 174, 20);
		frmDictionary.getContentPane().add(meaningField);
		meaningField.setColumns(10);
		
		
		textPane.setEditable(false);
		textPane.setBounds(77, 193, 366, 128);
		frmDictionary.getContentPane().add(textPane);
		
		JButton addButton = new JButton("Add");
		addButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		addButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				String word = wordField.getText();
//				String meaning = meaningField.getText();
				operation = "Add";
				word = wordField.getText();
				meaning = meaningField.getText();
				if(word.isEmpty() || meaning.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Word or Meaning can not be empty!");
					System.out.println("Word or Meaning can not be empty");
				}else {
					System.out.println(word.isEmpty() || meaning.isEmpty());
					//JSON
//					String request = "Add" + "-" + word + '-' + meaning + "\n";
					String request = createJSON().toJSONString() + "\n";
					System.out.println("add request: " + request);
					try {
						communication(request);
					} catch (IOException e1) {
						System.out.println("IOException: " + e1);
					}
				}
			}
		});
		addButton.setBounds(77, 150, 84, 32);
		frmDictionary.getContentPane().add(addButton);
		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		deleteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				String word = wordField.getText();
				operation = "Delete";
				word = wordField.getText();
				if(word.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Word can not be empty!");
				}else {
//					String request = "Delete" + "-" + word + "\n";
					String request = createJSON().toJSONString() + "\n";
					System.out.println("delete request: " + request);
					try {
						communication(request);
					} catch (IOException e1) {
						System.out.println("IOException: " + e1);
					}
				}
			}
		});
		deleteButton.setBounds(171, 150, 84, 32);
		frmDictionary.getContentPane().add(deleteButton);
		
		JButton updateButton = new JButton("Update");
		updateButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		updateButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
//				String word = wordField.getText();
//				String meaning = meaningField.getText();
				operation = "Update";
				word = wordField.getText();
				meaning = meaningField.getText();
				if(word.isEmpty() || meaning.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Word or Meaning can not be empty!");
					System.out.println("Word or Meaning can not be empty");
				}else {
					//JSON
//					String request = "Update" + "-" + word + '-' + meaning + "\n";
					String request = createJSON().toJSONString() + "\n";
					System.out.println("update request: " + request);
					try {
						communication(request);
					} catch (IOException e1) {
						System.out.println("IOException: " + e1);
					}
				}
			}
		});
		updateButton.setBounds(265, 150, 84, 32);
		frmDictionary.getContentPane().add(updateButton);
		
		JButton searchButton = new JButton("Search");
		searchButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		searchButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				operation = "Search";
				word = wordField.getText();
//				String word = wordField.getText();
				if(word.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Word can not be empty!");
				}else {
//					String request = "Search" + "-" + word + "\n";
					String request = createJSON().toJSONString() + "\n";
					System.out.println("search request: " + request);
					try {
						communication(request);
					} catch (IOException e1) {
						System.out.println("IOException: " + e1);
					}
				}
			}
		});
		searchButton.setBounds(359, 150, 84, 32);
		frmDictionary.getContentPane().add(searchButton);
	}
}
