import dayjs from 'dayjs';
import { TransactionStatus } from 'app/shared/model/enumerations/transaction-status.model';

export interface IRecentTransaction {
  id?: number;
  description?: string;
  amount?: number;
  status?: TransactionStatus;
  tCreated?: string;
}

export const defaultValue: Readonly<IRecentTransaction> = {};
