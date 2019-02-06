package io.toro.ojtbe.jimenez.Graphify.core.poet;

public final class PoetFactory {
    private final PoetServiceImpl poetService = new PoetServiceImpl();

    public PoetService getPoetService(){
        return poetService;
    }
}
