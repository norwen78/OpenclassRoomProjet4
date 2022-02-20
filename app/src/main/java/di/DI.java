package di;

import service.DummyReunionApiService;
import service.ReunionApiService;

public class DI {
    private static ReunionApiService service = new DummyReunionApiService();

    public static ReunionApiService getReunionApiService() {
        return service;
    }

    public static ReunionApiService getNewReunionApiService() {
        return new DummyReunionApiService();
    }
}
