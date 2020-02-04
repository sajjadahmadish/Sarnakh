package project.utils.map;


import project.utils.map.model.Direction;


public interface DirectionCallback {
    void onDirectionSuccess(Direction direction, String rawBody);
    void onDirectionFailure(Throwable t);
}
