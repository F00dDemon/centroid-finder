import express from 'express';
import dotenv from 'dotenv';
import path from 'path';
import videoRoutes from './routers/video.routes.js';

//configure Express.js app
const app = express();
dotenv.config();

//view engine
app.set("view engine", "ejs");
app.set("views", "src/views");

//static directories
app.use(express.static('public'));

//middleware
app.use(express.json());
app.use(express.urlencoded({ extended: true }));

const videoFolder = path.resolve(process.env.VIDEOS_DIR);

//routers
app.use("/videos", express.static(videoFolder));
app.use("/api/videos", videoRoutes);

export default app;