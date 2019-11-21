/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exa15_alreves;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 *
 * @author oracle
 */
public class Exa15_AlREVEs {

    /**
     * @param args the command line arguments
     */
    public static Connection conexion = null;

    public static Connection getConexion() throws SQLException {
        String usuario = "hr";
        String password = "hr";
        String host = "localhost";
        String puerto = "1521";
        String sid = "orcl";
        String ulrjdbc = "jdbc:oracle:thin:" + usuario + "/" + password + "@" + host + ":" + puerto + ":" + sid;

        conexion = DriverManager.getConnection(ulrjdbc);
        return conexion;
    }

    public static void main(String[] args) throws XMLStreamException, FileNotFoundException, IOException, ClassNotFoundException, SQLException {

        String codigop=null;
        String nombrep=null;
        File xmlLect=new File("/home/oracle/Desktop/ProbaExer4/platos.xml");
        File txtLect =new File("/home/oracle/Desktop/ProbaExer4/composicion.txt");
        FileReader xLect=new FileReader(xmlLect);
        XMLInputFactory InstLect=XMLInputFactory.newInstance();
        XMLStreamReader lectorXML=InstLect.createXMLStreamReader(xLect);
       
        File fichero1 =new File("/home/oracle/Desktop/ProbaExer4/serialPlatos");
        FileOutputStream localizador1=new FileOutputStream(fichero1);
        ObjectOutputStream guardado1=new ObjectOutputStream(localizador1);
       
        int graxatotal=0;
        int graxat=0;
        String codigo=null;
       
         while(lectorXML.hasNext()!=false){
            lectorXML.next();
            if(lectorXML.getEventType()==XMLStreamReader.START_ELEMENT){ //Mostramos el codigo
                if(lectorXML.getAttributeValue(0)!=null){
                    codigop=lectorXML.getAttributeValue(0);
                }
            }
           
            if(lectorXML.getEventType()==XMLStreamReader.CHARACTERS){ //Mostramos el nombre
               
                nombrep=lectorXML.getText();
               
                //Trabajar a partir de aqui
                FileInputStream acceso=new FileInputStream(txtLect);
                BufferedInputStream buffer=new BufferedInputStream(acceso);
                DataInputStream cargador=new DataInputStream(buffer);
                
                String texto;
                graxatotal=0;
                while((texto=cargador.readLine()) !=null){
                    String[] separador=texto.split("#");
                    if(separador[0].equals(codigop)){
                        String sql="select * from componentes where codc='"+separador[1]+"'";
                        Connection conn=getConexion();
                        Statement st=conn.createStatement();
                        ResultSet rs=st.executeQuery(sql);
                            while(rs.next()){
                            graxat=Integer.parseInt(separador[2]);
                            graxatotal=(graxat*rs.getInt("graxa")/100)+graxatotal; //Conseguimos el total de grasa que es lo tercero que necesitamos y lo unico que queremos de la tabla
                            }
                    }
                }
                Platos p1=new Platos(codigop,nombrep,graxatotal); //Creamos el objeto platos dandole los parametros
                System.out.println(p1.getCodigop()+" "+p1.getNomep()+" "+p1.getGraxatotal());// Comprobacion. Se podria hacer con las variables que tenemos del xml
                guardado1.writeObject(p1);  //Guardado del objeto
                //Sin salir de aqui
            }
        }
        guardado1.writeObject(null); //Necesario para que desoues se pueda leer
        guardado1.close();
           
        
    }
}