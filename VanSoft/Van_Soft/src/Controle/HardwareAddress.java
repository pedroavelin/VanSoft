/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HardwareAddress {

    public static String getMacAddress() throws UnknownHostException,
            SocketException {
        InetAddress ipAddress = InetAddress.getLocalHost();
        NetworkInterface networkInterface = NetworkInterface
                .getByInetAddress(ipAddress);
        byte[] macAddressBytes = networkInterface.getHardwareAddress();
        StringBuilder macAddressBuilder = new StringBuilder();
        
//                System.out.println(macAddressBytes); 
        if(macAddressBytes==null)
            return null;
        
        for (int macAddressByteIndex = 0; macAddressByteIndex < macAddressBytes.length; macAddressByteIndex++) {
            String macAddressHexByte = String.format("%02X",
                    macAddressBytes[macAddressByteIndex]);
            macAddressBuilder.append(macAddressHexByte);

            if (macAddressByteIndex != macAddressBytes.length - 1) {
                macAddressBuilder.append(":");
            }
        }

        return macAddressBuilder.toString();
    }

    public String GetMac() {

        InetAddress ip;
        try {

            ip = InetAddress.getLocalHost();
//            System.out.println("Current IP address : " + ip.getHostAddress());
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();

            System.out.print("Current MAC address : ");

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
            return sb.toString();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public String GetOP() {
        System.out.println("Sistema operacional: " + System.getProperty("os.name"));
        return System.getProperty("os.name");
    }

    public String GetNAME() {
        try {
            System.out.println("Nome da maquina: " + InetAddress.getLocalHost().getHostName());
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            Logger.getLogger(HardwareAddress.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String GetFULLNAME() {
        try {
            System.out.println("Nome completo da maquina: " + InetAddress.getLocalHost().getCanonicalHostName());

            return InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException ex) {
            Logger.getLogger(HardwareAddress.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void GetMSG() {

        try {

            System.out.println("Informacoes");
            System.out.println("Sistema operacional: " + System.getProperty("os.name"));
            System.out.println("IP/Localhost: " + InetAddress.getLocalHost().getHostAddress());
            try {
                System.out.println("MAC Address: " + getMacAddress());
            } catch (UnknownHostException ex) {
                Logger.getLogger(HardwareAddress.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SocketException ex) {
                Logger.getLogger(HardwareAddress.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                System.out.println("Nome da maquina: " + InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException ex) {
                Logger.getLogger(HardwareAddress.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Nome completo da maquina: " + InetAddress.getLocalHost().getCanonicalHostName());

        } catch (UnknownHostException ex) {
            Logger.getLogger(HardwareAddress.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
