package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	static Socket socket = null;
	static BufferedReader bufferedReader;
	static BufferedWriter bufferedWriter;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UI ui = new UI();
		ui.start();
		try {
//			args[0], args[1] convert from str to int 
//			socket = new Socket("localhost", 8888);
			socket = new Socket(args[0], Integer.parseInt(args[1]));
		} catch (UnknownHostException e) {
//			e.printStackTrace();
			System.out.println("UnknownHostException: " + e);
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println("IOException: " + e);
		}

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException: " + e);
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		try {
			bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException: " + e);
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		System.out.println("Server connection succeed");
	}

}
