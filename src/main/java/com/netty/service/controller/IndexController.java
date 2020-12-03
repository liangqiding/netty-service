package com.netty.service.controller;

import com.netty.service.dao.DeviceRepository;
import com.netty.service.dao.DeviceService;
import com.netty.service.dao.PersonRepository;
import com.netty.service.domain.Device;
import com.netty.service.domain.Msgrecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.Exceptions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;


@RestController
public class IndexController {
    /**
     * 可以使用
     */
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceService deviceService;

    private static final int DELAY_PER_ITEM_MS = 1;

    @GetMapping("/save")
    public Mono<Msgrecord> save() {
        Msgrecord person = new Msgrecord();
        person.setDevice_id("111");
        person.setDatetime("2020-11-25");
        return personRepository.save(person);
    }

    @GetMapping(path = "/list", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Device> list() {
        return deviceService.findAll().flatMap(obj -> {
            return Mono.just(obj);
        }).delayElements(Duration.ofMillis(1000));
    }

    @GetMapping(path = "/test", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> test() {
        Flux<String> flux = Flux.just("something", "chain");
        flux.map(secret -> secret.replaceAll(".", "*"));
        flux.subscribe(next -> System.out.println("Received: " + next));
        return flux;
    }

    @GetMapping(path = "/test2", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> errorCount() {
        AtomicInteger errorCount = new AtomicInteger();
        return Flux.<String>error(new IllegalArgumentException())
                .doOnError(e -> errorCount.incrementAndGet())
                .retryWhen(Retry.from(companion ->
                        companion.map(rs -> {
                            if (rs.totalRetries() < 3) return rs.totalRetries();
                            else throw Exceptions.propagate(rs.failure());
                        })
                ));
    }

    @GetMapping("/list/one")
    public Flux<String> list1() throws InterruptedException {
        /**
         * one
         */
//        Flux<String> seq1 = Flux.just("foo", "bar", "foobar");
//        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
//        Flux<String> seq2 = Flux.fromIterable(iterable);
        /**
         * two
         */
//        Mono<String> noData = Mono.empty();
//        Mono<String> data = Mono.just("foo");
//        Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3);
//        numbersFromFiveToSeven.subscribe(System.out::println);

        /**
         *  throw Exception
         */
//        Flux<Integer> ints = Flux.range(1, 4)
//                .map(i -> {
//                    if (i <= 3) return i;
//                    throw new RuntimeException("Got to 4");
//                });
//        ints.subscribe(i -> System.out.println(i),
//                error -> System.err.println("*** Error *** " + error));
        /**
         *  throw Exception 2
         */
//        Flux<Integer> ints = Flux.range(1, 4);
//        ints.subscribe(i -> System.out.println(i),
//                error -> System.err.println("***Error*** " + error),
//                () -> System.out.println("Done"));
/**
 *   subscribe
 */
//        Flux<Integer> ints = Flux.range(1, 4);
//        ints.subscribe(i -> System.out.println(i),
//                error -> System.err.println("Error " + error),
//                () -> System.out.println("Done"),
//                sub -> sub.request(10));
        /**
         * generate
         */
//        Flux<String> flux = Flux.generate(
//                () -> 0,
//                (state, sink) -> {
//                    sink.next("3 x " + state + " = " + 3*state);
//                    if (state == 10) sink.complete();
//                    return state + 1;
//                });
        /**
         *  generate - Atomic Long
         */
//        Flux<String> flux = Flux.generate(
//                AtomicLong::new,
//                (state, sink) -> {
//                    long i = state.getAndIncrement();
//                    sink.next("3 x " + i + " = " + 3*i);
//                    if (i == 10) sink.complete();
//                    return state;
//                });
        /**
         * generate - AtomicLong consumer
         */
//        Flux<String> flux = Flux.generate(
//                AtomicLong::new,
//                (state, sink) -> {
//                    long i = state.getAndIncrement();
//                    sink.next("3 x " + i + " = " + 3*i);
//                    if (i == 10) sink.complete();
//                    return state;
//                }, (state) -> System.out.println("state: " + state));
        /**
         *  myEventProcessor
         */
//        Flux<String> bridge = Flux.create(sink -> {
//            myEventProcessor.register(
//                    new MyEventListener<String>() {
//
//                        public void onDataChunk(List<String> chunk) {
//                            for(String s : chunk) {
//                                sink.next(s);
//                            }
//                        }
//
//                        public void processComplete() {
//                            sink.complete();
//                        }
//                    });
//        });

//        Flux<String> bridge = Flux.create(sink -> {
//            sink.onRequest(n -> channel.poll(n))
//                    .onCancel(() -> channel.cancel())
//                    .onDispose(() -> channel.close())
//        });

//        Flux<String> alphabet = Flux.just(-1, 30, 13, 9, 20)
//                .handle((i, sink) -> {
//                    String letter = alphabet(i);
//                    if (letter != null)
//                        sink.next(letter);
//                });
//        alphabet.subscribe(System.out::println);

//        Flux<String> flux =
        Flux<String> stringFlux = Flux.interval(Duration.ofMillis(250))
                .map(input -> {
                    if (input < 30) return "tick " + input;
                    throw new RuntimeException("boom");
                })
                .onErrorReturn("Uh oh");

//        flux.subscribe(System.out::println);
        Thread.sleep(2100);


//        Flux<Tuple2<Long, String>> boom = Flux.interval(Duration.ofMillis(250))
//                .map(input -> {
//                    if (input < 3) return "tick " + input;
//                    throw new RuntimeException("boom");
//                })
//                .retry(1)
//                .elapsed();

//        Thread.sleep(2100);

        return stringFlux;
    }

    public String alphabet(int letterNumber) {
        if (letterNumber < 1 || letterNumber > 26) {
            return null;
        }
        int letterIndexAscii = 'A' + letterNumber - 1;
        return "" + (char) letterIndexAscii;
    }

    @GetMapping("/delete/{id}")
    public Mono<String> delete(@PathVariable("id") String id) {
        return personRepository.findById(id)
                .flatMap(x ->
                        personRepository.deleteById(x.getDevice_id())
                                .then(Mono.just("ok")))
                .defaultIfEmpty("not found");
    }

    @GetMapping("/update/{id}")
    public Mono<String> update(@PathVariable("id") String id) {
        return personRepository.findById(id)
                .flatMap(x -> {
                    x.setDevice_id("111");
                    return personRepository.save(x);
                })
                .map(x -> x.toString())
                .defaultIfEmpty("error");
    }

    @GetMapping("/find/{id}")
    public Mono<Msgrecord> findById(@PathVariable("id") String id) {
        return personRepository.findById(id)
                .map(x -> x)
                .defaultIfEmpty(new Msgrecord());
    }


}
