package io.github.F00dDemon.centroidfinder;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * ProcessFrame is responsible for analyzing a single video frame and extracting
 * the most relevant connected pixel group based on color similarity.
 *
 * The processing pipeline works as follows:
 * - Computes color distance using EuclideanColorDistance
 * - Binarizes the image using DistanceImageBinarizer
 * - Finds connected components using a DFS-based grouping algorithm
 *
 * The result is a list of Group objects representing connected regions
 * that match the target color within the specified threshold.
 *
 * This class returns only the first detected group (if any exist).
 * If no valid groups are found or an error occurs, a fallback "empty" group
 * is returned with a coordinate of (-1, -1).
 */
public class ProcessFrame {
    /**
     * Processes a single video frame and extracts the primary connected group
     * of pixels matching the target color within a given threshold.
     *
     * The method performs image analysis by:
     * - Computing Euclidean color distance
     * - Binarizing the image based on threshold similarity
     * - Detecting connected pixel regions (groups)
     *
     * Behavior notes:
     * - Only the first detected group is returned
     * - If no groups are found, a default empty group is returned
     * - Any exception during processing results in a fallback group
     *
     * @param frame the input video frame as a BufferedImage
     * @param targetColor the target color to compare against (integer RGB value)
     * @param threshold maximum allowed color distance for pixel inclusion
     * @return the first detected Group, or a fallback group if none exist
     */
    public Group ProcessSingleFrame(BufferedImage frame, int targetColor, int threshold) {
        try {
            ColorDistanceFinder distanceFinder = new EuclideanColorDistance();
            ImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, targetColor, threshold);
        
            ImageGroupFinder groupFinder = new BinarizingImageGroupFinder(binarizer, new DfsBinaryGroupFinder());
        
            List<Group> groups = groupFinder.findConnectedGroups(frame);

            if (groups == null || groups.isEmpty()) return new Group(0, new Coordinate(-1, -1)); 

            return groups.get(0);
        } 
        catch (Exception e) { return new Group(0, new Coordinate(-1, -1)); }
    }
}
