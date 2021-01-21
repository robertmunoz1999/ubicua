
import com.teamdev.jxmaps.*;
import com.teamdev.jxmaps.MapReadyHandler;
import com.teamdev.jxmaps.MapStatus;
import com.teamdev.jxmaps.swing.MapView;
import java.awt.BorderLayout;
import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import javax.swing.JFrame;

/**
 *
 * @author rault
 */
public class Mapa extends MapView {

    private Map map;
    private ArrayList<String[]> lista = new ArrayList<String[]>();
    private HashMap<Integer, ArrayList<String>> Matriz = new HashMap<Integer, ArrayList<String>>();
            
    public Mapa(String nName) {
        JFrame frame = new JFrame(nName);
        Cliente c = new Cliente();//inicializamos cliente
        String infoCubosActivos = c.consultarCubosActivos();//consultamos la informacion del cubo
        String arr[] = infoCubosActivos.split("<");//almacenamos la info en un array
        String temp = "";
        for (int i = 0; i < arr.length; i++) {//añadimos las filas
            temp = arr[i];
            String[] split = temp.split(",");//volvemos a separar los valores , esta vez por comas, de manera que queden los 5 elementos separados
            ArrayList<String> valoresCubos = new ArrayList<String>();//hacemos y rellenamos el ArrayList de estos elementos
            valoresCubos.add(split[0]);
            valoresCubos.add(split[1]);
            valoresCubos.add(split[2]);
            valoresCubos.add(split[3]);
            valoresCubos.add(split[4]);
            Matriz.put(i,valoresCubos);//los añadimos a la matriz, siendo la key = i
            //System.out.println(Matriz.get(Integer.parseInt(split[0])));
        }
        //Ejemplos dpara sacar los datos del primer elemento de la Matriz
//        System.out.println(Matriz.get(0).get(0));
//        System.out.println(Matriz.get(0).get(1));
//        System.out.println(Matriz.get(0).get(2));
//        System.out.println(Matriz.get(0).get(3));
//        System.out.println(Matriz.get(0).get(4));
        
        setOnMapReadyHandler(new MapReadyHandler() {
            @Override
            public void onMapReady(MapStatus status) {
                if (status == MapStatus.MAP_STATUS_OK) {
                    map = getMap();
                    MapOptions mapOptions = new MapOptions();
                    MapTypeControlOptions controlOptions = new MapTypeControlOptions();
                    mapOptions.setMapTypeControlOptions(controlOptions);

                    map.setOptions(mapOptions);
                    map.setCenter(new LatLng(40.417471, -3.713230));
                    map.setZoom(11.0);

                    ArrayList<Marker> marcadores = new ArrayList<Marker>();//almacena los marcadores compuestos por coordenadas de cubos activos
                    for (int i = 0; i < Matriz.size(); i++) {
                        Marker m = new Marker(map);
                        m.setPosition(new LatLng(Double.parseDouble(Matriz.get(i).get(3)), Double.parseDouble(Matriz.get(i).get(4))));//se crea el marcador en la posicion: LAT LONG sacada de la matriz
                        marcadores.add(m);
                    }
                    System.out.println("Hay " + marcadores.size() + " cubos activos.");

                }
            }

        });
        frame.add(this, BorderLayout.CENTER);
        frame.setSize(700, 500);
        frame.setVisible(true);
    }

}
