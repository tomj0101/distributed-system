import dayjs from 'dayjs';

export interface IStatus {
  id?: number;
  name?: string;
  description?: string;
  registerDate?: string | null;
}

export const defaultValue: Readonly<IStatus> = {};
