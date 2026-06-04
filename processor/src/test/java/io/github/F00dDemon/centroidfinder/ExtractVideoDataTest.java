package io.github.F00dDemon.centroidfinder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class ExtractVideoDataTest {
    private static String TEST_VIDEO;

    @BeforeAll
    static void setup() {
        File file = new File("res/testInput/sample.mp4");
        TEST_VIDEO = file.getAbsolutePath();
        assertTrue(file.exists(), "Test video file should exist");
    }

    @Test
    void extractMetadataShouldReturnExpectedMetadata() throws Exception {
        ExtractVideoData metadata = new ExtractVideoData();
        metadata.extractMetadata(TEST_VIDEO);

        assertEquals(1920, metadata.getWidth(), "Width should match sample video");
        assertEquals(1080, metadata.getHeight(), "Height should match sample video");
        assertEquals(60.0, metadata.getFrameRate(), 0.1, "Frame rate should be approximately 60 FPS");
        assertEquals(600, metadata.getFrameCount(), 2, "Frame count should be approximately 600");
    }

    @Test
    void toStringShouldContainMetadataValues() {
        ExtractVideoData metadata = new ExtractVideoData();

        metadata.width = 1920;
        metadata.height = 1080;
        metadata.frameRate = 60.0;
        metadata.frameCount = 600;

        String result = metadata.toString();

        assertAll(
            () -> assertTrue(result.contains("width=1920")),
            () -> assertTrue(result.contains("height=1080")),
            () -> assertTrue(result.contains("frameRate=60.0")),
            () -> assertTrue(result.contains("frameCount=600"))
        );
    }

    @Test
    void extractMetadataShouldThrowForInvalidPath() {
        ExtractVideoData metadata = new ExtractVideoData();

        assertThrows(Exception.class, () -> metadata.extractMetadata("does-not-exist.mp4")
        );
    }
}