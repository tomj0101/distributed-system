import dayjs from 'dayjs';
import { IOrderMaster } from 'app/shared/model/order-master.model';

export interface IOrderDetails {
  id?: number;
  userId?: number | null;
  registerDate?: string | null;
  orderMaster?: IOrderMaster | null;
}

export const defaultValue: Readonly<IOrderDetails> = {};
