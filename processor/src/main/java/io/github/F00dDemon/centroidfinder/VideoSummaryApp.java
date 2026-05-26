package io.github.F00dDemon.centroidfinder;
//we can then do int xValue = centroid.x() and int yValue = centroid.y() to pull the individual values
//should be useful when we deal with appending the values into the array for the video CSV file

import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

public class VideoSummaryApp {
    public static int width;
    public static int height;
    public static double frameRate;
    public static long frameCount;

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
        width = metaData.getWidth();
        height = metaData.getHeight();
        frameRate = metaData.getFrameRate();
        frameCount = metaData.getFrameCount();

        int currentFrame = 0;
        ProcessFrame processFrame = new ProcessFrame();
        Queue<Group> frames = new LinkedList<>();

        while(currentFrame < frameCount){
            try {
                BufferedImage frame = ExtractFrame.extractRGBFrame(inputVideoPath, currentFrame);
                Group frameToAdd = processFrame.ProcessSingleFrame(frame, Integer.parseInt(hexTargetColor, 16), threshold);
                frames.add(frameToAdd);
            } catch (Exception e) {
                System.err.println(e);
            };
            currentFrame += (int)frameRate;
        }

        try (PrintWriter writer = new PrintWriter("groups.csv")) {
            int size = frames.size();
            for(int i = 0; i < size ; i++){
                Group line = frames.poll();
                writer.println(frameCount+", "+i+", "+line.toCsvRow());
            }
            System.out.println("Groups summary saved as groups.csv");
        } catch (Exception e) {
            System.err.println("Error writing groups.csv");
            e.printStackTrace();
        }

    }
}
