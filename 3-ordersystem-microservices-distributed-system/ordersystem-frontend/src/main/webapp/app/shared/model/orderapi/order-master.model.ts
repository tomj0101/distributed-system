import dayjs from 'dayjs';

export interface IOrderMaster {
  id?: number;
  paymentMethod?: string | null;
  total?: number | null;
  email?: string;
  registerDate?: string | null;
}

export const defaultValue: Readonly<IOrderMaster> = {};
