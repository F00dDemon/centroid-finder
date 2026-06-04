package io.github.F00dDemon.centroidfinder;

import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;

/**
 * VideoSummaryApp is the command-line entry point for processing a video
 * and generating a CSV summary of detected color-based groups over time.
 *
 * The application performs the following pipeline:
 * - Reads video metadata (width, height, frame rate, frame count)
 * - Iterates through the video at intervals based on frame rate
 * - Extracts frames from the video
 * - Processes each frame to detect connected pixel groups matching a target color
 * - Writes results to a CSV file (groups.csv)
 *
 * Usage: java VideoSummaryApp <input_video> <hex_target_color> <threshold>
 *
 * Arguments:
 * - input_video - Path to the input video file
 * - hex_target_color - Target color in hexadecimal format (e.g., FF0000)
 * - threshold - Maximum allowed color distance for pixel matching
 *
 * Output: groups.csv file containing processed frame group data
 *
 * Notes:
 * - Frames are sampled at intervals of approximately 1 frame / frameRate seconds
 * - If frame extraction or processing fails for a frame, it is skipped and logged
 */
public class VideoSummaryApp {
    /**
     * Width of the input video in pixels.
     */
    public static int width;

    /**
     * Height of the input video in pixels.
     */
    public static int height;

    /**
     * Frame rate of the input video (frames per second).
     */
    public static double frameRate;

    /**
     * Total number of frames in the input video.
     */
    public static long frameCount;

    /**
     * Entry point of the application.
     *
     * Parses command-line arguments, extracts video metadata, processes frames,
     * detects color-based groups, and writes results to a CSV file.
     *
     * @param args command-line arguments: input video path, hex color value (e.g. FF0000), threshold value (integer)
     */
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
