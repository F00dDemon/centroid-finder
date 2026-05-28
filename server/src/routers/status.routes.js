import express from 'express';
import * as statusController from '../controllers/status.controller.js';

const router = express.Router();

router.get('/:jobId/status', statusController.getStatus);

export default router;