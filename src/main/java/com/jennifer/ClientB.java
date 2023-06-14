package com.jennifer;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientB {

    BufferedReader bufferedreader;
    PrintWriter printwriter;
    Socket socket;

    public static void main(String[] args) {

        ClientB clientB = new ClientB();
        clientB.go();
    }
    public void go()
    {
        setUpNetworking();
        Thread thread = new Thread(new ReadFromServer());
        thread.start();
        try {
            BufferedReader bufferedCMDReader = new BufferedReader(new InputStreamReader(System.in));
            String message;
            while ((message = bufferedCMDReader .readLine()) != null)
            {
                printwriter.println(message);
                printwriter.flush();
            }
        }catch (IOException es)
        {
            es.printStackTrace();
        }


    }
    public void setUpNetworking(){
        try {
            socket = new Socket("127.0.0.1", 3200);
            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            bufferedreader = new BufferedReader(inputStreamReader);
            printwriter = new PrintWriter(socket.getOutputStream());
            System.out.println("connection successful");
        }catch (IOException es){
            es.printStackTrace();
        }
    }
    public class ReadFromServer implements Runnable{
            @Override
            public void run() {
                String message;
                try{
                    while ((message = bufferedreader.readLine())!= null){
                        System.out.println(message);
                        printwriter.println(message);
                    }
                }catch (IOException es){
                    es.printStackTrace();
                }

            }
    }

}


