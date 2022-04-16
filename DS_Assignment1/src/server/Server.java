package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

	public static ConcurrentHashMap<String, String> dictionary = new ConcurrentHashMap<String, String>();

	public String searchResult = null;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		readDictionary();
		ServerUI serverUI = new ServerUI();
		serverUI.start();
		ServerSocket serverSocket = null;
		try {
//			serverSocket = new ServerSocket(8888);
			serverSocket = new ServerSocket(Integer.parseInt(args[0]));
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
			} catch (IOException e) {
				System.out.println("IOException: " + e);
			}
			Connection connection = new Connection(socket, dictionary);
			connection.start();
			System.out.println("Server received new connection");
		}

//		System.out.print("server closed");
	}

// 	read data from file
	public static void readDictionary() {
//		File file = new File("src/dictionary.txt");
		File file = new File("dictionary.txt");
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(new FileInputStream(file), "UTF-8");
		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
			System.out.println("UnsupportedEncodingException: " + e);
		} catch (FileNotFoundException e) {
//			e.printStackTrace();
			System.out.println("FileNotFoundException: " + e);
		}
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String line = null;
		try {
			line = bufferedReader.readLine();
			while (line != null) {
				System.out.println("line: " + line);
				String[] lines = line.split(":");
				dictionary.put(lines[0], lines[1]);
				line = bufferedReader.readLine();
			}
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("IOException: " + e);
		}
//		while (line != null) {
//			System.out.println("line: " + line);
//			String[] lines = line.split(":");
//			dictionary.put(lines[0], lines[1]);
//		}
		try {
			inputStreamReader.close();
		} catch (IOException e) {
			System.out.println("IOException: " + e);
//			e.printStackTrace();
		}
		try {
			bufferedReader.close();
		} catch (IOException e) {
			System.out.println("IOException: " + e);
//			e.printStackTrace();
		}
	}

//	
//	write data into file
	public static void writeDictionary() {
//		File file = new File("src/dictionary.txt");
		File file = new File("dictionary.txt");
		BufferedWriter bufferedWriter = null;
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(file));
		} catch (IOException e) {
			System.out.println("IOException: " + e);
//			e.printStackTrace();
		}
		for (Entry<String, String> entry : dictionary.entrySet()) {
			try {
				bufferedWriter.write(entry.getKey() + ":" + entry.getValue() + "\n");
			} catch (IOException e) {
				System.out.println("IOException: " + e);
//				e.printStackTrace();
			}
		}
		try {
			bufferedWriter.flush();
			bufferedWriter.close();
		} catch (IOException e) {
			System.out.println("IOException: " + e);
//			e.printStackTrace();
		}
	}
}
