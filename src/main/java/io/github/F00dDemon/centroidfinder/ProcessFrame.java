package io.github.F00dDemon.centroidfinder;

import java.awt.image.BufferedImage;
import java.util.List;

public class ProcessFrame {
    public Group ProcessSingleFrame(BufferedImage frame, int targetColor, int threshold, int second) {
        ColorDistanceFinder distanceFinder = new EuclideanColorDistance();
        ImageBinarizer binarizer = new DistanceImageBinarizer(distanceFinder, targetColor, threshold);
        
        ImageGroupFinder groupFinder = new BinarizingImageGroupFinder(binarizer, new DfsBinaryGroupFinder());
        
        List<Group> groups = groupFinder.findConnectedGroups(frame);
        return groups.get(0);
    }
}
