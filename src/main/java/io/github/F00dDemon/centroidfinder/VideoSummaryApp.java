package io.github.F00dDemon.centroidfinder;
//we can then do int xValue = centroid.x() and int yValue = centroid.y() to pull the individual values
//should be useful when we deal with appending the values into the array for the video CSV file

public class VideoSummaryApp {
    public int width;
    public int height;
    public double frameRate;
    public long frameCount;
    public long durationMicros;
    public String videoCodec;
    public String format;
}
