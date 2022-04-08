package server;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JTextPane;

import client.Client;
import client.UI;

public class Connection extends Thread {
	Socket socket = null;
	BufferedReader input;
	BufferedWriter output;
	ConcurrentHashMap<String, String> dictionary = new ConcurrentHashMap<String, String>();
	String searchResult = null;
	@Override
	public void run() {
//		super.run();
		try {
			while(true) {
				String request = input.readLine();
				System.out.println("connection request: "+ request);
				String[] splitRequest = request.split("-");
				
				String operation = splitRequest[0];
				String word = splitRequest[1];
				switch(operation) {
				case "Add":
					String addMeaning = splitRequest[2];
					if(!dictionary.containsKey(word)) {
						dictionary.put(word, addMeaning);	
						Server.writeDictionary();
						output.write(word + " added successfully" + "\n");
						output.flush();
						System.out.println(word + "added successfully");
					}
					else {
						output.write(word + " is already existed" + "\n");
						output.flush();
						System.out.println(word + " is already existed");
					}
					break;
					
				case "Search":
					System.out.println("search word: " + word);
					if(!dictionary.containsKey(word)) {
						System.out.println(word + " is not existed");
						output.write(word + " is not existed\n");
						output.flush();
					}else {
						searchResult = dictionary.get(word);
						System.out.println("searchResult: " + searchResult);
						output.write(searchResult + "\n");
						output.flush();
					}
					break;
					
				case "Delete":
					if(!dictionary.containsKey(word)) {
						System.out.println(word + " is not existed");
						output.write(word + " is not existed\n");
						output.flush();
					}else {
						dictionary.remove(word);
						Server.writeDictionary();
						System.out.println(word + " is deleted");
						output.write(word + " is deleted" + "\n");
						output.flush();
					}
					break;
					
				case "Update":
					String updateMeaning = splitRequest[2];
					if(!dictionary.containsKey(word)) {	
						System.out.println(word + "is not existed");
						output.write(word + "is not existed" + "\n");
						output.flush();
					}
					else {
						dictionary.put(word, updateMeaning);
						Server.writeDictionary();
						System.out.println(word + " updated successfully");
						output.write(word + " updated successfully" + "\n");
						output.flush();
					}
					break;
				
				default:
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("client disconnected: "+ e);
		}	
	}
	
	public Connection(Socket socket, ConcurrentHashMap<String, String> dictionary) {
		this.socket = socket;
		try {
			input = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException: " + e);
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		try {
			output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException: " + e);
		} catch (IOException e) {
			System.out.println("IOException: " +  e);
		}
		
		this.dictionary = dictionary;
	}

}
