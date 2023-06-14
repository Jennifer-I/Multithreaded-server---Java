package com.jennifer;

import org.w3c.dom.ls.LSOutput;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

public class ServerB {
    List<PrintWriter> writtenMessages;
    Socket socket;
    PrintWriter printWriter;


    public static void main(String[] args) {
        ServerB serverB = new ServerB();
        serverB.execute();




    }

    public void execute() {
        writtenMessages = new ArrayList<>();
        try {
            ServerSocket serverSocket = new ServerSocket(3200, 10);
            while (true) {
                socket = serverSocket.accept();
                printWriter = new PrintWriter(socket.getOutputStream());
                writtenMessages.add(printWriter);
                Thread thread = new Thread(new Handler(socket));
                thread.start();
                System.out.println("Connected to client");
            }
        } catch (IOException es) {
            es.printStackTrace();
        }
    }

    public void broadCast(String message) {
        for (PrintWriter writer : writtenMessages) {
            writer.println(message);
            writer.flush();
        }
    }

    public class Handler implements Runnable {
        BufferedReader bufferedReader;
        InputStreamReader inputStreamReader;
        Socket socket1;

        public Handler(Socket socket) {
            try {
                socket1 = socket;

                inputStreamReader = new InputStreamReader(socket1.getInputStream());
                bufferedReader = new BufferedReader(inputStreamReader);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void run() {
            String message;
            try {
                while ((message = bufferedReader.readLine()) != null) {
                    System.out.println(message);
//                    printWriter.println(message);
                    broadCast(message);
                }
            }catch (IOException es){
                es.printStackTrace();

            }

        }
    }

    }
