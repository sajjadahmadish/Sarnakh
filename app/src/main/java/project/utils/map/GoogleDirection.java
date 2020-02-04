package project.utils.map;

import project.utils.map.request.DirectionOriginRequest;


public class GoogleDirection {
    public static DirectionOriginRequest withServerKey(String apiKey) {
        return new DirectionOriginRequest(apiKey);
    }
}
