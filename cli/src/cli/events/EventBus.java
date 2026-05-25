package cli.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventBus {
    private final Map<String, List<EventHandler>> handlers = new HashMap<>();

    public void subscribe(String eventType, EventHandler handler) {
        handlers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(handler);
    }

    public void publish(String eventType, Object data) {
        List<EventHandler> list = handlers.getOrDefault(eventType, new ArrayList<>());
        for (EventHandler h : list) {
            h.handle(new Event(eventType, data));
        }
    }
}