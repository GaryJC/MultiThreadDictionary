package server;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ServerUI extends Thread {

	private JFrame frame;
	static ConcurrentHashMap<String, String> dictionary = new ConcurrentHashMap<String, String>();
	public static JTextArea textArea = new JTextArea();
	/**
	 * Launch the application.
	 */
	public void run() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerUI window = new ServerUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ServerUI() {
		initialize();
	}
	
	public static void setServerDict() {
		dictionary = Server.dictionary;
		System.out.println("dictionaryString: " + dictionary);
		String dictionaryString = "";
		for (Entry<String, String> entry : dictionary.entrySet()) {
			dictionaryString += (entry.getKey() + ":" + entry.getValue() + "\n");
		}
		System.out.println("dictionaryString: " + dictionaryString);
		textArea.setText(dictionaryString);
	}
	
	public static void clearDictionary() {
		dictionary = Server.dictionary;
		dictionary.clear();
//		File file = new File("src/dictionary.txt");
		File file = new File("dictionary.txt");
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			System.out.println("IOException: " + e);
//			e.printStackTrace();
		}
//		for (Entry<String, String> entry : dictionary.entrySet()) {
//			try {
//				bufferedWriter.write(entry.getKey() + ":" + entry.getValue() + "\n");
//			} catch (IOException e) {
//				System.out.println("IOException: " + e);
////				e.printStackTrace();
//			}
//		}
		try {
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("IOException: " + e);
//			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 298);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		
		textArea.setBounds(98, 57, 237, 153);
		frame.getContentPane().add(textArea);
		setServerDict();
//		dictionary = Server.dictionary;
//		System.out.println("dictionaryString: " + dictionary);
//		String dictionaryString = "";
//		for (Entry<String, String> entry : dictionary.entrySet()) {
//			dictionaryString += (entry.getKey() + ":" + entry.getValue() + "\n");
//		}
//		System.out.println("dictionaryString: " + dictionaryString);
//		textArea.setText(dictionaryString);

		JLabel lblNewLabel = new JLabel("Server Client");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setBounds(148, 21, 151, 25);
		frame.getContentPane().add(lblNewLabel);
		
		JButton btnNewButton = new JButton("Clear");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				clearDictionary();
				textArea.setText("");
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 13));
		btnNewButton.setBounds(168, 221, 89, 27);
		frame.getContentPane().add(btnNewButton);
	}
}
