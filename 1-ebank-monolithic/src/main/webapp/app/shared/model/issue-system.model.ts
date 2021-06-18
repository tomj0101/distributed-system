import dayjs from 'dayjs';

export interface IIssueSystem {
  id?: number;
  title?: string | null;
  description?: string | null;
  iCreated?: string | null;
}

export const defaultValue: Readonly<IIssueSystem> = {};
