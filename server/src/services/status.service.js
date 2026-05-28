import { jobStore } from '../store/jobStore.js';

export const getJobStatus = async (jobId) => {
  const job = jobStore.get(jobId);

  if (!job) return null;

  return job;
};