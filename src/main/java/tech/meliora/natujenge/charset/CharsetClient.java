package tech.meliora.natujenge.charset;

import tech.meliora.natujenge.charset.util.ByteToHexPrinter;
import tech.meliora.natujenge.charset.util.StringToByteConverter;

import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.time.LocalDateTime;

public class CharsetClient {

    public void send(String ip, int port, String message, String encodingCharset) {

        Socket clientSocket = null;
        BufferedReader in = null;

        DataOutputStream dataOut = null;

        System.out.println(LocalDateTime.now() + "|server_ip: " + ip + "|server_port: " + port + "|about to connect");

        try {

            System.out.println("**************************************************");

            // the handshake happens... TCP handshake happens
            clientSocket = new Socket(ip, port);

            System.out.println(LocalDateTime.now() + "|server_ip: " + ip + "|server_port: " + port
                    + "|client_port: " + clientSocket.getLocalPort() + "|connected ");

            //prepare to read and write
            dataOut =  new DataOutputStream(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println(LocalDateTime.now() + "|server_ip: " + ip + "|server_port: " + port
                    + "|client_port: " + clientSocket.getLocalPort() + "|about to send ");


            //prepare the bytes to send - encoding happens from chars to binary(hex) or byte array (8 bits = 1 byte)
            byte[] bytesToSend = StringToByteConverter.convert(message, encodingCharset);

            System.out.println("### Message to send [" + message + " ]");

            System.out.println("### Bytes (" + encodingCharset + ") to send in Hex [" + ByteToHexPrinter.bytesToHex(bytesToSend) + " ]");

            System.out.println("### Bytes (" + encodingCharset + ") to send count [" + bytesToSend.length + " ]");
            System.out.println("### Characters (" + encodingCharset + ") to send count [" + message.length() + " ]");

            // sends data as byte stream or byte array
            dataOut.writeInt(bytesToSend.length); // write length of the message
            dataOut.write(bytesToSend);

            System.out.println(LocalDateTime.now() + "|server_ip: " + ip + "|server_port: " + port
                    + "|client_port: " + clientSocket.getLocalPort() + "|data sent");


            System.out.println("**************************************************\n\n\n\n");


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {

                if(in != null){
                    in.close();
                }

                if(dataOut != null){
                    dataOut.close();
                }


                //the terminate handshake happens... TCP session is closed

                if(clientSocket != null){
                    clientSocket.close();
                }

                System.out.println(LocalDateTime.now() + "|server_ip: " + ip + "|server_port: " + port
                        + "|client_port: " + clientSocket.getLocalPort() + "|closing the connection");

            } catch (Exception ex) {

                ex.printStackTrace(System.out);

            }
        }

    }



}
