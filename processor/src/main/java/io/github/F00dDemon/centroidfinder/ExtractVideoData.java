package io.github.F00dDemon.centroidfinder;

import org.bytedeco.javacv.FFmpegFrameGrabber;

/**
 * ExtractVideoData is responsible for reading basic metadata from a video file
 * using FFmpeg via JavaCV's FFmpegFrameGrabber.
 *
 * This class extracts:
 * - Video width (pixels)
 * - Video height (pixels)
 * - Frame rate (frames per second)
 * - Total frame count
 */
public class ExtractVideoData {
        /**
         * Video width in pixels.
         */
        public int width;
        
        /**
         * Video height in pixels.
         */
        public int height;

        /**
         * Frame rate of the video in frames per second.
         */
        public double frameRate;

        /**
         * Total number of frames in the video.
         */
        public int frameCount;

        /**
         * Returns a string representation of the extracted metadata.
         *
         * @return formatted string containing width, height, frameRate, and frameCount
         */
        @Override
        public String toString() {
            return "VideoMetadata{" +
                    "width=" + width +
                    ", height=" + height +
                    ", frameRate=" + frameRate +
                    ", frameCount=" + frameCount;
        }

        /**
         * Extracts metadata from the provided video file path.
         *
         * This method initializes FFmpegFrameGrabber, reads video properties,
         * and stores them in the instance fields:
         * 
         * - width
         * - height
         * - frameRate
         * - frameCount
         *
         * @param videoPath path to the input video file
         * @throws Exception if the video cannot be read or FFmpeg fails to process it
         */
        public void extractMetadata(String videoPath) throws Exception {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath)) {
            try {
                grabber.start();

                this.width = grabber.getImageWidth();
                this.height = grabber.getImageHeight();
                this.frameRate = grabber.getFrameRate();
                this.frameCount = grabber.getLengthInFrames();

            } finally {
                grabber.stop();
                grabber.release();
                }
            }
        }

        /**
         * @return video width in pixels
         */
        public int getWidth() {
            return width;
        }

        /**
         * @return total number of frames in the video
         */
        public long getFrameCount() {
            return frameCount;
        }

        /**
         * @return total number of frames in the video
         */
        public double getFrameRate() {
            return frameRate;
        }
        
        /**
         * @return video height in pixels
         */
        public int getHeight() {
            return height;
        }
        
}
