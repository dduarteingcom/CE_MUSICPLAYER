package com.example.ce_musicplayer;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;

public class Arduino {
    static SerialPort serial_Port;

    public static void prueba() throws IOException {

        SerialPort[] get_port = SerialPort.getCommPorts();
        for(SerialPort port : get_port){

            System.out.println(port.getSystemPortName());
            serial_Port = SerialPort.getCommPort(port.getSystemPortName());

            if(serial_Port.openPort()){
                serial_Port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
                System.out.println("Puerto Abierto");
            }
            else {
                System.out.println("Puerto No Encontrado");
            }
        }
        serial_Port.setBaudRate(9600);
        InputStream inputStream = serial_Port.getInputStream();

        while (true){
            char msg = (char) inputStream.read();
            System.out.println(msg);
        }
    }
}