package io.toro.ojtbe.jimenez.Graphify.core;

import java.util.ArrayList;
import java.util.List;

public class Pipeline<T> {

    private final List<Handler<T>> handlers;

    public Pipeline(){
        handlers = new ArrayList<>();
    }

    public Pipeline<T> addHandler(Handler<T> handler){
        handlers.add(handler);

        return this;
    }

    public T execute(T firstInput){
        T processed = firstInput;

        for(Handler<T> handler : handlers){
            processed = handler.process(processed);
        }

        return processed;
    }
}