import fs from "fs";
import path from "path";
import { spawn } from "child_process";
import { v4 as uuidv4 } from "uuid";

export const startVideoJob = async ({filename, targetColor, threshold}) => {
    const safeFilename = path.basename(filename);
    const videosDir = path.resolve(process.env.VIDEOS_DIR);
    const inputVideo = path.join(videosDir, safeFilename);

    await fs.promises.access(inputVideo);

    const jobId = uuidv4();
    const jarPath = path.resolve(process.env.PROCESSOR_JAR);

    const child = spawn(
        "java",
        [
            "-jar",
            jarPath,
            inputVideo,
            targetColor,
            threshold,
        ],
        {
            detached: true,
            stdio: "ignore"
        }
    );

    child.unref();
    return jobId;
};