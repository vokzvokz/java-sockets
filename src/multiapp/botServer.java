package multiapp;

import chat.chatServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class botServer {
    private List<botServer.Connection> connections =
            Collections.synchronizedList(new ArrayList<botServer.Connection>());
    private ServerSocket server;
    public List<PrinterDevice> printerDevices = Collections.synchronizedList(new LinkedList<botServer.PrinterDevice>());

    public botServer(){
        try {
            server = new ServerSocket(botMain.Port);
            printerDevices.add(new PrinterDevice("HP", 2017, "Laser", 5.5, 17.3));
            printerDevices.add(new PrinterDevice("HP", 2014, "Laser", 5.1, 17.3));
            printerDevices.add(new PrinterDevice("HP", 2018, "Laser", 7.8, 17.2));
            printerDevices.add(new PrinterDevice("IBM", 1994, "Painter", 0.012, 18.));
            printerDevices.add(new PrinterDevice("IBM", 1997, "Matrix", 0.02, 18));
            printerDevices.add(new PrinterDevice("DNS", 2017, "High Energy Laser", 115.6, 17.2));
            printerDevices.add(new PrinterDevice("DNS", 2005, "Painter", 1, 17.5));
            printerDevices.add(new PrinterDevice("DNS", 2011, "Laser", 5.5, 17.3));
            printerDevices.add(new PrinterDevice("IBM", 1999, "Matrix", 0.5, 17.3));
            printerDevices.add(new PrinterDevice("Tesla", 2020, "Quantum Matrix", 9000, 0.0002));
            printerDevices.add(new PrinterDevice("SpaceX", 2019, "High Energy Laser", 4035, 3.3));
            printerDevices.add(new PrinterDevice("SpaceY", 2021, "P-numerated Matrix", 15000.3, 0.00000002));

            //System.out.println(printerDevices.get(0).toString()+printerDevices.get(1).toString()+printerDevices.get(2).toString()+printerDevices.get(3).toString());
            while (true) {
                Socket socket = server.accept();
                Connection con = new Connection(socket);
                connections.add(con);
                con.start();
                System.out.println("New user");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeAll();
        }
    }

    public class PrinterDevice{
        public String maker;
        public int year;
        public String tecnology;
        public double printSpeed;
        public double paperDiag;

        public PrinterDevice(String maker, int year, String tecnology, double printSpeed, double paperDiag) {
            this.maker = maker;
            this.year = year;
            this.tecnology = tecnology;
            this.printSpeed = printSpeed;
            this.paperDiag = paperDiag;
        }

        @Override
        public String toString() {
            return "\nCompany:  "+maker+
                    "\nIssued in "+year+" year"+
                    "\nTechnology: "+tecnology+
                    "\nPrint speed: "+printSpeed+
                    "\nPaper: "+paperDiag;
        }
    }

    private class Connection extends Thread {
        private BufferedReader in;
        private PrintWriter out;
        private Socket socket;

        private String name = "";

        public Connection(Socket socket) {
            this.socket = socket;

            try {
                in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true);

            } catch (IOException e) {
                e.printStackTrace();
                close();
            }
        }

        @Override
        public void run() {
            try {
                out.println("Write your name here\n");
                name = in.readLine();
                out.println("Hello, "+name);
                out.println("You are connected to Printers serv. \n"+
                           "Write your query:"+
                           "\n/findName [name] to search by company"+
                           "\n/findYear [year] to search by year of issue"+
                           "\n/findTechnology [technology*] to search by technology"+
                           "\n/findSpeedInRange [min speed] [max speed] to search by print speed"+
                           "\n/findPaperInRange [paper diagonal min] [max] to search by paper sizes");
                String str = "";
                while (true) {
                    str = in.readLine();
                    if(str.equals("exit")) break;
                    out.println(answer(str));
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                close();
            }
        }

        public void close() {
            try {
                in.close();
                out.close();
                socket.close();
                connections.remove(this);
                if (connections.size() == 0) {
                    botServer.this.closeAll();
                    System.exit(0);
                }
            } catch (Exception e) {
                System.err.println("Работа Сервера прервана.");
            }
        }

        private String answer(String param){
            String [] commands = param.split(" ");
            String finalAnswer = "";
            Iterator<PrinterDevice> it = printerDevices.iterator();
            boolean flag = false;
            switch (commands[0]){
                case "/findName":
                    while (it.hasNext()){
                        //System.out.println("1");
                        PrinterDevice device = it.next();
                        if(device.maker.contains(commands[1]) && commands[1].contains(device.maker)){
                            System.out.println("Added");
                            finalAnswer = finalAnswer+device.toString();
                            flag = true;
                        }
                    }
                    if(!flag) finalAnswer = "Wrong parrameters "+commands.length+" '"+commands[0]+"' '"+commands[1]+"'";
                    break;
                case "/findYear":
                    while (it.hasNext()){
                        //System.out.println("2");
                        PrinterDevice device = it.next();
                        if(device.year==Integer.parseInt(commands[1])){
                            System.out.println("Added");
                            finalAnswer = finalAnswer+device.toString();
                            flag = true;
                        }
                    }
                    if(!flag) finalAnswer = "Wrong parrameters";
                    break;
                case "/findTechnology":
                    while (it.hasNext()){
                        //System.out.println("3");
                        PrinterDevice device = it.next();
                        if(device.tecnology.contains(commands[1])){
                            System.out.println("Added");
                            finalAnswer = finalAnswer+device.toString();
                            flag = true;
                        }
                    }
                    if(!flag) finalAnswer = "Wrong parrameters";
                    break;
                case "/findSpeedInRange":
                    while (it.hasNext()){
                        PrinterDevice device = it.next();
                        if(device.printSpeed>=Double.parseDouble(commands[1]) && device.printSpeed<=Double.parseDouble(commands[2])){
                            finalAnswer = finalAnswer+device.toString();
                            flag = true;
                        }
                    }
                    if(!flag) finalAnswer = "Wrong parrameters";
                    break;
                case "/findPaperInRange":
                    while (it.hasNext()){
                        PrinterDevice device = it.next();
                        if(device.paperDiag>=Double.parseDouble(commands[1]) && device.paperDiag<=Double.parseDouble(commands[2])){
                            finalAnswer = finalAnswer+device.toString();
                            flag = true;
                        }
                    }
                    if(!flag) finalAnswer = "Wrong parrameters";
                    break;
                    default:
                        finalAnswer = "Wrong parrameters";
            }
            return finalAnswer;
        }
    }

    private void closeAll() {
        try {
            server.close();
            synchronized(connections) {
                Iterator<Connection> iter = connections.iterator();
                while(iter.hasNext()) {
                    ((Connection) iter.next()).close();
                }
            }
        } catch (Exception e) {}
    }
}
