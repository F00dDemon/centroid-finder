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

    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Usage: java VideoSummaryApp <input_video> <hex_target_color> <threshold>");
            return;
        }

        String inputVideoPath = args[0];
        String hexTargetColor = args[1];
        int threshold = 0;
        try {
            threshold = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.err.println("Threshold must be an integer.");
            return;
        }

        ExtractVideoData metaData = new ExtractVideoData();
        try {
            metaData.extractMetadata(inputVideoPath);
        } catch (Exception e) {
            System.err.println(e);
            return;
        };

    }
}
