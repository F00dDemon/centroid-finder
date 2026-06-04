package io.github.F00dDemon.centroidfinder;

import java.awt.image.BufferedImage;
import java.io.IOException;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;

/**
 * ExtractFrame provides functionality for extracting a single video frame
 * from a video file and converting it into an RGB BufferedImage.
 *
 * This class uses FFmpeg via FFmpegFrameGrabber and converts
 * frames using Java2DFrameConverter.
 *
 * The returned image is always converted to an BufferedImage with TYPE_INT_RGB
 * to ensure consistent pixel format for downstream image processing.
 */
public class ExtractFrame {
    /**
     * Extracts a single frame from a video file and converts it to an RGB image.
     *
     * The method performs the following steps:
     * - Opens the video using FFmpegFrameGrabber
     * - Seeks to the requested frame index
     * - Grabs the frame as a Frame
     * - Converts it to a BufferedImage
     * - Forces conversion to RGB format (TYPE_INT_RGB)
     *
     * Note:
     * Frame accuracy depends on the underlying video codec and FFmpeg seeking behavior.
     * Some videos may not support exact frame seeking and may return nearby frames.
     *
     * @param videoPath path to the input video file
     * @param frameNumber index of the frame to extract (0-based)
     * @return RGB BufferedImage representing the extracted frame
     * @throws Exception if the video cannot be opened, the frame cannot be read, or conversion fails
     * @throws IOException if the requested frame cannot be retrieved (null frame)
     */
    public static BufferedImage extractRGBFrame(String videoPath, int frameNumber) throws Exception {
        try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(videoPath)) {
            try (Java2DFrameConverter converter = new Java2DFrameConverter()) {
                try {
                    grabber.start();
                    grabber.setFrameNumber(frameNumber);

                    Frame frame = grabber.grabImage();
                    if (frame == null) throw new IOException("Unable to grab frame " + frameNumber);
                    BufferedImage image = converter.convert(frame);

                    BufferedImage rgbImage = new BufferedImage(
                            image.getWidth(),
                            image.getHeight(),
                            BufferedImage.TYPE_INT_RGB
                    );

                    rgbImage.getGraphics().drawImage(image, 0, 0, null);
                    return rgbImage;

                } finally {
                    grabber.stop();
                    grabber.release();
                }
            }
        }
    }
}
