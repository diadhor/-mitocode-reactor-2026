package spring_reactor_demo.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    private Logger log = LoggerFactory.getLogger(DemoApplication.class);
    private List<String> dishes = new ArrayList();



//M1
    private void createMono() {
        Mono<String> m1 = Mono.just("Hello world");
        m1.subscribe(log::info);
    }


//    M2
    private void createFlux() {
        Flux<String> f1 = Flux.fromIterable(dishes);
        f1.collectList().subscribe(list -> log.info(list.toString())); //Retorna los elementos del flujo como un Mono comprimiendo esos datos del flujo!
//        f1.subscribe(log::info); //Rertorna un flujo de elementos
    }


//    M3
    private void m1DoOnNext() {
        Flux<String> f1 = Flux.fromIterable(dishes);
        f1.doOnNext(x -> log.info("Elemento: "+ x)).subscribe(); //Se dispara entre elemento y elemento, permite saber qué está ocurriendo antes de pasar al siguiente elemento
    }


//    M4
    private void m2Map() {
        Flux<String> f1 = Flux.fromIterable(dishes);
//        f1.map(x -> x.toUpperCase()); //cada accion genera un nuevo flujo con sus valores posiblemente alterados, para ver los cambios de ese flujo, debemos subscribirnos a él. O si no, guardar su valor dentro de una variable y subscribirse a ella.
//        f1.subscribe(log::info);  //Va a mostrar los elementos sin cambio alguno

        Flux<String> f2 = f1.map(String::toUpperCase);        //Map, es un operador de transformcion de sus elementos
        f2.subscribe(log::info);
    }


//  M5

    private void m3Flatmap() {
        Mono.just("jaime").map(x->34).subscribe(e->log.info("Data: "+e)); //Transforma el dato e imprime "Data: 34"
        Mono.just("jaime").map(x->Mono.just(34)).subscribe(e->log.info("Data: "+e)); //Debido a que el metodo map solo transforma ha de convertir el String "jaime" en un mono, retornando "Data:  Monojust"
        Mono.just("jaime").flatMap(x->Mono.just(34)).subscribe(e->log.info("Data: "+e)); // FlatMap aplana/descomprime el Mono pudiendo extraer su contenido para asi mostrarlo
    }

// M&

    private void m4Range() {
        Flux<Integer> fx = Flux.range(0,10);
        fx.map(x -> x * 2)
                .subscribe(s -> log.info("Data: "+s));
    }


//Continuar con la creacion del metodo que contiene la funcion de delay elements












    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        dishes.add("Tacos al pastor");
        dishes.add("Bandeja Paisa");
        dishes.add("Pan con Chicharron");
//        createMono();
//        createFlux();
//        m1DoOnNext();
//        m2Map();
//        m3Flatmap();
          m4Range();


    }

}
