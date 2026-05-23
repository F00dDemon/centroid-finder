Create video process main class:

        Class 1
        - Takes the path of the video
        - Validates it is a video
        - Extract metadata

    {Loop:

        Class 2
        - Take video meta data & path
        - Take the needed frame by 
        - Outputs a bufferedimage

        Class 3
        - Take in a buffered image.
        - Processes single framerate/sec frame,
        - Outputs centroid x and y coordinates 

    Add to array, if video is not done, go back}

        Class 4
        - Reads the array, writes to a csv

targetColor: 53020A
threshold: 50

StreamsAPI