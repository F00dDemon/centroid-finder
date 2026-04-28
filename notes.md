Rough flow of how program runs:
- determine distance between two colors
- take distance to determine if color is 1 or 0
- binarize image
- find groups of white pixels in image
- write found groups of white pixels into csv file

ImageSummaryApp connections:
- EuclideanColorDistance -> ColorDistanceFinder
- DistanceImageBinarizer -> ImageBinarizer
- BinarizingImageGroupFinder -> ImageGroupFinder

ImageSummaryApp - Has 3 parameters; image path, target color in hex, threshold for binarization.

Implement order;

DfsBinaryGroupFinder -> EuclideanColorDistance -> DistanceImageBinarizer -> BinarizingImageGroupFinder

1. BinaryGroupFinder