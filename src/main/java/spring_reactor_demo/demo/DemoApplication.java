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
        f1.map(x -> x.toUpperCase())        //map, es un operador de transformcion de sus elementos
                .subscribe(log::info);
    }



















    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        dishes.add("Tacos al pastor");
        dishes.add("Bandeja Paisa");
        dishes.add("Pan con Chicharron");
        createMono();
        createFlux();
        m1DoOnNext();
        m2Map();




    }

}
